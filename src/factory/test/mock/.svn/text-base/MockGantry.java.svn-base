/**
 * @author Jai Bapna
 */

package factory.test.mock;

import shared.Bin;
import shared.enums.PartType;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import gui.components.GUIBin;
import gui.components.GUIGantry;

public class MockGantry extends MockAgent implements Gantry {

	public MockGantry(String name) {
		super(name);
	}

	public EventLog log = new EventLog();
	
	@Override
	public void msgPurgeThisBin(Bin bin) {
		System.out.println("Received msgPurgeThisBin from feeder for bin '" + bin.getPartType().toString() + "'");
		log.add(new LoggedEvent(
				"Received msgPurgeThisBin from feeder for bin '" + bin.getPartType().toString() + "'"));
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGUIGantry(GUIGantry guiGantry) {
		// TODO Auto-generated method stub
	}

	@Override
	public void msgGUIDelieveredBinToFeeder(GUIBin bin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGUIDonePurging(GUIBin bin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGUIDoneGettingNewBin(GUIBin bin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGiveMeThisPart(PartType type, Feeder feeder) {
		System.out.println("Received msgGiveMeThisPart from feeder for type '" + type.toString() + "'");
		log.add(new LoggedEvent(
				"Received msgGiveMeThisPart from feeder for type '" + type.toString() + "'"));
		feeder.msgHereIsBin(new Bin(type));
	}

	@Override
	public void msgSetBinOnFire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGUIDoneSettingBinOnFire(GUIBin bin) {
		// TODO Auto-generated method stub
		
	}

}
