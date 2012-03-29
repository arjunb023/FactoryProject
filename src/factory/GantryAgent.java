/**
 * @author Jai Bapna
 */

package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import gui.components.GUIBin;
import gui.components.GUIGantry;

import shared.Bin;
import shared.enums.PartType;

import agent.Agent;

public class GantryAgent extends Agent implements Gantry {

	/** Data */

	public enum BinState {
		AWAITING_GUI_GETTING_NEW_BIN, GUI_GETTING_NEW_BIN, 
		READY_FOR_USE, DELIVER_TO_FEEDER, 
		GUI_BEING_SHUTTLED, GUI_BIN_DELIEVERED, 
		IN_USE_BY_FEEDER, AWAITING_PURGE, 
		GUI_PURGING, GUI_DONE_PURGING, PURGED,
		ON_FIRE, GUI_ON_FIRE
	};

	private List<FeederRequest> feederRequests = Collections
			.synchronizedList(new ArrayList<FeederRequest>());
	
	private List<MyBin> myBins = Collections
			.synchronizedList(new ArrayList<MyBin>());


	public static int requestCount = 0;
	
	private GUIGantry guiGantry;

	/**
	 * Private class that represents a Gantry Bin. Contains the name of the bin
	 * along with the bin state.
	 */
	private class MyBin {

		public BinState state;
		public Bin bin;
		public Feeder feeder;

		public MyBin(PartType type) {
			this.bin = new Bin(type);
			state = BinState.AWAITING_GUI_GETTING_NEW_BIN;
		}
		
		public MyBin(Bin bin) {
			this.bin = bin;
			state = BinState.READY_FOR_USE;
		}

		public void setFeeder(Feeder feeder) {
			this.feeder = feeder;
		}
	}

	private class FeederRequest {
		public PartType type;
		public Feeder feeder;

		public FeederRequest(PartType type, Feeder feeder) {
			this.type = type;
			this.feeder = feeder;
		}
	}

	// Timer for simulation
	Timer timer = new Timer();

	public GantryAgent(String name)
	{
		super();
		this.name = name;
	}

	/** Messages */
	public void msgGiveMeThisPart(PartType type, Feeder feeder) {
		requestCount++;
		print(feeder.getName() + " requesting part " + type + ". Request number " + requestCount);
		feederRequests.add(new FeederRequest(type, feeder));
		stateChanged();
	}

