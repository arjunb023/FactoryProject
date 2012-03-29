package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import agent.Agent;
import shared.KitConfig;
import shared.Part;
import shared.enums.DiverterDirection;
import shared.enums.PartType;
import factory.LaneAgent.ManagerStatus;
import factory.interfaces.Diverter;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import gui.components.GUIDiverter;

/**
 * 
 * right now, this is only set up to carry one part at a time
 * 
 * @author Duke Yin
 *
 */
public class DiverterAgent extends Agent implements Diverter
{
	//String name;
	int diverterNumber;

	CellManagerAgent cellManagerAgent;
	
	Feeder feederAgent;
	/** top lane */
	Lane laneAgent1;
	/** bottom lane */
	Lane laneAgent2;
	/** currently chosen lane */
	Lane laneAgent;
	GUIDiverter guiDiverter;
	
	/** enum for storing TOP or BOTTOM */
	DiverterDirection orientation = DiverterDirection.TOP;
	
	public enum DiverterStatus
	{
		acquiring_part, holding_part,
		none
	};
	DiverterStatus status;
	
	public enum MovingStatus
	{
		orientation_change_requested, changing_orientation,
		none
	};
	MovingStatus movingStatus = MovingStatus.none;

	/** current Part the Diverter is moving */
	MyPart currentPart;
	/** next Part that the Feeder is waiting to send to this Diverter */
	private Queue<MyPart> nextParts = new LinkedList<MyPart>();

	/** current kitConfig from manager */
	private KitConfig currentConfig;
	
	private PartType partType1 = null;
	private PartType partType2 = null;

	public enum ManagerStatus
	{
		notice_received,
		dumping,
		none;
	}
	ManagerStatus managerStatus = ManagerStatus.none;
	
	public enum PartStatus
	{
		none,
		not_here_yet,
		not_done, doing, done,
		waiting_to_pass, waiting_to_pass_full, okay_to_pass,
		purge;
	};
	
	private class MyPart
	{
		Part part;
		PartStatus status;
		
		public MyPart(Part part, PartStatus status)
		{
			this.part = part;
			this.status = status;
		}
	}
	
	
	public DiverterAgent(String name)
	{
		super();
		this.name = name;

		this.diverterNumber = Integer.parseInt(this.name.substring(this.name.length()-1));
		
		this.currentPart = null;
		this.status = DiverterStatus.none;
	}
/*
 * MESSAGES
 */
	@Override
	@Deprecated
	public void msgSetDiverterOrientation(DiverterDirection direction)
	{
		this.movingStatus = MovingStatus.orientation_change_requested;
		this.orientation = direction;
		
		if (direction == DiverterDirection.TOP)
		{
			laneAgent = laneAgent1;
			stateChanged();
		}
		else if (direction == DiverterDirection.BOTTOM)
		{
			laneAgent = laneAgent2;
			stateChanged();
		}
	}

	boolean needToFlipAfterAcquiringPart = false;
	
	@Override
	public void msgFlipDiverterOrientation()
	{
		if (this.currentPart != null)
		{
			print("NOTE:  there shouldn't be parts queuing in the Diverter");
		}
		
		if (this.status == DiverterStatus.acquiring_part)
		{
			System.out.println("NOTE:  CLOBBERING PROBLEM WHICH SHOULD NOT HAPPEN, but I'm handling it now");
			this.needToFlipAfterAcquiringPart = true;
		}
		else
		{
			this.movingStatus = MovingStatus.orientation_change_requested;
		}

		this.movingStatus = MovingStatus.orientation_change_requested;
		
		stateChanged();
	}
	
	@Override
	public void msgGuiDoneChangingOrientation()
	{
		print("Received GuiDone changing orientation");
		this.status = DiverterStatus.none;
		stateChanged();
	}
	
	@Override
	public void msgPartReady(Part part)
	{
		print("Received PartReady from Feeder for part '" + part.getName() + "'");
		nextParts.add(new MyPart(part, PartStatus.not_here_yet));
		stateChanged();
	}

