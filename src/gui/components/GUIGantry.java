package gui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import factory.interfaces.Gantry;
import gui.interfaces.GUIGantryInterface;

/**
 * 
 * @author Michael Gendotti, Duke Yin
 *
 */
@SuppressWarnings("serial")
public class GUIGantry extends GUIComponent implements GUIGantryInterface{

	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private ImageIcon image;
	private ImageIcon imageBroken;
	private ImageIcon imageLClaw;
	private ImageIcon imageRClaw;
	private ArrayList<GUIPart> partList;
	private boolean movingBin;
	private boolean purgingBin;
	private boolean newBin;
	private boolean hasLoadedBin;
	private boolean hasFilledFeeder;
	private GUIBin loadedBin;
	private GUIFeeder destFeeder;
	private int binX;
	private int binY;
	private int clawWidth;
	private int offScreenY;
	private int[][] binLocations;
	private boolean[] binLocationsFull;
	private int nextLocation;
	private boolean broken;
	private int brokenTime;

	private final ImageIcon stdImage = new ImageIcon("images/ab_gantry_robot_body.png");
	private final ImageIcon stdBrokenImage = new ImageIcon("images/ab_gantry_robot_body_broken.png");
	private final ImageIcon stdImageLClaw = new ImageIcon("images/ab_gantry_robot_clawL.png");
	private final ImageIcon stdImageRClaw = new ImageIcon("images/ab_gantry_robot_clawR.png");
	private final int stdWidth = 50;
	private final int stdClawWidth=25;
	private final int stdHeight = 50;
	private final int stdX = 90;
	private final int stdY = 90;
	private final int stdOffScreenY=800;
	String name;
	Gantry gantryAgent;
	
	/*
	 * These are the constructors that can take in various inputs to set initial values
	 */
	public GUIGantry(String name) {
		this.name = name;
		xPos=stdX;
		yPos=stdY;
		width=stdWidth;
		height=stdHeight;
		angle=0;
		image=stdImage;
		imageBroken=stdBrokenImage;
		imageLClaw=stdImageLClaw;
		imageRClaw=stdImageRClaw;
		movingBin=false;
		purgingBin=false;
		newBin=false;
		hasLoadedBin=false;
		hasFilledFeeder=false;
		loadedBin=null;
		destFeeder=null;
		binX=-1;
		binY=-1;
		clawWidth=stdClawWidth;
		offScreenY=stdOffScreenY;
		binLocations=new int[9][2];
		binLocationsFull= new boolean[9];
		broken=false;
		for(int i=0;i<9;i++)
		{
			binLocations[i][0]=i*50;
			binLocations[i][1]=150;
			binLocationsFull[i]=false;
		}
	}
	
	public GUIGantry(String name, int x, int y) {
		this.name = name;
		xPos=x;
		yPos=y;
		width=stdWidth;
		height=stdHeight;
		angle=0;
		image=stdImage;
		imageBroken=stdBrokenImage;
		imageLClaw=stdImageLClaw;
		imageRClaw=stdImageRClaw;
		movingBin=false;
		purgingBin=false;
		newBin=false;
		hasLoadedBin=false;
		hasFilledFeeder=false;
		loadedBin=null;
		destFeeder=null;
		binX=-1;
		binY=-1;
		clawWidth=stdClawWidth;
		offScreenY=stdOffScreenY;
		binLocations=new int[9][2];
		binLocationsFull= new boolean[9];
		broken=false;
		for(int i=0;i<9;i++)
		{
			binLocations[i][0]=i*50;
			binLocations[i][1]=150;
			binLocationsFull[i]=false;
		}
	}
	
	//This method delivers a bin to a feeder
	@Override
	public void msgMoveBinToFeeder(GUIBin bin, GUIFeeder feeder) {
		System.out.println(name + ": Received message moveBinToFeeder from Gantry agent");
		
		//Do Animation here
		movingBin=true;
		loadedBin=bin;
		destFeeder=feeder;
		binX=loadedBin.getX()-25;
		binY=loadedBin.getY();
		hasLoadedBin=false;
		//Send this message once this animation is done
		//gantryAgent.msgGUIDelieveredBinToFeeder(bin);
	}

