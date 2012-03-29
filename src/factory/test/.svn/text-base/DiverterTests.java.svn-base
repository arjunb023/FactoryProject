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

import factory.DiverterAgent;
import factory.interfaces.*;
import factory.test.mock.*;
import gui.components.GUIDiverter;

/**
 * This is a set of tests for the normal Diverter interaction cycle.
 * 
 * @author Duke Yin
 * 
 */
public class DiverterTests extends TestCase
{

	/**
	 * 
	 */
	@Test
	public void testOnePart()
	{
		int scheduleCounter = 0;
		System.out.println("------------------------------\n" +
		"Diverter testOnePart():");
		
		Diverter diverter = new DiverterAgent("Test Agent1");
		GUIDiverter guiDiverter = new GUIDiverter();

		MockFeeder feeder = new MockFeeder("Feeder1");
		MockLane lane1 = new MockLane("Lane1");
		MockLane lane2 = new MockLane("Lane2");

		diverter.setGuiDiverter(guiDiverter);
		diverter.setFeederAgent(feeder);
		diverter.setLaneAgent1(lane1);
		diverter.setLaneAgent2(lane2);
		guiDiverter.setDiverterAgent(diverter);
		
		assertEquals(
				"Mock Feeder should have an empty event log before the Diverter's scheduler is called. Instead, the mock feeder's event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 0, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
		
		// create a part used for testing
		Part part1 = new Part(PartType.A);
		
		diverter.msgPartReady(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgReadyToReceive. Event log: "
					+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
						"msgDiverterReadyToReceive from diverter for " + part1.type));
		
		diverter.msgHereIsPart(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 1 message, should not have received another here. Event log: "
						+ feeder.log.toString(), 1, feeder.log.size());
		
		diverter.msgGuiDoneMovingPart();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgPartReady. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgPartReady from diverter for " + part1.type));
		
		diverter.msgReadyToReceive(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgHereIsPart. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsPart from diverter for " + part1.type));

		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 1 message, should not have received another here. Event log: "
						+ feeder.log.toString(), 1, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have 2 messages. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 2, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
		
		System.out.println("END OF TEST");	
	}

	@Test
	public void testTwoPartsAndImFull()
	{
		int scheduleCounter = 0;
		System.out.println("------------------------------\n" +
				"Diverter testTwoPartsAndImFull():");
		
		Diverter diverter = new DiverterAgent("Test Agent1");
		GUIDiverter guiDiverter = new GUIDiverter();

		MockFeeder feeder = new MockFeeder("Feeder1");
		MockLane lane1 = new MockLane("Lane1");
		MockLane lane2 = new MockLane("Lane2");

		diverter.setGuiDiverter(guiDiverter);
		diverter.setFeederAgent(feeder);
		diverter.setLaneAgent1(lane1);
		diverter.setLaneAgent2(lane2);
		guiDiverter.setDiverterAgent(diverter);
		
		assertEquals(
				"Mock Feeder should have an empty event log before the Diverter's scheduler is called. Instead, the mock feeder's event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 0, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
		
		// create a part used for testing
		Part part1 = new Part(PartType.A);
		
		diverter.msgPartReady(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgReadyToReceive. Event log: "
						+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
								"msgDiverterReadyToReceive from diverter for " + part1.type));
		
		diverter.msgHereIsPart(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 1 message, should not have received another here. Event log: "
						+ feeder.log.toString(), 1, feeder.log.size());

		Part part2 = new Part(PartType.B);
		
		diverter.msgPartReady(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();

		diverter.msgGuiDoneMovingPart();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgPartReady. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgPartReady from diverter for " + part1.type));
		
		diverter.msgReadyToReceive(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgHereIsPart. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsPart from diverter for " + part1.type));

		// second part here
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgReadyToReceive. Event log: "
						+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
								"msgDiverterReadyToReceive from diverter for " + part2.type));

		diverter.msgHereIsPart(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 2 messages, should not have received another here. Event log: "
						+ feeder.log.toString(), 2, feeder.log.size());

		diverter.msgGuiDoneMovingPart();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgPartReady. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgPartReady from diverter for " + part2.type));
		
		diverter.msgReadyToReceive(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgHereIsPart. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsPart from diverter for " + part2.type));

		
		
		
		assertEquals(
				"Feeder should have only 2 messages, should not have received another here. Event log: "
						+ feeder.log.toString(), 2, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have 2 messages. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 4, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
		
		System.out.println("END OF TEST");
	}
	

	@Test
	public void testLeftRightSwitch()
	{
		int scheduleCounter = 0;
		System.out.println("------------------------------\n" +
				"Diverter testLeftRightSwitch():");
		
		Diverter diverter = new DiverterAgent("Test Agent1");
		GUIDiverter guiDiverter = new GUIDiverter();

		MockFeeder feeder = new MockFeeder("Feeder1");
		MockLane lane1 = new MockLane("Lane1");
		MockLane lane2 = new MockLane("Lane2");

		diverter.setGuiDiverter(guiDiverter);
		diverter.setFeederAgent(feeder);
		diverter.setLaneAgent1(lane1);
		diverter.setLaneAgent2(lane2);
		guiDiverter.setDiverterAgent(diverter);
		
		assertEquals(
				"Mock Feeder should have an empty event log before the Diverter's scheduler is called. Instead, the mock feeder's event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 0, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
		
		// create a part used for testing
		Part part1 = new Part(PartType.A);
		
		diverter.msgPartReady(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgReadyToReceive. Event log: "
						+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
								"msgDiverterReadyToReceive from diverter for " + part1.type));
		
		diverter.msgHereIsPart(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 1 message, should not have received another here. Event log: "
						+ feeder.log.toString(), 1, feeder.log.size());
		
		diverter.msgGuiDoneMovingPart();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgPartReady. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgPartReady from diverter for " + part1.type));

		diverter.msgReadyToReceive(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgHereIsPart. Event log: "
					+ lane1.log.toString(), lane1.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsPart from diverter for " + part1.type));
		
		// flip
		diverter.msgFlipDiverterOrientation();
		Part part2 = new Part(PartType.B);
		diverter.msgPartReady(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		
		
		diverter.msgGuiDoneChangingOrientation();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();


		// second part here
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock feeder should have received msgReadyToReceive. Event log: "
						+ feeder.log.toString(), feeder.log.getLastLoggedEvent().getMessage().contains(
								"msgDiverterReadyToReceive from diverter for " + part2.type));

		diverter.msgHereIsPart(part2);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertEquals(
				"Feeder should have only 2 messages, should not have received another here. Event log: "
						+ feeder.log.toString(), 2, feeder.log.size());

		diverter.msgGuiDoneMovingPart();
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgPartReady. Event log: "
					+ lane2.log.toString(), lane2.log.getLastLoggedEvent().getMessage().contains(
						"msgPartReady from diverter for " + part2.type));
		
		diverter.msgReadyToReceive(part1);
		System.out.println("SCHEDULER CALLED " + ++scheduleCounter);
		diverter.pickAndExecuteAnAction();
		assertTrue(
				"Mock lane should have received msgHereIsPart. Event log: "
					+ lane2.log.toString(), lane2.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsPart from diverter for " + part2.type));

		
		
		
		assertEquals(
				"Feeder should have only 2 messages, should not have received another here. Event log: "
						+ feeder.log.toString(), 2, feeder.log.size());
		assertEquals(
				"Mock Lane 1 should have 2 messages. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 2, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 2, lane2.log.size());
		
		System.out.println("END OF TEST");
	}
}
