package gui.interfaces;

import factory.LaneAgent;
import factory.interfaces.Lane;
import gui.components.GUIPart;


/**
 * DoXXX API for the lane
 * @author Duke Yin
 *
 */
public interface GUILaneInterface
{	
	/**
	 * sent from LaneAgent
	 * 
	 * GUIConveyor should add the guipart to the end of its internal list of parts.
	 * This guipart should be at the start of the lane, coming from the Diverter.
	 * @param guipart
	 */
	public abstract void msgDoAddPart(GUIPart guipart);
	
	/**
	 * sent from camera system agent?
	 * 
	 * vibration amplitude should increase, to get "stuck" parts moving again
	 */
	public abstract void msgDoIncreaseAmplitude();
	
	public void setLaneAgent(Lane laneAgent);
}
