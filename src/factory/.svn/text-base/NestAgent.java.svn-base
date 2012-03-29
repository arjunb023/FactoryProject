package factory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import shared.KitConfig;
import shared.enums.PartType;

import factory.interfaces.CameraSystem;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import gui.components.GUINest;
import gui.components.GUIPart;
import agent.Agent;

public class NestAgent extends Agent implements Nest
{
	//DATA:
	
	int counter = 0;
	CameraSystem	 			cameraSystemAgent;						// Reference to my camera agent
	Lane	 					laneAgent;								// Reference to the lane agent
	GUINest 					myGuiNest;								// Reference to my gui nest
	ArrayList<GUIPart>			myParts = new ArrayList<GUIPart>();		// ArrayList of type GUIPart, gotten from the gui
	GUIPart 					lanePart;								// Reference to the lane part coming into the nest
	int 						nestNum;								// int of the gui nest's number
	CellManagerAgent			cellManager;							// Reference to my cell manager agent
	PartType					partType;								// Current part type
	
	boolean						pendingLaneRequest = false;				// This boolean keeps track of whether there are parts the lane is waiting to send
	boolean 					firstPurge = false;						// This boolean ensures that I purge parts if my nest is not full 
	
	AtomicInteger				pendingLaneParts = new AtomicInteger(0);
	/**
	 * enum of the Nest's action state.
	 */
	public enum NestActionState 
	{
		NONE,IDLE,CHECKING,RECEIVING_PART,DUMPING_PARTS,PURGING_PARTS,
		WAITING_FOR_SETTLEMENT_BEFORE_PURGE,FINISH_PURGE,FIX_NEST
	}
	
	public enum NestPossessionState
	{
		EMPTY,FILLING,EMPTYING_TO_ROBOT,FULL
	}
	
	NestPossessionState			guiStatus;									// Local reference to my nest's possession state
	NestActionState				agentStatus;								// Local reference to my agent's action state
	

	/**
	 * the GUIPart received from Lane in msgHereIsPart
	 */
	GUIPart partThatLaneGave = null;
	/**
	 * the GUIPart received from GUINest in msgPartAdded
	 */
	GUIPart partThatLastSettled = null;
	
	
	//CONSTRUCTOR:
	
	public NestAgent(String name) 
	{
		super();
		this.name = name;
		this.guiStatus = NestPossessionState.EMPTY;								// Initialize my gui's possession state to empty
		this.agentStatus = NestActionState.NONE;								// Initialize my agent's action state to none
	}
	
	//SETTER METHODS:
	/**
	 * set the cell manager agent
	 * @param ca
	 */
	public void setCellManagerAgent(CellManagerAgent ca)
	{
		cellManager = ca;
	}
	public void setNestNum(int n)
	{
		nestNum = n;
	}
	/**
	 * Set the gui nest 
	 * @param guiNest
	 */
	public void setGuiNest(GUINest guiNest)
	{
		myGuiNest = guiNest;
	}
	/**
	 * Set the lane agent
	 * @param la
	 */
	public void setLaneAgent(Lane la) 
	{
		this.laneAgent = la;
	}
	/**
	 * Set the Camera Agent
	 * @param cs
	 */
	public void setCameraSystemAgent(CameraSystem cs)
	{
		this.cameraSystemAgent = cs;
	}
	
	//GETTER METHODS:
	
	public String getAgentState()
	{
		return "NOT IMPLEMENTED";
	}
	
	/**
	 * This returns whether the nest is full or not
	 */
	public boolean getFullState() 
	{
		if (this.guiStatus == NestPossessionState.FULL)
		{
			return true;
		}
		return false;
	}
	/**
	 * This returns the nest num
	 */
	public int getNestNum()
	{
		return nestNum;
	}
	public String getName()
	{
		return this.name;
	}
	
	//MESSAGES:
	
