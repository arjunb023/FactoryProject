/**
 *  CSCI 201 Factory Project Version v0
 * 
 * Mock KitRobot
 * 
 * Mock Class for KitRobot
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory.test.mock;

import java.util.ArrayList;

import shared.KitOrder;
import shared.enums.PartType;
import factory.CartAgent;
import factory.interfaces.*;
import gui.components.GUIKitRobot;
import gui.components.GUIKittingRobot;
import gui.interfaces.GUIKitRobotInterface;

public class MockKitRobot extends MockAgent implements KitRobot{
    GUIKitRobotInterface guiKitRobot;
    Cart cart;
    CameraSystem camera;
	public MockKitRobot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
    
	public EventLog log = new EventLog();
	
	public void msgIHaveABlankKit(Cart cartAgent) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent(
				"msgIHaveABlankKit from Cart\n"));
	}


	public void msgHereIsABlankKit(Cart cartAgent) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgHereIsABlankKit from Cart\n"));
	}


	public void msgFinishedMovingToStand(int number) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgFinishedMovingToStand from GuiKitRobot\n"));
	}


	public void msgThisKitIsFull(int number) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgHereIsAFullKit from GuiKitRobot\n"));
	}

	public void msgFinishedMovingToInspection() {
		log.add(new LoggedEvent("msgFinishedMovingToInspection from GuiKitRobot\n"));
		
	}
	public void msgKitIsGood() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgInspectionGood from CameraSystem Agent\n"));
	}


	public void msgKitIsBad() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgInspectionBad from CameraSystem Agent\n"));
	}

	public void msgBackToCart() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgBackToCart from CartAgent\n"));
	}


	public void msgDoneDump() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("msgDoneDump from GuiKitRobot\n"));
	}
	public void msgHereIsNewJob(KitOrder jobOrder, int numOrdersTotal) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("New Orders from managing system and the number of it should be: " + numOrdersTotal + "\n"));
	}


	public void msgIHaveLeft(Cart cartAgent) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Message From " + cartAgent.getName() + " to indicate that this cart has left\n"));
	}
	
	public void msgIAmWaiting(Cart cartAgent){
		
	}
    public void msgMissingParts(ArrayList<PartType> missingParts){
		
	}
    public void msgFinishedMovingToNewSpot(){
    	
    }
	public void setCartAgent(Cart cartAgent) {
		// TODO Auto-generated method stub
		this.cart = cartAgent;
	}


	public void setGuiKitRobot(GUIKitRobotInterface guiKitRobot) {
		// TODO Auto-generated method stub
		this.guiKitRobot = guiKitRobot;
	}

    
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCameraSystemAgent(CameraSystem CameraSystemAgent) {
		// TODO Auto-generated method stub
		this.camera = CameraSystemAgent;
	}


	@Override
	public void msgFinishedMovingBackToStand(int location) {
		// TODO Auto-generated method stub
		
	}



}
