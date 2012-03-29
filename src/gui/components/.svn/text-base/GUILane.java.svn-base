
package gui.components;

import java.io.Serializable;
import factory.DiverterAgent;
import factory.LaneAgent;
import factory.interfaces.Lane;
//import gui.components.GUINest.State;
import gui.interfaces.GUILaneInterface;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Graphics;
import java.util.ArrayList;
import shared.enums.*;

/**
 * 
 * @author Arjun
 *
 */
public class GUILane extends GUIComponent implements GUILaneInterface, Serializable
{
	//PRIVATE VARIABLES. THESE ARE NEEDED FOR ALL THE ANIMATIONS AND STATES OF THE LANE
	private Rectangle2D.Double lanePlaceholder;
	public enum State {EMPTY, PARTIALLYFILLED, FULL, BAD, IDLE, LANE_MOVING, DUMPING_PARTS, GIVING_PART}
	private State state;
	private ArrayList<GUIPart> parts;
	private ArrayList<MyPart> partsLane;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int maxSize;
	private String name;
	private ImageIcon imageL;
	private ImageIcon imageR;
	private ImageIcon imageM;
	private final int stdWidth = 50;
	private final int stdHeight = 60;
	private final ImageIcon stdImageL = new ImageIcon("images/ab_lane_endL.png");
	private final ImageIcon stdImageR = new ImageIcon("images/ab_lane_endR.png");
	private final ImageIcon stdImageM = new ImageIcon("images/ab_lane_middle.png");
	
	private Lane myAgent;
	private int endX;
	private int endY;
	private GUINest nest;
	private int fullNum;
	private int speed;
	private int shakeCounter;
	private int shakeY;
	
	private Rectangle2D.Double[] laneLines;
	
	private final int almostFull = 7;
	private final int almostEmpty = 2;
	private boolean toldAgentItsFull;
	
	//THIS IS AN INNERCLASS THAT IS USED FOR THE AGENT SIDE LANE
	private class MyPart
	{
		GUIPart part;
		boolean toldAgentItsDone = false;
		
		public MyPart(GUIPart p)
		{
			part = p;
		}
		
	};
	
	//CONSTRUCTOR FOR THE GUILANE
	public GUILane(int x, int y, int size, GUINest nest)
	{
		parts = new ArrayList<GUIPart>();
		partsLane = new ArrayList<MyPart>();
		width = stdWidth;
		height = stdHeight;
		xPos = x;
		yPos = y;
		endX = x;
		endY = y+(height/3);
		maxSize = size;
		name = "lane";
		imageL = stdImageL;
		imageR = stdImageR;
		imageM = stdImageM;
		lanePlaceholder	= new Rectangle2D.Double(xPos, yPos, width, height);
		state = State.EMPTY;
		this.nest = nest;
		speed = 1;
		toldAgentItsFull = false;
		shakeCounter = 0;
		shakeY= 0;
		
		laneLines = new Rectangle2D.Double[10];
		for(int i = 0; i < laneLines.length; i++)
		{
			laneLines[i] = new Rectangle2D.Double(xPos + i*25, yPos+8, 1, height-16);
		}
	}

	//THIS ADDS A PART TO THE LANE AND UPDATES AGENT INFO
	@Override
	public void msgDoAddPart(GUIPart guipart)
	{
		state = State.LANE_MOVING;
		parts.add(guipart);
		if(parts.size() >=  almostFull && toldAgentItsFull == false)
		{
			myAgent.msgGuiLaneAlmostFull();
			toldAgentItsFull = true;
		}
		if(parts.size() <   almostFull && toldAgentItsFull == true)
		{
			myAgent.msgGuiLaneNoLongerFull();
			toldAgentItsFull = false;
		}
		
		MyPart partAdd = new MyPart(guipart);
		partsLane.add(partAdd);
		
	}

	/**
	 * THIS INCREASEST THE SPEED AT WHICH IT IS DRAWN
	 */
	@Override
	public void msgDoIncreaseAmplitude()
	{
		// TODO Auto-generated method stub
		speed += 1;
	}
/*
 * EXTRA
 */
	//THIS SETS THE AGENT
	public void setLaneAgent(Lane laneAgent)
	{
		this.myAgent = laneAgent;
	}

	@Override
	public void givePart(GUIPart part, GUIComponent giver) {
		// TODO Auto-generated method stub
		
		// moving this to the message from LaneAgent
		// --Duke
	}

