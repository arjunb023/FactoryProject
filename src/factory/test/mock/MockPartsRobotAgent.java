package factory.test.mock;

import java.util.ArrayList;

import shared.KitConfig;
import shared.KitOrder;
import shared.NestPosition;
import shared.enums.PartQuality;
import shared.enums.PartType;
import factory.interfaces.PartsRobot;

public class MockPartsRobotAgent extends MockAgent implements PartsRobot
{

	public MockPartsRobotAgent(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgHereAreUpdatedNestContents(int laneNum,
			ArrayList<PartQuality> nestContents)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsANewKitOrderToFill(KitOrder newKitOrder,
			int kitStandLoc)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgThisIsCurrentConfiguration(KitConfig newKitConfig)
	{
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public void msgDonePickUpFromLanes(ArrayList<PartType> partsInHand)
	{
		// TODO Auto-generated method stub
		
	}
*/
	
	@Override
	public void msgDonePutPartsInKit(int kitStandLoc,ArrayList<PartType> depositedartTypes, ArrayList<PartType> stillHoldingPartTypes)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneDroppingParts(ArrayList<PartType> partsInHand)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneGoingIdle()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneResetting()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGoOnline()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGoOnStandy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDisable()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDonePickUpFromLanes(ArrayList<PartType> partsInHand,
			ArrayList<NestPosition> pickedPositions)
	{
		// TODO Auto-generated method stub
		
	}

}
