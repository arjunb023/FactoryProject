package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import agent.Agent;
import shared.KitConfig;
import shared.Part;
import shared.enums.PartType;
import factory.interfaces.Diverter;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import gui.components.GUILane;
import gui.components.GUIPart;

public class LaneAgent extends Agent implements Lane
{
	//private String name;
	int laneNumber;
	
	Feeder feederAgent;
	Diverter diverterAgent;
	Nest nestAgent;
	GUILane guiLane;

	CellManagerAgent cellManagerAgent;
	
	/** the current kit configuration, received from the CellManager */
	KitConfig currentConfig;
	
	/** list of all parts the LaneAgent currently has */
	private List<MyPart> currentParts = Collections.synchronizedList(new ArrayList<MyPart>());
	
	/** next Part that the Diverter is waiting to send to this Lane */
	private Queue<MyPart> nextParts = new LinkedList<MyPart>();
	
	/** type of part this lane is accepting */
	private PartType partType = null;
	
	/** stays TRUE after one part has gotten through, and the configuration hasn't changed */
	private boolean gotMyFirstPart = false;
	
	public enum PartStatus
	{
		not_here_yet, coming_next,
		not_done, doing, done,
		ready_to_pass, trying_to_pass, passing;
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
	
	public enum LaneEntryStatus
	{
		okay_to_receive, capacity_alert, stop_receiving,
		refill_requested,
		none;
	};
	LaneEntryStatus entryStatus = LaneEntryStatus.none;
	
	public enum LaneExitStatus
	{
		stop_sending,
		none;
	};
	LaneExitStatus exitStatus = LaneExitStatus.none;
	
	public enum ManagerStatus
	{
		notice_received,
		checking,
		dumping,
		none;
	}
	ManagerStatus managerStatus = ManagerStatus.none;
	
	public LaneAgent(String name)
	{
		super();
		this.name = name;
		
		this.laneNumber = Integer.parseInt(this.name.substring(this.name.length()-1));
		
	}

/*
 * MESSAGES
 */
	@Override
	public void msgPartReady(Part part)
	{
		print("Received PartReady message from Diverter for part '" + part.getName() + "'");
		synchronized(this.nextParts){
			nextParts.add(new MyPart(part, PartStatus.not_here_yet));
		}
		
		stateChanged();
	}

	@Override
	public void msgHereIsPart(Part part)
	{
		if (!this.gotMyFirstPart)
			gotMyFirstPart = true;
		
		print("Received Part '" + part.getName() + "' from Diverter");

		synchronized(nextParts)
		{
			if (part.equals(nextParts.remove().part))
			{
				synchronized(this.currentParts){
					currentParts.add(new MyPart(part, PartStatus.not_done));
				}
			}
			else
			{
				print("v ERROR\n" +
						"ERROR: Diverter gave me the wrong part!" +
						"^ ERROR");
				this.stopThread();
			}
		}
		
		stateChanged();
	}
	
	@Override
	public void msgGuiDoneMovingPart(GUIPart guipart)
	{
		print("Received DoneMovingPart from GUI");
		synchronized(this.currentParts){
			for (MyPart p : this.currentParts)
			{
				if (p.part.guiPart == guipart)
				{
					p.status = PartStatus.done;
					print("Part '" + p.part.getName() + "' reached end of Lane");
				}
			}
		}
		
		stateChanged();
	}

	@Override
	public void msgGuiLaneAlmostFull()
	{
		print("Received LaneAlmostFull from GUI");
		this.entryStatus = LaneEntryStatus.capacity_alert;
		
		stateChanged();
	}
	
	boolean needToTellFeederToContinue = false;
	
	@Override
	public void msgGuiLaneNoLongerFull()
	{
		print("Received LaneNoLongerFull from GUI");
		this.entryStatus = LaneEntryStatus.okay_to_receive;
		needToTellFeederToContinue = true;
		
		stateChanged();
	}
	
