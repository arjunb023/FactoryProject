package gui.components;

import java.io.Serializable;
import java.util.Random;
import java.lang.String;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

import shared.enums.PartType;
@SuppressWarnings("serial")

/**
 *	CSCI 200 Factory Project
 *	Version v0
 *
 *	GUIPart class
 *
 *	Represents a physical part.
 *	This part can be moved in two dimensions, rotated, and zoomed in and out.
 *	Other components of the factory will perform operations on the part that will change the state and display of the part.
 *	
 *	@author 	Carina Prynn
 *	@version	v0
 */
public class GUIPart extends GUIComponent implements Serializable
{
	/********
	 * DATA *
	 ********/
	 
	private Rectangle2D.Double partPlaceholder;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private int timeSinceExplosion = 0;
	private int time = 0;
	private String name;
	private PartType type;
	public enum State {GOOD, BAD, MISSING, EXPLODING, UKNOWN}
	private State state;
	private ImageIcon image;
	private final int stdWidth = 20;
	private final int stdHeight = 20;
	private final ImageIcon stdImage = new ImageIcon("images/part.png");
	
	/****************
	 * CONSTRUCTORS *
	 ****************/
	
	/**
	 *	Constructor takes one parameter: The type of part
	 */
	public GUIPart(PartType partType)
	{
		Random generator = new Random();
		int temp = generator.nextInt(9);
		if (temp < 2)
			state = State.BAD;
		else
			state = State.GOOD;
		
		// ALL GOOD PARTS --Duke
		//state = State.GOOD;
		
		type = partType;
		width = stdWidth;
		height = stdHeight;
		angle = 0;
		xPos = 300;	// (JY) Changed default positions of the parts
		yPos = -20;
		name = "part";
		//image = new ImageIcon("images/partExample.png");
		if (state == State.GOOD)
		{
			if (type == PartType.A)
				image = new ImageIcon("images/black_bird.png");
			else if (type == PartType.B)
				image = new ImageIcon("images/blue_bird.png");
			else if (type == PartType.C)
				image = new ImageIcon("images/egg.png");
			else if (type == PartType.D)
				image = new ImageIcon("images/green_bird.png");
			else if (type == PartType.E)
				image = new ImageIcon("images/helmet_pig.png");
			else if (type == PartType.F)
				image = new ImageIcon("images/red_bird.png");
			else if (type == PartType.G)
				image = new ImageIcon("images/tnt.png");
			else if (type == PartType.H)
				image = new ImageIcon("images/white_bird.png");
			else if (type == PartType.I)
				image = new ImageIcon("images/yellow_bird.png");
			else
				image = stdImage;
		} else
		{
			if (type == PartType.A)
				image = new ImageIcon("images/black_bird_broken.png");
			else if (type == PartType.B)
				image = new ImageIcon("images/blue_bird_broken.png");
			else if (type == PartType.C)
				image = new ImageIcon("images/egg_broken.png");
			else if (type == PartType.D)
				image = new ImageIcon("images/green_bird_broken.png");
			else if (type == PartType.E)
				image = new ImageIcon("images/helmet_pig_broken.png");
			else if (type == PartType.F)
				image = new ImageIcon("images/red_bird_broken.png");
			else if (type == PartType.G)
				image = new ImageIcon("images/tnt_broken.png");
			else if (type == PartType.H)
				image = new ImageIcon("images/white_bird_broken.png");
			else if (type == PartType.I)
				image = new ImageIcon("images/yellow_bird_broken.png");
			else
				image = stdImage;
		}
		partPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
	}
	
	/**
	 *	Constructor takes four parameters: The type of part, Initial x coordinate, Initial y coordinate, Initial angle
	 */
	public GUIPart(PartType partType, int x, int y, int a)
	{
		Random generator = new Random();
		int temp = generator.nextInt(9);
		if (temp < 2)
			state = State.BAD;
		else
			state = State.GOOD;
		type = partType;
		width = stdWidth;
		height = stdHeight;
		angle = a;
		xPos = x;
		yPos = y;
		name = "part";
		//image = new ImageIcon("images/partExample.png");
		if (state == State.GOOD)
		{
			if (type == PartType.A)
				image = new ImageIcon("images/black_bird.png");
			else if (type == PartType.B)
				image = new ImageIcon("images/blue_bird.png");
			else if (type == PartType.C)
				image = new ImageIcon("images/egg.png");
			else if (type == PartType.D)
				image = new ImageIcon("images/green_bird.png");
			else if (type == PartType.E)
				image = new ImageIcon("images/helmet_pig.png");
			else if (type == PartType.F)
				image = new ImageIcon("images/red_bird.png");
			else if (type == PartType.G)
				image = new ImageIcon("images/tnt.png");
			else if (type == PartType.H)
				image = new ImageIcon("images/white_bird.png");
			else if (type == PartType.I)
				image = new ImageIcon("images/yellow_bird.png");
			else
				image = stdImage;
		} else
		{
			if (type == PartType.A)
				image = new ImageIcon("images/black_bird_broken.png");
			else if (type == PartType.B)
				image = new ImageIcon("images/blue_bird_broken.png");
			else if (type == PartType.C)
				image = new ImageIcon("images/egg_broken.png");
			else if (type == PartType.D)
				image = new ImageIcon("images/green_bird_broken.png");
			else if (type == PartType.E)
				image = new ImageIcon("images/helmet_pig_broken.png");
			else if (type == PartType.F)
				image = new ImageIcon("images/red_bird_broken.png");
			else if (type == PartType.G)
				image = new ImageIcon("images/tnt_broken.png");
			else if (type == PartType.H)
				image = new ImageIcon("images/white_bird_broken.png");
			else if (type == PartType.I)
				image = new ImageIcon("images/yellow_bird_broken.png");
			else
				image = stdImage;
		}
		partPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
	}
	
