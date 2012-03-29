package factory.interfaces;

import java.util.ArrayList;

import factory.test.mock.EventLog;

import shared.KitPartPutInstruction;
import shared.NestPosition;
import shared.enums.PartType;

public interface PartsRobotActor
{
	public EventLog log = new EventLog();
	
	public abstract void doPickUpPartsFromLanes(ArrayList<NestPosition> lanesToGrab);	
	public abstract void doPutPartsInKit(ArrayList<KitPartPutInstruction> daList);
	public abstract void doDropTheseParts(ArrayList<PartType> toDrop);
	public abstract void doDropAllParts();	
	public abstract void doGoIdle();
	public abstract void doReset();
	public abstract void doBadTrip(ArrayList<KitPartPutInstruction> daList);
}
