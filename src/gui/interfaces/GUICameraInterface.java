package gui.interfaces;

import java.util.ArrayList;

import shared.KitConfig;
import shared.KitOrder;
import shared.Part;
import shared.enums.PartQuality;
import shared.enums.PartType;
import factory.CameraSystemAgent;
import factory.interfaces.CameraSystem;
import gui.components.GUIPart;

public interface GUICameraInterface
{
	public abstract void goIdle();
	
	/**
	 * Animate taking the picture. Call msgDoneTakingPicture when finished.
	 */
	public abstract void takeKitPicture(KitOrder k);
	
	public abstract void takeNestPicture(ArrayList<GUIPart> nest1, ArrayList<GUIPart> nest2);
	
	/**
	 * Analyze the list of parts on the nest, and return a "goodList" or "badList" 
	 * when you are finished using msgHereIsGoodList or msgHereIsBadList
	 */
	public abstract void analyzeKitPicture();
	
	/**
	 * Set your CameraSystemAgent 
	 */
	public abstract void setCameraSystemAgent(CameraSystem cameraSystemAgent);
	
	public abstract void setPartTypeForNests(PartType k1, PartType k2);
}
