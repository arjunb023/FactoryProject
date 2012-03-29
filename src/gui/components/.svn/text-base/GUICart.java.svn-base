/**
 *	CSCI 200 Factory Project
 *	Version v0
 *
 *	CartGUI class
 *
 *	Represents a cart which carries empty or completed kits.
 *	The cart can be moved vertically and zoomed in/out.
 *
 *	@author 	Reetika Rastogi
 *	@version	v0
 */

package gui.components;

import factory.CartAgent;
import gui.interfaces.GUICartInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class GUICart extends GUIComponent implements GUICartInterface {

	private CartAgent agent = null;
	private GUIKit myKit = null;

	private enum Command {
		noCommand, DoCome, DoGo, DoMove
	};

	private Rectangle2D.Double cartPlaceHolder;

	private int counter = 0;

	private int xPos = 20;

	private int yPos;
	private int yDest;

	private int width;
	private int height;
	private int angle;
	private int maxSize;
	private String name;
	private ImageIcon image;
	private final int hBuffer = 5;
	private final int vBuffer = 20;

	private final ImageIcon stdImage = new ImageIcon("images/ab_cart.png");

	private final int stdWidth = stdImage.getIconWidth() / 2;
	private final int stdHeight = stdImage.getIconHeight() / 2;

	private Command command = Command.noCommand;

	private static final int yStart = 750;
	private static final int yWait1 = 100;
	private static final int yWait2 = 250;
	private static final int yWait3 = 400;
	private static final int yLeave = -150;

	public GUICart(CartAgent c, int kitSize) {
		agent = c;

		yPos = yStart;
		yDest = yStart;

		name = "Cart";

		myKit = new GUIKit(xPos + hBuffer, yPos + vBuffer, 0, kitSize);

		width = stdWidth;
		height = stdHeight;
		angle = 0;
		maxSize = kitSize;
		image = stdImage;
		cartPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, height);
	}

	public GUICart(CartAgent c, int kitSize, ArrayList<GUIComponent> newKits) {
		agent = c;

		yPos = yStart;
		yDest = yStart;

		name = "Cart";

		myKit = new GUIKit(xPos + hBuffer, yPos + vBuffer, 0, kitSize);
		newKits.add(myKit);

		width = stdWidth;
		height = stdHeight;
		angle = 0;
		maxSize = kitSize;
		image = stdImage;
		cartPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, height);
	}

	public GUICart(int kitSize, ArrayList<GUIKit> newKits) {
		yPos = yStart;
		yDest = yStart;

		name = "Cart";

		myKit = new GUIKit(xPos + hBuffer, yPos + vBuffer, 0, kitSize);
		newKits.add(myKit);

		width = stdWidth;
		height = stdHeight;
		angle = 0;
		maxSize = kitSize;
		image = stdImage;
		cartPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, height);
	}

	// Updates cart's position
	public void update() {
		if (counter > 0)
			counter--;

		// Moves cart (and kit it contains) vertically
		if (counter == 0) {
			if (yPos < yDest) {
				yPos += 5;
				if (myKit != null)
					myKit.moveY(-5);
			} else if (yPos > yDest) {
				yPos -= 5;
				if (myKit != null)
					myKit.moveY(5);
			}
		}

		// Once cart's current position matches its destination, sends agent a
		// message with its status
		if (yPos == yDest) {
			if (command == Command.DoCome)
				agent.msgCartIsReady();
			if (command == Command.DoMove)
				agent.msgFinishedMovingToNewSpot();
			if (command == Command.DoGo) {
				yPos = yStart;
				yDest = yStart;
				if (myKit != null)
					myKit = null;
				agent.msgIHaveLeft();
			}
			command = Command.noCommand;
			counter = 0;
		}
		cartPlaceHolder.setFrame(xPos, this.yPos, this.width, this.height);
	}

	// Called by agent telling cart to enter factory and come to a specific stop
	public void DoCome(int stopNum) {
		if (stopNum == 1) {
			yDest = yWait1;
			counter = 0;
		}
		if (stopNum == 2) {
			yDest = yWait2;
			counter = 50;
		}
		if (stopNum == 3) {
			yDest = yWait3;
			counter = 100;
		}
		command = Command.DoCome;
	}

	// Called by agent telling cart to leave factory
	public void DoGo() {
		yDest = yLeave;
		counter = 0;
		command = Command.DoGo;
	}

	// Called by agent telling cart to come to a specific stop in the factory
	public void DoMove(int stopNum) {
		if (stopNum == 1)
			yDest = yWait1;
		if (stopNum == 2)
			yDest = yWait2;
		if (stopNum == 3)
			yDest = yWait3;
		command = Command.DoMove;
	}

	// Allows caller to set cart's kit
	public void setKit(int kitSize, ArrayList<GUIComponent> newKits) {

		myKit = new GUIKit(xPos + hBuffer, yPos + vBuffer, 0, kitSize);
		newKits.add(myKit);
	}

	// Returns a boolean stating if kit is null (i.e. if cart contains a kit)
	public boolean IfKitIsNull() {
		if (myKit == null)
			return true;
		else
			return false;
	}

	// KitRobot will call Cart.giveKit(GUIKit k) to give the Cart a GUIKit
	public void giveKit(GUIKit k) {
		myKit = k;
		k.setX(xPos + hBuffer);
		k.setY(yPos + vBuffer);
		agent.msgHereIsAFullKit();
	}

	// KitRobot will call Cart.getKit() to receive a GUIKit from the Cart
	public GUIKit getKit() {
		GUIKit toGive = myKit;
		myKit = null;
		agent.msgGivenOut();
		return toGive;
	}

	// Zooms in or out (amount) times
	public void zoom(int amount) {
		width += amount;
		height += amount;
		yPos -= amount / 2;
		myKit.zoom(amount);
	}

	// Rotates cart by (amount) degrees
	public void rotate(int amount) {
		angle += amount;
	}

	// Returns cart's x position
	public int getX() {
		return xPos;
	}

	// Returns cart's y position
	public int getY() {
		return this.yPos;
	}

	public void givePart(GUIPart part, GUIComponent giver) {
	}

	public GUIPart getPart(int position) {
		return null;
	}

	public void gotPart(GUIPart part) {
	}

	// Sets cart's agent
	public void setCartAgent(CartAgent cartAgent) {
		agent = cartAgent;
	}

	// Returns cart's name
	public String getName() {
		return name;
	}

	// Displays cart on screen
	public void draw(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		double kitCenterX = (xPos + (width / 2));
		double kitCenterY = (yPos + (height / 2));
		g.rotate(Math.toRadians(angle), kitCenterX, kitCenterY);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		g.rotate(Math.toRadians(-1 * angle), kitCenterX, kitCenterY);
	}

	// Sets cart's x position
	public void setX(int x) {
		xPos = x;
	}

	// Sets cart's y position
	public void setY(int y) {
		yPos = y;

	}

	// Returns width of cart
	public int getWidth() {
		return width;
	}

	// Returns height of cart
	public int getHeight() {
		return height;
	}
}