	@Override
	public void msgHereIsPart(Part part)
	{
		print("Received Part '" + part.getName() + "' from Feeder");
		try
		{
			if (part.equals(nextParts.peek().part))
			//if (part.equals(nextParts.remove().part))
			{
				nextParts.remove();
				currentPart = new MyPart(part, PartStatus.not_done);
				if (this.status == DiverterStatus.acquiring_part)
				{
					//this.status = DiverterStatus.none;
					this.status = DiverterStatus.holding_part;
					
					if (this.needToFlipAfterAcquiringPart == true)
					{
						print("Received Part from Feeder, so flipping DiverterOrientation now!");
						this.needToFlipAfterAcquiringPart = false;
						this.movingStatus = MovingStatus.orientation_change_requested;
					}
					
				}
			}
			else
			{
				print("v ERROR\n" +
						"ERROR: Diverter's nextParts.size() == " + nextParts.size() + ", Feeder gave me the wrong part!" +
						"^ ERROR");
				
				currentPart = new MyPart(part, PartStatus.not_done);
				if (this.status == DiverterStatus.acquiring_part)
				{
					//this.status = DiverterStatus.none;
					this.status = DiverterStatus.holding_part;
					
					if (this.needToFlipAfterAcquiringPart == true)
					{
						print("Received Part from Feeder, so flipping DiverterOrientation now!");
						this.needToFlipAfterAcquiringPart = false;
						this.movingStatus = MovingStatus.orientation_change_requested;
					}
					
				}
			}
		}
		catch (Exception e)
		{
			print("v ERROR\n" +
					"ERROR: Diverter's nextParts.size() == " + nextParts.size() + ", Feeder gave me the wrong part!" +
					"^ ERROR");
		}
		
		stateChanged();
	}
	
	@Override
	public void msgGuiDoneMovingPart()
	{
		//print("Received DoneMovingPart from GUI");
		currentPart.status = PartStatus.done;
		
		stateChanged();
	}

	@Override
	public void msgReadyToReceive(Part part)
	{
		//print("Received ReadyToReceive from Lane");
		if (currentPart != null)
		{
			currentPart.status = PartStatus.okay_to_pass;
		}
		else
			print("CURRENTPART NULL");
		
		
		stateChanged();
	}
	@Override
	public void msgIAmFull()
	{
		// is this message needed?
		print("Received IAmFull from Lane");
		currentPart.status = PartStatus.waiting_to_pass_full;
		
		stateChanged();
	}

	public void msgThisIsCurrentConfiguration(KitConfig currentConfig)
	{
		print("Received CurrentKitConfiguration from CellManager");
		this.currentConfig = currentConfig;
		
		this.managerStatus = ManagerStatus.notice_received;
		
		stateChanged();
	}
/*
 * SCHEDULER
 */
	/**
	 * diverter's scheduler
	 */
	@Override
	public boolean pickAndExecuteAnAction()
	{
		
		/*
		try
		{
			print("************************************************\n"
					+ "DIVERTER STATES\n"
					+ "diverterStatus == " + status +"\n"
					+ "managerStatus == " + managerStatus + "\n"
					+ "nextParts.size() == " + nextParts.size() + "\n"
					+ "currentPart == " + currentPart.part.getName() + "\n"
					+ "************************************************"
					);
		}
		catch(Exception e)
		{
			print("************************************************\n"
					+ "DIVERTER STATES\n"
					+ "diverterStatus == " + status +"\n"
					+ "managerStatus == " + managerStatus + "\n"
					+ "nextParts.size() == " + nextParts.size() + "\n"
					+ "currentPart == NULL\n"
					+ "************************************************"
					);
		}
		*/
		
		if (this.managerStatus == ManagerStatus.notice_received)
		{
			this.managerStatus = ManagerStatus.none;
			checkNextParts();
		}
		
		if (this.movingStatus == MovingStatus.orientation_change_requested
			&& this.status != DiverterStatus.acquiring_part
			&& currentPart == null)
		{
			this.movingStatus = MovingStatus.changing_orientation;
			changeDirection();
			return true;
		}
		if (this.status == DiverterStatus.none
			&& nextParts.size() >= 1
			&& currentPart == null)
		{
			if (nextParts.peek().status == PartStatus.purge)
			{
				print("deleting the voided part " + nextParts.peek().part.getName());
				nextParts.remove();
				return true;
			}
		
			prepareToReceive();
			return true;
		}
		/*
		if (currentPart != null && nextParts.size() > 1)
		{
			tellFeederHoldOn();
			return true;
		}
		*/
		if (currentPart != null)
		{
			if (currentPart.status == PartStatus.not_done)
			{
				addPart(currentPart);
				return true;
			}
		
			if (currentPart.status == PartStatus.doing)
			{
				movePart(currentPart);
				return true;
			}
		
			if (currentPart.status == PartStatus.done)
			{
				notifyNextAgent();
				return true;
			}
			
			if (currentPart.status == PartStatus.okay_to_pass)
			{
				passPart(currentPart);
				return true;
			}
		}
		
		print("Sleeping.");
		return false;
	}

/*
 * ACTIONS
 */
	private void prepareToReceive()
	{
		synchronized(nextParts)
		{
		print("PrepareToReceive " + nextParts.peek().part.getName());
		this.status = DiverterStatus.acquiring_part;
		feederAgent.msgDiverterReadyToReceive(nextParts.peek().part);
		}
	}
	private void tellFeederHoldOn()
	{
		print("FeederHoldOn");
		feederAgent.msgHoldOn();
	}
	
