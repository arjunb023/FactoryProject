package factory.interfaces;
import java.util.ArrayList;

import shared.KitConfig;
import shared.KitOrder;
import shared.NestPosition;
import shared.Part;
import shared.enums.PartQuality;
import shared.enums.PartType;
import factory.PartsRobotAgent;

public interface PartsRobot
{
	public abstract void msgHereAreUpdatedNestContents(int laneNum, ArrayList<PartQuality> nestContents);
	public abstract void msgHereIsANewKitOrderToFill(KitOrder newKitOrder, int kitStandLoc);
	public abstract void msgThisIsCurrentConfiguration(KitConfig newKitConfig);
	public abstract void msgDonePickUpFromLanes(ArrayList<PartType> partsInHand, ArrayList<NestPosition> pickedPositions);
	public abstract void msgDonePutPartsInKit(int kitStandLoc, ArrayList<PartType> depositedPartTypes, ArrayList<PartType> stillHoldingPartTypes);
	public abstract void msgDoneDroppingParts(ArrayList<PartType> partsInHand);
	public abstract void msgDoneGoingIdle();
	public abstract void msgDoneResetting();
	public abstract void msgGoOnline();
	public abstract void msgGoOnStandy();
	public abstract void msgDisable();
}
