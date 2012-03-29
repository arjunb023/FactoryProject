package gui.components;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

import shared.enums.PartType;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 *	CSCI 200 Factory Project
 *	Version v0
 *
 *	GUIBin class
 *
 *	Represents a bin which can contain parts.
 *	This bin can be moved in two dimensions, rotated, and zoomed in and out.
 *	Other components of the factory will perform operations on the bin that will change the number of parts contained in the bin and the state of the bin.
 *	
 *	@author 	Carina Prynn
 *	@version	v0
 */
@SuppressWarnings("serial")
public class GUIBin extends GUIComponent implements Serializable
{
	/********
	 * DATA *
	 ********/
	 
	private Rectangle2D.Double binPlaceholder;
	private ArrayList<GUIPart> parts;
	public PartType type;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private String name;
	private ImageIcon image;
	private final int stdWidth = 50;
	private final int stdHeight = 50;
//	private final ImageIcon stdImage = new ImageIcon("images/bin.png");
	
	private ImageIcon black_image;
	private ImageIcon blue_image;
	private ImageIcon egg_image;
	private ImageIcon green_image;
	private ImageIcon helmet_image;
	private ImageIcon red_image;
	private ImageIcon tnt_image;
	private ImageIcon white_image;
	private ImageIcon yellow_image;

	
	private final ImageIcon stdImage = new ImageIcon("images/standard_bin.png");
	private final ImageIcon black_bird_bin = new ImageIcon("images/black_bird_bin.png");
	private final ImageIcon blue_bird_bin = new ImageIcon("images/blue_bird_bin.png");
	private final ImageIcon egg_bin = new ImageIcon("images/egg_bin.png");
	private final ImageIcon green_bird_bin = new ImageIcon("images/green_bird_bin.png");
	private final ImageIcon helmet_pig_bin = new ImageIcon("images/helmet_pig_bin.png");
	private final ImageIcon red_bird_bin = new ImageIcon("images/red_bird_bin.png");
	private final ImageIcon tnt_bin = new ImageIcon("images/tnt_bin.png");
	private final ImageIcon white_bird_bin = new ImageIcon("images/white_bird_bin.png");
	private final ImageIcon yellow_bird_bin = new ImageIcon("images/yellow_bird_bin.png");
	
	
	/****************
	 * CONSTRUCTORS *
	 ****************/
	
	/**
	 *	Constructor takes one parameter: The type of bin
	 */
	public GUIBin(PartType binType)
	{
		parts = new ArrayList<GUIPart>();
		type = binType;
		
		
		
		width = stdWidth;
		height = stdHeight;
		angle = 0;
		xPos = 850;	// (JY) Changed the default position of the bin
		yPos = -60;
		name = "bin";
		image = stdImage;
		black_image = black_bird_bin;
		blue_image = blue_bird_bin;
		egg_image = egg_bin;
		green_image = green_bird_bin;
		helmet_image = helmet_pig_bin;
		red_image = red_bird_bin;
		tnt_image = tnt_bin;
		white_image = white_bird_bin;
		yellow_image = yellow_bird_bin;
		binPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
	}
	
