package factory.test;

import java.util.ArrayList;

import org.junit.Test;

import shared.Part;
import shared.enums.PartType;
import factory.DiverterAgent;
import factory.NestAgent;
import factory.interfaces.Diverter;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.test.mock.MockCameraSystem;
import factory.test.mock.MockFeeder;
import factory.test.mock.MockLane;
import gui.components.GUIDiverter;
import gui.components.GUILane;
import gui.components.GUINest;
import gui.components.GUIPart;
import junit.framework.TestCase;


public class NestTests extends TestCase 
{
	/**
	 * Testing the part one interaction between the lane and nest. In this scenario, the nest is ready to 
	 * receive parts.
	 */
	@Test
	public void testPartOneFree()
	{
		int scheduleCounter = 0;
		System.out.println("-----------------------\nTesting Part One (FREE):\n-----------------------\n");
		
		Nest 				nest = new NestAgent("Nest");
		MockLane			lane = new MockLane("Lane");
		MockCameraSystem	camera = new MockCameraSystem("Dummy");		//creating dummy camera system to avoid null pointers
		GUINest				guiNest = new GUINest(0, 0, 12);
		
		nest.setCameraSystemAgent(camera);
		nest.setGuiNest(guiNest);
		nest.setLaneAgent(lane);
		guiNest.setNestAgent(nest);
		
		assertEquals("Mock Lane should have an empty event log before its scheduler is called",0,lane.log.size());
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		nest.msgPartReady(lane);
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		//mock part for reception
		GUIPart p = new GUIPart(PartType.A);
		
		nest.msgHereIsPart(p);
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		System.out.println("\n-----------------------\nFinished Testing Part One (FREE):\n-----------------------\n");
	}
	/**
	 * Testing the part one interaction between the lane and nest. In this scenario, the nest is full and not ready to 
	 * receive parts. I manually remove a part to make it ready.
	 */
	@Test
	public void testPartOneFull()
	{
		int scheduleCounter = 0;
		System.out.println("-----------------------\nTesting Part One (FULL):\n-----------------------\n");
		
		Nest 				nest = new NestAgent("Nest");
		MockLane			lane = new MockLane("Lane");
		MockCameraSystem	camera = new MockCameraSystem("Dummy");		//creating dummy camera system to avoid null pointers
		GUINest				guiNest = new GUINest(0, 0, 12);
		GUILane				guiLane	= new GUILane(0, 0, 0, guiNest);
		
		
		nest.setCameraSystemAgent(camera);
		nest.setGuiNest(guiNest);
		nest.setLaneAgent(lane);
		guiNest.setNestAgent(nest);
		
		GUIPart p1 = new GUIPart(PartType.A);
		GUIPart p2 = new GUIPart(PartType.A);
		GUIPart p3 = new GUIPart(PartType.A);
		GUIPart p4 = new GUIPart(PartType.A);
		GUIPart p5 = new GUIPart(PartType.A);
		GUIPart p6 = new GUIPart(PartType.A);
		GUIPart p7 = new GUIPart(PartType.A);
		GUIPart p8 = new GUIPart(PartType.A);
		GUIPart p9 = new GUIPart(PartType.A);
		GUIPart p10 = new GUIPart(PartType.A);
		GUIPart p11 = new GUIPart(PartType.A);
		GUIPart p12 = new GUIPart(PartType.A);	
		
		ArrayList<GUIPart> partsInc = new ArrayList<GUIPart>();
		partsInc.add(p1);
		partsInc.add(p2);
		partsInc.add(p3);
		partsInc.add(p4);
		partsInc.add(p5);
		partsInc.add(p6);
		partsInc.add(p7);
		partsInc.add(p8);
		partsInc.add(p9);
		partsInc.add(p10);
		partsInc.add(p11);
		partsInc.add(p12);
		
		//set nest to be full
		guiNest.fillNest(partsInc);
		
		assertEquals("Mock Lane should have an empty event log before its scheduler is called",0,lane.log.size());
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		nest.msgPartReady(lane);

		assertEquals("Mock Lane should have a log of size one since it received the I am full response",1,lane.log.size());
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		assertEquals("Mock Lane should have a log of size one since it received the I am full response",2,lane.log.size());
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		System.out.println("Removing a part from the nest to make room for one more");
		guiNest.getPart(0);
		nest.msgReadyToReceive();
		nest.msgHereIsPart(p11);
		
		assertEquals("Mock Lane should have a log of size one since it received the I am full response",3,lane.log.size());
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		nest.pickAndExecuteAnAction();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		
		
		System.out.println("\n-----------------------\nFinished Testing Part One (FULL):\n-----------------------\n");
	}
	/**
	 * This tests the camera, nest interaction in part two. The nest is initialized to full and tells the camera to 
	 * take a picture, sending the camera its list. 
	 */
	@Test
	public void testPartTwo()
	{
		System.out.println("\n-----------------------\nTesting Part Two:\n-----------------------\n");
		
		int scheduleCounter = 0;
		Nest 				nest = new NestAgent("Nest");
		MockLane			lane = new MockLane("Dummy");
		MockCameraSystem	camera = new MockCameraSystem("Camera");		//creating dummy camera system to avoid null pointers
		GUINest				guiNest = new GUINest(0, 0, 12);
		
		nest.setCameraSystemAgent(camera);
		nest.setGuiNest(guiNest);
		nest.setLaneAgent(lane);
		guiNest.setNestAgent(nest);
		
		GUIPart p1 = new GUIPart(PartType.A);
		GUIPart p2 = new GUIPart(PartType.A);
		GUIPart p3 = new GUIPart(PartType.A);
		GUIPart p4 = new GUIPart(PartType.A);
		GUIPart p5 = new GUIPart(PartType.A);
		GUIPart p6 = new GUIPart(PartType.A);
		GUIPart p7 = new GUIPart(PartType.A);
		GUIPart p8 = new GUIPart(PartType.A);
		GUIPart p9 = new GUIPart(PartType.A);
		GUIPart p10 = new GUIPart(PartType.A);
		GUIPart p11 = new GUIPart(PartType.A);
		GUIPart p12 = new GUIPart(PartType.A);	
		
		ArrayList<GUIPart> partsInc = new ArrayList<GUIPart>();
		partsInc.add(p1);
		partsInc.add(p2);
		partsInc.add(p3);
		partsInc.add(p4);
		partsInc.add(p5);
		partsInc.add(p6);
		partsInc.add(p7);
		partsInc.add(p8);
		partsInc.add(p9);
		partsInc.add(p10);
		partsInc.add(p11);
		partsInc.add(p12);
		
		assertEquals("Mock Camera should have an empty event log before its scheduler is called",0,camera.log.size());
		
		//set nest to be full
		guiNest.fillNest(partsInc);

		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		nest.pickAndExecuteAnAction();
		
		assertEquals("Mock Camera should have an event log of size one since it received the nest list.",1,camera.log.size());
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		nest.pickAndExecuteAnAction();
		
		System.out.println("\n-----------------------\nFinished Testing Part Two:\n-----------------------\n");
	}
	/**
	 * This tests the camera, nest interaction in part two. The nest is initialized to full and tells the camera to 
	 * take a picture, sending the camera its list. The camera sends back the dump parts message
	 */
	@Test
	public void testPartTwoDump()
	{
		System.out.println("\n-----------------------\nTesting Part Two (DUMP):\n-----------------------\n");
		
		int scheduleCounter = 0;
		Nest 				nest = new NestAgent("Nest");
		MockLane			lane = new MockLane("Dummy");				//creating dummy lane to avoid null pointers
		MockCameraSystem	camera = new MockCameraSystem("Camera");		
		GUINest				guiNest = new GUINest(0, 0, 12);
		
		nest.setCameraSystemAgent(camera);
		nest.setGuiNest(guiNest);
		nest.setLaneAgent(lane);
		guiNest.setNestAgent(nest);
		
		GUIPart p1 = new GUIPart(PartType.A);
		GUIPart p2 = new GUIPart(PartType.A);
		GUIPart p3 = new GUIPart(PartType.A);
		GUIPart p4 = new GUIPart(PartType.A);
		GUIPart p5 = new GUIPart(PartType.A);
		GUIPart p6 = new GUIPart(PartType.A);
		GUIPart p7 = new GUIPart(PartType.A);
		GUIPart p8 = new GUIPart(PartType.A);
		GUIPart p9 = new GUIPart(PartType.A);
		GUIPart p10 = new GUIPart(PartType.A);
		GUIPart p11 = new GUIPart(PartType.A);
		GUIPart p12 = new GUIPart(PartType.A);	
		
		ArrayList<GUIPart> partsInc = new ArrayList<GUIPart>();
		partsInc.add(p1);
		partsInc.add(p2);
		partsInc.add(p3);
		partsInc.add(p4);
		partsInc.add(p5);
		partsInc.add(p6);
		partsInc.add(p7);
		partsInc.add(p8);
		partsInc.add(p9);
		partsInc.add(p10);
		partsInc.add(p11);
		partsInc.add(p12);
		
		assertEquals("Mock Camera should have an empty event log before its scheduler is called",0,camera.log.size());
		
		//set nest to be full
		guiNest.fillNest(partsInc);

		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		nest.pickAndExecuteAnAction();
		
		assertEquals("Mock Camera should have an event log of size one since it received the nest list.",1,camera.log.size());
		
		nest.msgDumpParts();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		nest.pickAndExecuteAnAction();
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		nest.pickAndExecuteAnAction();
		
		System.out.println("\n-----------------------\nFinished Testing Part Two (DUMP):\n-----------------------\n");
	}
	
	
}