	@Override
	public GUIPart getPart(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gotPart(GUIPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	/**
	 * THIS IS THE IMPORTANT PART-- DRAWS ALL THE STATES OF LANE AND UPDATES ITS PARTS
	 */
	@Override
	public void draw(Graphics g)
	{
		if(state == State.FULL)
		{
			
		}
		if(state == State.PARTIALLYFILLED)
		{
			g.drawImage(imageL.getImage(), xPos, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*2, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*3, yPos, width, height, null);
			g.drawImage(imageR.getImage(), xPos+stdWidth*4, yPos, width, height,  null);
			for(int i = 0; i < laneLines.length; i++)
			{
				Graphics2D g2 = (Graphics2D) g;
//				g2.setColor(Color.BLACK);
				g2.setColor(new Color(20,124,185));
				g2.draw(laneLines[i]);
			}
			
		}
		if(state == State.EMPTY)
		{
			g.drawImage(imageL.getImage(), xPos, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*2, yPos, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*3, yPos, width, height, null);
			g.drawImage(imageR.getImage(), xPos+stdWidth*4, yPos, width, height,  null);
			for(int i = 0; i < laneLines.length; i++)
			{
				Graphics2D g2 = (Graphics2D) g;
//				g2.setColor(Color.BLACK);				
				g2.setColor(new Color(20,124,185));
				g2.draw(laneLines[i]);
			}
		}
		if(state == State.LANE_MOVING)
		{
			g.drawImage(imageL.getImage(), xPos, yPos+shakeY, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth, yPos+shakeY, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*2, yPos+shakeY, width, height, null);
			g.drawImage(imageM.getImage(), xPos+stdWidth*3, yPos+shakeY, width, height, null);
			g.drawImage(imageR.getImage(), xPos+stdWidth*4, yPos+shakeY, width, height,  null);
			for(int i = 0; i < laneLines.length; i++)
			{
				Graphics2D g2 = (Graphics2D) g;
//				g2.setColor(Color.BLACK);
				g2.setColor(new Color(20,124,185));
				g2.draw(laneLines[i]);
			}
			boolean movedLaneLines = false;
			//boolean shakeLane = false;
			for(int i=0; i<parts.size(); i++)
			{
				int placeX = endX + i*18;
				if(parts.get(i).getX() > placeX)
				{
					if (parts.get(i).getX() - speed > placeX)
						parts.get(i).moveX(-speed);
					else
					{
						//parts.get(i).moveX(placeX - parts.get(i).getX());
						parts.get(i).moveX(-1);
					}
					
					if(!movedLaneLines)
					{
						for(int k = 0; k < laneLines.length; k++)
						{
							Double x = laneLines[k].getX();
							if(x > xPos)
							{
								x--;
							}
							else
							{
								x = (double) xPos+250;
							}
							laneLines[k].setFrame(x, yPos+8, 1, height-16);
						}
						movedLaneLines = true;
					}
					/*if(!shakeLane)
					{
						shakeCounter++;
						if(shakeCounter==10)
						{
							shakeY=-1;
						}
						if(shakeCounter==12)
						{
							shakeY=0;
						}
						if(shakeCounter>=14)
						{
							shakeY=1;
							shakeCounter = 0;
						}
						shakeLane = true;
					}*/
					
				}
				
				if(parts.get(i).getY() > endY)
				{
//					if (parts.get(i).getY() + speed > endY)
//						parts.get(i).moveY(speed);
//					else
//					{
						//parts.get(i).moveX(endY - parts.get(i).getY());
						parts.get(i).moveX(1);
					//}
				}
				if(parts.get(i).getY() < endY)
				{
//					if (parts.get(i).getY() - speed < endY)
//						parts.get(i).moveY(-speed);
//					else
//					{
						//parts.get(i).moveY(endY + parts.get(i).getY());
						parts.get(i).moveY(-1);
					//}
				}
				if(parts.get(i).getX() == endX && parts.get(i).getY() == endY)
				{
					//state = State.PARTIALLYFILLED;
					//THIS WILL CHANGE BASED ON AGENT INTERACTIONS
					
					
					if(nest.getState() != NestState.ADDING_PART && nest.getState() != NestState.FULL && nest.fullState == false)
					{
						if(partsLane.get(i).toldAgentItsDone == false)
						{
							myAgent.msgGuiDoneMovingPart(parts.get(i));
							partsLane.get(i).toldAgentItsDone = true;
						}
						nest.givePart(parts.get(i), this);
						
							parts.remove(i);
							partsLane.remove(i);
					}
				}
			}
		}
	}
	int counter;
/*
 * THESE ARE EXTRANEOUS GETTERS AND SETTERS AND OTHER FUNCTIONS
 */
	public void zoom(int amount) {
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		lanePlaceholder.setFrame(this.xPos, this.yPos, this.width, this.height);	
	}


	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		xPos = x;
		endX = x;
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		yPos = y;
		endY = y+(height/3);
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
		return width*5;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}


}
