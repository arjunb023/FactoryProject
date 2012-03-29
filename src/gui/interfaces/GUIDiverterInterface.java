package gui.interfaces;

import shared.enums.DiverterDirection;
import factory.interfaces.Diverter;
import gui.components.GUIPart;

/**
 * DoXXX API for the diverter
 * @author Duke Yin
 *
 */
public interface GUIDiverterInterface
{
	/**
	 * GUIDiverter should add the guipart to the end of its internal list of parts.
	 * The guipart should come from the Feeder.
	 * @param guipart
	 */
	public abstract void msgDoAddPart(GUIPart guipart);

	/**
	 * GUIDiverter should move the guipart to the GUILane, then remove the guipart from its list of parts.
	 * @param guipart
	 */
	public abstract void msgDoMovePart(GUIPart guipart);

	/**
	 * GUIDiverter should move its output to the specified lane (top or bottom)
	 * @param direction
	 */
	public abstract void msgDoChangeOrientation(DiverterDirection direction);
	
	public abstract void setDiverterAgent(Diverter diverterAgent);
	
}
