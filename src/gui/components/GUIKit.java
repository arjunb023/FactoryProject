package gui.components;

import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *	CSCI 200 Factory Project
 *	Version v0
 *
 *	GUIKit class
 *
 *	Represents a kit which can contain parts.
 *	This kit can be moved in two dimensions, rotated, and zoomed in and out.
 *	Other components of the factory will perform operations on the kit that will change the number of parts contained in the kit and the state of the kit.
 *
 *	@author 	Carina Prynn
 *	@version	v0
 */
@SuppressWarnings("serial")
public class GUIKit extends GUIComponent implements Serializable
{
	/********
	 * DATA *
	 ********/
	private Rectangle2D.Double kitPlaceholder;
	public enum State {EMPTY, PARTIALLYFILLED, FULL, BAD, MISSING, UNKNOWN}
	private State state;
	private ArrayList<GUIPart> parts;
	private boolean[] filled;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private int maxSize;
	private String name;
	private ImageIcon image;
	private final int stdWidth = 60;
	private final int stdHeight = 60;
	private final ImageIcon stdImage = new ImageIcon("images/ab_kit.png");
	
	/****************
	 * CONSTRUCTORS *
	 ****************/
	
	/**
	 *	Constructor takes four parameters: Initial x coordinate, Initial y coordinate, Initial angle, Maximum number of parts the kit can contain.
	 */
	public GUIKit(int x, int y, int a, int size)
	{
		parts = new ArrayList<GUIPart>();
		width = stdWidth;
		height = stdHeight;
		angle = a;
		xPos = x;
		yPos = y;
		maxSize = size;
		name = "kit";
		image = stdImage;
		kitPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
		filled = new boolean[9];
		for (int i = 0; i < filled.length; i++)
			filled[i] = false;
	}
	
	/***********
	 * METHODS *
	 ***********/
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	@Override
	public void givePart(GUIPart part, GUIComponent giver)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public GUIPart getPart(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void gotPart(GUIPart part)
	{
		// TODO Auto-generated method stub
		
	}
	
	/**	
	 * Convenience method for other classes to change the display representing this kit after an operation is completed on the kit.
	 * Arguments: none
	 * Returns: void	
	 */
	public void update()
	{
		kitPlaceholder.setFrame(this.xPos, this.yPos, this.width, this.height);
		if(state == State.BAD && this.width < 5){
			kitPlaceholder.setFrame(this.xPos, this.yPos, this.width * 0.8, this.height * 0.8);
		}
	}
	
	/**	
	 * Displays the part onscreen by drawing its images to a Graphics2D object.
	 * Arguments: Graphics2D object to which to draw
	 * Returns: void
	 */
	public void draw(Graphics g1)
	{
		Graphics2D g = (Graphics2D)g1;
		double	kitCenterX	= (xPos + (width/2));
		double	kitCenterY	= (yPos + (height/2));
		g.rotate(Math.toRadians(angle), kitCenterX, kitCenterY);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		g.rotate(Math.toRadians(-1*angle), kitCenterX, kitCenterY);
	}	
	
	/**	
	 * Moves the kit in the horizontal direction by the specified number of pixels. If numPixels is positive, moves right.
	 * If numPixels is negative, moves left.
	 * Arguments: integer of the amount to move
	 * Returns: void	
	 * @param numPixels
	 */
	public void moveX(int numPixels)
	{
		xPos += numPixels;
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).moveX(numPixels);
	}
	
