package factory.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import org.junit.Test;

import factory.*;
import factory.PartsRobotAgent.KitOrderState;
import factory.PartsRobotAgent.PartsRobotKitOrder;
import factory.PartsRobotAgent.PartsRobotLaneModel;
import factory.PartsRobotAgent.PartsRobotKitOrder.KitPartOrder;
import factory.PartsRobotAgent.PartsRobotPartInHand;
import factory.PartsRobotAgent.RobotActionState;
import factory.PartsRobotAgent.RobotPartPossessionState;
import factory.interfaces.*;
import factory.test.mock.*;
import gui.components.GUINest;
import gui.components.GUIPart;
import gui.components.GUIPartsRobot;

import shared.KitConfig;
import shared.KitOrder;
import shared.NestPosition;
import shared.Part;
import shared.enums.PartQuality;
import shared.enums.PartType;

public class PartsRobotTests extends TestCase
{
//	public void testNormativeScenario()
//	{
//		PartsRobotAgent		partsRobotAgent		= new PartsRobotAgent("Parts Robot");
//		MockKitRobot		kitRobotAgent		= new MockKitRobot("Kit Robot");
//		MockCameraSystem	cameraSystemAgent	= new MockCameraSystem("Camera System");
//		MockNest			nest1Agent			= new MockNest("Nest 1");
//		MockNest			nest2Agent			= new MockNest("Nest 2");
//		PartsRobotActor		partsRobotGui	 	= new MockGuiPartsRobot("gui");
//		
//		partsRobotAgent.setKitRobotAgent(kitRobotAgent);
//		partsRobotAgent.setCameraSystemAgent(cameraSystemAgent);
//		partsRobotAgent.setGuiPartsRobot(partsRobotGui);
//		
//		//NOTHING HAS HAPPENED YET
//			
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should have no KitOrder representations. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//						partsRobotAgent.myKitOrders.isEmpty());
//			assertTrue("PartsRobotAgent should have no lane representations. Instead it has " + partsRobotAgent.myLanes.size() + " lanes",
//						partsRobotAgent.myLanes.isEmpty());
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//		
//			assertEquals(
//				"Mock Kit Robot should have an empty event log before the Part Robot's scheduler is called. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should have an empty event log before the Part Robot's scheduler is called. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//					partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should have no KitOrder representations. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//						partsRobotAgent.myKitOrders.isEmpty());
//			assertTrue("PartsRobotAgent should have no lane representations. Instead it has " + partsRobotAgent.myLanes.size() + " lanes",
//						partsRobotAgent.myLanes.isEmpty());
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//				
//		//Create a KitConfig
//		ArrayList<PartType>	configSpec		= new ArrayList<PartType>();
//    	configSpec.add(PartType.A);
//    	configSpec.add(PartType.B);
//    	KitConfig			initialConfig	= new KitConfig(configSpec);
//		
//    	//INITIAL CONFIGURATION IS PASSED IN
//    	partsRobotAgent.msgThisIsCurrentConfiguration(initialConfig);
//		
//			assertTrue("Initial KitConfig was intended to have 2 lanes", initialConfig.kitConfig.size() == 2);
//			assertTrue("PartsRobotAgent should have representations for 2 lanes", partsRobotAgent.myLanes.size() == 2);
//			for(int i = 0; i < initialConfig.kitConfig.size(); i++)
//			for(PartsRobotLaneModel l : partsRobotAgent.myLanes)
//				assertTrue("PartsRobotAgent lane-type representations should match up with KitConfig", partsRobotAgent.myLanes.get(i).partType == initialConfig.kitConfig.get(i));
//			
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//					partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should have no KitOrder representations. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//						partsRobotAgent.myKitOrders.isEmpty());
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//
//			assertEquals("Mock Kit Robot should have an empty event log before the Part Robot's scheduler is called. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should have an empty event log before the Part Robot's scheduler is called. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//					partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should have no KitOrder representations. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//						partsRobotAgent.myKitOrders.isEmpty());
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//	
//		//Create a kit order
//		KitOrder	order1	= new KitOrder();
//		ArrayList<PartType>	parts1	= new ArrayList<PartType>();
//		for(int i = 0; i < 4; i++)
//			parts1.add(PartType.A);
//		for(int i = 0; i < 4; i++)
//			parts1.add(PartType.B);
//		order1.setKitOrder(parts1);
//		
//		//KIT ROBOT PLACES NEW EMPTY KIT ON KIT STAND AND INFORMS PART ROBOT OF ORDER FOR THIS KIT
//		partsRobotAgent.msgHereIsANewKitOrderToFill(order1, 1);
//		
//			assertTrue("PartsRobotAgent should only have one representation of KitOrder", partsRobotAgent.myKitOrders.size() == 1);
//			PartsRobotKitOrder	k	= partsRobotAgent.myKitOrders.get(partsRobotAgent.myKitOrders.size()-1); 
//			assertTrue("PartsRobotAgent should have added representation of KitOrder at KitStand location 1", k.kitStandLocation == 1);
//			for(int i = 0; i < k.kitParts.size(); i++)
//				assertTrue("PartsRobotAgent's KitOrder should match the KitOrder given by KitRobot", k.kitParts.get(i).partType == order1.getKitOrder().get(i));
//			
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		partsRobotAgent.pickAndExecuteAnAction();
//		
//			assertTrue("PartsRobotAgent should have only one KitOrder representation. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//						partsRobotAgent.myKitOrders.size() == 1);
//			assertTrue("PartsRobotAgent should have changed state of its one KitOrder to PARTS_NOT_FOUND because it hasn't received info from the camera system yet. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.PARTS_NOT_FOUND);		
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//	
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals(
//				"Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		//Create simulated nest content camera results
//		ArrayList<PartQuality>	nest1Contents	= new ArrayList<PartQuality>();
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.BAD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.BAD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.GOOD);
//		nest1Contents.add(PartQuality.BAD);
//		ArrayList<PartQuality>	nest2Contents	= new ArrayList<PartQuality>();
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.BAD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.BAD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.GOOD);
//		nest2Contents.add(PartQuality.BAD);
//		
//		//CAMERA SYSTEM MESSAGES PARTSROBOT WITH CONTENTS OF 2 NESTS WHEN THEY HAVE BOTH BECOME FULL
//		partsRobotAgent.msgHereAreUpdatedNestContents(1, nest1Contents);
//		partsRobotAgent.msgHereAreUpdatedNestContents(2, nest2Contents);
//
//			assertTrue("PartsRobotAgent should have only one KitOrder representation. Instead it has " + partsRobotAgent.myKitOrders.size() + " KitOrders", 
//				partsRobotAgent.myKitOrders.size() == 1);
//			assertTrue("PartsRobotAgent should associate analyzed nest contents from camera with lane 1. Instead the representation of this nest's contents is " + partsRobotAgent.myLanes.get(0).nestContents, 
//						partsRobotAgent.myLanes.get(0).nestContents.equals(nest1Contents));
//			assertTrue("PartsRobotAgent should associate analyzed nest contents from camera with lane 2. Instead the representation of this nest's contents is " + partsRobotAgent.myLanes.get(0).nestContents, 
//					partsRobotAgent.myLanes.get(1).nestContents.equals(nest2Contents));
//			assertTrue("PartsRobotAgent should have changed the state of its one KitOrder back to a state representing its completion. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state,
//					partsRobotAgent.myKitOrders.get(0).state != KitOrderState.PARTS_NOT_FOUND);
//
//		//PARTS ROBOT AGENT SHOULD TELL THE GUI TO GO PICK UP PARTS
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should still believe its one KitOrder is EMPTY because it should've started getting parts for it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.EMPTY);		
//			assertTrue("PartsRobotAgent should be PICKING_UP_PARTS. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.PICKING_UP_PARTS);
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//					partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//					partsRobotAgent.myPartsInHand.isEmpty());
//	
//			assertEquals("Mock GUI Parts Robot should have received one message - to gather the specified parts. Instead, the Gui Parts Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						1, partsRobotGui.log.size());
//			assertTrue("Mock GUI Parts Robot should have been told to pick up parts. Instead, its log reads: " + partsRobotGui.log,
//						partsRobotGui.log.getLastLoggedEvent().getMessage().contains("doPickUpPartsFromLanes"));
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		//(SIMULATED) MESSAGE FROM GUI BACK TO AGENT WHEN IT HAS FINISHED ACTUALLY PICKING UP THE PARTS
//		ArrayList<PartType>	partsInHand	= new ArrayList<PartType>();
//		for(int i = 0; i < 4; i++)
//			partsInHand.add(PartType.A);
//		partsRobotAgent.msgDonePickUpFromLanes(partsInHand);			
//			
//			assertTrue("PartsRobotAgent should now believe its one KitOrder is EMPTY because it hasn't told the Gui to actually put parts in it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.EMPTY);		
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should believe to be holding parts since it told the Gui to pick some up. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should have 4 parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.size() == 4);
//	
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//	
//		//PARTS ROBOT AGENT SHOULD TELL THE GUI TO DROP THE PARTS OFF INTO THE PROPER KIT ON THE KIT STAND
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should believe its one KitOrder is EMPTY because it hasn't heard from the Gui if anything was successfully deposited in a kit. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.EMPTY);		
//			assertTrue("PartsRobotAgent should be DROPPING_OFF_PARTS. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.DROPPING_OFF_PARTS);
//			assertTrue("PartsRobotAgent should believe to be holding parts until it hears from Gui that they were deposited. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.size() == 4);
//	
//			assertEquals("Mock GUI Parts Robot should have received a second message - to distribute the specified parts to a kit. Instead, the Gui Parts Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						2, partsRobotGui.log.size());
//			assertTrue("Mock GUI Parts Robot should have been told to pick up parts. Instead, its log reads: " + partsRobotGui.log,
//						partsRobotGui.log.getLastLoggedEvent().getMessage().contains("doPutPartsInKit"));
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		//(SIMULATED) MESSAGE FROM GUI BACK TO THE AGENT TO TELL IT THAT IT HAS ACTUALLY DROPPED THE PARTS OFF IN THE KIT
//		ArrayList<PartType> partsStillInHand	= new ArrayList<PartType>();
//		ArrayList<PartType>	partsDeposited	= new ArrayList<PartType>();
//		for(int i = 0; i < 4; i++)
//			partsDeposited.add(PartType.A);
//		partsRobotAgent.msgDonePutPartsInKit(1, partsDeposited, partsStillInHand);			
//			
//			assertTrue("PartsRobotAgent should now believe its one KitOrder is PARTIALLY_FILLED because the Gui said it put parts in it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.PARTIALLY_FILLED);		
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should not believe to be holding parts since the Gui said it deposited them. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//		
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//////////////KIT IS FILLED WITH ALL THE A PARTS
//			/////////////
//			///////////
//			/////////
//			
//		//PARTS ROBOT AGENT SHOULD TELL THE GUI TO GO PICK UP PARTS
//		partsRobotAgent.pickAndExecuteAnAction();
//			
//			assertTrue("PartsRobotAgent should still believe its one KitOrder is PARTIALLY_FILLED because it is just about to get the rest of the parts for it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.PARTIALLY_FILLED);		
//			assertTrue("PartsRobotAgent should be PICKING_UP_PARTS. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.PICKING_UP_PARTS);
//			assertTrue("PartsRobotAgent should not believe to be holding parts. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//		
//			assertEquals("Mock GUI Parts Robot should have received another message - to gather the specified parts. Instead, the Gui Parts Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						3, partsRobotGui.log.size());
//			assertTrue("Mock GUI Parts Robot should have been told to pick up parts. Instead, its log reads: " + partsRobotGui.log,
//						partsRobotGui.log.getLastLoggedEvent().getMessage().contains("doPickUpPartsFromLanes"));
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//			
//		//(SIMULATED) MESSAGE FROM GUI BACK TO AGENT WHEN IT HAS FINISHED ACTUALLY PICKING UP THE PARTS
//		partsInHand	= new ArrayList<PartType>();
//		for(int i = 0; i < 4; i++)
//			partsInHand.add(PartType.B);
//		partsRobotAgent.msgDonePickUpFromLanes(partsInHand);			
//			
//			assertTrue("PartsRobotAgent should still believe its one KitOrder is only PARTIALLY_FILLED because it hasn't told the Gui to actually put parts in it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.PARTIALLY_FILLED);		
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should believe to be holding parts since it told the Gui to pick some up. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should have 4 parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.size() == 4);
//		
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//		
//		//PARTS ROBOT AGENT SHOULD TELL THE GUI TO DROP THE PARTS OFF INTO THE PROPER KIT ON THE KIT STAND
//		partsRobotAgent.pickAndExecuteAnAction();
//				
//			assertTrue("PartsRobotAgent should believe its one KitOrder is PARTIALLY_FILLED because it hasn't heard from the Gui if anything was successfully deposited in a kit. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.PARTIALLY_FILLED);		
//			assertTrue("PartsRobotAgent should be DROPPING_OFF_PARTS. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.DROPPING_OFF_PARTS);
//			assertTrue("PartsRobotAgent should believe to be holding parts until it hears from Gui that they were deposited. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.size() == 4);
//			assertEquals("Mock GUI Parts Robot should have received a second message - to distribute the specified parts to a kit. Instead, the Gui Parts Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						4, partsRobotGui.log.size());
//			assertTrue("Mock GUI Parts Robot should have been told to pick up parts. Instead, its log reads: " + partsRobotGui.log,
//						partsRobotGui.log.getLastLoggedEvent().getMessage().contains("doPutPartsInKit"));
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//				
//		//(SIMULATED) MESSAGE FROM GUI BACK TO THE AGENT TO TELL IT THAT IT HAS ACTUALLY DROPPED THE PARTS OFF IN THE KIT
//		partsStillInHand	= new ArrayList<PartType>();
//		partsDeposited	= new ArrayList<PartType>();
//		for(int i = 0; i < 4; i++)
//			partsDeposited.add(PartType.B);
//		partsRobotAgent.msgDonePutPartsInKit(1, partsDeposited, partsStillInHand);			
//			
//			assertTrue("PartsRobotAgent should now believe its one KitOrder is FULL because the Gui said it put the final parts in it. Instead its KitOrder state is " + partsRobotAgent.myKitOrders.get(0).state, 
//						partsRobotAgent.myKitOrders.get(0).state == KitOrderState.FULL);		
//			assertTrue("PartsRobotAgent should be in STANDBY. Instead it is in " + partsRobotAgent.myActionState, 
//						partsRobotAgent.myActionState == RobotActionState.STANDBY);
//			assertTrue("PartsRobotAgent should not believe to be holding parts since the Gui said it deposited them. Instead it believes it is " + partsRobotAgent.myPartPossessionState, 
//						partsRobotAgent.myPartPossessionState == RobotPartPossessionState.NOT_HOLDING_PARTS);
//			assertTrue("PartsRobotAgent should not have parts in its hand. Instead it has " + partsRobotAgent.myPartsInHand.size() + " parts in hand", 
//						partsRobotAgent.myPartsInHand.isEmpty());
//			assertEquals("Mock Kit Robot should not have been messaged yet. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						0, kitRobotAgent.log.size());
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());	
//		
//		partsRobotAgent.pickAndExecuteAnAction();
//		
//			assertEquals("Mock Kit Robot should been messaged to inform it that the kit order has been filled. Instead, the Mock Kit Robot's event log reads: " + kitRobotAgent.log.toString(), 
//						1, kitRobotAgent.log.size());
//			assertTrue("Mock Kit Robot should have been messaged to inform it that the kit order has been filled." + kitRobotAgent.log,
//						kitRobotAgent.log.getLastLoggedEvent().getMessage().contains("msgHereIsAFullKit"));
//			assertEquals("Mock Camera System should not have been message yet. Instead, the Mock Camera System's event log reads: " + cameraSystemAgent.log.toString(), 
//						0, cameraSystemAgent.log.size());
//	}
}