	@Override
	public void msgReadyToReceive()
	{
		print("Received ReadyToReceive from Nest");
		synchronized(currentParts){
			currentParts.get(0).status = PartStatus.ready_to_pass;
		}
		
		if (this.exitStatus == LaneExitStatus.stop_sending)
		{
			print("Cancelled StopSending");
			this.exitStatus = LaneExitStatus.none;
		}
		
		stateChanged();
	}

	@Override
	public void msgIAmFull()
	{
		print("Received IAmFull from Nest");
		this.exitStatus = LaneExitStatus.stop_sending;
		
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
	 * MAGIC NUMBER
	 */
	int requestWhenCount = 2;
	
	/**
	 * diverter's scheduler
	 */
	@Override
	public boolean pickAndExecuteAnAction()
	{
		this.setCurrentState(currentParts.size() + " part(s) " + partType);
		this.setCurrentState1("entry = " + entryStatus);
		this.setCurrentState2("manager = " + managerStatus);
		
		if (this.entryStatus == LaneEntryStatus.okay_to_receive
			&& needToTellFeederToContinue)
		{
			needToTellFeederToContinue = false;
			tellFeederToContinue();
		}
		
		if (this.managerStatus == ManagerStatus.notice_received)
		{
			checkPartsAgainstConfiguration();
			return true;
		}
		
		/*
		print("...\npartType = " + this.partType
				+ "currentParts.size() = " + currentParts.size()
				+ ", entryStatus = " + entryStatus
				+ ", managerStatus = " + managerStatus
				);
		*/
		
		//if (this.currentParts.size() > 0
		if (this.partType != null
			&& this.currentParts.size() < requestWhenCount
			&& entryStatus != LaneEntryStatus.refill_requested
			&& managerStatus == ManagerStatus.none)
		{
			tellFeederToSendMore();
			return true;
		}
		
		if (this.currentParts.size() > requestWhenCount && entryStatus == LaneEntryStatus.refill_requested)
		{
			print("currentParts > " + requestWhenCount + " && entryStatus == refill_requested ===> entryStatus = none");
			entryStatus = LaneEntryStatus.none;
			return true;
		}
		
		if (this.entryStatus == LaneEntryStatus.capacity_alert)
		{
			tellFeederToStop();
			return true;
		}
		
		
		for (MyPart p : currentParts)
		{
			if (p.status == PartStatus.ready_to_pass && exitStatus != LaneExitStatus.stop_sending)
			{
				passToNest(p);
				return true;
			}
		}
		
		for (MyPart p : currentParts)
		{
			if (p.status == PartStatus.done)
			{
				informNest(p);
				return true;
			}
		}
		
		for (MyPart p : currentParts)
		{
			if (p.status == PartStatus.not_done && this.entryStatus != LaneEntryStatus.stop_receiving)
			{
				addPart(p);
				return true;
			}
		}
		

		if (this.currentParts.size() == 0
			&& this.managerStatus == ManagerStatus.dumping
			&& this.gotMyFirstPart)
		{
			giveNestAndCellManagerOkay();
			return true;
		}
		
		
		if (!nextParts.isEmpty() && nextParts.peek().status == PartStatus.not_here_yet 
			&& this.entryStatus  != LaneEntryStatus.stop_receiving)
		{
			prepareToReceive();
			return true;
		}
		
		return false;
	}

/*
 * ACTIONS
 */
	private void prepareToReceive()
	{
		//print("PrepareToReceive " + nextParts.peek().part.getName());
		nextParts.peek().status = PartStatus.coming_next;
		diverterAgent.msgReadyToReceive(nextParts.peek().part);
	}
	
	private void addPart(MyPart p)
	{
		print("AddPart " + p.part.getName() + " to GUI");
		p.status = PartStatus.doing;
		guiLane.msgDoAddPart(p.part.guiPart);
		nestAgent.msgPartComing();
	}
	
	private void informNest(MyPart p)
	{
		print("InformNest PartReady" + p.part.getName());
		p.status = PartStatus.trying_to_pass;
		nestAgent.msgPartReady(this);
	}

	private void passToNest(MyPart p)
	{
		print("PassToNest" + " part '" + p.part.getName() + "'");
		nestAgent.msgHereIsPart(p.part.guiPart);
		
		boolean partRemoved = false;
		synchronized(this.currentParts)
		{
			if (this.currentParts.remove(p))
			{
				partRemoved = true;
				//print("part removed");
			}
		}
		
		if (!partRemoved)
		{
			print("WARNING:  COULD NOT REMOVE PART that was handed to Nest!");
		}
	}

	int feederRequestsCounter = 0;
	
	private void tellFeederToSendMore()
	{
		print(++feederRequestsCounter + " TellFeederToSendMore of " + this.partType);
		this.entryStatus = LaneEntryStatus.refill_requested;
		feederAgent.msgSendMoreParts(this, this.partType);
	}
	
	private void tellFeederToStop()
	{
		print("TellFeederToStop");
		this.entryStatus = LaneEntryStatus.stop_receiving;
		feederAgent.msgHoldOn();
	}
	private void tellFeederToContinue()
	{
		print("TellFeederToContinue");
		feederAgent.msgContinueSending();
	}
	
	private void checkPartsAgainstConfiguration()
	{
		//print("CheckPartsAgainstConfiguration");
		this.managerStatus = ManagerStatus.checking;
		
		/*
		// check if what I'm pushing to the nest, is what the current config consists of
		boolean dumpParts = true;
		for (int i = 0; i < currentConfig.kitConfig.size(); i++)
		{
			if (this.partType == currentConfig.kitConfig.get(i))
			{
				dumpParts = false;
			}
		}
		*/
		
		if (this.partType == null)
		{
			print("CheckPartsAgainstConfiguration - replacing null with initial config");
			this.partType = currentConfig.kitConfig.get( laneNumber-1 );
			giveNestAndCellManagerOkay();
		}
		else if ( this.partType != currentConfig.kitConfig.get( laneNumber-1 ) )
		{
			print("CheckPartsAgainstConfiguration - Going to Dump " + partType + " for "
					+ currentConfig.kitConfig.get( laneNumber-1) );
			this.partType = currentConfig.kitConfig.get( laneNumber-1 );
			if (this.entryStatus == LaneEntryStatus.refill_requested)
			{
				print("VOIDING refill_requested state!");
				this.entryStatus = LaneEntryStatus.none;
			}
			this.managerStatus = ManagerStatus.dumping;
		}
		else
		{
			print("CheckPartsAgainstConfiguration - all okay");
			giveNestAndCellManagerOkay();
		}
	}
	
	private void giveNestAndCellManagerOkay()
	{
		managerStatus = ManagerStatus.none;
		print("GiveCellManagerOkay - currentParts.size() == " + currentParts.size());
		if (gotMyFirstPart)
		{
			this.nestAgent.msgTheFinalPurge();
		}
		this.gotMyFirstPart = false;
		this.cellManagerAgent.msgIAmProperlyConfigured(this);
		print("GiveCellManagerOkay DONE - currentParts.size() == " + currentParts.size());
	}
	
/*
 * EXTRA
 */
	@Override
	public void setFeederAgent(Feeder feederAgent)
	{
		this.feederAgent = feederAgent;
	}
	@Override
	public void setDiverterAgent(Diverter diverterAgent)
	{
		this.diverterAgent = diverterAgent;
	}
	@Override
	public void setNestAgent(Nest nestAgent)
	{
		this.nestAgent = nestAgent;
	}

	public void setCellManagerAgent(CellManagerAgent cellManagerAgent)
	{
		this.cellManagerAgent = cellManagerAgent;
	}
	@Override
	public void setGuiLane(GUILane guiLane)
	{
		this.guiLane = guiLane;
	}
	@Override
	public void setPartType(PartType type)
	{
		this.partType = type;
	}
	@Override
	public String getName()
	{
		return this.name;
	}
	@Override
	public PartType getPartType()
	{
		return this.partType;
	}


}
