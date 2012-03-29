/**
 * CSCI 201 Factory Project Version v0
 * 
 * KitRobot Interface
 * 
 * For KitRobot Agent unit testing 
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory.interfaces;

import java.util.ArrayList;

import shared.KitOrder;
import shared.enums.PartType;
import factory.CartAgent;
import factory.test.mock.MockGuiKitRobot;
import gui.components.GUIKitRobot;
import gui.components.GUIKittingRobot;
import gui.interfaces.GUIKitRobotInterface;


public interface KitRobot{
	public abstract void  msgIHaveABlankKit(Cart cartAgent);
	public abstract void  msgHereIsABlankKit(Cart cartAgent);
    public abstract void  msgThisKitIsFull(int number);
	
    public abstract void  msgFinishedMovingToStand(int number);   //GUIkitRobot
	public abstract void  msgFinishedMovingToInspection();        //GUIkitRobot
	
 	public abstract void  msgKitIsGood(); 
	public abstract void  msgKitIsBad();
	
	public abstract void  msgBackToCart();                        //GUIkitRobot
	public abstract void  msgDoneDump();                          //GUIkitRobot
	
	public abstract void  msgIHaveLeft(Cart cartAgent);           //Cart Agent
	public abstract void setCartAgent(Cart cartAgent);
	public abstract void setCameraSystemAgent(CameraSystem CameraSystemAgent);
	public abstract void setGuiKitRobot(GUIKitRobotInterface guiKitRobot);
	public abstract void msgIAmWaiting(Cart cartAgent);
    public abstract void msgMissingParts(ArrayList<PartType> missingParts);
    public abstract void msgFinishedMovingBackToStand(int location);
    //public abstract void msgFinishedMovingToNewSpot();
	//public abstract void  NewOrders(KitOrder kitOrder, int amount);
	
	abstract boolean pickAndExecuteAnAction();
	//public abstract void setGuiKitRobot(MockGuiKitRobot mockGuiKitRobot);
	public abstract void msgHereIsNewJob(KitOrder jobOrder, int numOrdersTotal);
	
}