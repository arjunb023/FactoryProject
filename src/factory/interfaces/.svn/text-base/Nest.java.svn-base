package factory.interfaces;

import java.util.ArrayList;

import shared.KitConfig;

import factory.CameraSystemAgent;
import factory.LaneAgent;
import factory.NestAgent.NestActionState;
import gui.components.GUINest;
import gui.components.GUIPart;

public interface Nest
{

	/**
	This message is called by the LaneAgent, it is asking the nest if it is ready for a part
	*/
	public abstract void msgPartReady(Lane la);

	
	/**
	Called by the NestGui if it is ready to receive a part. If it is not ready, the NestGui will
	call msgIAmFull. 
	*/
	public abstract void msgReadyToReceive();
	
	public abstract void msgIAmFull(ArrayList<GUIPart> p, int num);
 
	public abstract void msgThisIsCurrentConfiguration(KitConfig k);
	
	public abstract void msgIAmNoLongerFull();
	
	public abstract void msgTheFinalPurge();
	
	public abstract void msgPartComing();
	
	public abstract void msgNestNeedsMoreParts();
	
	public abstract void msgIAmEmpty();
	/**
	This gets called by the CameraSystemAgent when the nest is full and all of the parts
	on the nest are bad.
	*/
	public abstract void msgDumpParts();
	public abstract void msgHereIsPart(GUIPart p);
	
	//Scheduler
	abstract boolean pickAndExecuteAnAction();
	
	//SETTER METHODS:
	public abstract void setNestNum(int num);
	public abstract void setGuiNest(GUINest guiNest);
	public abstract void setLaneAgent(Lane la);
	public abstract void setCameraSystemAgent(CameraSystem cs);
	
	
	//GETTER METHODS:
	public abstract boolean getFullState();
	public abstract int getNestNum();
	public abstract String getName();

	/**
	 * GUINest sends this to NestAgent when the part it got from the GUILane has settled in the GUINest
	 */
	public abstract void msgPartAdded(GUIPart p);
	
}