	/***********
	 * METHODS *
	 ***********/
	@Override
	public String getName()
	{
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
	 * Convenience method for other classes to change the display representing this part after an operation is completed on the part.
	 * Arguments: none
	 * Returns: void	
	 */
	public void update()
	{
		partPlaceholder.setFrame(this.xPos, this.yPos, this.width, this.height);		
	}
	
	/**	
	 * Displays the part onscreen by drawing its images to a Graphics2D object.
	 * Arguments: Graphics2D object to which to draw
	 * Returns: void
	 */
	public void draw(Graphics g1)
	{
		Graphics2D g = (Graphics2D)g1;
		double	partCenterX	= (xPos + (width/2));
		double	partCenterY	= (yPos + (height/2));
		g.rotate(Math.toRadians(angle), partCenterX, partCenterY);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		// Added to make the graphics unnecessary
		/*switch(type)
		{
		case A:
			g.setColor(Color.RED);
			break;
		case B:
			g.setColor(Color.ORANGE);
			break;
		case C:
			g.setColor(Color.YELLOW);
			break;
		case D:
			g.setColor(Color.GREEN);
			break;
		case E:
			g.setColor(Color.BLUE);
			break;
		case F:
			g.setColor(new Color(128, 30, 200));
			break;
		case G:
			g.setColor(Color.PINK);
			break;
		case H:
			g.setColor(Color.WHITE);
			break;
		case I:
			g.setColor(new Color(((int)(255*Math.random())), ((int)(255*Math.random())), ((int)(255*Math.random())))); 
		}
		if (state != State.BAD)
			g.fillOval(xPos, yPos, width, height);
		else
			g.fillRect(xPos, yPos, width, height);*/
		g.rotate(Math.toRadians(-1*angle), partCenterX, partCenterY);
	}
	
	/**	
	 * Moves the part in the horizontal direction by the specified number of pixels. If numPixels is positive, moves right.
	 * If numPixels is negative, moves left.
	 * Arguments: integer of the amount to move
	 * Returns: void	
	 * @param numPixels
	 */
	public void moveX(int numPixels)
	{
		xPos += numPixels;
	}
	
	/**	
	 * Moves the part in the vertical direction by the specified number of pixels. If numPixels is positive, moves up.
	 * If numPixels is negative, moves down.
	 * Arguments: integer of the amount to move
	 * Returns: void	
	 * @param numPixels
	 */
	public void moveY(int numPixels)
	{
		yPos -= numPixels;
	}
	
	/**	
	 * Makes the size of the part bigger or smaller by the specified amount. If amount is positive, zooms in.
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
	}
	
	/**	
	 * Rotates the part by the specified amount. If amount is positive, rotates clockwise.
	 * If amount is negative, rotates counter clockwise.
	 * Arguments: integer of the amount to rotate
	 * Returns: void	
	 * @param amount
	 */
	public void rotate(int amount)
	{
		angle += amount;
	}
	
	
	//changes the part to the exploded state and changes the image to the exploded image
	public boolean explode()
	{
		if (time % 50 == 0)
		{
			if ((type == PartType.A || type == PartType.G) && state != State.BAD)
			{
				switch (timeSinceExplosion)
				{
				case 0:
					image = new ImageIcon("images/explosion1.png");
					state = State.EXPLODING;
					break;
				case 1:
					image = new ImageIcon("images/explosion2.png");
					break;
				case 2:
					image = new ImageIcon("images/explosion3.png");
					break;
				}
			}
			timeSinceExplosion++;
			if (timeSinceExplosion == 4)
			{
				setState(State.BAD);
				timeSinceExplosion = 0;
				time = 0;
				return (false);
			}
			//update();
		}
		time++;
		return(true);
	}
	
	/***************************
	 * MODIFIERS AND ACCESSORS *
	 ***************************/
	
	//Sets the name of the part.
	public void setName(String s)
	{
		this.name = s;
	}
	
	//Sets the state of the part to the enum of the parameter.
	public void setState(State s)
	{
		state = s;
		if (state == State.BAD)
		{
			if (type == PartType.A)
				image = new ImageIcon("images/black_bird_broken.png");
			else if (type == PartType.B)
				image = new ImageIcon("images/blue_bird_broken.png");
			else if (type == PartType.C)
				image = new ImageIcon("images/egg_broken.png");
			else if (type == PartType.D)
				image = new ImageIcon("images/green_bird_broken.png");
			else if (type == PartType.E)
				image = new ImageIcon("images/helmet_pig_broken.png");
			else if (type == PartType.F)
				image = new ImageIcon("images/red_bird_broken.png");
			else if (type == PartType.G)
				image = new ImageIcon("images/tnt_broken.png");
			else if (type == PartType.H)
				image = new ImageIcon("images/white_bird_broken.png");
			else if (type == PartType.I)
				image = new ImageIcon("images/yellow_bird_broken.png");
			else
				image = stdImage;
		}
	}
	
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
	
	//Returns the state of the part in the form of an enum.
	public State getState()
	{
		return (state);
	}
	
	//Returns the current x position of the part.
	public int getX()
	{
		return (xPos);
	}
	
	//Returns the current y position of the part.
	public int getY()
	{
		return (yPos);
	}
	
	//Returns the width of the part
	public int getWidth()
	{
		return(width);
	}
	
	//Returns the height of the part
	public int getHeight()
	{
		return(height);
	}
	
	//Returns the part type in the form of a enum.
	public PartType getType()
	{
		return (type);
	}
}