	/**
	 * This message is sent by the lane agent, asking if the nest is ready for a part.
	 * @param la
	 */
	public void msgPartReady(Lane la) 
	{
		print("Received msgPartReady(): agentStatus = CHECKING");
		pendingLaneRequest = true;
		laneAgent = la;
		this.agentStatus = NestActionState.CHECKING;
		stateChanged();
	}
	/**
	 * This message is sent by the gui nest if it is ready to receive a new part.
	 */
	@Deprecated
	public void msgReadyToReceive() 
	{
		print("NO LONGER IN USE");
	}
	
	/**
	 * This message is sent by the gui nest if it is full and not ready for a part.
	 * @param p
	 * @param num
	 */
	public void msgIAmFull(ArrayList<GUIPart> p, int num) 
	{
		print("Received msgIAmFull from GUINest.");
		nestNum = num;
		
		/**
		 * If I am not on any purge state
		 */
		if (this.agentStatus != NestActionState.PURGING_PARTS && this.agentStatus != NestActionState.FINISH_PURGE && !firstPurge
				&& this.agentStatus != NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE)
		{
			/**
			 * Clear my parts if my list is populated
			 */
			if (myParts.size() != 0)
			{
				myParts.clear();
			}
			synchronized(p)
			{
				for (int i = 0; i < p.size(); i++)
				{
					myParts.add(p.get(i));
				}
			}
			
			if (myParts.size() != 12)
			{
				print("ERROR: Got a bad list from the GUINest. Not sending parts to camera.");
			}
			else
			{
				print("GUI possession state is Full.");
				this.guiStatus = NestPossessionState.FULL;	
				cameraSystemAgent.msgIAmFull(this, myParts);				// Sending the CameraSystemAgent my list of parts
			}
		}
		else
		{
			print("GUI possession state is Full2.");
			this.guiStatus = NestPossessionState.FULL;
			if (firstPurge)
			{
				
			}
		}
		stateChanged();
	}
	
	/**
	 * Message called by the lane agent, passes the part p which gets passed to the nest
	 * @param p
	 */
	public void msgHereIsPart(GUIPart p)
	{
		print("Received msgHereIsPart() from lane agent: agentStatus = receiving_part.");
		this.partThatLaneGave = p;
		lanePart = p;
		this.agentStatus = NestActionState.RECEIVING_PART;
		stateChanged();
	}

	
	public void msgPartAdded(GUIPart p)
	{
		pendingLaneParts.set(pendingLaneParts.get() - 1);
		print("Received message msgPartAdded(Settled) " + p.getName() + "from my gui.  CurrentState == " + agentStatus
				+ "\n           pendingLaneParts == " + pendingLaneParts.get());
		partThatLastSettled = p;
		if (this.agentStatus == NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE
			&& this.partThatLaneGave.equals(this.partThatLastSettled))
		{
			this.agentStatus = NestActionState.FINISH_PURGE;
		}
		stateChanged();
	}
	
	/**
	 * Message called by the CameraAgentSystem when the nest needs to be dumped.
	 */
	public void msgDumpParts() 
	{
		print("Received msgDumpParts: agentStatus = dumping_parts");
		System.out.println("Neil Patrick Harris: DUMPING");
		this.agentStatus = NestActionState.DUMPING_PARTS;
		stateChanged();
	}

	public void msgPartComing()
	{
		pendingLaneParts.set(pendingLaneParts.get() + 1);
		print("Pending lane parts ++ to " + pendingLaneParts.get());
		stateChanged();
	}
	
	public void msgTheFinalPurge()
	{
		print("Received msgTheFinalPurge");
		if (this.partThatLaneGave != null && this.partThatLastSettled != null)
		{
			print("partThatLaneGave == " + partThatLaneGave.getName()
				+ "\npartThatLastSettled == " + partThatLastSettled.getName());
			
			if (this.partThatLaneGave.equals(this.partThatLastSettled))
			{
				print("On the final purge: agentStatus = finish_purge");
				this.agentStatus = NestActionState.FINISH_PURGE;
			}
			else
			{
				print ("Waiting for settlement: agentStatus = waiting_for_settlement_before_purge");
				this.agentStatus = NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE;
			}
		}
		stateChanged();
	}
	