	//This method tells the gantry to take the bin offscreen
	@Override
	public void msgPurgeBin(GUIBin bin) {
		System.out.println(name + ": Received message PurgeBin from Gantry agent");

		//Do Animation here
		purgingBin=true;
		loadedBin=bin;
		binX=loadedBin.getX()-25;
		binY=loadedBin.getY();
		hasLoadedBin=false;
		//Send this message once this animation is done
		//gantryAgent.msgGUIDonePurging(bin);
	}

	//This method tells the gantry to pick up a bin from offscreen and place it onscreen
	@Override
	public void msgGetNewBin(GUIBin bin) {
		System.out.println(name + ": Received message GetNewBin type" + bin.getType() + "from Gantry agent");		
		
		//Do Animation here
		newBin=true;
		loadedBin=bin;
		hasLoadedBin=false;
		binX=bin.getX();
		binY=bin.getY();
		for(int i=0;i<9;i++)
		{
			if(!binLocationsFull[i])
			{
				nextLocation=i;
				break;
			}
		}
		//Send this message once this animation is done
		//gantryAgent.msgGUIDoneGettingNewBin(bin);		
	}

	@Override
	public void setGantryAgent(Gantry gantryAgent) {
		this.gantryAgent = gantryAgent;
	}

	@Override
	public String getName() {
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2=(Graphics2D) g;
		double CenterX = (xPos + width);
		double CenterY = (yPos + (height / 2));
		if(broken)
		{
			g2.rotate(Math.toRadians(angle), CenterX, CenterY);
			g2.drawImage(imageBroken.getImage(), xPos, yPos, width, height, null);
			g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			brokenTime+=50;
			if(brokenTime==25000)
			{
				broken=false;
				//gantryAgent.msgGUIDoneSettingBinOnFire(loadedBin);
			}
		}
		//This animates the bin being delivered to a feeder
		else if(movingBin)
		{
			g2.rotate(Math.toRadians(angle), CenterX, CenterY);
			g2.drawImage(image.getImage(), xPos, yPos, width, height, null);
			g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			//This makes the gantry move to go pick up a bin
			if(!hasLoadedBin)
			{
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageLClaw.getImage(), xPos-clawWidth, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageRClaw.getImage(), xPos+width, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				
				if (movedToYX(binX+loadedBin.getWidth()/2, binY))
				{
					hasLoadedBin=true;
					for(int i=0;i<binLocations.length;i++)
					{
						if(binLocations[i][0]==xPos && binLocations[i][1]==yPos)
						{
							binLocationsFull[i]=false;
							break;
						}
					}
				}
			}
			//This makes the gantry delivered the picked up bin to the feeder
			else if(!hasFilledFeeder)
			{
				if (movedWithBinToXY(destFeeder.getX()+destFeeder.getWidth()*2/5, destFeeder.getY()+destFeeder.getHeight()/6))
				{
					//hasFilledFeeder=true;
					movingBin=false;
					hasFilledFeeder=false;
					hasLoadedBin=false;
					gantryAgent.msgGUIDelieveredBinToFeeder(loadedBin);
				}
			}
		}
		//This purges the bin by taking it offscreen
		else if(purgingBin)
		{
			g2.rotate(Math.toRadians(angle), CenterX, CenterY);
			g2.drawImage(image.getImage(), xPos, yPos, width, height, null);
			g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			//This moves the gantry to pick up the bin
			if(!hasLoadedBin)
			{
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageLClaw.getImage(), xPos-clawWidth, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageRClaw.getImage(), xPos+width, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				
				if (movedToXY(binX+loadedBin.getWidth()/2, binY))
				{
					hasLoadedBin=true;
				}
			}
			//This makes the gantry move the bin offscreen
			else
			{
				if (movedWithBinToY(offScreenY))
				{
					purgingBin=false;
					hasLoadedBin=false;
					gantryAgent.msgGUIDonePurging(loadedBin);
				}
			}
		}
		//This makes the gantry go pick up a bin and move it onto the screen
		else if(newBin)
		{
			g2.rotate(Math.toRadians(angle), CenterX, CenterY);
			g2.drawImage(image.getImage(), xPos, yPos, width, height, null);
			g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			//This makes the gantry go pick up the bin
			if(!hasLoadedBin)
			{
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageLClaw.getImage(), xPos-clawWidth, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				g2.rotate(Math.toRadians(angle), CenterX, CenterY);
				g2.drawImage(imageRClaw.getImage(), xPos+width, yPos, clawWidth, height, null);
				g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
				
				if (movedToY(offScreenY))
				{
					hasLoadedBin=true;
					loadedBin.setX(xPos);
					loadedBin.setY(yPos);
				}
			}
			//This moves the bin to the location it needs to be
			else
			{
				if (movedWithBinToYX(binLocations[nextLocation][0], binLocations[nextLocation][1]))
				{
					binLocationsFull[nextLocation]=true;
					newBin=false;
					hasLoadedBin=false;
					gantryAgent.msgGUIDoneGettingNewBin(loadedBin);
				}
			}
		}
		else 
		{
			g2.rotate(Math.toRadians(angle), CenterX, CenterY);
			g2.drawImage(image.getImage(), xPos, yPos, width, height, null);
			g2.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			if(movedToY(offScreenY)){};
		}
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		xPos=x;
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		yPos=y;
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
	
	public void setBinLocations(int inputBinLocations[][])
	{
		binLocations=inputBinLocations;
		for(int i=0;i<9;i++)
		{
			binLocationsFull[i]=true;
		}
	}
	
	
	/*
	 *******************
	 * MOVEMENT HELPERS
	 */
	
	/**
	 * how many pixels each movement call 
	 */

	private int movementAmount = 15;

	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedToXY(int x, int y)
	{
		// it's at its destination
		if (movedToX(x) && movedToY(y))
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	private boolean movedToYX(int x, int y)
	{
		// it's at its destination
		if (movedToY(y) && movedToX(x))
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedToY(int y)
	{
		if(yPos < y)
		{
			if (yPos <= y-movementAmount)
				yPos += movementAmount;
			else
				yPos += 1;
		}
		else if(yPos > y)
		{
			if (yPos >= y+movementAmount)
				yPos -= movementAmount;
			else
				yPos -= 1;
		}
		else // it's at its destination
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedToX(int x)
	{
		if(xPos < x)
		{
			if (xPos <= x - movementAmount)
				xPos += movementAmount;
			else
				xPos += 1;
		}
		else if(xPos > x)
		{
			if (xPos >= x + movementAmount)
				xPos -= movementAmount;
			else
				xPos -= 1;
		}
		else // it's at its destination
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	/*
	 ******************************
	 * LOADED BIN MOVEMENT HELPERS
	 */
	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedWithBinToXY(int x, int y)
	{
		// it's at its destination
		if (movedWithBinToX(x) && movedWithBinToY(y))
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	private boolean movedWithBinToYX(int x, int y)
	{
		// it's at its destination
		if (movedWithBinToY(y) && movedWithBinToX(x))
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedWithBinToY(int y)
	{
		if(yPos < y)
		{
			if (yPos <= y-movementAmount)
			{
				yPos += movementAmount;
				loadedBin.moveY(-movementAmount);
			}
			else
			{
				yPos += 1;
				loadedBin.moveY(-1);
			}
		}
		else if(yPos > y)
		{
			if (yPos >= y+movementAmount)
			{
				yPos -= movementAmount;
				loadedBin.moveY(movementAmount);
			}
			else
			{
				yPos -= 1;
				loadedBin.moveY(1);
			}
		}
		else // it's at its destination
			return true;
		
		// if it's not at its destination yet,
		return false;
	}
	
	/**
	 * NOT for when it's moving a bin!
	 * 
	 * Returns TRUE once movement is finished 
	 */
	private boolean movedWithBinToX(int x)
	{
		if(xPos < x)
		{
			if (xPos <= x - movementAmount)
			{
				xPos += movementAmount;
				loadedBin.moveX(movementAmount);
			}
			else
			{
				xPos += 1;
				loadedBin.moveX(1);
			}
		}
		else if(xPos > x)
		{
			if (xPos >= x + movementAmount)
			{
				xPos -= movementAmount;
				loadedBin.moveX(-movementAmount);
			}
			else
			{
				xPos -= 1;
				loadedBin.moveX(-1);
			}
		}
		else // it's at its destination
			return true;
		
		// if it's not at its destination yet,
		return false;
	}

	@Override
	public void msgBreakGantry() {
		System.out.println("Received msgSetBinOnFire from gantry");
		
		//Do Animation Here
		broken=true;
		brokenTime=0;

		//Send this after animation is done
		//gantryAgent.msgGUIDoneSettingBinOnFire(bin);
		
	}
}
