package factory;

import factory.interfaces.CameraSystem;
import factory.interfaces.KitRobot;
import factory.interfaces.PartsRobot;
import factory.interfaces.PartsRobotActor;
import gui.components.GUIPartsRobot;

import java.util.ArrayList;

import agent.Agent;

import shared.KitConfig;
import shared.KitOrder;
import shared.KitPartPutInstruction;
import shared.NestPosition;
import shared.Part;
import shared.enums.PartQuality;
import shared.enums.PartType;

public class PartsRobotAgent extends Agent implements PartsRobot
{
	//////////
	// DATA //
	//////////
	
	/**
	 * TODO: Take this out after debugging
	 * 
	 */
	public int	numKitsFilled	= 0;
	
	
	public	enum RobotActionState{OFF, RESETTING, GOING_IDLE, STANDBY, PICKING_UP_PARTS, DROPPING_OFF_PARTS, UNKNOWN};
	public	enum RobotPartPossessionState{HOLDING_PARTS, NOT_HOLDING_PARTS, UNKNOWN};
	public	enum PartInHandState{HOLDING, READY_TO_PUT_IN_KIT, DROPPING, UNKNOWN};
	public 	enum NestPositionState{HOLDING_NO_PART, HOLDING_GOOD_PART, HOLDING_BAD_PART, PICKUP_PENDING, UNKNOWN};
	public	enum KitOrderState{DONE, FULL, PARTIALLY_FILLED, EMPTY, INCORRECT, PARTS_NOT_FOUND, UNKNOWN};
	public	enum KitPartOrderState{NEEDED, ACCEPTING, FILLED, MISSING, INCORRECT, UNKNOWN};
	
	/*
	 * Convenience inner class that represents the PartsRobotAgent's concept of a Part that it is holding
	 */
	public class PartsRobotPartInHand
	{
	     public	PartType			partType;
	     public	PartInHandState 	state;

	     public PartsRobotPartInHand(PartType type)
	     {
	          this.partType	= type;
	          this.state 	= PartInHandState.HOLDING;
	     }
	}

	/*
	 *  Convenience inner class that represents the PartsRobotAgent's concept of a Lane
	 */
	public class PartsRobotLaneModel
	{		
	     public	int 				laneNum;
	     public	PartType 			partType;
	     public	int 				nestCapacity;
	     public	ArrayList<NestPositionState>	nestContents;
	     public	boolean 			noGoodPartsInNest;
	     public boolean				onlyBadPartsInNest;
	     
	     private final int	DEFAULT_NEST_CAPACITY	= 12;

	     public PartsRobotLaneModel(int num, int nestCap)
	     {
	          this.laneNum		= num;
	          this.partType 	= PartType.UNKNOWN;
	          this.nestCapacity	= nestCap;
	          this.nestContents	= new ArrayList<NestPositionState>();
	          for(int i = 0; i < this.nestCapacity; i++)
	               this.nestContents.add(NestPositionState.UNKNOWN); 
	          checkForGoodPartsInNest();    
	          checkIfOnlyBadPartsInNest();
	     }
	     
	     public PartsRobotLaneModel(int num, PartType type)
	     {
	          this.laneNum		= num;
	          this.partType 	= type;
	          this.nestCapacity	= DEFAULT_NEST_CAPACITY;
	          this.nestContents	= new ArrayList<NestPositionState>();
	          for(int i = 0; i < this.nestCapacity; i++)
	               this.nestContents.add(NestPositionState.UNKNOWN); 
	          checkForGoodPartsInNest(); 
	          checkIfOnlyBadPartsInNest();
	     }

	     private void setNestContents(ArrayList<PartQuality> contents)
	     {
	    	 ArrayList<NestPositionState>	nestContents	= new ArrayList<NestPositionState>();
	    	 for(PartQuality q : contents)
	    	 {
	    		 switch(q)
	    		 {
	    			 case	GOOD:		nestContents.add(NestPositionState.HOLDING_GOOD_PART);
	    			 					break;
	    			 case	BAD:		nestContents.add(NestPositionState.HOLDING_BAD_PART);
	    			 					break;
	    			 case	MISSING:	nestContents.add(NestPositionState.HOLDING_NO_PART);
	    			 					break;
	    			 case	UNKNOWN:	nestContents.add(NestPositionState.UNKNOWN);
	    			 					break;
	    			 default:			nestContents.add(NestPositionState.UNKNOWN);
	 									break;
	    		 }
	    	 }
	    	 
	    	 this.nestContents	= nestContents;
	         checkForGoodPartsInNest();
	         checkIfOnlyBadPartsInNest();
	     }
	     
