package factory;

import java.util.ArrayList;

import agent.Agent;

import shared.KitConfig;
import shared.KitOrder;
import shared.enums.PartQuality;
import shared.enums.PartType;
import factory.interfaces.CameraSystem;
import factory.interfaces.KitRobot;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;
import gui.components.GUICamera;
import gui.components.GUIPart;
/**
 * The Camera System Agent is responsible for instructing all of the GUICameras.
 * These are the four which belong to the eight nests, and the one at the inspection
 * stand.
 * @author Elias
 *
 */
public class CameraSystemAgent extends Agent implements CameraSystem 
{
	//DATA
	
	PartsRobot		 		partsRobotAgent;								// Reference to the parts robot agent
	KitRobot 				kitRobotAgent;									// Reference to the kit robot agent
	CellManagerAgent		cellManager;									// Reference to my cell manager agent
	GUICamera 				guiCamera1;										// Reference to my gui camera by nests 1 and 2
	GUICamera 				guiCamera2;										// Reference to my gui camera by nests 3 and 4
	GUICamera 				guiCamera3;										// Reference to my gui camera by nests 5 and 6
	GUICamera 				guiCamera4;										// Reference to my gui camera by nests 7 and 8
	GUICamera				guiKitCamera;									// Reference to the gui camera by the kit
	KitOrder 				robotKitOrder;									// Reference to the kit order for the kit robot picture
	ArrayList<PartType> 	missingParts = new ArrayList<PartType>();		// Reference to the list that the kit camera sends me of missing parts
	
	/**
	 * CameraActionState enum, list of states that the inspection stand camera can
	 * be in.
	 */
	public enum CameraActionState
	{
		NONE,IDLE,TAKE_KIT_PICTURE,KIT_GOOD,KIT_BAD,KIT_MISSING
	}
	
	CameraActionState 		state;											// Local reference to my action state enum
	
	/**
	 * The nest action state is the enum which is the list of states that the nests can be in.
	 *
	 */
	enum NestActionState
	{
		NONE,IDLE,NEEDS_PICTURE,HAS_PICTURE,ANALYZED_FULL,DUMP_PARTS
	}
	
	/**
	 * NestClass is a class which represents the entire interaction for two nests and a camera.
	 * @author Elias
	 *
	 */
	public class NestClass
	{
		int nestNum1;															// The nest number of the top nest
		int nestNum2;															// The nest number of the bottom nest
		Nest nestAgent1;														// Reference to the first nest agent
		Nest nestAgent2;														// Reference to the second nest agent
		GUICamera guiCamera;													// Reference to the GUICamera of the nests
		PartType partType1;														// Part type of the top nest
		PartType partType2;														// Part type of the second nest
		ArrayList<GUIPart> partsNest1 = new ArrayList<GUIPart>();				// The list of parts contained in the first nest
		ArrayList<GUIPart> partsNest2 = new ArrayList<GUIPart>();				// The list of parts contained in the second nest
		ArrayList<PartQuality> qualityList1 = new ArrayList<PartQuality>();		// The quality list of the first nest
		ArrayList<PartQuality> qualityList2 = new ArrayList<PartQuality>();		// The quality list of the first nest
		NestActionState nestState1;												// The NestActionState of the first nest
		NestActionState nestState2;												// The NestActionState of the second nest
	}
	ArrayList<NestClass> nests = new ArrayList<NestClass>();					// ArrayList of type NestClass with the four nest interactions
	
	// CONSTRUCTOR:
	
	public CameraSystemAgent(String n)
	{
		super();
		this.name = n;
	}
	
