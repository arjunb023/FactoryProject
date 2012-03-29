package factory.test.mock;

import java.util.ArrayList;

import shared.KitConfig;
import shared.Part;
import factory.LaneAgent;
import factory.interfaces.CameraSystem;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import gui.components.GUINest;
import gui.components.GUIPart;

public class MockNest extends MockAgent implements Nest
{

	public MockNest(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();


	@Override
	public void msgReadyToReceive()
	{
		log.add(new LoggedEvent(
				"msgReadyToReceive from lane"));
	}

	@Override
	public void msgDumpParts()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFullState()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgHereIsPart(GUIPart p)
	{
		log.add(new LoggedEvent(
			"msgHereIsPart from lane for guipart '" + p + "'"));
	}

	@Override
	public void msgIAmFull(ArrayList<GUIPart> p, int num)
	{
		log.add(new LoggedEvent(
				"msgIAmFull"));
		
	}

	@Override
	public void setGuiNest(GUINest guiNest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLaneAgent(Lane la) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCameraSystemAgent(CameraSystem cs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNestNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void msgPartReady(Lane la)
	{
		log.add(new LoggedEvent(
			"msgPartReady from lane"));
	}

	@Override
	public void msgThisIsCurrentConfiguration(KitConfig k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNestNum(int num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIAmNoLongerFull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIAmEmpty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgTheFinalPurge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPartAdded(GUIPart p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPartComing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgNestNeedsMoreParts() {
		// TODO Auto-generated method stub
		
	}


}
