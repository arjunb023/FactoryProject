package gui.interfaces;

import java.util.List;

import factory.interfaces.Feeder;
import gui.components.GUIBin;
import gui.components.GUIPart;

/**
 * DoXXX API for the Feeder
 * @author Jai Bapna
 *
 */

public interface GUIFeederInterface {

	public abstract void msgAcceptBin(GUIBin bin);
	
	public abstract void msgShuttlePart(GUIPart part);
	
	@SuppressWarnings("rawtypes")
	public abstract void msgEmptyPartsForBin(GUIBin bin, List parts);
	
	public abstract void setFeederAgent(Feeder feederAgent);
	
	public abstract String getName();
}
