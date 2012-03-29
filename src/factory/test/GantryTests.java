/**
 * @author Jai Bapna
 */

package factory.test;

import java.util.ArrayList;

import org.junit.Test;

import shared.Bin;
import shared.enums.PartType;
import factory.GantryAgent;
import factory.test.mock.MockFeeder;
import gui.components.GUIFeeder;
import gui.components.GUIGantry;
import junit.framework.TestCase;

public class GantryTests extends TestCase {

	/**
	 * This is the GantryAgent to be tested.
	 */
	private GantryAgent gantry;
	private MockFeeder feeder;
   	private GUIGantry guiGantry;
	private GUIFeeder guiFeeder;
	private ArrayList<Bin> gantryBins = new ArrayList<Bin>() {
		{
			add(new Bin(PartType.A));
			add(new Bin(PartType.B));
			add(new Bin(PartType.C));
			add(new Bin(PartType.D));
			add(new Bin(PartType.E));
			add(new Bin(PartType.F));
			add(new Bin(PartType.G));
			add(new Bin(PartType.H));			
		}
	};   	
	public GantryTests() {
		gantry = new GantryAgent("Gantry");
		feeder = new MockFeeder("feeder");
		
		guiGantry = new GUIGantry("guiGantry");
    	guiFeeder = new GUIFeeder("feeder");
    	
		gantry.setGUIGantry(guiGantry);
		gantry.setGantryBins(gantryBins);
		feeder.setGUIFeeder(guiFeeder);
		
    	guiGantry.setGantryAgent(gantry);
    	guiFeeder.setFeederAgent(feeder);
    	
	}

	/**
	 * 
	 */
	@Test
	public void testGantryGetNewBin() {
		gantry.msgGiveMeThisPart(PartType.A, feeder);
		gantry.pickAndExecuteAnAction();
		gantry.msgGUIDelieveredBinToFeeder(gantry.getBin(0).guiBin);
		gantry.pickAndExecuteAnAction();
		
		assertTrue(
				"Feeder should have received msgHereIsBin. Event log: "
						+ feeder.log.toString(), feeder.log
						.containsString("Received message msgHereIsBin from gantry"));
		
		System.out.println("-------------Test 1 Successfully Passed------------------");
		
	}
	
	public void testGantryPurgeParts() {
		gantry.msgPurgeThisBin(gantry.getBin(0));
		gantry.pickAndExecuteAnAction();
		gantry.msgGUIDonePurging(gantry.getBin(0).guiBin);
		gantry.pickAndExecuteAnAction();
		feeder.msgGantryDonePurging();
		
		assertTrue(
				"Feeder should have received msgGantryDonePurging. Event log: "
						+ feeder.log.toString(), feeder.log
						.containsString("Received msgGantryDonePurging from gantry"));
		
		System.out.println("-------------Test 2 Successfully Passed------------------");
	}

}
