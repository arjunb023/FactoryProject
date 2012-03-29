/**
 * CSCI 201 Factory Project Version v0
 * 
 * CartAgent Class
 * 
 * For CartAgent
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory;

import agent.Agent;
import factory.interfaces.Cart;
import factory.interfaces.KitRobot;
import gui.components.GUICart;

public class CartAgent extends Agent implements Cart {
	KitRobot kitRobotAgent;
	public GUICart guiCart;

	public enum CartState {
		NONE, COMING, WAITING, GOING, MOVING
	}

	enum CartEvent {
		COME, STOP, GIVE, MOVE, BACK, DUMP, LEAVE, LEFT, NONE
	}

	int numberCarts;
	public int spots;
	public CartState cState;
	public CartEvent cEvent;

	public CartAgent(String name) {
		super();
		this.name = name;
		this.cState = CartState.NONE;

		// this.cEvent = CartEvent.COME;
	}

	public void msgHereIsANewOrder(int number) {
		this.numberCarts = number;
		this.cEvent = CartEvent.COME;
		pickAndExecuteAnAction();
	}

	public void msgCartIsReady() {
		// TODO Auto-generated method stub
		print("OK");
		cEvent = CartEvent.STOP;
		print("Current event is " + cEvent.toString());
		pickAndExecuteAnAction();
	}

	public void msgPleaseGiveMeOne() {
		// TODO Auto-generated method stub
		print("Kit robot wants a blank kit");
		cEvent = CartEvent.GIVE;
		pickAndExecuteAnAction();
	}

	public void msgGivenOut() {
		// TODO Auto-generated method stub
		print("I have given out my blank kit");
		cEvent = CartEvent.NONE;
		kitRobotAgent.msgIAmWaiting(this);
		pickAndExecuteAnAction();
	}

	public void msgMoveToNewSpot(int number) {
		this.spots = number;
		cEvent = CartEvent.MOVE;
		pickAndExecuteAnAction();
	}

	public void msgFinishedMovingToNewSpot() {
		cEvent = CartEvent.STOP;
		pickAndExecuteAnAction();
	}

	public void msgHereIsAFullKit() {
		// TODO Auto-generated method stub
		print("I received a full kit from kit robot");
		cEvent = CartEvent.BACK;
		pickAndExecuteAnAction();
	}

	public void msgKitDumped() {
		print("Kit Dumped!");
		cEvent = CartEvent.DUMP;
		pickAndExecuteAnAction();
	}

	public void msgIHaveLeft() {
		print("I moved away");
		cEvent = CartEvent.LEFT;
		pickAndExecuteAnAction();
	}

	public void setKitRobotAgent(KitRobot kitRobotAgent) {
		// TODO Auto-generated method stub

		this.kitRobotAgent = kitRobotAgent;
	}

	public void setGuiCart(GUICart guiCart) {
		// TODO Auto-generated method stub
		this.guiCart = guiCart;
	}

	public boolean pickAndExecuteAnAction() {

		// TODO Auto-generated method stub
		// System.out.println("RUN");

		if (cState == CartState.NONE) {
			if (cEvent == CartEvent.COME) {

				cState = CartState.COMING;
				setCurrentState("Coming");
				DoCome(numberCarts);

				return true;
			}
		}

		if (cState == CartState.COMING) {
			if (cEvent == CartEvent.STOP) {
				print("I am here");
				cState = CartState.WAITING;
				setCurrentState("Waiting");
				kitRobotAgent.msgIHaveABlankKit(this);
				return true;
			}
		}
		if (cState == CartState.WAITING) {
			if (cEvent == CartEvent.GIVE) {
				kitRobotAgent.msgHereIsABlankKit(this);
				cState = CartState.WAITING;
				setCurrentState("Waiting");
				return true;
			}
			if (cEvent == CartEvent.BACK) {
				GetBack();
				return true;
			}
			if (cEvent == CartEvent.MOVE) {
				DoMove();
				return true;
			}
			if (cEvent == CartEvent.DUMP) {
				DoGo();
				return true;
			}
		}
		if (cState == CartState.MOVING) {
			if (cEvent == CartEvent.STOP) {
				cState = CartState.WAITING;
				setCurrentState("Waiting");
				//kitRobotAgent.msgFinishedMovingToNewSpot();
				return true;
			}
		}
		if (cState == CartState.GOING) {
			if (cEvent == CartEvent.LEAVE) {
				DoGo();
				return true;
			}
		}
		if (cState == CartState.GOING) {
			if (cEvent == CartEvent.LEFT) {
				this.cState = CartState.NONE;
				setCurrentState("None");
				kitRobotAgent.msgIHaveLeft(this);
				return true;
			}

		}

		print("Sleeping.");
		return false;
	}

	public void DoCome(int number) {
		print("COME!");
		if (guiCart != null)
			guiCart.DoCome(number);
		stateChanged();
	}

	public void DoMove() {
		print("MOVE!");
		cState = CartState.MOVING;
		setCurrentState("Moving");
		if (guiCart != null)
			guiCart.DoMove(spots);
	}

	public void DoGo() {
		print("GO!");
		cState = CartState.GOING;
		cEvent = CartEvent.NONE;
		setCurrentState("None");
		if (guiCart != null)
			guiCart.DoGo();
		stateChanged();

	}

	public void GetBack() {
		print("BACK!");
		// kitRobotAgent.msgBackToCart();
		cState = CartState.GOING;
		cEvent = CartEvent.LEAVE;
		setCurrentState("Leaving");
		DoGo();
		stateChanged();
	}

	public GUICart getCart() {
		return this.guiCart;
	}

}