	     private void resetNestContents()
	     {
	    	 ArrayList<NestPositionState>	defaultContents	= new ArrayList<NestPositionState>();
	    	 for(int l = 0; l < this.nestCapacity; l++)
	    	 {
	    		 defaultContents.add(NestPositionState.UNKNOWN);
	    	 }
	    	 
	    	 this.nestContents	= defaultContents;
	         checkForGoodPartsInNest();
	         checkIfOnlyBadPartsInNest();
	     }


	     private void checkForGoodPartsInNest()
	     {
	          	for(NestPositionState q : this.nestContents)
	          	{
	                if(q == NestPositionState.HOLDING_GOOD_PART)
	                {
	                	System.out.println("PartsRobotAgent SAYS: There is at least one good part in nest " + this.laneNum);
	                     noGoodPartsInNest = false;
	                     return;
	                }
	          }
	          noGoodPartsInNest	= true;
	     }
	     
	     private void checkIfOnlyBadPartsInNest()
	     {
	          	for(NestPositionState q : this.nestContents)
	          	{
	                if(q == NestPositionState.HOLDING_GOOD_PART || q == NestPositionState.PICKUP_PENDING || q == NestPositionState.UNKNOWN)
	                {
	                	System.out.println("PartsRobotAgent SAYS: There is at least one not bad part in nest " + this.laneNum);
	                     this.onlyBadPartsInNest = false;
	                     return;
	                }
	          }
	          onlyBadPartsInNest	= true;
	     }
	}

	/*
	 * Convenience inner class that represents a KitOrder and its related data as far as the PartsRobot is concerned
	 */
	public class PartsRobotKitOrder
	{
	     public class KitPartOrder
	     {
	          public	PartType 			partType;
	          public	KitPartOrderState	state;

	          public KitPartOrder(PartType type)
	          {
	                partType			= type;
	                state				= KitPartOrderState.NEEDED;
	                //partsReadyToBeAdded	= empty;
	          }
	     }

	     public	ArrayList<KitPartOrder>	kitParts	= new ArrayList<KitPartOrder>();
	     public	KitOrderState 			state;
	     public	int		 				kitStandLocation;

	     public PartsRobotKitOrder(KitOrder kitOrder, int standLoc)
	     {
	    	  for(PartType p : kitOrder.kitOrder)
	          {
	               this.kitParts.add(new KitPartOrder(p));
	          }

	          this.state 			= KitOrderState.EMPTY;
	          this.kitStandLocation	= standLoc;
	   	     }
	
	     private void checkKitOrderProgress()
	     {
	          int filledPartsCount = 0;
	          for(KitPartOrder p : kitParts)
	          {
	                if(p.state == KitPartOrderState.MISSING || p.state == KitPartOrderState.INCORRECT)
	                {
	                     this.state = KitOrderState.INCORRECT;
	                     return;
	                }
	                else if(p.state == KitPartOrderState.FILLED)
	                {
	                     filledPartsCount++;
	                }
	          }

	          if(filledPartsCount == kitParts.size())
	          {
	               this.state = KitOrderState.FULL;
	          }
	          else if(filledPartsCount > 0 && filledPartsCount < kitParts.size())
	          {
	               this.state = KitOrderState.PARTIALLY_FILLED;
	          }
	          else
	          {
	        	  this.state = KitOrderState.EMPTY;
	          }
	          
	          System.out.println(this + " kit is " + this.state);
	     }
	}

	KitRobot 			myKitRobotAgent;
	CameraSystem		myCameraSystemAgent;
	CellManagerAgent	myManagerAgent;

	PartsRobotActor 	myPartsRobotGui;

	public	RobotActionState 				myActionState;
	public	RobotPartPossessionState		myPartPossessionState;
	public	boolean							faultyRobot;
	
	public 	ArrayList<PartsRobotLaneModel>	myLanes;

	public	ArrayList<PartsRobotKitOrder> 	myKitOrders;

	public	int 							myNumGrippers;
	public 	ArrayList<PartsRobotPartInHand>	myPartsInHand;
	
	String	myName;

	/////////////////
	// CONSTRUCTOR //
	/////////////////
	
	public PartsRobotAgent(String name)
	{
		super();
		this.myName	= name;
		this.name = name;
		
		this.myLanes		= new ArrayList<PartsRobotLaneModel>();
		this.myKitOrders	= new ArrayList<PartsRobotKitOrder>();
		this.myPartsInHand	= new ArrayList<PartsRobotPartInHand>();
		this.myNumGrippers	= 4;
		this.myActionState	= RobotActionState.STANDBY;
		this.myPartPossessionState	= RobotPartPossessionState.NOT_HOLDING_PARTS;
		this.faultyRobot	= false;
	}
	
	//////////////
	// MESSAGES //
	//////////////
	
	/*
	 * Message received from a CameraSystem agent informing the PartsRobot of the contents of a nest determined by taking and analyzing a photo of the nest
	 */
	public void msgHereAreUpdatedNestContents(int laneNumber, ArrayList<PartQuality> nestContents)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgHereAreUpdatedNestContents");
		