	//SETTER METHODS:
	/**
	 * Two nests are set at a time. This must be set first! Example: 
	 * camera.setNests(1, nest1, nest2);
	 * camera.setNests(2, nest3, nest4);
	 * etc.
	 * @param num
	 * @param n
	 */
	public void setNests(int num, Nest n1, Nest n2)
	{
		NestClass nest = new NestClass();
		
		nest.nestAgent1 = n1;
		nest.nestAgent2 = n2;
		
		nest.nestNum1 = num;
		nest.nestNum2 = num + 1;
		
		nests.add(nest);
	}
	/**
	 * Setter for the cell manager agent
	 * @param ca
	 */
	public void setCellManagerAgent(CellManagerAgent ca)
	{
		cellManager = ca;
	}
	/**
	 * Link to the GUICamera
	 * @param c
	 */
	public void setCameraGui1(GUICamera c) 
	{
		nests.get(0).guiCamera = c;
	}
	public void setCameraGui2(GUICamera c) 
	{
		nests.get(1).guiCamera = c;
	}
	public void setCameraGui3(GUICamera c) 
	{
		nests.get(2).guiCamera = c;
	}
	public void setCameraGui4(GUICamera c) 
	{
		nests.get(3).guiCamera = c;
	}
	/**
	 * Setter for the kit camera gui
	 * @param c
	 */
	public void setKitCamera(GUICamera c)
	{
		guiKitCamera = c;
	}
	/**
	 * Link to the KitRobotAgent
	 * @param kr
	 */
	public void setKitRobotAgent(KitRobot kr)
	{
		kitRobotAgent = kr;
	}
	/**
	 * Link to the PartsRobotAgent
	 * @param pr
	 */
	public void setPartsRobotAgent(PartsRobot pr)
	{
		partsRobotAgent = pr;
	}
	
	//GETTER METHODS:
	/**
	 * Returns the name 
	 */
	public String getName()
	{
		return this.name;
	}
	/**
	 * Returns the state of the camera
	 * @return
	 */
	public CameraActionState getCameraState()
	{
		return state;
	}
	public String getAgentState()
	{
		return "Not yet implemented";
	}
	
	//MESSAGES:
	
