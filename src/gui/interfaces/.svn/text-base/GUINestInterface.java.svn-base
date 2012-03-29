package gui.interfaces;

import java.awt.List;
import java.util.ArrayList;

import shared.Part;

import factory.interfaces.Nest;
import gui.components.GUIPart;

public interface GUINestInterface
{
	public abstract void goIdle();
	
	/**
	 * Reply with msgIAmFull() or msgReadyToReceive depending on whether you have a free slot
	 */
	public abstract void areYouReadyToReceive();
	
	/**
	 * Animate dumping of parts
	 */
	public abstract void dumpParts();
	
	/**
	 * Create a reference to the NestAgent
	 * @param nestAgent
	 */
	public abstract void setNestAgent(Nest nestAgent);

}
