/**
 *CSCI 201 Factory Project Version v0
 * 
 * KitRobotAgent Class
 * 
 * For KitRobotAgent
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.KitOrder;
import shared.Part;
import shared.enums.PartType;
import agent.Agent;
import factory.interfaces.CameraSystem;
import factory.interfaces.Cart;
import factory.interfaces.KitRobot;
import gui.components.GUICart;
import gui.interfaces.GUIKitRobotInterface;
import gui.panels.FactoryPanel;
import gui.panels.OrderPanel;

public class KitRobotAgent extends Agent implements KitRobot {
	// String name;

	ArrayList<Cart> cartAgents = new ArrayList<Cart>();
	// remove comment when KitOrder is finished
	public List<KitOrder> kitOrders = Collections
			.synchronizedList(new ArrayList<KitOrder>());
	public List<CartState> cartStates = Collections
			.synchronizedList(new ArrayList<CartState>());

	public List<Integer> orders = Collections
			.synchronizedList(new ArrayList<Integer>());
	
	
	
	int OnTrack = 0;
	CameraSystem cameraSystemAgent;
	PartsRobotAgent partsRobotAgent;
	GUIKitRobotInterface guiKitRobot;
	CellManagerAgent cellManagerAgent;
	public boolean Left = true;
	private boolean spot1 = true;
	private boolean spot2 = true;

	enum KitState {
		NONE, MOVING, FETCHING, POSITIONED, PROCESSING, FULL, INSPECTING, MISSING,GOOD, BAD, GIVING, DUMPING
	}

	enum KitOrderState {
		EMPTY, PROCESSING, FULL
	}

	enum CartStatus {
		NONE, WORKING, WAITING, LEAVING, GONE
	}

	enum GuiState {
		IDLE, WORKING
	}

	GuiState gState = GuiState.IDLE;

	boolean InspectionFull = false;
	boolean Stand_One = false, Stand_Two = false;

	private class myKit {
		KitState state;
		KitOrder kitOrder;
		Cart cartAgent;
		int location;
		ArrayList<PartType> missingParts = new ArrayList<PartType>();
		KitOrder missingOrder;
		myKit(Cart cartAgent, KitOrder kitOrder) {
			this.state = KitState.NONE;
			this.cartAgent = cartAgent;
			this.location = -1;
			this.kitOrder = kitOrder;
			//this.missingOrder = new KitOrder();
		}

	}

	private class CartState {
		private Cart cartAgent;
		private CartStatus cState;
		private int spot;

		CartState(Cart cartAgent, CartStatus cS) {
			this.cartAgent = cartAgent;
			this.cState = cS;
		}
	}

	List<myKit> myKits = Collections.synchronizedList(new ArrayList<myKit>()); // Three
																				// elements
																				// at
																				// most

	public KitRobotAgent(String name) {
		super();
		this.name = name;
		for (int i = 0; i < 4; i++) {
			CartAgent cart = new CartAgent("Cart" + (i + 1));
			GUICart guiCart;
			synchronized (FactoryPanel.kits) {
				guiCart = new GUICart(cart, 8, FactoryPanel.kits);
			}
			cart.setKitRobotAgent(this);
			synchronized (FactoryPanel.underParts) {
				FactoryPanel.underParts.add(guiCart);
				FactoryPanel.underParts.add(FactoryPanel.kits
						.get(FactoryPanel.kits.size() - 1));
			}
			cart.setGuiCart(guiCart);
			guiCart.setCartAgent(cart);
			this.setCartAgent(cart);
			
			// why was this never done before? --Duke
			synchronized(cartAgents){
			cartAgents.add(cart);}
		}
		synchronized (orders) {
			orders.add(0);
		}
		// remove the comment when unit testing happens

		// kitOrders.add(new KitOrder());
		// kitOrders.add(new KitOrder());
	}

	public String getName() {
		return name;
	}

	public void setCellManagerAgent(CellManagerAgent cm) {
		this.cellManagerAgent = cm;
	}

	public void setCartAgent(Cart cartAgent) {
		synchronized (cartStates) {
			cartStates.add(new CartState(cartAgent, CartStatus.NONE));
		}
		Integer x = cartStates.size();
		print("Now there are " + x.toString() + " carts");
	}

	// remove the comment when KitOrder is finished
	public void msgHereIsNewJob(KitOrder jobOrder, int numOrdersTotal) {
		print("Received msgHereIsNewJob");
		// System.exit(1);
		synchronized (kitOrders) {
			kitOrders.add(jobOrder);
		}
		synchronized (orders) {
			if (orders.get(0) == 0)
				orders.set(0, numOrdersTotal);
			else
				orders.add(numOrdersTotal);
		}
		stateChanged();
	}

	/*
	 * for (int i = 0; i < numOrdersTotal; i++) { // print("1");
	 * 
	 * 
	 * CartAgent cart = new CartAgent("Cart" + (i+1)); //cart.startThread();
	 * GUICart guiCart; synchronized(FactoryPanel.kits){ guiCart = new
	 * GUICart(cart, 8, FactoryPanel.kits); } cart.setKitRobotAgent(this);
	 * synchronized(FactoryPanel.underParts){
	 * FactoryPanel.underParts.add(guiCart);
	 * FactoryPanel.underParts.add(FactoryPanel
	 * .kits.get(FactoryPanel.kits.size() - 1)); } cart.setGuiCart(guiCart);
	 * guiCart.setCartAgent(cart); this.setCartAgent(cart);
	 * 
	 * }
	 */

	/*
	 * for (CartState cstate: cartStates) { //print("2"); if(cstate.cState ==
	 * CartStatus.NONE && cartStates.size() <= 3) { cstate.cState =
	 * CartStatus.WORKING;
	 * cstate.cartAgent.msgHereIsANewOrder(cartStates.size()); }
	 * 
	 * }
	 */

	public void msgIHaveABlankKit(Cart cartAgent) {
		// TODO Auto-generated method stub

		print("Being asked by cartAgent to receive a blank kit");
		if (myKits.size() < 2)
			cartAgent.msgPleaseGiveMeOne();
		stateChanged();

	}

	public void msgHereIsABlankKit(Cart cartAgent) {
		// TODO Auto-generated method stub
		print("Receive a blank kit from cart");
		synchronized (myKits) {
			myKits.add(new myKit(cartAgent, kitOrders.get(0)));
		}
		/*
		 * synchronized (kitOrders) { kitOrders.remove(0); }
		 */

		stateChanged();
	}

	public void msgIAmWaiting(Cart cartAgent) {
		print("I am waiting!" + cartAgent.getName());
		synchronized (cartStates) {
			for (CartState cartState : cartStates) {
				if (cartState.cartAgent.equals(cartAgent))
					cartState.cState = CartStatus.WAITING;
			}
		}
	}

	public void msgFinishedMovingToStand(int number) {
		// TODO Auto-generated method stub
		// print("message from Gui indicating positioned kit");
		if (number == 1)
			Stand_One = true;
		else if (number == 2)
			Stand_Two = true;
		synchronized (myKits) {
			for (myKit m : myKits) {
				if (m.state == KitState.MOVING) {
					m.state = KitState.POSITIONED;
					m.location = number;
				}
			}
		}
		gState = GuiState.IDLE;
		print((InspectionFull == false) ? "false" : "true");
		stateChanged();
	}

	public void msgThisKitIsFull(int location) {
		// TODO Auto-generated method stub
		// print("message from Part Robot indicating a full kit");
		
		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.location == location) {
					//print("location: " + location);
					mk.state = KitState.FULL;
					break;
				}
			}
		}

		stateChanged();

	}

	public void msgFinishedMovingToInspection() {
		synchronized (myKits) {
			for (myKit mk : myKits)
				if (mk.location == 0) {
					if (mk.kitOrder != null)
						{
						  cameraSystemAgent.msgTakeKitPicture(mk.kitOrder);
						  print("Current Order is " + mk.kitOrder.toString());
						}
					// msgKitIsBad();
					break;
				}
		}
		gState = GuiState.IDLE;

		stateChanged();
	}

	public void msgKitIsGood() {
		// TODO Auto-generated method stub
		// print("message from Camera indicating good kit");
		synchronized (myKits) {
			for (myKit mykit : myKits) {
				if (mykit.state == KitState.INSPECTING)
					mykit.state = KitState.GOOD;
			}
		}

		stateChanged();

	}
	public void msgMissingParts(ArrayList<PartType> missingParts){
		synchronized(missingParts){
			StringBuilder s = new StringBuilder();
			s.append("Received msgMissing:");
			for(PartType p : missingParts)
			{
				s.append(" " + p);
			}
			s.append(".");
			print(s.toString());
		}
		synchronized(myKits){
			for(myKit mykit : myKits){
				if(mykit.state == KitState.INSPECTING){
					if(mykit.missingParts != null)
					 mykit.missingParts.clear(); 
					 mykit.missingOrder = new KitOrder();
					 mykit.state = KitState.MISSING;
					 for(int i  = 0 ; i < missingParts.size(); i ++){
						 mykit.missingParts.add(missingParts.get(i));
						 
					 }
					 mykit.missingOrder.setKitOrder(mykit.missingParts);
					}
			}
		}
		stateChanged();
	}

	public void msgKitIsBad() {
		// TODO Auto-generated method stub
		 print("message from Camera indicating bad kit");
		synchronized (myKits) {
			for (myKit mykit : myKits) {
				if (mykit.state == KitState.INSPECTING)
					mykit.state = KitState.BAD;
			}
		}
		stateChanged();
	}

	public void msgFinishedMovingBackToStand(int location){
		
		synchronized(myKits) {
			for(myKit mk : myKits){
				if(mk.location == location){
					
				    print("location for kit that are missing parts "+location);
					mk.state = KitState.POSITIONED;
					InspectionFull = false;
					//partsRobotAgent.msgHereIsANewKitOrderToFill(mk.missingOrder, mk.location);
					break;
				}
			}
		}
		gState = GuiState.IDLE;
		stateChanged();
	}
	public void msgBackToCart() {
		// TODO Auto-generated method stub
		// print("message from Cart indicating kit back to cart");
		synchronized (myKits) {
			for (myKit mykit : myKits) {
				if (mykit.state == KitState.GIVING) {
					myKits.remove(mykit);
					InspectionFull = false;
					break;
				}

			}
		}
		synchronized (orders) {
			if (orders.get(0) > 1) {
				orders.set(0, orders.get(0) - 1);
			    OrderPanel.orderComplete(kitOrders.get(0));
			} else {
				OrderPanel.orderComplete(kitOrders.get(0));
				cellManagerAgent.msgCompletedAllKitsForCurrentJob();
				orders.remove(0);

				kitOrders.remove(0);
				orders.add(0);
			}
		}

		

		// OnTrack--;
		// System.out.println("There are now " + OnTrack + " carts on track");
        //print(orders.get(0).toString());
		 synchronized (cartStates) {
			 for(CartState ct : cartStates){
				 if(ct.spot == 1){
					 ct.cState = CartStatus.LEAVING;
					 ct.spot = -1;
					 spot1 = true;
					 synchronized(orders){
							if(orders.get(0) == 0){
								spot1 = true;
								break;
							}
						}
				 }
				 if(spot2 == true){
						spot1 = true;
						print("All of them are vacant");
						break;
					 }
				 if(ct.spot == 2 && ct.cState == CartStatus.WAITING){
					 ct.spot = 1;
					 ct.cartAgent.msgMoveToNewSpot(1);
					 spot1 = false;
					 spot2 = true;
					 break;
				 }
			 }
		 }
		 gState = GuiState.IDLE;
		 print((spot1 == true) ? "SPOT1 vacant" : "SPOT1 not vacant");
		 print((spot2 == true) ? "SPOT2 vacant" : "SPOT2 not vacant");
		 stateChanged();
	}

	public void msgDoneDump() {
		// TODO Auto-generated method stub
		print("message from gui indicating kit dumped");
		synchronized (myKits) {
			for (myKit mykit : myKits) {
				if (mykit.state == KitState.DUMPING) {
					InspectionFull = false;
					mykit.cartAgent.msgKitDumped();
					myKits.remove(mykit);
					break;
				}
			}
		}

		// OnTrack--;
		// System.out.println("There are now " + OnTrack + " carts on track");
		gState = GuiState.IDLE;
		 synchronized (cartStates) {
			 for(CartState ct : cartStates){
				 if(ct.spot == 1){
					 ct.spot = -1;
					 ct.cState = CartStatus.LEAVING;
						synchronized(orders){
							if(orders.get(0) == 0){
								spot1 = true;
								break;
							}
						}
				 }
				 if(spot2 == true){
					spot1 = true;
					print("All of them are vacant");
					break;
				 }
				 if(ct.spot == 2 && ct.cState == CartStatus.WAITING){
					 ct.spot = 1;
					 ct.cartAgent.msgMoveToNewSpot(1);
					 spot1 = false;
					 spot2 = true;
					 break;
				 }
			 }
		 }
		stateChanged();
	}

	public void msgIHaveLeft(Cart cartAgent) {
		print("message from cartAgent that it leaves");
		synchronized (cartStates) {
			for (CartState ct : cartStates) {
				if (ct.cartAgent.equals(cartAgent) ) {
					/*if (ct.spot == 1)
						spot1 = true;
					if (ct.spot == 2)
						spot2 = true;
					*/
				
					ct.cState = CartStatus.NONE;
					// OnTrack -- ;
					Left = true;
					break;
				}
			}
		}
		/*
		 * synchronized (cartStates) { for (int i = 0; i < ((cartStates.size() >
		 * 1) ? 1 : cartStates .size()); i++) { if(cartStates.get(i).cState ==
		 * CartStatus.WAITING){ cartStates.get(i).cartAgent.msgMoveToNewSpot(i +
		 * 1); spot2 = true; spot1 = false;
		 * 
		 * }
		 * 
		 * } }
		 */
		stateChanged();
	}

	/* remove the comment after PartsRobotAgent is there. */
	public void setPartsRobotAgent(PartsRobotAgent partsRobotAgent) {
		this.partsRobotAgent = partsRobotAgent;
	}

	// remove the comment when CameraSystem is there

	public void setCameraSystemAgent(CameraSystem cameraSystemAgent) {
		this.cameraSystemAgent = cameraSystemAgent;
	}

	public void setGuiKitRobot(GUIKitRobotInterface guiKitRobot) {
		// TODO Auto-generated method stub
		this.guiKitRobot = guiKitRobot;
	}

	public boolean pickAndExecuteAnAction() {
		setCurrentState(gState.toString());
		
		if (gState == GuiState.WORKING)
			return true;
		
		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.state == KitState.NONE) {

					if (gState == GuiState.IDLE) {
						if (Stand_One == false) {

							mk.state = KitState.MOVING;
							DoMoveToStand(1, mk.cartAgent);

						} else if (Stand_Two == false) {

							mk.state = KitState.MOVING;
							DoMoveToStand(2, mk.cartAgent);
						}

						return true;
					}

				}

			}
		}

		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.state == KitState.POSITIONED) {
					TellPartsRobot(mk);
					return true;
				}
			}
		}
		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.state == KitState.FULL) {
					if (gState == GuiState.IDLE) {
						if (InspectionFull == false) {
							print("I think this should print!" + mk.location);
							int locate = mk.location;
							if (mk.location == 1)
								Stand_One = false;
							else if (mk.location == 2)
								Stand_Two = false;

							InspectionFull = true;
							mk.state = KitState.INSPECTING;
							mk.location = 0;
							
							DoMoveToInspection(locate);
							return true;
						}

					}
				}
			}
		}
		synchronized (myKits) {
			for(myKit mk : myKits) {
				if(mk.state == KitState.MISSING) {
					int locate;
					if(Stand_One == false)
						locate = 1;
					else if(Stand_Two == false)
						locate = 2;
					else {
						DoDump();
						return true;
					}
					mk.location = locate;
					DoMoveBackToStand(locate);
					return true;
				}
			}
		}
		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.state == KitState.BAD) {
					if (gState == GuiState.IDLE) {
						
						mk.state = KitState.DUMPING;
						DoDump();
						return true;
					}
				}
			}
		}
		synchronized (myKits) {
			for (myKit mk : myKits) {
				if (mk.state == KitState.GOOD) {
					if (gState == GuiState.IDLE) {
						mk.state = KitState.GIVING;
						for(CartState cs : cartStates){
						  if(cs.spot == 1){
							  DoMoveToCart(cs.cartAgent);
							  return true;
						  }
						}
						
					}

				}

			}
		}
		synchronized (cartStates) {
			for (CartState cstate : cartStates) {
				if(spot1 == true && spot2 == false){
					if(cstate.spot == 2){
						cstate.spot = 1;
						spot1 = false;
						spot2 = true;
						cstate.spot = 1;
						cstate.cartAgent.msgMoveToNewSpot(1);
						return true;
					}
				}
				if (cstate.cState == CartStatus.NONE && orders != null) {
					if (orders.get(0) > 0) {
						
						if (spot2 == true && spot1 == true) {
							print("" + orders.get(0));
							synchronized (FactoryPanel.underParts) {
								if (cstate.cartAgent.getCart().IfKitIsNull()) {
									synchronized(FactoryPanel.kits){
									  cstate.cartAgent.getCart().setKit(8,FactoryPanel.kits);
									}
									FactoryPanel.underParts.add(FactoryPanel.kits.get(FactoryPanel.kits.size() - 1));
								    }
									}
							//print("This must happen!");
							cstate.cartAgent.msgHereIsANewOrder(1);
							cstate.spot = 1;
							spot1 = false;
							cstate.cState = CartStatus.WORKING;
							return true;

						}
						else if (spot2 == true  && orders.get(0) != 1) {
							print("" + orders.get(0));
							synchronized (FactoryPanel.underParts) {
								if (cstate.cartAgent.getCart().IfKitIsNull()) {
									synchronized(FactoryPanel.kits){
										  cstate.cartAgent.getCart().setKit(8,FactoryPanel.kits);
										}
									FactoryPanel.underParts.add(FactoryPanel.kits.get(FactoryPanel.kits.size() - 1));
								}
							}
							//print("This must happen!");
							cstate.cartAgent.msgHereIsANewOrder(2);
							cstate.spot = 2;
							spot2 = false;
							cstate.cState = CartStatus.WORKING;
							return true;

						}
					}
				}
			}
		}

		print("Sleeping.");
		return false;
	}

	private void DoMoveBackToStand(int locate) {
		print("MovingBackToStand");
		if(locate == 1)
			Stand_One  = true;
		else
			Stand_Two  = true;
		gState = GuiState.WORKING;
		guiKitRobot.DoMoveFromInspectionToStand(locate);
		stateChanged();
	}

	public void DoMoveToStand(int i, Cart cartAgent) {
		print("DoMoveToStand " + i + " from cart " + cartAgent.getName());
		if (i == 1)
			Stand_One = true;
		else if (i == 2)
			Stand_Two = true;
		gState = GuiState.WORKING;
		stateChanged();

		guiKitRobot.DoMoveToStand(i, cartAgent.getCart());


	}

	public void TellPartsRobot(myKit mk) {
		// print("Here We Go!");
		mk.state = KitState.PROCESSING;
		stateChanged();
		if(mk.missingOrder == null)
		    partsRobotAgent.msgHereIsANewKitOrderToFill(mk.kitOrder, mk.location);
		else
			partsRobotAgent.msgHereIsANewKitOrderToFill(mk.missingOrder, mk.location);
		// System.exit(1);

	}

	public void DoMoveToInspection(int location) {
        print("This should print, right?");
		gState = GuiState.WORKING;
		guiKitRobot.DoMoveToInspection(location);
		stateChanged();
		/*
		 * try{ GuiSemaphore.acquire(); } catch (Exception e){ print("shit!");
		 * return; }
		 */

	}

	public void DoMoveToCart(Cart cartAgent) {
		gState = GuiState.WORKING;
		guiKitRobot.DoMoveToCart(cartAgent.getCart());
		print(gState.toString());
		stateChanged();

	}

	public void DoDump() {
		gState = GuiState.WORKING;
		print("Dump!");
		guiKitRobot.DoDump();
		stateChanged();

	}
	
	public ArrayList<Cart> getCartAgents()
	{
		return cartAgents;
	}

}
