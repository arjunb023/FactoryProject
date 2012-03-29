package factory.interfaces;

import gui.components.GUILane;
import gui.components.GUIPart;
import shared.Part;
import shared.enums.PartType;

/**
 * 
 * @author Duke Yin
 *
 */
public interface Lane
{
	/**
	 * sent from Diverter.
	 * 
	 * informs the Lane that Diverter is ready to pass it a part
	 * @param part
	 */
	public abstract void msgPartReady(Part part);
	
	/**
	 * sent from Diverter.
	 * 
	 * Diverter hands off a part to this Lane
	 * @param part
	 */
	public abstract void msgHereIsPart(Part part);

	/**
	 * sent from GUILane.
	 * 
	 * informs Lane agent that the specified GUIPart has reached the end of the GUILane,
	 * and the part should be passed to the next agent
	 * 
	 * @param guipart
	 */
	public abstract void msgGuiDoneMovingPart(GUIPart guipart);
	
	/**
	 * sent from GUILane.
	 * 
	 * informs Lane agent that the lane is nearing capacity,
	 * so it should stop accepting parts from the Diverter
	 */
	public abstract void msgGuiLaneAlmostFull();

	/**
	 * sent from GUILane.
	 * 
	 * informs Lane agent that the lane has space for more parts again
	 */
	public abstract void msgGuiLaneNoLongerFull();
	
	/**
	 * sent from Nest.
	 * 
	 * informs Lane agent it should give the part to the Nest
	 */
	public abstract void msgReadyToReceive();
	
	/**
	 * sent from Nest.
	 * 
	 * ????
	 * 
	 */
	public abstract void msgIAmFull();
	

	public abstract void setDiverterAgent(Diverter diverterAgent);
	public abstract void setNestAgent(Nest nestAgent);
	public abstract void setFeederAgent(Feeder feederAgent);
	public abstract void setGuiLane(GUILane guiLane);
	
	abstract boolean pickAndExecuteAnAction();

	/**
	 * sent from Main.
	 * 
	 * tells lane what kind of part it should be accepting
	 * @param type
	 */
	public abstract void setPartType(PartType type);

	/**
	 * returns the PartType which this lane should be receiving
	 * @return partType
	 */
	public abstract PartType getPartType();
	
	public String getName();


}
