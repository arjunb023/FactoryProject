package factory.test.mock;

import shared.Part;
import shared.enums.PartType;
import factory.interfaces.Diverter;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import gui.components.GUILane;
import gui.components.GUIPart;

public class MockLane extends MockAgent implements Lane
{

	public MockLane(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgPartReady(Part part)
	{
		log.add(new LoggedEvent(
				"msgPartReady from diverter for " + part.type));
	}

	@Override
	public void msgHereIsPart(Part part)
	{
		log.add(new LoggedEvent(
				"msgHereIsPart from diverter for " + part.type));
	}

	@Override
	public void msgGuiDoneMovingPart(GUIPart guipart)
	{
		log.add(new LoggedEvent(
				"msgGuiDoneMovingPart " + guipart + " from GUILane"));
	}

	@Override
	public void msgGuiLaneAlmostFull()
	{
		log.add(new LoggedEvent(
				"msgGuiLaneAlmostFull from GUILane"));
	}

	@Override
	public void msgGuiLaneNoLongerFull()
	{
		log.add(new LoggedEvent(
				"msgGuiLaneNoLongerFull from GUILane"));
	}
	
	@Override
	public void msgReadyToReceive()
	{
		log.add(new LoggedEvent(
				"msgReadyToReceive from nest"));
	}

	@Override
	public void msgIAmFull()
	{
		log.add(new LoggedEvent(
				"msgIAmFull from nest"));
	}

	@Override
	public void setDiverterAgent(Diverter diverterAgent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNestAgent(Nest nestAgent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeederAgent(Feeder feederAgent)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setGuiLane(GUILane guiLane)
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
	public void setPartType(PartType type)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public PartType getPartType()
	{
		// TODO Auto-generated method stub
		return null;
	}


}