	public void msgNestNeedsMoreParts()
	{
		print("Received message from camera that the nest is not really full.");
		this.agentStatus = NestActionState.FIX_NEST;
		stateChanged();
	}
	
	public void msgIAmNoLongerFull()
	{
		print("Received msgIAmNoLongerFull");

		/**
		 * If I am not in any purge state
		 */
		if (this.agentStatus != NestActionState.PURGING_PARTS && this.agentStatus != NestActionState.FINISH_PURGE 
				&& !firstPurge && this.agentStatus != NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE)
		{
			print("guiStatus = emptying_to_robot");
			this.guiStatus = NestPossessionState.EMPTYING_TO_ROBOT;
			cameraSystemAgent.msgIAmNoLongerFull(this);
		}
		stateChanged();
	}
	
	public void msgIAmEmpty()
	{
		print("Received msgIAmEmpty: guiStatus = empty");
		this.guiStatus = NestPossessionState.EMPTY;
		stateChanged();
	}
	
	public void msgThisIsCurrentConfiguration(KitConfig k)
	{
		print("Received msgThisIsCurrentConfiguration");
		for (PartType p : k.getKitConfig())
		{
			print("." + p);
		}
		print(k.getKitConfig().get(nestNum-1) + ".");
		
		
		/**
		 * Initialize my partType (beginning of KittingCell)
		 */
		if (partType == null)
		{
			print("I am already initialized.");
			partType = k.getKitConfig().get(nestNum-1);
			cellManager.msgIAmProperlyConfigured(this);
		}
		/**
		 * Update my partType (KittingCell has already started)
		 */
		else
		{
			if (partType != k.getKitConfig().get(nestNum-1))
			{
				print("Purging parts because my part type has changed");
				partType = k.getKitConfig().get(nestNum-1);
				this.agentStatus = NestActionState.PURGING_PARTS;
				firstPurge = true;
			}
			else
			{
				print("My part type did not change.");
				cellManager.msgIAmProperlyConfigured(this);
			}
		}
		stateChanged();
	}
	
	//SCHEDULER:
	
	public boolean pickAndExecuteAnAction() 
	{	
		//this.setCurrentState(myParts.size() + " part(s)");
		StringBuilder sb = new StringBuilder();
		sb.append(pendingLaneParts.get() + " coming " + partType);
		if (this.pendingLaneRequest) sb.append(",pLR");
		if (this.firstPurge) sb.append(",fP");
		this.setCurrentState(sb.toString());
		this.setCurrentState1("gui = " + this.guiStatus);
		this.setCurrentState2("action = " + this.agentStatus);
		
		if (this.agentStatus == NestActionState.CHECKING)
		{
			areYouReadyToReceive();
			return true;
		}
		if (this.agentStatus == NestActionState.RECEIVING_PART)
		{
			receivePart();
			return true;
		}
		if (this.agentStatus == NestActionState.DUMPING_PARTS)
		{
			dumpParts();
			return true;
		}
		if (this.guiStatus == NestPossessionState.EMPTY	&& this.agentStatus == NestActionState.FINISH_PURGE
			&& pendingLaneParts.get() == 0)
		{
			theFinalPurgeCompleted();
			return true;
		}
		
		if (this.agentStatus == NestActionState.FIX_NEST)
		{
			fixNest();
			return true;
		}
		
		if ((firstPurge && guiStatus != NestPossessionState.FULL && pendingLaneParts.get() == 0)
				|| (firstPurge && (guiStatus == NestPossessionState.FULL || this.guiStatus == NestPossessionState.EMPTYING_TO_ROBOT))
				|| (this.agentStatus == NestActionState.PURGING_PARTS && (this.guiStatus == NestPossessionState.FULL || this.guiStatus == NestPossessionState.EMPTYING_TO_ROBOT)) 
				|| (this.agentStatus == NestActionState.FINISH_PURGE && pendingLaneParts.get() == 0)
			)
		{
			if (firstPurge)
			{
				firstPurge = false;
			}
			purgeParts();
			return true;
		}
		print("Nest scheduler has nothing to do.");
		return false;
	}
	
