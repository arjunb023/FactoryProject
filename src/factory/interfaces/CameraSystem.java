package factory.interfaces;

import java.util.ArrayList;

import factory.KitRobotAgent;
import factory.NestAgent;
import factory.PartsRobotAgent;
import gui.components.GUICamera;
import gui.components.GUIPart;

import shared.KitConfig;
import shared.KitOrder;
import shared.enums.PartQuality;
import shared.enums.PartType;

public interface CameraSystem
{	
	
	/**
	msgIAmFull is called by the NestAgent when the NestGui becomes full. If both of the
	camera's nests are full at the same time, a picture is taken.
	*/
	public abstract void msgIAmFull(Nest nest, ArrayList<GUIPart> p); 
	
	/**
	 * Return part quality list after finished analyzing nest
	 * @param pq1
	 * @param pq2
	 */
	public abstract void msgHereIsNestList(ArrayList<PartQuality> pq1, ArrayList<PartQuality> pq2, GUICamera gc);
	
	/**
	This is called by the KitRobotAgent when a kit needs to be inspected by the camera.
	*/
	public abstract void msgTakeKitPicture(KitOrder k);
	
	/**
	This is called by the CameraGui after it has analyzed the kits. It called 
	msgKitIsGood() if the kit is good, and msgKitIsBad() if the kit is bad.
	*/
	public abstract void msgKitIsGood();
	
	/**
	This is called by the CameraGui after it has analyzed the kits. It called 
	msgKitIsGood() if the kit is good, and msgKitIsBad() if the kit is bad.
	*/
	public abstract void msgKitIsBad();
	
	public abstract void msgIAmNoLongerFull(Nest n);
	
	public abstract void msgKitHasMissingParts(ArrayList<PartType> mp);
	
	public abstract void msgThisIsCurrentConfiguration(KitConfig k);
	
	public abstract void msgDoneTakingGoodPartsFromNest(int num);
	
	//SETTER METHODS:
	
	public abstract void setCameraGui1(GUICamera c);
	public abstract void setCameraGui2(GUICamera c);
	public abstract void setCameraGui3(GUICamera c);
	public abstract void setCameraGui4(GUICamera c);
	public abstract void setKitCamera(GUICamera c);
	public abstract void setNests(int num, Nest n1, Nest n2);
	public abstract void setKitRobotAgent(KitRobot kr);
	public abstract void setPartsRobotAgent(PartsRobot pr);
	
	//GETTER METHODS:
	public abstract String getName();
	
	//Scheduler
	abstract boolean pickAndExecuteAnAction();
	
}