	/**
	 * The message IAmFull is sent from the nest when it is filled with parts. It passes its own reference and a list of
	 * the parts it has.
	 * @param nest
	 * @param parts
	 */
	public void msgIAmFull(Nest nest, ArrayList<GUIPart> parts)
	{
		print("Received message I am full from nest agent");
		
		for (int i = 0; i < nests.size(); i++)
		{
			if (nest == nests.get(i).nestAgent1)
			{
				nests.get(i).partsNest1 = parts;
				nests.get(i).nestState1 = NestActionState.NEEDS_PICTURE;
			}
			else if (nest == nests.get(i).nestAgent2)
			{
				nests.get(i).partsNest2 = parts;
				nests.get(i).nestState2 = NestActionState.NEEDS_PICTURE;
			}
		}
		stateChanged();
	}	
	/**
	 * This message is sent from the camera gui after it is finished analyzing the lists. The Camera Agent
	 * does a check to see if all of the parts in a list are bad. If they are, then it sends the appropriate nest 
	 * the dump parts message. Then it sends back two lists of PartQuality for each of the nests (1 and 2)
	 * @param list1
	 * @param list2
	 */
	public void msgHereIsNestList(ArrayList<PartQuality> list1, ArrayList<PartQuality> list2, GUICamera gc)
	{
		print("Received message here is nest list from gui.");
		
		if (list1.size() != 12 || list2.size() != 12)
		{
			print("*******************************************************************************");
			print("The camera produced a bad quality list! Not forwarding this to the parts robot.");
			print("*******************************************************************************");
			this.state = CameraActionState.NONE;
		}
		else
		{
			for (int i = 0; i < nests.size(); i++)
			{
				if (gc == nests.get(i).guiCamera)
				{
					nests.get(i).qualityList1 = list1;
					nests.get(i).qualityList2 = list2;
					
					// If all of the parts are bad, then instruct the nest to dump its parts
					if (checkQualityList(list1))
					{
						nests.get(i).nestAgent1.msgDumpParts();
						nests.get(i).nestState1 = NestActionState.IDLE;
					}
					else 
					{
						boolean missingPartsFound = false;
						for (int j = 0; j < list1.size(); j++)
						{
							if (list1.get(j) == PartQuality.MISSING)
							{
								print("Nest is not full");
								nests.get(i).nestAgent1.msgNestNeedsMoreParts();
								missingPartsFound = true;
								break;
							}
						}
						if (!missingPartsFound)
						{
							nests.get(i).nestState1 = NestActionState.HAS_PICTURE;
						}
					}
					
					// If all of the parts are bad, then instruct the nest to dump its parts
					if (checkQualityList(list2))
					{
						nests.get(i).nestAgent2.msgDumpParts();
						nests.get(i).nestState2 = NestActionState.IDLE;
					}
					else
					{
						boolean missingPartsFound = false;
						for (int j = 0; j < list2.size(); j++)
						{
							if (list2.get(j) == PartQuality.MISSING)
							{
								print("Nest is not full");
								nests.get(i).nestAgent2.msgNestNeedsMoreParts();
								missingPartsFound = true;
								break;
							}
						}
						if (!missingPartsFound)
						{
							nests.get(i).nestState2 = NestActionState.HAS_PICTURE;
						}
					}
				}
			}
		}
		stateChanged();
	}
	/**
	 *  Private method which returns the boolean true if all of the members of the quality list are bad
	 * @param qualityList
	 * @return
	 */
	private boolean checkQualityList(ArrayList<PartQuality> qualityList)
	{
		int j = 0;
		for (int i = 0; i < qualityList.size(); i++)
		{
			if (qualityList.get(i) == PartQuality.BAD)
			{
				j++;
				if (j == qualityList.size())
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This message is called by the KitRobotAgent when the kit is ready to be analyzed by the camera
	 */
	public void msgTakeKitPicture(KitOrder k)
	{
		print("Received message take kit picture.");
		robotKitOrder = k;
		this.state = CameraActionState.TAKE_KIT_PICTURE;
		stateChanged();
	}
	/**
	 * This message is sent by the camera gui after it is finished analyzing the kit. This message means the
	 * kit is good.
	 */
	public void msgKitIsGood()
	{
		print("Received message kit is good.");
		this.state = CameraActionState.KIT_GOOD;
		stateChanged();
	}
	
	/**
	 * Non-normative message from GUI
	 * @param p
	 */
	public void msgKitHasMissingParts(ArrayList<PartType> p)
	{
		StringBuilder s = new StringBuilder();
		s.append("Received message that the kit has missing parts");
		s.append(" " + missingParts.size());
		
		// Clear elements of current list
		if (missingParts.size() != 0)
		{
			missingParts.clear();
		}
		// Copy the elements of the msising parts list to my local array list (missingParts)
		synchronized(missingParts){
			s.append(": ");
			for (int i = 0; i < p.size(); i++)
			{
				s.append(p.get(i) + " ");
				missingParts.add(p.get(i));
			}
		}
		
		print(s.toString());
		this.state = CameraActionState.KIT_MISSING;
		stateChanged();
	}
	
	/**
	 * Sent by Parts Robot Agent when the nest is has no more good parts
	 */
	public void msgDoneTakingGoodPartsFromNest(int num)
	{
		print("Received message from Parts Robot that there are no more good parts in the nest");
		
		if (num == 1)
		{
			nests.get(0).nestState1 = NestActionState.DUMP_PARTS;
		}
		else if (num == 2)
		{
			nests.get(0).nestState2 = NestActionState.DUMP_PARTS;
		}
		else if (num == 3)
		{
			nests.get(1).nestState1 = NestActionState.DUMP_PARTS;
		}
		else if (num == 4)
		{
			nests.get(1).nestState2 = NestActionState.DUMP_PARTS;
		}
		else if (num == 5)
		{
			nests.get(2).nestState1 = NestActionState.DUMP_PARTS;
		}
		else if (num == 6)
		{
			nests.get(2).nestState2 = NestActionState.DUMP_PARTS;
		}
		else if (num == 7)
		{
			nests.get(3).nestState1 = NestActionState.DUMP_PARTS;
		}
		else if (num == 8)
		{
			nests.get(3).nestState2 = NestActionState.DUMP_PARTS;
		}
		stateChanged();
	}	
	
	/**
	 * This message is sent by the camera gui after it is finished analyzing the kit. This message means the
	 * kit is bad.
	 */
	public void msgKitIsBad()
	{
		print("Received message kit is bad.");
		this.state = CameraActionState.KIT_BAD;
		stateChanged();
	}
	
	/**
	 * Message from the Nest agent sent when it goes from full to not full
	 */
	public void msgIAmNoLongerFull(Nest n)
	{
		
		for (int i = 0; i < nests.size(); i++)
		{
			if (nests.get(i).nestAgent1 == n)
			{
				print("Nest in index " + i + " (first one) is no longer full.");
				nests.get(i).nestState1 = NestActionState.IDLE;
			}
			else if (nests.get(i).nestAgent2 == n)
			{
				print("Nest in index " + i + " (second one) is no longer full.");
				nests.get(i).nestState2 = NestActionState.IDLE;
			}
		}
	}
	
	public void msgThisIsCurrentConfiguration(KitConfig k)
	{
		print("Received a new configuration from the cell manager agent.");
		
		// Update configurations for each nest
		nests.get(0).partType1 = k.kitConfig.get(0);
		nests.get(0).partType1 = k.kitConfig.get(1);
		nests.get(1).partType1 = k.kitConfig.get(2);
		nests.get(1).partType1 = k.kitConfig.get(3);
		nests.get(2).partType1 = k.kitConfig.get(4);
		nests.get(2).partType1 = k.kitConfig.get(5);
		nests.get(3).partType1 = k.kitConfig.get(6);
		nests.get(3).partType1 = k.kitConfig.get(7);
		
		// Send guiCameras the updated kit configuration
		
		for (int i = 0; i < nests.size(); i++)
		{
			nests.get(i).guiCamera.setPartTypeForNests(nests.get(i).partType1, nests.get(i).partType2);
		}
		
		// Send back msgIAmProperlyConfigured()
		cellManager.msgIAmProperlyConfigured(this);
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction()
	{
		// Loop through the nest list
		for (int i = 0; i < nests.size(); i++)
		{
			if (nests.get(i).nestState1 == NestActionState.IDLE && nests.get(i).nestState2 == NestActionState.IDLE)
			{
				// Tell the appropriate camera to go idle
				goIdle(i);
				nests.get(i).nestState1 = NestActionState.NONE;
				nests.get(i).nestState2 = NestActionState.NONE;
			}
			if ( (nests.get(i).nestState1 == NestActionState.NEEDS_PICTURE && nests.get(i).nestState2 == NestActionState.NEEDS_PICTURE)
					|| (nests.get(i).nestState1 == NestActionState.NEEDS_PICTURE && nests.get(i).nestState2 == NestActionState.HAS_PICTURE)
					|| (nests.get(i).nestState1 == NestActionState.HAS_PICTURE && nests.get(i).nestState2 == NestActionState.NEEDS_PICTURE))
			{
				// Tell the appropriate camera to take a picture
				takeNestPicture(i);
				// Wait for the gui to return the quality lists
				nests.get(i).nestState1 = NestActionState.NONE;
				nests.get(i).nestState2 = NestActionState.NONE;
				return true;
			}
			if (nests.get(i).nestState1 == NestActionState.DUMP_PARTS)
			{
				// Instruct the nest to dump its parts
				dumpParts(i, 1);
				nests.get(i).nestState1 = NestActionState.IDLE;
				return true;
			}
			if (nests.get(i).nestState2 == NestActionState.DUMP_PARTS)
			{
				dumpParts(i, 2);
				nests.get(i).nestState2 = NestActionState.IDLE;
				return true;
			}
			if (nests.get(i).nestState1 == NestActionState.HAS_PICTURE && nests.get(i).nestState2 == NestActionState.HAS_PICTURE)
			{
				// Sends nest analysis to the parts robot
				nestAnalysisComplete(i);
				return true;
			}
		}
		if (this.state == CameraActionState.TAKE_KIT_PICTURE)
		{
			takeKitPicture();
			this.state = CameraActionState.IDLE;
			return true;
		}
		if (this.state == CameraActionState.KIT_BAD) 
		{
			giveKitStatus(false);
			this.state = CameraActionState.IDLE;
			return true;
		}
		if (this.state == CameraActionState.KIT_GOOD) 
		{
			giveKitStatus(true);
			this.state = CameraActionState.IDLE;
			return true;
		}
		if (this.state == CameraActionState.KIT_MISSING)
		{
			giveKitMissing();
			this.state = CameraActionState.IDLE;
			return true;
		}
		print("Scheduler has nothing to do.");
		return false;
	}
	
	//Actions
	private void goIdle(int num)
	{
		nests.get(num).guiCamera.goIdle();
	}
	
	private void takeNestPicture(int num)
	{
		print("Told gui " + num + " to take nest picture");
		nests.get(num).guiCamera.takeNestPicture(nests.get(num).partsNest1, nests.get(num).partsNest2);
		
	}
	private void nestAnalysisComplete(int num)
	{
		print("Giving parts robot updated nest contents.");
		if (num == 0)
		{
			setCurrentState("Nests1-2 analyzed");
			partsRobotAgent.msgHereAreUpdatedNestContents(1, nests.get(num).qualityList1);
			partsRobotAgent.msgHereAreUpdatedNestContents(2, nests.get(num).qualityList2);
		}
		else if (num == 1)
		{
			setCurrentState("Nests3-4 analyzed");
			partsRobotAgent.msgHereAreUpdatedNestContents(3, nests.get(num).qualityList1);
			partsRobotAgent.msgHereAreUpdatedNestContents(4, nests.get(num).qualityList2);
		}
		else if (num == 2)
		{
			setCurrentState("Nests5-6 analyzed");

			partsRobotAgent.msgHereAreUpdatedNestContents(5, nests.get(num).qualityList1);
			partsRobotAgent.msgHereAreUpdatedNestContents(6, nests.get(num).qualityList2);
		}
		else if (num == 3)
		{
			setCurrentState("Nests7-8 analyzed");
			
			partsRobotAgent.msgHereAreUpdatedNestContents(7, nests.get(num).qualityList1);
			partsRobotAgent.msgHereAreUpdatedNestContents(8, nests.get(num).qualityList2);
		}
		nests.get(num).nestState1 = NestActionState.ANALYZED_FULL;
		nests.get(num).nestState2 = NestActionState.ANALYZED_FULL;
	}
	private void takeKitPicture()
	{
		print("Told gui to take kit picture.");
		setCurrentState("Shooting KitStand");
		guiKitCamera.takeKitPicture(robotKitOrder);
	}
	private void giveKitStatus(boolean kitIsGood)
	{
		print("Giving kit robot kit status.");
		if (kitIsGood)
		{
			setCurrentState("Kit Good");
			kitRobotAgent.msgKitIsGood();
		}
		else
		{
			setCurrentState("Kit Bad");
			kitRobotAgent.msgKitIsBad();
		}
		this.state = CameraActionState.IDLE;
	}
	private void giveKitMissing()
	{
		print("Sending msgMissingParts to KitRobotAgent");
		kitRobotAgent.msgMissingParts(missingParts);
	}
	private void dumpParts(int index, int num)
	{
		if (num == 1)
		{
			nests.get(index).nestAgent1.msgDumpParts();
		}
		else 
		{
			nests.get(index).nestAgent2.msgDumpParts();
		}
	}
}