	public void msgPurgeThisBin(Bin bin) {
		print("Purge bin type " + bin.getPartType());
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.bin == bin) {
					if (myBin.state == BinState.IN_USE_BY_FEEDER) {
						myBin.state = BinState.AWAITING_PURGE;
						stateChanged();
					}
				}
			}
		}
	}

	@Override
	public void msgSetBinOnFire() {
//		for (MyBin myBin : myBins) {
//			if (myBin.state == BinState.READY_FOR_USE) {
//				myBin.state = BinState.ON_FIRE;
//				stateChanged();
//			}
//		}
	}
	/** FROM GUI **/

	@Override
	public void msgGUIDoneSettingBinOnFire(GUIBin bin) {
		print("Received msgGUIDoneSettingBinOnFire from guiGantry");
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.bin.guiBin == bin) {
					if (myBin.state == BinState.GUI_ON_FIRE) {
						myBin.state = BinState.AWAITING_PURGE;
						stateChanged();
					}
				}
			}
		}		
	}
	
	@Override
	public void msgGUIDelieveredBinToFeeder(GUIBin bin) {
		print("Received msgGUIDeliveredBinToFeeder from "
				+ guiGantry.getName());
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.bin.guiBin == bin) {
					myBin.state = BinState.GUI_BIN_DELIEVERED;
					stateChanged();
				}
			}
		}
	}

	@Override
	public void msgGUIDonePurging(GUIBin bin) {
		print("Gantry GUI done purging bin");
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.bin.guiBin == bin) {
					if (myBin.state == BinState.GUI_PURGING) {
						myBin.state = BinState.GUI_DONE_PURGING;
						stateChanged();
					}
				}
			}
		}
	}

	@Override
	public void msgGUIDoneGettingNewBin(GUIBin bin) {
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.bin.guiBin == bin) {
					if (myBin.state == BinState.GUI_GETTING_NEW_BIN) {
						print("Gantry GUI done getting new bin type "
								+ myBin.bin.getPartType());
						myBin.state = BinState.READY_FOR_USE;
						stateChanged();
					}
				}
			}
		}
	}

	/** Scheduler */
	public boolean pickAndExecuteAnAction() {
		
		synchronized(feederRequests)
		{
			StringBuilder sb = new StringBuilder();
			for (FeederRequest r : feederRequests)
			{
				//sb.append(r.feeder.getName().substring(r.feeder.getName().length()-1) + "," + r.type + "," + r.feeder + "  ");
				sb.append(r.feeder.getName().substring(r.feeder.getName().length()-1) + "," + r.type + "  ");
			}
			this.setCurrentState2(sb.toString());
		}
		
		boolean ok = true;
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.state == BinState.GUI_GETTING_NEW_BIN
					|| myBin.state == BinState.GUI_BEING_SHUTTLED 
					|| myBin.state == BinState.GUI_PURGING
					|| myBin.state == BinState.GUI_ON_FIRE) {
					ok = false;
				}
			}
		}

		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if(ok) {
					if (myBin.state == BinState.ON_FIRE) {
						GUISetBinOnFire(myBin);
						return true;
					}
				}
			}
		}
		
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if(ok) {
					if (myBin.state == BinState.AWAITING_PURGE) {
						GUIPurgeBin(myBin);
						return true;
					}
				}
			}
		}
		
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.state == BinState.GUI_DONE_PURGING) {
					PurgeBin(myBin);
					return true;
				}
			}
		}
			
		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if(ok) {
					if (myBin.state == BinState.AWAITING_GUI_GETTING_NEW_BIN) {
						GUIGetNewBin(myBin);
						return true;
					}
				}
			}
		}

		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if (myBin.state == BinState.GUI_BIN_DELIEVERED) {
					DeliverBinToFeeder(myBin);
					return true;
				}
			}
		}

		synchronized (myBins) {
			for (MyBin myBin : myBins) {
				if(ok) {
					if (myBin.state == BinState.DELIVER_TO_FEEDER) {
						GUIDeliverBinToFeeder(myBin);
						return true;
					}
				}
			}
		}
		
		synchronized (feederRequests) {
			for (FeederRequest request : feederRequests) {
				//if (!request.feeder.HasBin()) {
					if(ok) {
						synchronized (myBins) {
							for (MyBin myBin : myBins) {
								if (myBin.bin.getPartType() == request.type) {
									if (myBin.state == BinState.READY_FOR_USE) {
										DeliverToFeeder(myBin,request);
										return true;
									}
								}
							}
						}
					}
				//}
			}
		}
		
		synchronized (myBins) {
			StringBuilder s = new StringBuilder();
			s.append("Bin type states:");
			for (MyBin myBin : myBins) {
				s.append("\n\t" + myBin.state.toString());
			}
			print(s.toString());
		}
		
		return false;
	}

	/** Actions */
	public void GUISetBinOnFire(MyBin myBin) {
		print ("Setting bin " + myBin.bin.getPartType() + " on fire.");
		myBin.state = BinState.GUI_ON_FIRE;
		//guiGantry.msgSetBinOnFire(myBin.bin.guiBin);
	}
	public void DeliverToFeeder(MyBin myBin, FeederRequest request) {
		print ("Processing DeliverToFeeder request");
		myBin.setFeeder(request.feeder);
		myBin.state = BinState.DELIVER_TO_FEEDER;
		feederRequests.remove(request);
		stateChanged();
	}
	
	public void DeliverBinToFeeder(MyBin myBin) {
		print("Delivering Bin " + myBin.bin.getPartType() + " to Feeder");
		setCurrentState("Delivering " + myBin.bin.getPartType() + " to " + myBin.feeder.getName());
		myBin.feeder.msgHereIsBin(myBin.bin);
		myBin.state = BinState.IN_USE_BY_FEEDER;
		myBins.add(new MyBin(myBin.bin.getPartType()));
	}

	public void GUIDeliverBinToFeeder(MyBin myBin) {
		print("GUIDeliverBinToFeeder bin of " + myBin.bin.getPartType());
		myBin.state = BinState.GUI_BEING_SHUTTLED;
		guiGantry.msgMoveBinToFeeder(myBin.bin.guiBin,
				myBin.feeder.getGUIFeeder());
	}

	public void PurgeBin(MyBin myBin) {
		print("PurgeBin " + myBin.toString() + myBin.bin.getPartType() + " from Screen");
		myBin.feeder.msgGantryDonePurging();
		myBin.state = BinState.PURGED;
		myBins.remove(myBin);
	}

	public void GUIPurgeBin(MyBin myBin) {
		print("GUIPurgeBin " + myBin.bin.getPartType() );
		setCurrentState("Purging Bin " + myBin.bin.getPartType() + " from floor");
		guiGantry.msgPurgeBin(myBin.bin.guiBin);
		myBin.state = BinState.GUI_PURGING;
	}

	public void GUIGetNewBin(MyBin myBin) {
		print("GUIGetNewBin " + myBin.bin.getPartType());
		setCurrentState("Getting " + myBin.bin.getPartType());
		myBin.state = BinState.GUI_GETTING_NEW_BIN;
		guiGantry.msgGetNewBin(myBin.bin.guiBin);
	}

	/** EXTRA -- all the simulation routines */

	/* Returns the name of the cook */
	public String getName() {
		return name;
	}

	@Override
	public void setGUIGantry(GUIGantry guiGantry) {
		this.guiGantry = guiGantry;
	}
	
	public void setGantryBins(ArrayList<Bin> bins) {
		for (Bin bin:bins) {
			myBins.add(new MyBin(bin));
		}
	}
	
	public Bin getBin(int index) {
		return myBins.get(index).bin;
	}

}