		synchronized(nestContents)
		{
			StringBuilder s = new StringBuilder();
			s.append("Nest " + laneNumber + " has " + nestContents.size() + " contents: "
					+ "\n");
			if (nestContents.size() == 12)
			{
				s.append(
							nestContents.get(0) + "" + nestContents.get(3) + "" + nestContents.get(6) + "" + nestContents.get(9)
					+ "\n" + nestContents.get(0+1) + "" + nestContents.get(3+1) + "" + nestContents.get(6+1) + "" + nestContents.get(9+1)
					+ "\n" + nestContents.get(0+2) + "" + nestContents.get(3+2) + "" + nestContents.get(6+2) + "" + nestContents.get(9+2)
					);
			}
			else
			{
				for(PartQuality q : nestContents)
				{
					s.append(q + "\n");
				}
			}
			print(s.toString());
		}
		
		
		for(PartsRobotLaneModel l : this.myLanes)
		{
			System.out.println("PartsRobotAgent SAYS: Looking for the right lane to update nest contents. Given contents for lane " + laneNumber + "; Looking at lane " + l.laneNum);
			if(l.laneNum == laneNumber)
			{
				System.out.println("PartsRobotAgent SAYS: Setting nest contents for lane " + laneNumber);
				l.setNestContents(nestContents);
				
				PartType	nestType	= l.partType; 
				synchronized(this.myKitOrders){
					for(PartsRobotKitOrder k : this.myKitOrders)
					{
						for(factory.PartsRobotAgent.PartsRobotKitOrder.KitPartOrder part : k.kitParts)
						{
							if(nestType == part.partType)
							{
								k.checkKitOrderProgress();
								break;
							}
						}
					}
				}
				break;
			}
		}
		
