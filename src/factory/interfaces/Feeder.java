/**
 * @author Jai Bapna
 */

package factory.interfaces;

import gui.components.GUIBin;
import gui.components.GUIFeeder;
import gui.components.GUIPart;
import shared.Bin;
import shared.Part;
import shared.enums.PartType;

public interface Feeder {
	
	public abstract void msgHereIsBin(Bin bin);
	
	public abstract void msgLaneNearCapacity();
	
	public abstract void msgGantryDonePurging();

	/**
	 * sent from Lane.
	 * 
	 * the lane is almost empty, so feeder should feed another bin
	 * @param lane
	 */
	public abstract void msgSendMoreParts(Lane lane, PartType type);
	
	public abstract void msgDiverterReadyToReceive(Part part);
	
	public abstract void msgHoldOn();
	
	/** FROM GUI **/
	public abstract void msgDonePlacingBin(GUIBin bin);
	
	public abstract void msgGUIPartDelivered(GUIPart part);
	
	public abstract void msgGUIRemovedPartsFromFeeder();

	/** Extra **/
	public abstract void setGUIFeeder(GUIFeeder guiFeeder);
	
	public abstract boolean pickAndExecuteAnAction();
	
	public abstract String getName();

	public abstract GUIFeeder getGUIFeeder();

	void msgContinueSending();
	
	public boolean HasBin();

}
