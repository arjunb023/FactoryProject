package factory.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import shared.KitOrder;
import shared.Part;
import shared.enums.PartType;

import factory.CameraSystemAgent;
import factory.DiverterAgent;
import factory.interfaces.*;
import factory.test.mock.*;
import gui.components.GUICamera;
import gui.components.GUIDiverter;
import gui.components.GUIKit;
import gui.components.GUIKitStand;
import gui.components.GUIPart;


public class CameraSystemTests extends TestCase
{

	/**
	 * Test the camera, nest, parts robot interaction in part two.
	 */
	@Test
	public void testPartTwo()
	{
		int scheduleCounter = 0;
		System.out.println("-----------------------\nTesting Part Two:\n-----------------------\n");
		
		//Set and associate all components of interaction
		CameraSystem 			camera = new CameraSystemAgent("Camera");
		GUICamera 				guiCamera = new GUICamera();
		
		MockNest 				nest1 = new MockNest("Nest1");
		MockNest 				nest2 = new MockNest("Nest2");
		MockPartsRobotAgent 	partsRobot = new MockPartsRobotAgent("Parts Robot");
		
		camera.setCameraGui1(guiCamera);
		//camera.setNest(1, nest1);
		//camera.setNest(2, nest2);
		camera.setPartsRobotAgent(partsRobot);
		guiCamera.setCameraSystemAgent(camera);
		
		assertEquals("Mock Nest1 should have an empty event log before its scheduler is called",0,nest1.log.size());
		assertEquals("Mock Nest2 should have an empty event log before its scheduler is called",0,nest2.log.size());
		assertEquals("Mock Parts Robot should have an empty event log before its scheduler is called",0,partsRobot.log.size());
		
		//Fill up the nest
		ArrayList<GUIPart> list1 = new ArrayList<GUIPart>();
		ArrayList<GUIPart> list2 = new ArrayList<GUIPart>();
		
		GUIPart p = new GUIPart(PartType.A);
		
		//Add 6 pieces to each nest (=6)
		for (int i = 0; i < 6; i++)
		{
			list1.add(p);
			list2.add(p);
		}
		
		assertFalse("The nests should not be full.",nest1.getFullState());
		assertFalse("The nests should not be full.",nest2.getFullState());
		
		//Camera should have nothing to do.
		camera.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		//Add 6 more pieces to each nest (=12)
		for (int i = 0; i < 6; i++)
		{
			list1.add(p);
			list2.add(p);
		}
		
		nest1.msgIAmFull(list1, 1);
		nest2.msgIAmFull(list2, 2);
	
		//send the camera the I am full message from each nest 
		camera.msgIAmFull(nest1, list1);
	   	camera.msgIAmFull(nest2, list2);
	   	
	   	System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		assertEquals("Mock Nest1 should have one even in its log since it is now full",1,nest1.log.size());
		assertEquals("Mock Nest2 should have one even in its log since it is now full",1,nest2.log.size());
		assertEquals("Mock Parts Robot should have an empty event log before its scheduler is called",0,partsRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		assertEquals("Mock Nest1 should have one even in its log since it is now full",1,nest1.log.size());
		assertEquals("Mock Nest2 should have one even in its log since it is now full",1,nest2.log.size());
		assertEquals("Mock Parts Robot should have an empty event log before its scheduler is called",0,partsRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();

		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("-----------------------\nEnd of Part Two Test\n-----------------------\n");
	}
	/**
	 * Testing part three with the KitRobot, Camera, Cart interaction. The kit may be good or bad, since the
	 * parts are randomly set to be GOOD or BAD. This means that most of the time, the test will fail, but it
	 * should occasionally.
	 */
	@Test
	public void testPartThreeGoodKit()
	{
		int scheduleCounter = 0;
		System.out.println("-----------------------\nTesting Part Three (SOMETIMES GOOD):\n-----------------------\n");
		
		//Set and associate all components of interaction
		CameraSystem 			camera = new CameraSystemAgent("Camera");
		GUICamera 				guiCamera = new GUICamera();
		
		GUIKitStand				guiKitStand = new GUIKitStand(0,0);
		MockKitRobot 			kitRobot = new MockKitRobot("Kit Robot");
		MockCart				cart = new MockCart("Cart");
		
		camera.setCameraGui1(guiCamera);
		camera.setKitRobotAgent(kitRobot);
		guiCamera.setCameraSystemAgent(camera);
		guiCamera.setKitStand(guiKitStand);
		
		assertEquals("Mock Kit Robot should have an empty log since its scheduler hasn't yet been called.",0,kitRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		//Make the kit order {A, B, C, F, G}
		ArrayList<PartType> order = new ArrayList<PartType>();
		order.add(PartType.A);
		order.add(PartType.B);
		order.add(PartType.C);
		order.add(PartType.F);
		order.add(PartType.G);
		//Create kit order
		KitOrder k = new KitOrder();
		//add the order array to the kit order k
		k.setKitOrder(order);
		
		//Create GUI parts of type {A, B, C, F, G} (matching the order)
		GUIPart pa = new GUIPart(PartType.A);
		GUIPart pb = new GUIPart(PartType.B);
		GUIPart pc = new GUIPart(PartType.C);
		GUIPart pf = new GUIPart(PartType.F);
		GUIPart pg = new GUIPart(PartType.G);
		//Create a GUIKit with 5 slots
		GUIKit kit = new GUIKit(0, 0, 0, 5);
		//add the parts to the new kit
		kit.addPart(pa);
		kit.addPart(pb);
		kit.addPart(pc);
		kit.addPart(pf);
		kit.addPart(pg);
		//put the new on the kit stand
		guiKitStand.putKitOnStand(kit, 0);
		
		
		
		//kit robot take picture call with kit order k
		camera.msgTakeKitPicture(k);
		
		assertEquals("Mock Kit Robot should have an empty log since its scheduler hasn't yet been called.",0,kitRobot.log.size());
		
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		assertEquals("Mock Kit Robot should have a log of size one since the camera gave it the kit status.",1,kitRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();	
		
		System.out.println("-----------------------\nFinished Testing Part Three (SOMETIMES GOOD):\n-----------------------\n");
	}
	/**
	 * Testing part three with the KitRobot, Camera, Cart interaction. The kit will always be bad, since the
	 * order does not match the kit. This means that most of the time, the test will always fail.
	 */
	@Test
	public void testPartThreeBadKit()
	{
		int scheduleCounter = 0;
		System.out.println("-----------------------\nTesting Part Three (BAD TEST):\n-----------------------\n");
		
		//Set and associate all components of interaction
		CameraSystem 			camera = new CameraSystemAgent("Camera");
		GUICamera 				guiCamera = new GUICamera();
		
		GUIKitStand				guiKitStand = new GUIKitStand(0,0);
		MockKitRobot 			kitRobot = new MockKitRobot("Kit Robot");
		MockCart				cart = new MockCart("Cart");
		
		camera.setCameraGui1(guiCamera);
		camera.setKitRobotAgent(kitRobot);
		guiCamera.setCameraSystemAgent(camera);
		guiCamera.setKitStand(guiKitStand);
		
		assertEquals("Mock Kit Robot should have an empty log since its scheduler hasn't yet been called.",0,kitRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		//Make the kit order {A, B, D, F, G}
		ArrayList<PartType> order = new ArrayList<PartType>();
		order.add(PartType.A);
		order.add(PartType.B);
		order.add(PartType.D);
		order.add(PartType.F);
		order.add(PartType.G);
		//Create kit order
		KitOrder k = new KitOrder();
		//add the order array to the kit order k
		k.setKitOrder(order);
		
		//Create GUI parts of type {A, B, C, F, G} (NOT matching the order)
		GUIPart pa = new GUIPart(PartType.A);
		GUIPart pb = new GUIPart(PartType.B);
		GUIPart pc = new GUIPart(PartType.C);
		GUIPart pf = new GUIPart(PartType.F);
		GUIPart pg = new GUIPart(PartType.G);
		//Create a GUIKit with 5 slots
		GUIKit kit = new GUIKit(0, 0, 0, 5);
		//add the parts to the new kit
		kit.addPart(pa);
		kit.addPart(pb);
		kit.addPart(pc);
		kit.addPart(pf);
		kit.addPart(pg);
		//put the new on the kit stand
		guiKitStand.putKitOnStand(kit, 0);
		
		//kit robot take picture call with kit order k
		camera.msgTakeKitPicture(k);
		
		assertEquals("Mock Kit Robot should have an empty log since its scheduler hasn't yet been called.",0,kitRobot.log.size());
		
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		assertEquals("Mock Kit Robot should have a log of size one since the camera gave it the kit status.",1,kitRobot.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		camera.pickAndExecuteAnAction();
		
		System.out.println("-----------------------\nFinished Testing Part Three (BAD TEST):\n-----------------------\n");
	}

}