	/**	
	 * Moves the kit in the vertical direction by the specified number of pixels. If numPixels is positive, moves up.
	 * If numPixels is negative, moves down.
	 * Arguments: integer of the amount to move
	 * Returns: void	
	 * @param numPixels
	 */
	public void moveY(int numPixels)
	{
		yPos -= numPixels;
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).moveY(numPixels);
	}
	
	/**	
	 * Makes the size of the kit bigger or smaller by the specified amount. If amount is positive, zooms in.
	 * If amount is negative, zooms out.
	 * Arguments: integer of the amount to zoom
	 * Returns: void	
	 * @param amount
	 */
	public void zoom(int amount)
	{
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).zoom(amount * parts.get(parts.size() - 1).getWidth() / width);
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
		setX(xPos);
		setY(yPos);
	}
	

	/**	
	 * Rotates the kit by the specified amount. If amount is positive, rotates clockwise.
	 * If amount is negative, rotates counter clockwise.
	 * Arguments: integer of the amount to rotate
	 * Returns: void	
	 * @param amount
	 */
	public void rotate(int amount)
	{
		double rotAngle;
		double	kitCenterX	= (xPos + (width/2));
		double	kitCenterY	= (yPos + (height/2));
		
		angle += amount;
		
		double theta = Math.toRadians(-1 * angle);
		for (int i = 0; i < parts.size(); i++)
		{
			double partX = parts.get(i).getX() + (parts.get(i).getWidth() / 2);
			double partY = parts.get(i).getY() + (parts.get(i).getHeight() / 2);
			parts.get(i).rotate(amount);
			if (i == 0)
				rotAngle = (theta + Math.PI / 4);
			else if (i == 1)
				rotAngle = theta;
			else if (i == 2)
				rotAngle = theta - Math.PI / 4;
			else if (i == 3)
				rotAngle = theta + Math.PI / 2;
			else if (i == 4)
				rotAngle = theta - Math.PI / 2;
			else if (i == 5)
				rotAngle = (theta + 3 * Math.PI / 4);
			else if (i == 6)
				rotAngle = theta + Math.PI;
			else if (i == 7)
				rotAngle = (theta - 3 * Math.PI / 4);
			else
				rotAngle = 0;
			double radius = Math.sqrt(Math.pow((kitCenterX - partX), 2) + Math.pow((kitCenterY - partY), 2));
			double newX = radius * Math.sin(rotAngle);
			double newY = radius * Math.cos(rotAngle);
			parts.get(i).setX((int)(kitCenterX - newX - (parts.get(i).getWidth() / 2)));
			parts.get(i).setY((int)(kitCenterY - newY - (parts.get(i).getHeight() / 2)));
		
		}
		
	}
	
	//Adds an ArrayList of parts to the kit.
	public boolean addParts(ArrayList<GUIPart> newParts)
	{
		for (int i = 0; i < newParts.size(); i++)
			if (!addPart(newParts.get(i)))
				return (false);
		return (true);
	}
	
	//Adds a part to the kit.
	public boolean addPart(GUIPart newPart)
	{
		if (state != State.FULL)
		{
			parts.add(newPart);
			for (int i = 0; i < filled.length; i++)
			{
				if (!filled[i])
				{
					switch (i + 1)
					{
					case 1:
						parts.get(parts.size() - 1).setX(xPos + width * 2/60);
						parts.get(parts.size() - 1).setY(yPos + height * 2/60);
						break;
					case 2:
						parts.get(parts.size() - 1).setX(xPos + width * 21/60);
						parts.get(parts.size() - 1).setY(yPos + height * 2/60);
						break;
					case 3:
						parts.get(parts.size() - 1).setX(xPos + width * 40/60);
						parts.get(parts.size() - 1).setY(yPos + height * 2/60);
						break;
					case 4:
						parts.get(parts.size() - 1).setX(xPos + width * 2/60);
						parts.get(parts.size() - 1).setY(yPos + height * 21/60);
						break;
					case 5:
						parts.get(parts.size() - 1).setX(xPos + width * 40/60);
						parts.get(parts.size() - 1).setY(yPos + height * 21/60);
						break;
					case 6:
						parts.get(parts.size() - 1).setX(xPos + width * 2/60);
						parts.get(parts.size() - 1).setY(yPos + height * 40/60);
						break;
					case 7:
						parts.get(parts.size() - 1).setX(xPos + width * 21/60);
						parts.get(parts.size() - 1).setY(yPos + height * 40/60);
						break;
					case 8:
						parts.get(parts.size() - 1).setX(xPos + width * 40/60);
						parts.get(parts.size() - 1).setY(yPos + height * 40/60);
						break;
					case 9:
						parts.get(parts.size() - 1).setX(xPos + width * 21/60);
						parts.get(parts.size() - 1).setY(yPos + height * 21/60);
						break;
					}
					parts.get(parts.size() - 1).zoom(((width - stdWidth) / stdWidth) * parts.get(parts.size() - 1).getWidth());
					rotate(0);
					filled[i] = true;
					break;
				}
			}
			if (parts.size() == maxSize)
				state = State.FULL;
			return (true);
		} else
			return (false);
	}
	
	//Removes the part at the specified index from the kit.
	public void removePart(int index)
	{
		int x, y;
		if (parts.size() > 0 && index < parts.size() && index >= 0)
		{
			x = parts.get(index).getX() - xPos;
			y = parts.get(index).getY() - yPos;
			if (x == width * 2/60 && y == height * 2/60)
				filled[0] = false;
			else if (x == width * 21/60 && y == height * 2/60)
				filled[1] = false;
			else if (x == width * 40/60 && y == height * 2/60)
				filled[2] = false;
			else if (x == width * 2/60 && y == height * 21/60)
				filled[3] = false;
			else if (x == width * 40/60 && y == height * 21/60)
				filled[4] = false;
			else if (x == width * 2/60 && y == height * 40/60)
				filled[5] = false;
			else if (x == width * 21/60 && y == height * 40/60)
				filled[6] = false;
			else if (x == width * 40/60 && y == height * 40/60)
				filled[7] = false;
			else if (x == width * 21/60 && y == height * 21/60)
				filled[8] = false;
			parts.remove(index);
			if (state == State.FULL)
				state = State.PARTIALLYFILLED;
		}
	}
	
	//Removes all the parts in the kit.
	public void emptyKit()
	{
		for (int i = parts.size() - 1; i >= 0; i--)
			parts.remove(i);
		for (int i = 0; i < filled.length; i++)
			filled[i] = false;
	}
	
	/***************************
	 * MODIFIERS AND ACCESSORS *
	 ***************************/
	 
	//Sets the state of the kit to the enum of the parameter.
	public void setState(State newState)
	{
		state = newState;
	}
	
	//Sets the xPos 
	public void setX(int x)
	{
		xPos = x;
		for(int i = 0; i < parts.size(); i++)
		{
			switch (i + 1)
			{
				case 1:
					parts.get(i).setX(xPos + width * 2/60);
					break;
				case 2:
					parts.get(i).setX(xPos + width * 21/60);
					break;
				case 3:
					parts.get(i).setX(xPos + width * 40/60);
					break;
				case 4:
					parts.get(i).setX(xPos + width * 2/60);
					break;
				case 5:
					parts.get(i).setX(xPos + width * 40/60);
					break;
				case 6:
					parts.get(i).setX(xPos + width * 2/60);
					break;
				case 7:
					parts.get(i).setX(xPos + width * 21/60);
					break;
				case 8:
					parts.get(i).setX(xPos + width * 40/60);
					break;
				case 9:
					parts.get(i).setX(xPos + width * 21/60);
					break;
			}
		}
	}
	
	//Sets the yPos 
	public void setY(int y)
	{
		yPos = y;
		for(int i = 0 ; i < parts.size() ; i++)
		{
			
			switch (i + 1)
			{
				case 1:
					parts.get(i).setY(yPos + height * 2/60);
					break;
				case 2:
					parts.get(i).setY(yPos + height * 2/60);
					break;
				case 3:
					parts.get(i).setY(yPos + height * 2/60);
					break;
				case 4:
					parts.get(i).setY(yPos + height * 21/60);
					break;
				case 5:
					parts.get(i).setY(yPos + height * 21/60);
					break;
				case 6:
					parts.get(i).setY(yPos + height * 40/60);
					break;
				case 7:
					parts.get(i).setY(yPos + height * 40/60);
					break;
				case 8:
					parts.get(i).setY(yPos + height * 40/60);
					break;
				case 9:
					parts.get(i).setY(yPos + height * 21/60);
					break;
			}
		}
	}
	
	//Returns the state of the kit in the form of an enum.
	public State getState()
	{
		return (state);
	}
	
	//Returns the number of parts in the kit.
	public int getNumParts()
	{
		return (parts.size());
	}
	
	//Returns the part at the specified index.
	public GUIPart getPartAt(int index)
	{
		return (parts.get(index));
	}
	
	//Returns the current x position of the kit.
	public int getX()
	{
		return (xPos);
	}
	
	//Returns the current y position of the kit.
	public int getY()
	{
		return (yPos);
	}
	
	//Returns the width
	public int getWidth()
	{
		return (width);
	}
	
	//Returns the height
	public int getHeight()
	{
		return (height);
	}
	
	public ArrayList<GUIPart> getPartsList() 
	{
		return parts;
	}
}