/**
 * @author Jai Bapna
 */

package factory.interfaces;

import gui.components.GUIBin;
import gui.components.GUIGantry;
import shared.Bin;
import shared.enums.PartType;

public interface Gantry {

	public abstract void msgGiveMeThisPart(PartType type, Feeder feeder);
	
	public abstract void msgPurgeThisBin(Bin bin);
	
	public abstract void msgSetBinOnFire();
	/** FROM GUI **/
	
	public abstract void msgGUIDelieveredBinToFeeder(GUIBin bin);
	
	public abstract void msgGUIDonePurging(GUIBin bin);
	
	public abstract void msgGUIDoneGettingNewBin(GUIBin bin);
	
	public abstract void msgGUIDoneSettingBinOnFire(GUIBin bin);
	
	/** Extra **/
	public abstract void setGUIGantry(GUIGantry guiGantry);
	
	public abstract boolean pickAndExecuteAnAction();
	
	public abstract String getName();
}
