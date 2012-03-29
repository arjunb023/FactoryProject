/**
 *CSCI 201 Factory Project Version v0
 * 
 * Mock Cart
 * 
 * Mock Class for Cart
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */

package factory.test.mock;

import factory.interfaces.Cart;
import factory.interfaces.KitRobot;
import gui.components.GUICart;



public class MockCart extends MockAgent implements Cart{
    public GUICart cart;
	public MockCart(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public EventLog log = new EventLog();

	public void msgCartIsReady() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgCartIsReady From GuiCart"));
	}
	public void msgPleaseGiveMeOne() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgPleaseGiveMeOne From KitRobot"));
	}
	public void msgGivenOut() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgGivenOut From GuiCart"));
	}

	public void msgHereIsAFullKit() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgHereIsAFullKit From GuiCart"));
	}
	public void msgHereIsANewOrder(int number) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgHereIsANewOrder From KitRobot"));
	}

	public void msgIHaveLeft() {
		// TODO Auto-generated method stub
		
	}
	public void msgFinishedMovingToNewSpot() {
		// TODO Auto-generated method stub
		
	}
	
	public void msgMoveToNewSpot(int number) {
		// TODO Auto-generated method stub
		
	}
	public void msgKitDumped(){
		
	}
	
	public void setKitRobotAgent(KitRobot kitRobotAgent) {
		// TODO Auto-generated method stub
		
	}

	public void setGuiCart(GUICart guiCart) {
		// TODO Auto-generated method stub
		
	}

	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public GUICart getCart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	

}