	/**
	 *	Constructor takes four parameters: The type of bin, Initial x coordinate, Initial y coordinate, Initial angle.
	 */
	public GUIBin(PartType binType, int x, int y, int a)
	{
		parts = new ArrayList<GUIPart>();
		type = binType;
		width = stdWidth;
		height = stdHeight;
		angle = a;
		xPos = x;
		yPos = y;
		name = "bin";
		image = stdImage;
		binPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
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
	 * Convenience method for other classes to change the display representing this bin after an operation is completed on the bin.
	 * Arguments: none
	 * Returns: void	
	 */
	public void update()
	{
		binPlaceholder.setFrame(this.xPos, this.yPos, this.width, this.height);		
	}
	
	/**
	 * Displays the part onscreen by drawing its images to a Graphics2D object.
	 * Arguments: Graphics2D object to which to draw
	 * Returns: void	
	 */
	public void draw(Graphics g1)
	{
		Graphics2D g = (Graphics2D)g1;
		double	binCenterX	= (xPos + (width/2));
		double	binCenterY	= (yPos + (height/2));
		g.rotate(Math.toRadians(angle), binCenterX, binCenterY);
//		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		
		
		// temporary colors
		if (type != null)
		{
			switch(type)
			{
			case A:
				g.drawImage(black_image.getImage(), xPos, yPos, width, height, null);
//				g.setColor(Color.RED);
				break;
			case B:
				g.drawImage(blue_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(Color.ORANGE);
				break;
			case C:
				g.drawImage(egg_image.getImage(), xPos, yPos, width, height, null);
//				g.setColor(Color.YELLOW);
				break;
			case D:
				g.drawImage(green_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(Color.GREEN);
				break;
			case E:
				g.drawImage(helmet_image.getImage(), xPos, yPos, width, height, null);
//				g.setColor(Color.BLUE);
				break;
			case F:
				g.drawImage(red_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(new Color(128, 30, 200));
				break;
			case G:
				g.drawImage(tnt_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(Color.PINK);
				break;
			case H:
				g.drawImage(white_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(Color.WHITE);
				break;
			case I:
				g.drawImage(yellow_image.getImage(), xPos, yPos, width, height, null);				
//				g.setColor(Color.BLACK);
				break;
				
			}
		}
		else
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
//			g.setColor(Color.MAGENTA);
//		g.drawRect(xPos, yPos, width, height);
//		g.fillRect(xPos, yPos, width/2, height/2);
		// \\ temporary colors
		
		
		
		g.rotate(Math.toRadians(-1*angle), binCenterX, binCenterY);
	}	
	
	/**	
	 * Moves the bin in the horizontal direction by the specified number of pixels. If numPixels is positive, moves right.
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
	 * Moves the bin in the vertical direction by the specified number of pixels. If numPixels is positive, moves up.
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
	 * Makes the size of the bin bigger or smaller by the specified amount. If amount is positive, zooms in.
	 * If amount is negative, zooms out.
	 * Arguments: integer of the amount to zoom
	 * Returns: void	
	 * @param amount
	 */
	public void zoom(int amount)
	{
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).zoom(amount);
	}
	
	/**	
	 * Rotates the bin by the specified amount. If amount is positive, rotates clockwise.
	 * If amount is negative, rotates counter clockwise.
	 * Arguments: integer of the amount to rotate
	 * Returns: void	
	 * @param amount
	 */
	public void rotate(int amount)
	{
		angle += amount;
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).rotate(amount);
	}
	
	//Adds a part to the bin.
	public void addPart(GUIPart newPart)
	{
		parts.add(newPart);
		parts.get(parts.size() - 1).setX(xPos + width * 16/50);
		parts.get(parts.size() - 1).setY(yPos + height * 16/50);
	}
	
	//Removes the part at the specified index from bin.
	public void removePart(int index)
	{
		if (parts.size() > 0 && index < parts.size() && index >= 0)
			parts.remove(index);
	}
	
	/***************************
	 * MODIFIERS AND ACCESSORS *
	 ***************************/
	 
	//Sets the x position
	public void setX(int x)
	{
		xPos = x;
	}
	
	//Sets the y position
	public void setY(int y)
	{
		yPos = y;
	}
	
	//Sets the angle of rotation
	public void setAngle(int a)
	{
		angle = a;
	}
	
	//Returns the number of parts in the bin.
	public int getNumParts()
	{
		return (parts.size());
	}
	
	//Returns the part at the specified index.
	public GUIPart getPartAt(int index)
	{
		return (parts.get(index));
	}
	
	//Returns the type of the bin.
	public PartType getType()
	{
		return (type);
	}

	//Returns the current x position of the bin.
	public int getX()
	{
		return (xPos);
	}
	
	//Returns the current y position of the bin.
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
}