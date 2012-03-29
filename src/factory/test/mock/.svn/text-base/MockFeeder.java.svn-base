/**
 * @author Jai Bapna
 */

package factory.test.mock;

import shared.Bin;
import shared.Part;
import shared.enums.PartType;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.test.mock.LoggedEvent;
import gui.components.GUIBin;
import gui.components.GUIFeeder;
import gui.components.GUIPart;

public class MockFeeder extends MockAgent implements Feeder {

	public MockFeeder(String name) {
		super(name);
	}

	public EventLog log = new EventLog();

	@Override
	public void msgLaneNearCapacity()
	{
		System.out.println("Received message msgLaneNearCapacity from lane");
		log.add(new LoggedEvent(
				"Received message msgLaneNearCapacity from lane"));
	}

	@Override
	public void msgDiverterReadyToReceive(Part part)
	{
		System.out.println("Received message msgDiverterReadyToReceive from diverter for " + part.type + ".");
		log.add(new LoggedEvent(
				"Received message msgDiverterReadyToReceive from diverter for " + part.type + "."));
	}

	@Override
	public void msgHoldOn()
	{
		System.out.println("Received message msgHoldOn from diverter" + ".");
		log.add(new LoggedEvent(
				"Received message msgHoldOn from diverter"));
	}

	@Override
	public void msgHereIsBin(Bin bin)
	{
		System.out.println("Received message msgHereIsBin from gantry");
		log.add(new LoggedEvent(
				"Received message msgHereIsBin from gantry"));
		
	}

	@Override
	public void msgSendMoreParts(Lane lane, PartType type)
	{
		log.add(new LoggedEvent(
				"Received message msgSendMoreParts from lane " + lane + " for type " + type + "."));
	}

	@Override
	public void msgGUIPartDelivered(GUIPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGUIRemovedPartsFromFeeder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GUIFeeder getGUIFeeder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setGUIFeeder(GUIFeeder guiFeeder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDonePlacingBin(GUIBin bin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGantryDonePurging() {
		System.out.println("Received msgGantryDonePurging from gantry");
		log.add(new LoggedEvent(
				"Received msgGantryDonePurging from gantry"));
		
	}

	@Override
	public void msgContinueSending()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean HasBin()
	{
		// TODO Auto-generated method stub
		return false;
	}


}
