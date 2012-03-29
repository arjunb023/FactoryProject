package gui.components;

import java.io.Serializable;
import factory.interfaces.Nest;
import gui.components.GUIKit.State;
import gui.interfaces.GUINestInterface;
import javax.swing.*;

import shared.Part;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.util.ArrayList;
import shared.enums.*;

//THIS IS THE GUINEST CLASS
@SuppressWarnings("serial")
public class GUINest extends GUIComponent implements GUINestInterface, Serializable
{
	private int mySize = 12;
	
	private Rectangle2D.Double nestPlaceholder;
	private NestState state;
	private ArrayList<GUIPart> parts;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private String name;
	private ImageIcon image;
	private final int stdWidth = 78;
	private final int stdHeight = 60;
	private final ImageIcon stdImage = new ImageIcon("images/ab_nest.png");
	
	private int border = 3;
	private int plusRow = 18;
	private int plusCol = 18;
	private int nextFreeCol;
	private int nextFreeRow;
	private boolean stateChange = true;
	private int[] colArray;
	private int[] rowArray;
	private boolean[] isFull;
	
	public boolean fullState;
	
	public boolean changeNormative = true;
	
	private Nest myAgent;

	private int nestNum;
	private boolean endPartIn;
	
	/***************************
	 * CONSTRUCTOR
	 */
	public GUINest(int x, int y, int size)
	{
		parts = new ArrayList<GUIPart>();
		width = stdWidth;
		height = stdHeight;
		xPos = x;
		yPos = y;
		nextFreeCol = x + border;
		nextFreeRow = y + border;
		name = "nest";
		image = stdImage;
		nestPlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
		state = NestState.EMPTY;
		colArray = new int[mySize];
		for(int i =0; i< colArray.length; i++)
		{
			colArray[i] = xPos + plusCol*i;
		}
		rowArray = new int[mySize];
		for(int i =0; i< rowArray.length; i++)
		{
			rowArray[i] = yPos + plusRow*i;
		}
		isFull = new boolean[mySize];
		for(int i =0; i< isFull.length; i++)
		{
			isFull[i] = false;
		}
		fullState = false;
		endPartIn = false;
		for(int i = 0; i<12; i++)
		{
			parts.add(null);
		}
	}
	
	
	
	
	//THIS ADDS A PART TO THE NEST
	@Override
	public void givePart(GUIPart part, GUIComponent giver) {
		// TODO Auto-generated method stub
		//LANE
		if(stateChange == true)
		{
			state = NestState.ADDING_PART;
			stateChange = false;
			for(int i = 0; i < parts.size(); i++)
			{
				if(parts.get(i)==null)
				{
					parts.set(i,part);
					break;
				}
			}
			for(int i =0; i<isFull.length;i++)
			{
				if(isFull[i] == false)
				{
					switch (i)
					{
						case 0:
							nextFreeCol = xPos+border;
							nextFreeRow = yPos+border;
							break;
						case 3:
							nextFreeCol = xPos+plusCol+border;
							nextFreeRow = yPos+border;
							break;
						case 6:
							nextFreeCol = xPos+plusCol*2+border;
							nextFreeRow = yPos+border;
							break;
						case 9:
							nextFreeCol = xPos+plusCol*3+border;
							nextFreeRow = yPos+border;
							break;
						case 1:
							nextFreeCol = xPos+border;
							nextFreeRow = yPos+plusRow+border;
							break;
						case 4:
							nextFreeCol = xPos+plusCol+border;
							nextFreeRow = yPos+plusRow+border;
							break;
						case 7:
							nextFreeCol = xPos+plusCol*2+border;
							nextFreeRow = yPos+plusRow+border;
							break;
						case 10:
							nextFreeCol = xPos+plusCol*3+border;
							nextFreeRow = yPos+plusRow+border;
							break;
						case 2:
							nextFreeCol = xPos+border;
							nextFreeRow = yPos+plusRow*2+border;
							break;
						case 5:
							nextFreeCol = xPos+plusCol+border;
							nextFreeRow = yPos+plusRow*2+border;
							break;
						case 8:
							nextFreeCol = xPos+plusCol*2+border;
							nextFreeRow = yPos+plusRow*2+border;
							break;
						case 11:
							nextFreeCol = xPos+plusCol*3+border;
							nextFreeRow = yPos+plusRow*2+border;
							break;
					}
					isFull[i] = true;
					break;
				}
			}
			
			if(changeNormative)
			{
			if(isFull[11] == true)
			{
				fullState = true;
				//state = NestState.FULL;
				/*
				ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
				synchronized(parts)
				{
					for (GUIPart p : parts)
						partsCopy.add(p);
				}
				*/
				//myAgent.msgIAmFull(parts, nestNum);
			}
			}
			else
			{
				if(isFull[8] == true)
				{
					fullState = true;
					//state = NestState.FULL;
					/*
					ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
					synchronized(parts)
					{
						for (GUIPart p : parts)
							partsCopy.add(p);
					}
					*/
					//myAgent.msgIAmFull(parts, nestNum);
				}
			}
			
		
			
		}
	}
	/**
	 * Elias added this
	 */
	public ArrayList<GUIPart> doGetGuiParts()
	{
		return parts;
	}
	public boolean doCheckFull()
	{
		if(changeNormative)
		{
		for (int i = 0; i < mySize; i++)
		{
			if (!isFull[i])
			{
				return false;
			}
		}
		}
		else
		{
			for (int i = 0; i < mySize-3; i++)
			{
				if (!isFull[i])
				{
					return false;
				}
			}
		}
		return true;
	}