	//ACTIONS:
	
	private void areYouReadyToReceive()
	{
		if (this.guiStatus == NestPossessionState.EMPTY)
		{
			print("Asking if ready for a part / guiStatus is empty.");
			this.guiStatus = NestPossessionState.FILLING;
			this.agentStatus = NestActionState.NONE;
			laneAgent.msgReadyToReceive();
			pendingLaneRequest = false;
		}
		else if (this.guiStatus == NestPossessionState.FILLING)
		{
			print("Asking if ready for a part / guiStatus is Filling");
			if (laneAgent != null)
			{
				laneAgent.msgReadyToReceive();
				this.agentStatus = NestActionState.NONE;
				pendingLaneRequest = false;
			}
		}
		else if (this.guiStatus == NestPossessionState.FULL)
		{
			print("Asking if ready for a part / guiStatus is full");
			this.agentStatus = NestActionState.NONE;
			if (this.agentStatus != NestActionState.PURGING_PARTS && this.agentStatus != NestActionState.FINISH_PURGE && !firstPurge
					&& this.agentStatus != NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE)
			{
				cameraSystemAgent.msgIAmFull(this, myParts);
				laneAgent.msgIAmFull();
			}
		}
		else if (this.guiStatus == NestPossessionState.EMPTYING_TO_ROBOT)
		{
			print("Asking if ready for a part / guiStatus is emptying to robot and cannot give to lane");
			this.agentStatus = NestActionState.NONE;
			laneAgent.msgIAmFull();
		}
		else
		{
			print("Condition unmet in areYouReadyToReceive(). State = " + this.guiStatus);
		}
	}
	private void dumpParts()
	{
		print("Dumping parts");
		myGuiNest.dumpParts();
		this.agentStatus = NestActionState.IDLE;
		this.guiStatus = NestPossessionState.EMPTY;
	}
	private void purgeParts()
	{
		if (counter < 1)
		{
			counter++;
			return;
		}
		else
		{
			counter = 0;
			print("Purging parts  -  pendingLaneParts == " + pendingLaneParts.get());
			myGuiNest.dumpParts();
			if (pendingLaneRequest)
			{
				print("LANE REQUEST FULFILLED");
				laneAgent.msgReadyToReceive();
				pendingLaneRequest = false;
			}
		}
	}
	private void theFinalPurgeCompleted()
	{	
		print("I AM ON THE FINAL PURGE");

		if (pendingLaneRequest)
		{
			laneAgent.msgReadyToReceive();
			pendingLaneRequest = false;
		}

		if (counter < 1)
		{
			counter++;
			return;
		}
		else
		{
			myGuiNest.dumpParts();
		}
		this.agentStatus = NestActionState.NONE;
		
		cellManager.msgIAmProperlyConfigured(this);	
	}
	private void receivePart()
	{
		print("Receiving part");
		
		if (this.guiStatus == NestPossessionState.EMPTY)
		{
			this.guiStatus = NestPossessionState.FILLING;
		}
		if (this.agentStatus != NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE)
		{
			this.agentStatus = NestActionState.NONE;
		}
		/*
		if (this.agentStatus == NestActionState.WAITING_FOR_SETTLEMENT_BEFORE_PURGE)
		{
			this.agentStatus = NestActionState.FINISH_PURGE;
		}
		*/
	}
	private void fixNest()
	{
		myGuiNest.changeNormativeNormal();
		this.agentStatus = NestActionState.NONE;
		this.guiStatus = NestPossessionState.FILLING;
	}
}
