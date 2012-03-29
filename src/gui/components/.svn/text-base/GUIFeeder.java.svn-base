package gui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import shared.Bin;

import factory.interfaces.Feeder;
import gui.interfaces.GUIFeederInterface;

/**
 * 
 * @author Mike
 *
 */
@SuppressWarnings("serial")
public class GUIFeeder extends GUIComponent implements GUIFeederInterface{
	
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private ImageIcon image;
	private ImageIcon image2;
	private ImageIcon purger;
	private boolean outputting;
	private GUIBin setBin;
	private Rectangle output;
	private int outlength;
	private int binH;
	private int binY;
	private int binX;
	private boolean partReady;
	private GUIPart setPart;
	private boolean outputBin;
	private int purgerHeight;
	private int purgerWidth;
	private boolean movingPurger= false;
	
	private final ImageIcon stdImage = new ImageIcon("images/ab_feeder.png");
	private final ImageIcon stdImage2 = new ImageIcon("images/ab_feeder_top.png");
	private final ImageIcon stdPurger = new ImageIcon("images/ab_purger.png");
	private final int stdWidth = 125;	// (JY) Adjusted the feeder graphic width to account for image change
	private final int stdHeight = 120;
	private final int stdPurgerWidth = 50;
	private final int stdPurgerHeight = 50;
	private final int stdX = 40;
	private final int stdY = 40;
	
	String name;
	Feeder feederAgent;
	
	public GUIFeeder(String name) {
		this.name = name;
		xPos=stdX;
		yPos=stdY;
		width=stdWidth;
		height=stdHeight;
		angle=0;
		image=stdImage;
		image2=stdImage2;
		purger=stdPurger;
		purgerWidth=stdPurgerWidth;
		purgerHeight=stdPurgerHeight;
		outputting=false;
		partReady=false;
		outputBin=false;
	}

	public GUIFeeder(String name,int x,int y) {
		this.name = name;
		xPos=x;
		yPos=y;
		width=stdWidth;
		height=stdHeight;
		angle=0;
		image=stdImage;
		image2=stdImage2;
		purger=stdPurger;
		purgerWidth=stdPurgerWidth;
		purgerHeight=stdPurgerHeight;
		outputting=false;
		partReady=false;
		outputBin=false;
	}
	//This accepts a bin from the gantry
	@Override
	public void msgAcceptBin(GUIBin bin) {
		System.out.println(name + ": Received msgAcceptBin from Feeder agent");
		setBin=bin;
		setBin.setX(xPos+width-bin.getWidth());
		//setBin.setY(yPos+30);
		outputBin=true;
		//Do Animation here
		//feederAgent.msgDonePlacingBin(bin);
	}
	
	//This delivers a part to the diverter
	@Override
	public void msgShuttlePart(GUIPart part) {
		System.out.println(name + ": Received message ShuttlePart from Feeder agent");
		
		//Do Animation here
		part.setX(xPos);
		part.setY(height/2-part.getHeight()/2+yPos);
		partReady=true;
		setPart=part;
		//Send this message once this animation is done
		feederAgent.msgGUIPartDelivered(part);
	}

	//This pours out the input parts into the input bin
	@SuppressWarnings("rawtypes")
	@Override
	public void msgEmptyPartsForBin(GUIBin bin, List parts) {
		System.out.println(name + ": Received message EmptyPartsForBin from Feeder agent");
		
		//Do Animation here
		Iterator inter=parts.iterator();
		while(inter.hasNext())
		{
			bin.addPart((GUIPart)inter.next());
		}
		binX=bin.getX();
		binY=bin.getY();
		binH=bin.getHeight();
		outlength=1;
		movingPurger=true;
		setBin=bin;
		
		output=new Rectangle(binX,binY,outlength,binH);
		outputting=true;
		//sends this message once the animation is done
		//feederAgent.msgGUIRemovedPartsFromFeeder();
	}

	@Override
	public void setFeederAgent(Feeder feederAgent) {
		this.feederAgent = feederAgent;
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
	
	public void draw(Graphics2D g)
	{
		//This draws the feeder
		double CenterX = (xPos + (width / 2));
		double CenterY = (yPos + (height / 2));
		//This animates the parts being poured into the bins
		
		//This animates the bin moving out of the machine
		if(outputBin)
		{
			if(setBin.getX()<xPos+width)
			{
				setBin.moveX(1);
			}
			else
			{
				feederAgent.msgDonePlacingBin(setBin);
				outputBin=false;
			}
		}
		else if(outputting)
		{
			if(movingPurger)
			{
				outlength++;
			}
			else
			{
				outlength--;
			}
			if(outlength>=purgerWidth)
			{
				movingPurger=false;
			}
			if(outlength>0)
			{
				g.rotate(Math.toRadians(angle), CenterX, CenterY);
				g.drawImage(purger.getImage(), xPos+width+outlength-purgerWidth, setBin.getY(), purgerWidth, purgerHeight, null);
				g.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
			}
			else
			{
				outputting=false;
				feederAgent.msgGUIRemovedPartsFromFeeder();
			}
		}
		g.rotate(Math.toRadians(angle), CenterX, CenterY);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		g.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
		
	}
	
	public void zoom(int amount) {
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		draw((Graphics2D) g);
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}

	public void setX(int x)
	{
		xPos=x;
	}
	
	public void setY(int y)
	{
		yPos=y;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}

}
