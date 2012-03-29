package factory.test.mock;

import factory.PartsRobotAgent;
import factory.interfaces.PartsRobot;
import factory.interfaces.PartsRobotActor;
import gui.components.GUIKit;

import java.util.ArrayList;

import shared.KitPartPutInstruction;
import shared.NestPosition;
import shared.enums.PartType;

public class MockGuiPartsRobot implements PartsRobotActor
{
	PartsRobot	myAgent	= new PartsRobotAgent("Parts Robot Agent");
	
	private String name;
	
	public MockGuiPartsRobot(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.getClass().getName() + ": " + name;
	}
	
	public void doPickUpPartsFromLanes(ArrayList<NestPosition> lanesToGrab)
	{	
		System.out.println("doPickUpPartsFromLanes");
		this.log.add(new LoggedEvent("doPickUpPartsFromLanes"));
	}
	
	public void doPutPartsInKit(int KitStandLoc, ArrayList<PartType> listOParts)
	{
		this.log.add(new LoggedEvent("doPutPartsInKit"));
	}
	
	public void doDropTheseParts(ArrayList<PartType> toDrop)
	{
		this.log.add(new LoggedEvent("doDropTheseParts"));
	}
	
	public void doDropAllParts()
	{
		this.log.add(new LoggedEvent("doDropAllParts"));
	}
	
	public void doGoIdle()
	{
		this.log.add(new LoggedEvent("doGoIdle"));
	}
	
	public void doReset()
	{
		this.log.add(new LoggedEvent("doReset"));
	}

	@Override
	public void doPutPartsInKit(ArrayList<KitPartPutInstruction> daList)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doBadTrip(ArrayList<KitPartPutInstruction> daList)
	{
		// TODO Auto-generated method stub
		
	}

}
