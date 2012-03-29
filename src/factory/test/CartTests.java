/**
 * * CSCI 201 Factory Project Version v0
 * 
 * Cart Test
 * 
 * For unit testing Cart Agent
 * 
 * @author Jiachen Zhou
 * @version v0
 * 
 */
package factory.test;

import factory.*;
import factory.interfaces.*;
import factory.test.mock.MockKitRobot;
import junit.framework.TestCase;
import java.util.*;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class CartTests extends TestCase{
   public void testNormalCase()
   {
     
      Cart cart = new CartAgent("cart1");
      MockKitRobot kitRobot = new MockKitRobot("kitRobot");
      cart.setKitRobotAgent(kitRobot);
      assertEquals("Kit Robot should have an empty event log at the beginning while it reads: " + kitRobot.log.toString(), 0, kitRobot.log.size() );
      
      //Since each cart has only one kit, then there's no need to create a kit variable. The agent side does not need this.
      System.out.println("Test Starts!");
      cart.msgHereIsANewOrder(0);
      cart.msgCartIsReady();

      //System.out.println(kitRobot.log.getLastLoggedEvent().toString());
      assertTrue(
				"Mock KitRobot should have received msgIHaveABlankKit. Event log: "
					+ kitRobot.log.toString(), kitRobot.log.getLastLoggedEvent().getMessage().contains(
						"msgIHaveABlankKit from Cart\n"));
      cart.msgPleaseGiveMeOne();
      assertTrue(
				"Mock KitRobot should have received msgIHereIsABlankKit. Event log: "
					+ kitRobot.log.toString(), kitRobot.log.getLastLoggedEvent().getMessage().contains(
						"msgHereIsABlankKit from Cart\n"));
      cart.msgGivenOut();
      //System.out.println(kitRobot.log.getLastLoggedEvent().toString());
      cart.msgHereIsAFullKit();
      assertTrue(
				"Mock KitRobot should have received msgBackToCart. Event log: "
					+ kitRobot.log.toString(), kitRobot.log.getLastLoggedEvent().getMessage().contains(
						"msgBackToCart from CartAgent\n"));
      //System.out.println(kitRobot.log.getLastLoggedEvent().toString());
      //finished normal case testing
   }
}
