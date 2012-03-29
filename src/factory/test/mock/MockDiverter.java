package factory.test.mock;

import shared.Part;
import shared.enums.DiverterDirection;
import factory.FeederAgent;
import factory.interfaces.Diverter;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.test.mock.LoggedEvent;
import gui.components.GUIDiverter;

public class MockDiverter extends MockAgent implements Diverter
{

	public MockDiverter(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgSetDiverterOrientation(DiverterDirection direction)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGuiDoneChangingOrientation()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPartReady(Part part)
	{
		System.out.println("Received msgPartReady from feeder for " + part.type);
		log.add(new LoggedEvent(
				"Received msgPartReady from feeder for " + part.type));
	}

	@Override
	public void msgHereIsPart(Part part)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGuiDoneMovingPart()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgReadyToReceive(Part part)
	{
		log.add(new LoggedEvent(
				"msgReadyToReceive from lane for " + part.getName()));
	}

	@Override
	public void msgIAmFull()
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
	public void setGuiDiverter(GUIDiverter guiDiverter)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeederAgent(Feeder feederAgent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLaneAgent1(Lane laneAgent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLaneAgent2(Lane laneAgent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFlipDiverterOrientation()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public DiverterDirection getOrientation()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