	private void addPart(MyPart p)
	{
		setCurrentState(p.part.getName() + " received");
		print("AddPart " + p.part.getName() + " to GUI");
		p.status = PartStatus.doing;
		guiDiverter.msgDoAddPart(p.part.guiPart);
	}
	
	private void movePart(MyPart p)
	{
		print("MovePart sent to GUI");
		p.status = PartStatus.none;
		guiDiverter.msgDoMovePart(p.part.guiPart);
	}
	
	private void notifyNextAgent()
	{
		print("NotifyNextAgent");
		currentPart.status = PartStatus.waiting_to_pass;
		laneAgent.msgPartReady(currentPart.part);
	}
	
	private void passPart(MyPart p)
	{
		print("PassPart (now NULL)");
		setCurrentState(p.part.getName() + " passed");
		laneAgent.msgHereIsPart(p.part);
		currentPart = null;
		this.status = DiverterStatus.none;
	}
	
	private void changeDirection()
	{
		print("ChangeDirection");
		
		if (this.orientation == DiverterDirection.TOP)
			this.orientation = DiverterDirection.BOTTOM;
		else //(this.orientation == DiverterDirection.BOTTOM)
			this.orientation = DiverterDirection.TOP;

		if (this.orientation == DiverterDirection.TOP)
		{
			laneAgent = laneAgent1;
		}
		else if (this.orientation == DiverterDirection.BOTTOM)
		{
			laneAgent = laneAgent2;
		}
		
		guiDiverter.msgDoChangeOrientation(this.orientation);
	}
	
	private void checkNextParts()
	{
		print("CheckNextParts");
		
		// number 1 *2->  2;  get 0 and 1
		// number 2 *2->  4;  get 2 and 3
		// number 3 *2->  6;  get 4 and 5
		// number 4 *2->  8;  get 6 and 7
		this.partType1 = this.currentConfig.kitConfig.get( diverterNumber*2 - 2 );
		this.partType2 = this.currentConfig.kitConfig.get( diverterNumber*2 - 1 );
		
		for (MyPart p : this.nextParts)
		{
			if (p.part.type != partType1
				&& p.part.type != partType2)
			{
				print("VOIDING part " + p.part.getName() + " that was to be received");
				p.status = PartStatus.purge;
			}
		}
		print("CheckNextParts done");
		this.cellManagerAgent.msgIAmProperlyConfigured(this);
	}
	
/*
 * EXTRA
 */
	public void setFeederAgent(Feeder feederAgent)
	{
		this.feederAgent = feederAgent;
	}
	/**
	 * set top lane
	 * also defaults Diverter to use this new top lane
	 * @param laneAgent
	 */
	public void setLaneAgent1(Lane laneAgent)
	{
		this.laneAgent1 = laneAgent;

		// default to top lane
		this.orientation = DiverterDirection.TOP;
		this.laneAgent = laneAgent1;
	}
	/**
	 * set bottom lane
	 * @param laneAgent
	 */
	public void setLaneAgent2(Lane laneAgent)
	{
		this.laneAgent2 = laneAgent;
	}

	public void setCellManagerAgent(CellManagerAgent cellManagerAgent)
	{
		this.cellManagerAgent = cellManagerAgent;
	}
	
	public void setGuiDiverter(GUIDiverter guiDiverter)
	{
		this.guiDiverter = guiDiverter;
	}
	
	@Override
	public DiverterDirection getOrientation()
	{
		return this.orientation;
	}
}
