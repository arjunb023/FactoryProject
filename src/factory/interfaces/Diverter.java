package factory.interfaces;

import gui.components.GUIDiverter;
import shared.Part;
import shared.enums.DiverterDirection;

/**
 * 
 * @author Duke Yin
 *
 */
public interface Diverter
{
	/**
	 * sent from Feeder.
	 * 
	 * DiverterAgent changes the LaneAgent that it is communicating with and going to output to.
	 * @param direction
	 */
	@Deprecated
	public abstract void msgSetDiverterOrientation(DiverterDirection direction);

	/**
	 * sent from Feeder.
	 * 
	 * if the Feeder is currently passing a new part type to the Diverter, it should flip the diverter, then give the part to feeder
	 */
	public abstract void msgFlipDiverterOrientation();
	
	/**
	 * sent from GUI.
	 * 
	 * Diverter can start pushing parts through again
	 */
	public abstract void msgGuiDoneChangingOrientation();
	
	/**
	 * sent from Feeder.
	 * 
	 * Feeder is seeking permission from this Diverter, to pass a part to this Diverter
	 * @param part
	 */
	public abstract void msgPartReady(Part part);
	
	/**
	 * sent from Feeder.
	 * 
	 * Feeder hands off a part to this Diverter
	 * @param part 
	 */
	public abstract void msgHereIsPart(Part part);
	
	/**
	 * sent from GUIDiverter.
	 * 
	 * lets Diverter agent know that the Lane agent should be passed the part now
	 */
	public abstract void msgGuiDoneMovingPart();
	
	/**
	 * sent from Lane.
	 * 
	 * Give lane the ready part.
	 * @param part
	 */
	public abstract void msgReadyToReceive(Part part);
	
	/**
	 * sent from Lane.
	 * 
	 * Is this actually needed?
	 */
	public abstract void msgIAmFull();

	/** Returns the name of the diverter */
	public abstract String getName();
	
	public abstract void setGuiDiverter(GUIDiverter guiDiverter);
	public abstract void setFeederAgent(Feeder feederAgent);
	public abstract void setLaneAgent1(Lane laneAgent);
	public abstract void setLaneAgent2(Lane laneAgent);
	
	abstract boolean pickAndExecuteAnAction();

	DiverterDirection getOrientation();

}