	//THIS REMOVES A PART FROM THE NEST
	@Override
	public GUIPart getPart(int position) {
		// TODO Auto-generated method stub
		//PARTS ROBOT
		
		/**
		 * ELIAS ADDED THIS. If you were full, and this method gets called, you will no longer be full
		 * This statement just informs the agent of this state change.
		 */
		
		if (state == NestState.FULL)
		{
			myAgent.msgIAmNoLongerFull();
		}
		
		if(position > mySize)
		{
			return null;
		}
		else
		{
			state = NestState.GIVING_PART;
			GUIPart partRemove = parts.get(position);
			
			
			//parts.remove(position);
			parts.set(position, null);
			if(changeNormative)
			{
			for(int i = isFull.length-1; i>=0;i--)
			{
				if(isFull[i])
				{
					isFull[i] = false;
					break;
				}
			}
			}
			else
			{
				for(int i = isFull.length-4; i>=0;i--)
				{
					if(isFull[i])
					{
						isFull[i] = false;
						break;
					}
				}
			}
			
			/**
			 * ELIAS ADDED THIS. If you were full, and this method gets called, you will no longer be full
			 * This statement just informs the agent of this state change.
			 */
			
			boolean allEmpty = true;
			for (int i = 0; i < mySize; i++)
			{
				if (isFull[i])
				{
					allEmpty = false;
				}
			}
			if (allEmpty)
			{
				fullState = false;
				parts.clear();
				System.out.println("GUINEST IS TELLING MYAGENT THAT IT IS EMPTY YO");
				myAgent.msgIAmEmpty();
				state = NestState.EMPTY;
			}
			
			
			return partRemove;
		}
	}

	@Override
	public void gotPart(GUIPart part) {
		// TODO Auto-generated method stub
		//LANE
	}

	//THIS TELLS THE AGENT THAT IT IS NOT FULL
	@Override
	public void areYouReadyToReceive() {
		// TODO Auto-generated method stub
		if(state != NestState.FULL)
		{
			myAgent.msgReadyToReceive();
		}
		else
		{
			ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
			synchronized(parts)
			{
				for (GUIPart p : parts)
					partsCopy.add(p);
			}
			myAgent.msgIAmFull(parts, nestNum);
		}
	}

