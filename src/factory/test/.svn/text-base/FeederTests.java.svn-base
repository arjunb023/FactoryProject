/**
 * @author Jai Bapna
 */

package factory.test;

import org.junit.Test;

import shared.enums.PartType;

import factory.FeederAgent;
import factory.test.mock.MockDiverter;
import factory.test.mock.MockGantry;
import factory.test.mock.MockLane;
import gui.components.GUIDiverter;
import gui.components.GUIFeeder;
import gui.components.GUIGantry;
import gui.components.GUILane;
import gui.components.GUINest;
import junit.framework.TestCase;

public class FeederTests extends TestCase {

	/**
	 * This is the FeederAgent to be tested.
	 */

	private MockGantry gantry;
	private FeederAgent feeder;
	private MockDiverter diverter;
	private MockLane lane1;
	private MockLane lane2;
	private GUIGantry guiGantry;
	private GUIFeeder guiFeeder;
	private GUIDiverter guiDiverter;
	private GUILane guiLane1;
	private GUILane guiLane2;
	
	public FeederTests() {		

		gantry = new MockGantry("Gantry");
		feeder = new FeederAgent("Feeder");
		diverter = new MockDiverter("Diverter");
		lane1 = new MockLane("Lane1");
		lane2 = new MockLane("Lane2");
		
		guiGantry = new GUIGantry("guiGantry");
		guiFeeder = new GUIFeeder("guiFeeder");
		guiDiverter = new GUIDiverter();
		guiLane1 = new GUILane(1,2,3,new GUINest(1, 2, 3));
		guiLane2 = new GUILane(1,2,3,new GUINest(1, 2, 3));
		
		gantry.setGUIGantry(guiGantry);
		feeder.setGUIFeeder(guiFeeder);
		diverter.setGuiDiverter(guiDiverter);
		lane1.setGuiLane(guiLane1);
		lane2.setGuiLane(guiLane2);
		
		guiGantry.setGantryAgent(gantry);
		guiFeeder.setFeederAgent(feeder);
		feeder.setDiverterAgent(diverter);
		feeder.setGantryAgent(gantry);
		guiLane1.setLaneAgent(lane1);
		guiLane2.setLaneAgent(lane2);
		
		assertEquals(
				"Mock Gantry should have an empty event log before the Diverter's scheduler is called. Instead, the mock feeder's event log reads: "
						+ gantry.log.toString(), 0, gantry.log.size());
		assertEquals(
				"Mock Lane 1 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane1.log.toString(), 0, lane1.log.size());
		assertEquals(
				"Mock Lane 2 should have an empty event log before the Diverter's scheduler is called. Instead, the mock lane's event log reads: "
						+ lane2.log.toString(), 0, lane2.log.size());
	}
	
	/**
	 * 
	 */
	@Test
	public void testGantryReceivesFeederBinRequest() {
		feeder.msgSendMoreParts(lane1, PartType.A);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		
		assertTrue(
			"Mock gantry should have received msgGiveMeThisPart. Event log: "
			+ gantry.log.toString(), gantry.log.getLastLoggedEvent().getMessage().contains(
			"Received msgGiveMeThisPart from feeder for type '" + PartType.A.toString() + "'"));

		
	}
	
	@Test
	public void testPurge() {
		feeder.msgSendMoreParts(lane1, PartType.A);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		
		assertTrue(
				"Mock gantry should have received msgGiveMeThisPart. Event log: "
				+ gantry.log.toString(), gantry.log.getLastLoggedEvent().getMessage().contains(
				"Received msgGiveMeThisPart from feeder for type '" + PartType.A.toString() + "'"));
		
		feeder.msgGUIRemovedPartsFromFeeder();
		feeder.pickAndExecuteAnAction();
		
		assertTrue(
				"Mock gantry should have received msgPurgeThisBin. Event log: "
				+ gantry.log.toString(), gantry.log.getLastLoggedEvent().getMessage().contains(
				"Received msgPurgeThisBin from feeder for bin '" + PartType.A.toString() + "'"));
	}
	

}
