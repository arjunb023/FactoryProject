/**
 * CSCI 201 Factory Project Version v0
 * 
 * Cart Interface
 * 
 * For Cart Agent unit testing
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory.interfaces;

import gui.components.GUICart;
import gui.components.GUIKitRobot;

public interface Cart {
	public GUICart guiCart = null;
	public abstract void msgCartIsReady();
	public abstract void msgGivenOut();
	public abstract void msgHereIsAFullKit();
	public abstract void msgHereIsANewOrder(int number);
	public abstract void msgPleaseGiveMeOne();
	public abstract void msgIHaveLeft();
	public abstract void msgFinishedMovingToNewSpot();
	public abstract void msgMoveToNewSpot(int number);
	public abstract void msgKitDumped();
	public abstract void setKitRobotAgent(KitRobot kitRobotAgent);
	public abstract void setGuiCart(GUICart guiCart);
	public abstract String getName();
	abstract boolean pickAndExecuteAnAction();
	public abstract GUICart getCart();
}