		stateChanged();
	}
	
	/*
	 * Message received from the Kit Robot when it places a new empty kit to be filled onto the kit stand.
	 * Informs the PartsRobot of what parts need to be placed in this kit to fill the order for that kit
	 */
	public void msgHereIsANewKitOrderToFill(KitOrder newKitOrder, int kitStandLoc)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgHereIsANewKitOrderToFill " + kitStandLoc);
		
		synchronized(this.myKitOrders){
		this.myKitOrders.add(new PartsRobotKitOrder(newKitOrder, kitStandLoc));
		}
		
		for(PartType op : newKitOrder.kitOrder)
		{
			System.out.println(op);
		}
		
		stateChanged();
	}
	
	/*
	 * Message received from the KittingCellManagerAgent when initializing the factory or changing the factory configuration to fill a new kit type
	 * Informs the PartsRobotAgent of which lanes/nests will be supplying which part types
	 */
	public void msgThisIsCurrentConfiguration(KitConfig newKitConfig)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgThisIsCurrentKitConfiguration");
		
		for(int i = 0; i < newKitConfig.getKitConfig().size(); i++)
		{
			PartType	laneType	= newKitConfig.getKitConfig().get(i);
			if(i >= this.myLanes.size())
			{
				this.myLanes.add(new PartsRobotLaneModel((i+1), laneType));
			}
			if(this.myLanes.get(i).partType != laneType)
			{
				this.myLanes.get(i).resetNestContents();
			}
			
			this.myLanes.get(i).partType	= laneType;
		}
		
		this.myManagerAgent.msgIAmProperlyConfigured(this);
		stateChanged();
	}
	
	/*
	 * Message received from the GUIPartsRobot to let the PartsRobotAgent know that the pick up from lanes action has been completed on the front end
	 */
	public void msgDonePickUpFromLanes(ArrayList<PartType> partsInHand, ArrayList<NestPosition> pickedPositions)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDonePickUpFromLanes");
		
		this.myPartsInHand.clear();
		for(PartType t : partsInHand)
		{
			this.myPartsInHand.add(new PartsRobotPartInHand(t));
		}
		if(!this.myPartsInHand.isEmpty())
		{
			this.myPartPossessionState	= RobotPartPossessionState.HOLDING_PARTS;
		}
		
		for(NestPosition np : pickedPositions)
		{
			System.out.println("PartsRobotAgent SAYS: Gui told me he picked up from Lane " + np.nestNumber + " position " + np.nestPosition);
		}
		
		for(NestPosition np : pickedPositions)
		{
			
			this.myLanes.get(np.nestNumber-1).nestContents.set((np.nestPosition), NestPositionState.HOLDING_NO_PART);
			System.out.println("PartsRobotAgent SAYS: Part at Lane " + np.nestNumber + " position " + np.nestPosition + " is " + this.myLanes.get(np.nestNumber-1).nestContents.get(np.nestPosition));
		}
		//System.exit(0);
		
		for(PartsRobotLaneModel lane : this.myLanes)
		{
			lane.checkIfOnlyBadPartsInNest();
			if(lane.onlyBadPartsInNest)
			{
				System.out.println("PartsRobotAgent IS: Telling Camera System that there are no more good parts left in nest for Lane " + lane.laneNum);
				ArrayList<PartQuality>	allUnknownContents	= new ArrayList<PartQuality>();
				for(int i = 0; i < lane.nestCapacity; i++)
					allUnknownContents.add(PartQuality.UNKNOWN);
				if(lane.laneNum % 2 == 0)//Even numbered lane
				{
					lane.resetNestContents();
					this.myLanes.get((lane.laneNum - 1) - 1).resetNestContents();
					this.myCameraSystemAgent.msgDoneTakingGoodPartsFromNest(lane.laneNum);
					this.myCameraSystemAgent.msgDoneTakingGoodPartsFromNest(lane.laneNum - 1);
				}
				else //odd numbered lane
				{
					lane.resetNestContents();
					this.myLanes.get(lane.laneNum).resetNestContents();
					this.myCameraSystemAgent.msgDoneTakingGoodPartsFromNest(lane.laneNum);
					this.myCameraSystemAgent.msgDoneTakingGoodPartsFromNest(lane.laneNum + 1);
				}
			}
		}
		
		//For debugging
		//System.exit(0);
		
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}
	
	/*
	 * Message received from the GUIPartsRobot to let the PartsRobotAgent know that the put parts in kits action has been completed on the front end
	 */
	public void msgDonePutPartsInKit(int kitStandLoc, ArrayList<PartType> depositedPartTypes, ArrayList<PartType> stillHoldingPartTypes)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDonePutPartsInKit");
		
		synchronized(this.myKitOrders){
			for(PartsRobotKitOrder k : this.myKitOrders)
			{
				if(k.kitStandLocation == kitStandLoc)
				{
					for(int t = depositedPartTypes.size()-1; t>=0; t--)
					{
						PartType	depType	= depositedPartTypes.get(t);
						for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : k.kitParts)
						{
							if(p.partType == depType && p.state != KitPartOrderState.FILLED)
							{
								p.state	= KitPartOrderState.FILLED;
								depositedPartTypes.remove(t);
								break;
							}
						}
					}
				}
				k.checkKitOrderProgress();
			}
		}
		
		for(PartType p : depositedPartTypes)
		{
			for(PartsRobotLaneModel l : this.myLanes)
			{
				if(p == l.partType)
				{
					for(NestPositionState np : l.nestContents)
					{
						if(np == NestPositionState.PICKUP_PENDING)
						{
							
						}
					}
				}
			}
		}

		this.myPartsInHand.clear();
		for(PartType t : stillHoldingPartTypes)
		{
			this.myPartsInHand.add(new PartsRobotPartInHand(t));
		}		
		
		if(!this.myPartsInHand.isEmpty())
		{
			this.myPartPossessionState	= RobotPartPossessionState.HOLDING_PARTS;
		}
		else
		{
			this.myPartPossessionState	= RobotPartPossessionState.NOT_HOLDING_PARTS;
		}
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}

	/*
	 * Message received from the GUIPartsRobot to let the PartsRobotAgent know that the dropping parts action has been completed on the front end
	 */
	public void msgDoneDroppingParts(ArrayList<PartType> partsInHand)
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDoneDroppingParts");
		
		this.myPartsInHand.clear();
		for(PartType t : partsInHand)
		{
			this.myPartsInHand.add(new PartsRobotPartInHand(t));
		}		
		
		if(!this.myPartsInHand.isEmpty())
		{
			this.myPartPossessionState	= RobotPartPossessionState.HOLDING_PARTS;
		}
		else
		{
			this.myPartPossessionState	= RobotPartPossessionState.NOT_HOLDING_PARTS;
		}
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}
	
	/*
	 * Message received from the GUIPartsRobot to let the PartsRobotAgent know that the going idle action has been completed on the front end
	 */
	public void msgDoneGoingIdle()
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDoneGoingIdle");
		
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}
	
	/*
	 * Message received from the GUIPartsRobot to let the PartsRobotAgent know that the resetting action has been completed on the front end
	 */
	public void msgDoneResetting()
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDoneResetting");
		
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}
	
	/*
	 * Message received that tells the PartsRobot to go online
	 */
	public void msgGoOnline()
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgGoOnline");
		
		this.myActionState	= RobotActionState.GOING_IDLE;
		stateChanged();
	}
	
	/*
	 * Message received that puts the PartsRobot on standby
	 */
	public void msgGoOnStandy()
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgGoOnStandby");
		
		this.myActionState	= RobotActionState.STANDBY;
		stateChanged();
	}
	
	/*
	 * Message received that disables the PartsRobot
	 */
	public void msgDisable()
	{
		System.out.println("PartsRobotAgent RECEIVED MESSAGE: msgDisable");
		
		this.myActionState	= RobotActionState.OFF;
		stateChanged();
	}
	
	///////////////
	// SCHEDULER //
	///////////////
	
	public boolean pickAndExecuteAnAction()
	{
		this.setCurrentState("" + this.myActionState);
		this.setCurrentState1("" + this.myPartPossessionState);	
		if(this.faultyRobot)
		{
			this.setCurrentState2("Experiencing Difficulties");
		}
		else
		{
			this.setCurrentState2("");
		}
		
		
		//System.out.println("PRA State is " + this.myActionState);
		if(this.myActionState == RobotActionState.RESETTING)
		{
			System.out.println("PartsRobotAgent SCHEDULER: ActionState = RESETTING");
			reset();
			return true;
		}
		if(this.myActionState == RobotActionState.GOING_IDLE)
		{
			System.out.println("PartsRobotAgent SCHEDULER: ActionState = GOING IDLE");
			goIdle();
			return true;
		}
		synchronized(this.myKitOrders){
			for(PartsRobotKitOrder k : this.myKitOrders)
			{
				System.out.println("PartsRobotAgent SCHEDULER: HAS " + this.myKitOrders.size() + " KIT ORDER(s) (ANY STATE)");
				if(k.state == KitOrderState.FULL)
				{
					System.out.println("PartsRobotAgent SCHEDULER: A KIT ORDER IS FULL");
					informKitRobotOfCompletedKit(k.kitStandLocation);
					return true;
				}
			}
		}
		if(this.myActionState == RobotActionState.STANDBY)
		{
			System.out.println("PartsRobotAgent SCHEDULER: ActionState = STANDBY");
			if(this.myPartPossessionState == RobotPartPossessionState.HOLDING_PARTS)
			{
				System.out.println("PartsRobotAgent SCHEDULER: PossessionState = HOLDING PARTS");
				distributeParts();
				return true;
			}
			else
			{
				System.out.println("PartsRobotAgent SCHEDULER: PossessionState = NOT HOLDING PARTS");
				for(PartsRobotKitOrder k : this.myKitOrders)
				{
					System.out.println("PartsRobotAgent SCHEDULER: HAS AT LEAST ONE KIT ORDER");
					System.out.println("entering this loop");
					if(k.state == KitOrderState.EMPTY || k.state == KitOrderState.PARTIALLY_FILLED)
					{
						System.out.println("PartsRobotAgent SCHEDULER: A KIT IS EMPTY OR PARTIALLY FILLED");
						gatherParts();
						return true;
					}
				}
			}
		}
		System.out.println("PartsRobotAgent SCHEDULER: Nothing to do.");

		print("Sleeping.");
		//nothing to do
		return false;
	}
	
	/////////////
	// ACTIONS //
	/////////////
	
	private void gatherParts()
	{
		setCurrentState("Gather");
		System.out.println("PartsRobotAgent PERFORMING ACTION: gatherParts");
		
		ArrayList<NestPosition>	partsToGather	= new ArrayList<NestPosition>();
		
		PartsRobotKitOrder	priorityKit	= this.myKitOrders.get(0);
		ArrayList<PartsRobotAgent.PartsRobotKitOrder.KitPartOrder>	priorityKitNeededParts = new ArrayList<PartsRobotAgent.PartsRobotKitOrder.KitPartOrder>();
		if(priorityKit != null)
		{
			System.out.println("PartsRobotAgent SAYS: Priority KitOrder is currently the following:");
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder po : priorityKit.kitParts)
			{
				System.out.println("\t\t" + po.partType + ": " + po.state);
			}
			
			System.out.println("PartsRobotAgent SAYS: I'm looking for needed parts for priority kit");
			System.out.println("PartsRobotAgent SAYS: I have " + this.myPartsInHand.size() + " parts in hand and " + this.myNumGrippers + " grippers total");
			for(int i = 0; ((priorityKitNeededParts.size() < (this.myNumGrippers - this.myPartsInHand.size())) && (i < priorityKit.kitParts.size())); i++)
			{
				if(priorityKit.kitParts.get(i).state == KitPartOrderState.NEEDED)
				{
					priorityKitNeededParts.add(priorityKit.kitParts.get(i));
				}
			}
			
			boolean	noPriorityAvailablePartsFound	= true;
			System.out.println("PartsRobotAgent SAYS: I need " + priorityKitNeededParts.size() + " parts for priority kit");
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : priorityKitNeededParts)
			{
				System.out.println("PartsRobotAgent SAYS: I'm looking for a lane where I can pick up a " + p.partType + " part.");
				boolean	currentPartPickUpFound	= false;
				for(PartsRobotLaneModel l : this.myLanes)
				{
					System.out.println("\tPartsRobotAgent SAYS: I'm looking at lane " + l.laneNum + " which has " + l.partType + " parts.");
					if(l.partType == p.partType)
					{
						System.out.println("\t\tPartsRobotAgent SAYS: I found a lane with the desired part type.");
						if(!l.noGoodPartsInNest)
						{
							System.out.println("\t\t\tPartsRobotAgent SAYS: I'm looking that has at least one good part.");
							for(int pos = 0; pos < l.nestContents.size(); pos++)
							{
								System.out.println("\t\t\t\tPartsRobotAgent SAYS: I'm looking at Pos " + pos + " in lane " + l.laneNum + ", which is " + l.nestContents.get(pos));
								if(l.nestContents.get(pos) == NestPositionState.HOLDING_GOOD_PART)
								{
									////boolean	okToPickUpFromPos	= true;
									////for(NestPosition np : partsToGather)
									////{
										////if(l.laneNum == np.nestNumber && pos == np.nestPosition)
										////{
										////	System.out.println("\t\t\t\t\tPartsRobotAgent SAYS: I already have the part at this position in my list of partsToGather");
										////	okToPickUpFromPos	= false;
										////	break;
										////}
									////}
									////if(okToPickUpFromPos)
									////{
										System.out.println("\t\t\t\t\tPartsRobotAgent SAYS: I'm adding the part at this position to my list of partsToGather");
										currentPartPickUpFound	= true;
										l.nestContents.set(pos, NestPositionState.PICKUP_PENDING); //just added this
										partsToGather.add(new NestPosition(l.laneNum, pos));
										//System.out.println("BREAK");
										break;
									////}								
								}
							}
						}
					}
					if(currentPartPickUpFound)
					{
						break;
					}
				}
				/*
				if(!currentPartPickUpFound)
				{
					System.out.println("PartsRobotAgent SAYS: I need nest info from camera to proceed with this kit.");
					priorityKit.state	= KitOrderState.PARTS_NOT_FOUND;
					break;
				}
				*/
			}
			if(noPriorityAvailablePartsFound)
			{
				System.out.println("PartsRobotAgent SAYS: I need nest info from camera to proceed with this kit.");
				priorityKit.state	= KitOrderState.PARTS_NOT_FOUND;
			}
		}
		
		PartsRobotKitOrder	secondaryKit	= null;
		if(this.myKitOrders.size() > 1)
			secondaryKit	= this.myKitOrders.get(1);
		
		ArrayList<PartsRobotAgent.PartsRobotKitOrder.KitPartOrder>	secondaryKitNeededParts = new ArrayList<PartsRobotAgent.PartsRobotKitOrder.KitPartOrder>();
		if(secondaryKit != null)
		{
			System.out.println("PartsRobotAgent SAYS: secondary KitOrder is currently the following:");
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder po : secondaryKit.kitParts)
			{
				System.out.println("\t\t" + po.partType + ": " + po.state);
			}
			
			System.out.println("PartsRobotAgent SAYS: I'm looking for needed parts for secondary kit");
			System.out.println("PartsRobotAgent SAYS: I have " + this.myPartsInHand.size() + " parts in hand and " + this.myNumGrippers + " grippers total");
			for(int i = 0; ((secondaryKitNeededParts.size() < (this.myNumGrippers - (partsToGather.size() + this.myPartsInHand.size()))) && (i < secondaryKit.kitParts.size())); i++)
			{
				if(secondaryKit.kitParts.get(i).state == KitPartOrderState.NEEDED)
				{
					secondaryKitNeededParts.add(secondaryKit.kitParts.get(i));
				}
			}
		
			boolean	noAvailableSecondaryPartsFound	= true;
			System.out.println("PartsRobotAgent SAYS: I need " + secondaryKitNeededParts.size() + " parts for secondary kit");
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : secondaryKitNeededParts)
			{
				System.out.println("PartsRobotAgent SAYS: I'm looking for a lane where I can pick up a " + p.partType + " part.");
				boolean	currentPartPickUpFound	= false;
				for(PartsRobotLaneModel l : this.myLanes)
				{
					System.out.println("\tPartsRobotAgent SAYS: I'm looking at lane " + l.laneNum + " which has " + l.partType + " parts.");
					if(l.partType == p.partType)
					{
						System.out.println("\t\tPartsRobotAgent SAYS: I found a lane with the desired part type.");
						if(!l.noGoodPartsInNest)
						{
							System.out.println("\t\t\tPartsRobotAgent SAYS: I'm looking that has at least one good part.");
							for(int pos = 0; pos < l.nestContents.size(); pos++)
							{
								System.out.println("\t\t\t\tPartsRobotAgent SAYS: I'm looking at part with " + l.nestContents.get(pos) + " quality at Pos " + pos + " in lane " + l.laneNum);
								if(l.nestContents.get(pos) == NestPositionState.HOLDING_GOOD_PART)
								{
									boolean	okToPickUpFromPos	= true;
									for(NestPosition np : partsToGather)
									{
										if(l.laneNum == np.nestNumber && pos == np.nestPosition)
										{
											System.out.println("\t\t\t\t\tPartsRobotAgent SAYS: I already have the part at this position in my list of partsToGather");
											okToPickUpFromPos	= false;
											break;
										}
									}
									if(okToPickUpFromPos)
									{
										System.out.println("\t\t\t\t\tPartsRobotAgent SAYS: I'm adding the part at this position to my list of partsToGather");
										currentPartPickUpFound	= true;
										l.nestContents.set(pos, NestPositionState.PICKUP_PENDING); //just added this
										print("Nest position " + pos + " in lane number " + l.laneNum + " is good.");
										partsToGather.add(new NestPosition(l.laneNum, pos));
										break;
									}								
								}
							}
						}
					}
					if(currentPartPickUpFound)
					{
						break;
					}
				}
				/*
				if(!currentPartPickUpFound)
				{
					System.out.println("PartsRobotAgent SAYS: I need nest info from camera to proceed with this kit.");
					secondaryKit.state	= KitOrderState.PARTS_NOT_FOUND;
					break;
				}
				*/
			}
			if(noAvailableSecondaryPartsFound)
			{
				System.out.println("PartsRobotAgent SAYS: I need nest info from camera to proceed with this kit.");
				secondaryKit.state	= KitOrderState.PARTS_NOT_FOUND;
			}
		}
		
		if(!partsToGather.isEmpty())
		{
			for(NestPosition n : partsToGather)
			{
				System.out.println("PartsRobotAgent GUI COMMAND: Pick up from Lane " + n.nestNumber + ", pos " + n.nestPosition);
			}
			this.myActionState	= RobotActionState.PICKING_UP_PARTS;
			this.myPartsRobotGui.doPickUpPartsFromLanes(partsToGather);
		}
		else
		{
			System.out.println("PartsRobotAgent SAYS: I have nothing to pick up!");
		}
	}
	
	private void distributeParts()
	{
		setCurrentState("Distribute");
		System.out.println("PartsRobotAgent PERFORMING ACTION: distributeParts");
		
		ArrayList<KitPartPutInstruction>	partsToDistribute	= new ArrayList<KitPartPutInstruction>();
		
		PartsRobotKitOrder	priorityKit			= this.myKitOrders.get(0);
		if(priorityKit != null)
		{
			System.out.println("PartsRobot get rid of this print statement: priorityKit loc = " + priorityKit.kitStandLocation);
			ArrayList<PartType>	partsForPriorityKit	= new ArrayList<PartType>();
			
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : priorityKit.kitParts)
			{
				for(PartsRobotPartInHand h : this.myPartsInHand)
				{
					if(p.partType == h.partType && p.state == KitPartOrderState.NEEDED && h.state == PartInHandState.HOLDING)
					{
						System.out.println("DOING THIS 1");
						p.state	= KitPartOrderState.ACCEPTING;
						h.state	= PartInHandState.READY_TO_PUT_IN_KIT;
						partsForPriorityKit.add(h.partType);
						System.out.println(h.state);
					}
				}
			}
			
			if(!partsForPriorityKit.isEmpty())
			{
				System.out.println("!!!!!!!!" + priorityKit.kitStandLocation);
				partsToDistribute.add(new KitPartPutInstruction(partsForPriorityKit, priorityKit.kitStandLocation));
			}
		}
		
		if(this.myKitOrders.size() >= 2)
		{
			// (JY) Moved this line into the if statement and changed the if statement to check the size first
			// (if statement used to check existence of secondaryKit)
			PartsRobotKitOrder	secondaryKit			= this.myKitOrders.get(1);
			ArrayList<PartType>	partsForSecondaryKit	= new ArrayList<PartType>();
			
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : secondaryKit.kitParts)
			{
				for(PartsRobotPartInHand h : this.myPartsInHand)
				{
					if(p.partType == h.partType && p.state == KitPartOrderState.NEEDED && h.state == PartInHandState.HOLDING)
					{
						System.out.println("DOING THIS 2");
						p.state	= KitPartOrderState.ACCEPTING;
						h.state	= PartInHandState.READY_TO_PUT_IN_KIT;
						partsForSecondaryKit.add(h.partType);
						System.out.println(h.state);
					}
				}
			}
			
			if(!partsForSecondaryKit.isEmpty())
			{
				partsToDistribute.add(new KitPartPutInstruction(partsForSecondaryKit, secondaryKit.kitStandLocation));
			}
		}
		
		if(!partsToDistribute.isEmpty())
		{
			this.myActionState	= RobotActionState.DROPPING_OFF_PARTS;
			System.out.println("PartsRobotAgent: Telling gui to put parts in kit(s)");
			if(faultyRobot)
			{
				this.myPartsRobotGui.doBadTrip(partsToDistribute);
			}
			else
			{
				this.myPartsRobotGui.doPutPartsInKit(partsToDistribute);
			}
			return;
		}
		
		for(PartsRobotPartInHand h : this.myPartsInHand)
		{
			System.out.println(h.state);
		}
		
		ArrayList<PartType>	partsNotNeeded	= new ArrayList<PartType>();
		for(PartsRobotPartInHand h : this.myPartsInHand)
		{System.out.println(h.state);
			if(h.state == PartInHandState.HOLDING)
			{
				partsNotNeeded.add(h.partType);
			}
		}
		if(!partsNotNeeded.isEmpty())
		{
			this.myPartsRobotGui.doDropTheseParts(partsNotNeeded);
		}
		
		
		/**@TODO
		 * NEED TO THINK HARD ABOUT THIS. 
		 */
		/*
		int	currentKitLoc	= 0;
		while(currentKitLoc < this.myKitOrders.size())
		{
			ArrayList<PartType>	partsForCurrentKit	= new ArrayList<PartType>();
			
			for(PartsRobotAgent.PartsRobotKitOrder.KitPartOrder p : this.myKitOrders.get(currentKitLoc).kitParts)
			{
				for(PartsRobotPartInHand h : this.myPartsInHand)
				{
					if(p.partType == h.partType && p.state == KitPartOrderState.NEEDED && h.state == PartInHandState.HOLDING)
					{
						p.state	= KitPartOrderState.ACCEPTING;
						h.state	= PartInHandState.READY_TO_PUT_IN_KIT;
						partsForCurrentKit.add(h.partType);
					}
				}
			}
			
			if(!partsForCurrentKit.isEmpty())
			{
				this.myActionState	= RobotActionState.DROPPING_OFF_PARTS;
				System.out.println("PartsRobotAgent: Telling gui to put parts in kit " + (currentKitLoc+1));
				this.myPartsRobotGui.doPutPartsInKit(currentKitLoc+1, partsForCurrentKit);
				return;
			}
			else
			{
				currentKitLoc++;
				return;
			}
		}
		
		ArrayList<PartType>	partsNotNeeded	= new ArrayList<PartType>();
		for(PartsRobotPartInHand h : this.myPartsInHand)
		{
			if(h.state == PartInHandState.HOLDING)
			{
				partsNotNeeded.add(h.partType);
			}
		}
		this.myPartsRobotGui.doDropTheseParts(partsNotNeeded);
		//this.myActionState	= RobotActionState.DROPPING_OFF_PARTS;
		*/
	}
	
	private void informKitRobotOfCompletedKit(int kitStandLoc)
	{
		setCurrentState("Kit Completed");
		synchronized(this.myKitOrders)
		{
			for(int k = this.myKitOrders.size()-1; k >= 0; k--)
			{
				if(this.myKitOrders.get(k).kitStandLocation == kitStandLoc)
				{
					this.myKitOrders.get(k).state = KitOrderState.DONE;
					this.myKitOrders.remove(k);
					print("PartsRobotAgent SAYS: I'm informing my Kit Robot that the kit at stand loc " + kitStandLoc + " is full");
					this.myKitRobotAgent.msgThisKitIsFull(kitStandLoc);
				}
			}
		}
	}
	
	private void reset()
	{
		System.out.println("PartsRobotAgent PERFORMING ACTION: reset");
		setCurrentState("Stop Drop and ROll");
		myPartsRobotGui.doDropAllParts();
		myPartsRobotGui.doGoIdle();
	}
	
	private void goIdle()
	{
		System.out.println("PartsRobotAgent PERFORMING ACTION: goIdle");
		
		myPartsRobotGui.doGoIdle();
	}
	
	//
	// SETTERS & GETTERS //
	//
	
	public void setKitRobotAgent(KitRobot kitRobotAgent)
	{
		this.myKitRobotAgent	= kitRobotAgent;
	}
	
	public void setCameraSystemAgent(CameraSystem csa)
	{
		this.myCameraSystemAgent	= csa;
	}
	
	public void setCellManagerAgent(CellManagerAgent cma)
	{
		this.myManagerAgent	= cma;
	}
	
	public void setFaulty(boolean b)
	{
		this.faultyRobot	= b;
	}
	
	public void setGuiPartsRobot(PartsRobotActor partsRobotGui)
	{
		this.myPartsRobotGui	= partsRobotGui;
	}
	
	public String getAgentState()
	{
		return this.myActionState.toString();
	}
}


