/**
 * 
 */
package factory.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import shared.Part;
import shared.enums.PartType;

import factory.LaneAgent;
import factory.interfaces.Lane;
import factory.test.mock.MockDiverter;
import factory.test.mock.MockFeeder;
import factory.test.mock.MockNest;
import gui.components.GUIDiverter;
import gui.components.GUILane;
import gui.components.GUINest;

/**
 * This is a set of tests for the normal Lane interaction cycle.
 * 
 * @author Duke Yin
 * 
 */
public class LaneTests extends TestCase
{

	/**
	 * 
	 */
	@Test
	public void testReceiveTwoParts()
	{
		int scheduleCounter = 0;
		System.out.println("------------------------------\n" +
		"testReceiveTwoParts():");
		
		Lane lane = new LaneAgent("Lane1");
		GUILane guiLane = new GUILane(1,2,3,new GUINest(1, 2, 3));

		MockFeeder feeder = new MockFeeder("Feeder1");
		MockDiverter diverter = new MockDiverter("Diverter1");
		MockNest nest = new MockNest("Nest1");
		
		lane.setFeederAgent(feeder);
		lane.setDiverterAgent(diverter);
		lane.setNestAgent(nest);
		lane.setGuiLane(guiLane);
		guiLane.setLaneAgent(lane);
		
		assertEquals(
				"Mock Feeder should have an empty event log before the Lane's scheduler is called. Instead, the Mock Feeder's event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		assertEquals(
				"Mock Diverter should have an empty event log before the Lane's scheduler is called. Instead, the Mock Diverter's event log reads: "
						+ diverter.log.toString(), 0, diverter.log.size());
		assertEquals(
				"Mock Nest should have an empty event log before the Lane's scheduler is called. Instead, the Mock Nest's event log reads: "
						+ nest.log.toString(), 0, nest.log.size());
		
		// create a part used for testing
		Part part1 = new Part(PartType.A);
		
		lane.msgPartReady(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock diverter should have received msgReadyToReceive. Event log: "
						+ diverter.log.toString(), diverter.log.getLastLoggedEvent().getMessage().contains(
							"msgReadyToReceive from lane for " + part1.getName()));
		
		lane.msgHereIsPart(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertEquals(
				"Diverter should have only 1 message, should not have received another here. Event log: "
						+ diverter.log.toString(), 1, diverter.log.size());


		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		// GUI Lane should move the parts on the lane here
		
		// create a part used for testing
		Part part2 = new Part(PartType.D);
		
		lane.msgPartReady(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock diverter should have received msgReadyToReceive. Event log: "
						+ diverter.log.toString(), diverter.log.getLastLoggedEvent().getMessage().contains(
							"msgReadyToReceive from lane for " + part2.getName()));
		
		lane.msgHereIsPart(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertEquals(
				"Diverter should have only 2 messages, should not have received another here. Event log: "
						+ diverter.log.toString(), 2, diverter.log.size());
		
		lane.msgGuiDoneMovingPart(part1.guiPart);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock nest should have received msgPartReady. Event log: "
						+ nest.log.toString(), nest.log.getLastLoggedEvent().getMessage().contains(
							"msgPartReady"));
		
		lane.msgReadyToReceive();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock nest should have received msgHereIsPart. Event log: "
						+ nest.log.toString(), nest.log.getLastLoggedEvent().getMessage().contains(
							"msgHereIsPart"));
		
		
		System.out.println("END OF TEST");
	}
	
	public void testLaneFull()
	{
		//WIP
		
		int scheduleCounter = 0;
		System.out.println("------------------------------\n" +
		"testLaneFull():");
		
		Lane lane = new LaneAgent("Lane1");
		GUILane guiLane = new GUILane(1,2,3,new GUINest(1, 2, 3));
		
		MockFeeder feeder = new MockFeeder("Feeder1");
		MockDiverter diverter = new MockDiverter("Diverter1");
		MockNest nest = new MockNest("Nest1");

		lane.setFeederAgent(feeder);
		lane.setDiverterAgent(diverter);
		lane.setNestAgent(nest);
		lane.setGuiLane(guiLane);
		guiLane.setLaneAgent(lane);
		
		assertEquals(
				"Mock Feeder should have an empty event log before the Lane's scheduler is called. Instead, the Mock Feeder's event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		assertEquals(
				"Mock Diverter should have an empty event log before the Lane's scheduler is called. Instead, the Mock Diverter's event log reads: "
						+ diverter.log.toString(), 0, diverter.log.size());
		assertEquals(
				"Mock Nest should have an empty event log before the Lane's scheduler is called. Instead, the Mock Nest's event log reads: "
						+ nest.log.toString(), 0, nest.log.size());
		
		for (int pn = 0; pn < 7; pn++)
		{
			// create a part used for testing
			Part part1 = new Part(PartType.A);
			
			lane.msgPartReady(part1);
			System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
			lane.pickAndExecuteAnAction();
			assertTrue(
					"Mock diverter should have received msgReadyToReceive. Event log: "
							+ diverter.log.toString(), diverter.log.getLastLoggedEvent().getMessage().contains(
								"msgReadyToReceive from lane for " + part1.type));
			
			lane.msgHereIsPart(part1);
			System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
			lane.pickAndExecuteAnAction();
			
		}

		lane.msgGuiLaneAlmostFull();
		System.out.println("SCHEDULER CALLED **");
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgHoldOn. Event log: "
						+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
							"msgHoldOn"));

		// create a part used for testing
		Part part2 = new Part(PartType.B);
		
		lane.msgPartReady(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		// gui lane should move all its parts
		
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		// gui lane should move all its parts
		
		lane.msgGuiLaneNoLongerFull();
		System.out.println("SCHEDULER CALLED ***");
		lane.pickAndExecuteAnAction();
		assertTrue(
				"Mock diverter should have received msgReadyToReceive. Event log: "
						+ diverter.log.toString(), diverter.log.getLastLoggedEvent().getMessage().contains(
							"msgReadyToReceive from lane for " + part2.type));
		
		lane.msgHereIsPart(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		

		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		lane.pickAndExecuteAnAction();
		// GUI Lane should move the parts on the lane here
		
		System.out.println("END OF TEST");
	}
}
