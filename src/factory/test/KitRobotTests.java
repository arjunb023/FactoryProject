/**
 * * CSCI 201 Factory Project Version v0
 * 
 * KitRobot Test
 * 
 * For unit testing KitRobot Agent Testing
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */

package factory.test;

import shared.KitOrder;
import factory.CartAgent;
import factory.KitRobotAgent;
import factory.interfaces.Cart;
import factory.interfaces.KitRobot;
import factory.test.mock.MockCameraSystem;
import factory.test.mock.MockCart;
import factory.test.mock.MockGuiKitRobot;
import factory.test.mock.MockKitRobot;
import junit.framework.TestCase;

public class KitRobotTests extends TestCase{
	public void testSingleCase(){
		  KitRobot kitRobot = new KitRobotAgent("kitRobot");
	      MockCart cart1 = new MockCart("cart2");
	      kitRobot.setCartAgent(cart1);
	      MockCart cart2 = new MockCart("cart2");
	      kitRobot.setCartAgent(cart2);
	      MockGuiKitRobot mockGui = new MockGuiKitRobot();
	      mockGui.setName("mockGui");
	      MockCameraSystem camera = new MockCameraSystem("camera");
	      
	      kitRobot.setGuiKitRobot(mockGui);
	      kitRobot.setCameraSystemAgent(camera);
	      mockGui.setKitRobotAgent(kitRobot);
	      //kitRobot.setGuiKitRobot(new MockGuiKitRobot());
	      assertEquals("Cart1 should have an empty event log at the beginning while it reads: " + cart1.log.toString(), 0, cart1.log.size() );
	      assertEquals("Cart2 should have an empty event log at the beginning while it reads: " + cart2.log.toString(), 0, cart2.log.size() );
	      
	      System.out.println("Test Starts!");
	      KitOrder kitOrder = new KitOrder();
	      //kitRobot.
	      kitRobot.msgHereIsNewJob(kitOrder, 2);
          kitRobot.msgIHaveABlankKit(cart1);
          assertTrue(
    				"Mock cart1 should have received msgPleaseGiveMeOne. Event log: "
    					+ cart1.log.toString(), cart1.log.getLastLoggedEvent().getMessage().contains(
    						"msgPleaseGiveMeOne From KitRobot"));
          
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
    				"Mock cart1 should have received msgHereIsANewOrder. Event log: "
    					+ cart1.log.toString(), cart1.log.getLastLoggedEvent().getMessage().contains(
    						"msgHereIsANewOrder From KitRobot"));
          kitRobot.msgHereIsABlankKit(cart1);
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
    				"Mock guiKitRobot should have received DoMoveToStand. Event log: "
    					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
    						"DoMoveToStand from KitRobotAgent"));
          
          kitRobot.msgThisKitIsFull(1);
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
  				"Mock guiKitRobot should have received DoMoveToInspection. Event log: "
  					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
  						"DoMoveToInspection from KitRobotAgent"));
          
          kitRobot.pickAndExecuteAnAction();
          kitRobot.msgKitIsGood();
          
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
  				"Mock guiKitRobot should have received DoMoveToCart. Event log: "
  					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
  						"DoMoveToCart from KitRobotAgent"));
          
          
          kitRobot.pickAndExecuteAnAction();
          kitRobot.msgIHaveLeft(cart1);
          kitRobot.pickAndExecuteAnAction();
          
          
          
          /*kitRobot.msgIHaveABlankKit(cart2);
          assertTrue(
    				"Mock cart2 should have received msgPleaseGiveMeOne. Event log: "
    					+ cart2.log.toString(), cart2.log.getLastLoggedEvent().getMessage().contains(
    						"msgPleaseGiveMeOne From KitRobot"));
          kitRobot.pickAndExecuteAnAction();
          kitRobot.pickAndExecuteAnAction();
          System.out.println(cart2.log.getLastLoggedEvent().getMessage().toString());
          assertTrue(
    				"Mock cart2 should have received msgHereIsANewOrder. Event log: "
    					+ cart2.log.toString(), cart2.log.getLastLoggedEvent().getMessage().contains(
    						"msgHereIsANewOrder From KitRobot"));
          kitRobot.msgHereIsABlankKit(cart2);
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
  				"Mock guiKitRobot should have received DoMoveToStand. Event log: "
  					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
  						"DoMoveToStand from KitRobotAgent"));
         
          
          kitRobot.msgThisKitIsFull(1);
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
  				"Mock guiKitRobot should have received DoMoveToInspection. Event log: "
  					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
  						"DoMoveToInspection from KitRobotAgent"));
          
          
          kitRobot.pickAndExecuteAnAction();
          kitRobot.msgKitIsGood();
          
          kitRobot.pickAndExecuteAnAction();
          assertTrue(
  				"Mock guiKitRobot should have received DoMoveToCart. Event log: "
  					+ mockGui.log.toString(), mockGui.log.getLastLoggedEvent().getMessage().contains(
  						"DoMoveToCart from KitRobotAgent"));
          
          kitRobot.pickAndExecuteAnAction();
          */
          
	}
}