	@Override
	public void setNestAgent(Nest nestAgent) {
		// TODO Auto-generated method stub
		this.myAgent = nestAgent;
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	//THIS IS IMPORTANT -- THIS DRAWS THE NEST AND UPDATES ITS PARTS
	@Override
	public void draw(Graphics g)
	{
		
		if(state == NestState.FULL)
		{
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		}
		if(state == NestState.PARTIALLYFILLED)
		{
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		}
		if(state == NestState.GIVING_PART)
		{
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		}
		else if(state == NestState.EMPTY)
		{
			g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		}
		else if(state == NestState.ADDING_PART)
		{
			GUIPart newestPart = null;
			
			if(stateChange == false)
			{
					int i = -1;
					for(int k = parts.size()-1; k >= 0; k--)
					{
						if(parts.get(k) != null)
						{
							newestPart = parts.get(k);
							i = k;
							break;
						}
					}
					g.drawImage(image.getImage(), xPos, yPos, width, height, null);
					//parts.get(i).draw(g);
					if(i != -1)
					{
					if(parts.get(i).getX() > nextFreeCol)
					{
						parts.get(i).moveX(-1);
					}
					if(parts.get(i).getY() > nextFreeRow)
					{
						parts.get(i).moveY(1);
					}
					if(parts.get(i).getY() < nextFreeRow)
					{
						parts.get(i).moveY(-1);
					}
					if(parts.get(i).getX() == nextFreeCol && parts.get(i).getY() == nextFreeRow)
					{
						stateChange = true;
						myAgent.msgPartAdded(newestPart);
						if(changeNormative)
						{
						if(isFull[11])
						{
							ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
							synchronized(parts)
							{
								for (GUIPart p : parts)
									partsCopy.add(p);
							}
							myAgent.msgIAmFull(partsCopy, nestNum);
						}
						}
						else
						{
							if(isFull[8])
							{
								ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
								synchronized(parts)
								{
									for (GUIPart p : parts)
										partsCopy.add(p);
								}
								myAgent.msgIAmFull(partsCopy, nestNum);
								
								
							}
						}
						
					}
					}
			}
			if(stateChange == true)
			{
				if(changeNormative)
				{
				if(isFull[11] == true)
				{
					state = NestState.FULL;
					/*
					ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
					synchronized(parts)
					{
						for (GUIPart p : parts)
							partsCopy.add(p);
					}
					 */
					//myAgent.msgIAmFull(parts, nestNum);
				}
				else
				{
					state = NestState.PARTIALLYFILLED;
				}
				}
				else
				{
					if(isFull[8] == true)
					{
						state = NestState.FULL;
						/*
						ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
						synchronized(parts)
						{
							for (GUIPart p : parts)
								partsCopy.add(p);
						}
						 */
						//myAgent.msgIAmFull(parts, nestNum);
					}
					else
					{
						state = NestState.PARTIALLYFILLED;
					}
				}
			}

			
		}
	}
	
	public void changeNormative()
	{
		changeNormative = false;
	}
	
	public void changeNormativeNormal()
	{
		changeNormative = true;
		state = NestState.PARTIALLYFILLED;
		fullState = false;
	}
	
	/*
	 * 
	 * EXTRANEOUS METHODS AND GETTERS AND SETTERS
	 */
	@Override
	public void update()
	{
		nestPlaceholder.setFrame(this.xPos, this.yPos, this.width, this.height);		
	}
	
	public void zoom(int amount) {
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
	}

	@Override
	public void goIdle()
	{
		// TODO Auto-generated method stub
		if(stateChange == true)
		{
		state = NestState.IDLE;
		}
	}

	@Override
	public void dumpParts()
	{
		// you forgot this Arjun.  --Duke
		fullState = false;
		
		// TODO Auto-generated method stub
		//if(stateChange == true)
		//{
		state = NestState.DUMPING_PARTS;
		for(GUIPart part : parts)
		{
			if(part != null)
			{
				part.setX(-30);
				part.setY(-30);
			}
		}
		parts.clear();
		for(int i = 0; i<12; i++)
		{
			parts.add(null);
		}
		for(int i =0; i< isFull.length; i++)
		{
			isFull[i] = false;
		}
		nextFreeCol = xPos+border;
		nextFreeRow = yPos+border;
		state = NestState.EMPTY;
		
		//}
		state = NestState.EMPTY;
		myAgent.msgIAmEmpty();
	}

	
	public void setNestNum(int num)
	{
		nestNum = num;
		
	}
	
	public int getNestNum()
	{
		return nestNum;
	}
	
	public NestState getState()
	{
		return state;
	}

	/*
	 * DO THIS ARJUN!!
	 */
	//@Override
	//public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	//}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		xPos = x;
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		yPos = y;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return xPos;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return yPos;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	
	//THIS LETS AGENTS FILL IN THE NEST TO A CERTAIN PERCENTAGE
	public void fillNest(ArrayList<GUIPart> partsInc)
	{
		parts = partsInc;
		for(int i =0; i< isFull.length; i++)
		{
			isFull[i] = false;
		}
		for(int i =0; i< parts.size(); i++)
		{
			isFull[i] = true;
		}
		if(isFull[11] == true)
		{
			state = NestState.FULL;
			ArrayList<GUIPart> partsCopy = new ArrayList<GUIPart>();
			synchronized(parts)
			{
				for (GUIPart p : parts)
					partsCopy.add(p);
			}
			myAgent.msgIAmFull(parts, nestNum);
		}
		else if(isFull[0] == true)
		{
			state = NestState.PARTIALLYFILLED;
		}
		else if(isFull[0] == false)
		{
			state = NestState.EMPTY;
		}
		for(int i =0; i<parts.size();i++)
		{
				switch (i)
				{
					case 0:
						parts.get(i).setX(xPos+border);
						parts.get(i).setY(yPos+border);
						break;
					case 3:
						parts.get(i).setX(xPos+plusCol+border);
						parts.get(i).setY(yPos+border);
						break;
					case 6:
						parts.get(i).setX(xPos+plusCol*2+border);
						parts.get(i).setY(yPos+border);
						break;
					case 9:
						parts.get(i).setX(xPos+plusCol*3+border);
						parts.get(i).setY(yPos+border);
						break;
					case 1:
						parts.get(i).setX(xPos+border);
						parts.get(i).setY(yPos+plusRow+border);
						break;
					case 4:
						parts.get(i).setX(xPos+plusCol+border);
						parts.get(i).setY(yPos+plusRow+border);
						break;
					case 7:
						parts.get(i).setX(xPos+plusCol*2+border);
						parts.get(i).setY(yPos+plusRow+border);
						break;
					case 10:
						parts.get(i).setX(xPos+plusCol*3+border);
						parts.get(i).setY(yPos+plusRow+border);
						break;
					case 2:
						parts.get(i).setX(xPos+border);
						parts.get(i).setY(yPos+plusRow*2+border);
						break;
					case 5:
						parts.get(i).setX(xPos+plusCol+border);
						parts.get(i).setY(yPos+plusRow*2+border);
						break;
					case 8:
						parts.get(i).setX(xPos+plusCol*2+border);
						parts.get(i).setY(yPos+plusRow*2+border);
						break;
					case 11:
						parts.get(i).setX(xPos+plusCol*3+border);
						parts.get(i).setY(yPos+plusRow*2+border);
						break;
				}
		}
	}

}
