package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

/**
 * CSCI 200 Factory Project Version v0
 * 
 * GUIKitStand class
 * 
 * Represents a kit stand which can hold up to three kits.
 * 
 * @author Reetika Rastogi
 * @version v0
 */
@SuppressWarnings("serial")
public class GUIKitStand extends GUIComponent {

	private GUIKit[] kits;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private String name;
	private ImageIcon image;
	private final int standSize = 3;
	private final int buffer = 10;

	private final ImageIcon stdImage = new ImageIcon("images/kitstand.png");

	private final int stdWidth = stdImage.getIconWidth();
	private final int stdHeight = stdImage.getIconHeight();

	private Rectangle2D.Double kitStandPlaceHolder;

	public GUIKitStand(int a, int kitSize) {
		xPos = 100;
		yPos = 100;
		angle = a;
		width = stdWidth;
		height = stdHeight;
		image = stdImage;
		name = "Kit Stand";
		kitStandPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, height);

		kits = new GUIKit[standSize];
	}

	// Updates placeHolder properties
	public void update() {
		kitStandPlaceHolder.setFrame(this.xPos, this.yPos, this.width,
				this.height);
	}

	// Other classes call this function when they want to pick up a kit from the
	// stand
	public GUIKit getKitFromStand(int i) {
		GUIKit myKit = null;
		if (i >= 0 && i < standSize)
			if (kits[i] != null) {
				myKit = kits[i];
			}
		return myKit;
	}

	// Removes the kit at position i
	public void deleteKit(int i) {
		kits[i] = null;
	}

	// Other classes call this function when they want to put a kt on the stand
	public void putKitOnStand(GUIKit k, int i) {
		if (i >= 0 && i < standSize) {
			k.setX(xPos + buffer);
			k.setY(yPos + (i + 1) * buffer + i * k.getWidth());
			kits[i] = k;
		}
	}

	// Zooms in or out (amount) times
	public void zoom(int amount) {
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
	}

	// Rotates (amount) degrees
	public void rotate(int amount) {
		amount += amount;
	}

	// Returns kit stand's name
	public String getName() {
		return this.name;
	}

	public void givePart(GUIPart part, GUIComponent giver) {
	}

	public GUIPart getPart(int position) {
		return null;
	}

	public void gotPart(GUIPart part) {
	}

	// Draws kit stand on screen
	public void draw(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		double kitCenterX = (xPos + (width / 2));
		double kitCenterY = (yPos + (height / 2));
		g.rotate(Math.toRadians(angle), kitCenterX, kitCenterY);
		g.setColor(Color.RED);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		g.rotate(Math.toRadians(-1 * angle), kitCenterX, kitCenterY);
	}

	// Sets kit stand's x position
	public void setX(int x) {
		xPos = x;
	}

	// Sets kit stand's y position
	public void setY(int y) {
		yPos = y;
	}

	// Returns kit stand's x position
	public int getX() {
		return xPos;
	}

	// Returns kit stand's y position
	public int getY() {
		return yPos;
	}

	// Returns kit's width
	public int getWidth() {
		return width;
	}

	// Returns kit's height
	public int getHeight() {
		return height;
	}
}