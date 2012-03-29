package gui.components;

import shared.enums.DiverterDirection;
import factory.interfaces.Diverter;
import gui.interfaces.GUIDiverterInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * 
 * @author Mike
 *
 */
@SuppressWarnings("serial")
public class GUIDiverter extends GUIComponent implements GUIDiverterInterface
{
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int angle;
	private DiverterDirection currentDirection;
	private ImageIcon image;
	private ArrayList<GUIPart> partList;
	private GUILane laneOne;
	private GUILane laneTwo;

	private final ImageIcon stdImage = new ImageIcon("images/ab_diverter.png");
	private final int stdWidth = 60;
	private final int stdHeight = 50;
	private final int stdX = 90;
	private final int stdY = 90;
	private final int HIGH = 35;
	private final int LOW = -35;
	private GUIPart loadedPart;
	private boolean loadingPart;
	private boolean partIsLoaded;
	private boolean deliveringPart;
	private boolean changingOrientation;
	private DiverterDirection nextDirection;
	
	String name;
	Diverter diverterAgent;
	
	/*
	 * These are the various constructors that allow many different values to be changed by changing
	 * the input
	 */
	public GUIDiverter(String name)
	{
		this.name=name;
		xPos=stdX;
		yPos=stdY;
		width=stdWidth;
		height=stdHeight;
		image=stdImage;
		currentDirection=DiverterDirection.TOP;
		partList= new ArrayList<GUIPart>();
		partIsLoaded=false;
		deliveringPart=false;
		changingOrientation=false;
		loadingPart=false;
		nextDirection=DiverterDirection.TOP;
		angle=HIGH;
		laneOne=null;
		laneTwo=null;
	}
	
	public GUIDiverter(String name,int x, int y)
	{
		this.name=name;
		xPos=x;
		yPos=y;
		width=stdWidth;
		height=stdHeight;
		image=stdImage;
		currentDirection=DiverterDirection.TOP;
		partList= new ArrayList<GUIPart>();
		partIsLoaded=false;
		deliveringPart=false;
		changingOrientation=false;
		loadingPart=false;
		nextDirection=DiverterDirection.TOP;
		angle=HIGH;
		laneOne=null;
		laneTwo=null;
	}

	public GUIDiverter()
	{
		this.name="GUIDiverter";
		xPos=stdX;
		yPos=stdY;
		width=stdWidth;
		height=stdHeight;
		image=stdImage;
		currentDirection=DiverterDirection.TOP;
		partList= new ArrayList<GUIPart>();
		partIsLoaded=false;
		deliveringPart=false;
		changingOrientation=false;
		loadingPart=false;
		nextDirection=DiverterDirection.TOP;
		angle=HIGH;
		laneOne=null;
		laneTwo=null;
	}
	
	public GUIDiverter(int x, int y, int inputWidth, int inputHeight)
	{
		name="GUIDiverter";
		xPos=x;
		yPos=y;
		width=inputWidth;
		height=inputHeight;
		image=stdImage;
		currentDirection=DiverterDirection.TOP;
		partList= new ArrayList<GUIPart>();
		partIsLoaded=false;
		deliveringPart=false;
		changingOrientation=false;
		loadingPart=false;
		nextDirection=DiverterDirection.TOP;
		angle=HIGH;
		laneOne=null;
		laneTwo=null;
	}
	
	//This method moves a part onto the diverter
	@Override
	public void msgDoAddPart(GUIPart guipart)
	{
		//System.out.println(name + ": Received message AddPart from Diverter agent");
		
		loadedPart=guipart;
		loadingPart=true;
		// TODO Auto-generated method stub
	}

	//This method moves a part from the diverter to the lane
	@Override
	public void msgDoMovePart(GUIPart guipart)
	{
		//System.out.println(name + ": Received message MovePart from Diverter agent");
		// TODO Auto-generated method stub
		loadedPart=guipart;
		deliveringPart=true;
		//partIsLoaded=false;
		// once part has moved to the end of the GUIDiverter, and is ready to go to the GUILane,
		//diverterAgent.msgGuiDoneMovingPart();
	}
	
	//This method changes the orientation for the diverter to whatever the input direction is
	@Override
	public void msgDoChangeOrientation(DiverterDirection direction)
	{
		//System.out.println(name + ": Received message ChangeOrientation from Diverter agent");
		// animate the diverter pointing to the new lane
		changingOrientation=true;
		nextDirection=direction;
		// once your animation is done
		//diverterAgent.msgGuiDoneChangingOrientation();
	}
/*
 * EXTRA
 */
	public void setDiverterAgent(Diverter diverterAgent)
	{
		this.diverterAgent = diverterAgent;
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
/*
	@Override
	public void getPart(GUIPart part)
	{
		// TODO Auto-generated method stub

	}*/

	@Override
	public void gotPart(GUIPart part)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics2D g)
	{
		//This draws the diverter
		double CenterX = (xPos + width);
		double CenterY = (yPos + (height / 2));
		g.rotate(Math.toRadians(angle), CenterX, CenterY);
		g.drawImage(image.getImage(), xPos, yPos, width, height, null);
		g.rotate(Math.toRadians(-1 * angle), CenterX, CenterY);
		
		//This animates the part moving onto the diverter from the feeder
		if(loadingPart)
		{
			//System.out.println("guiDiverter loadingPart");
			if(currentDirection==DiverterDirection.TOP && loadedPart.getY()>(yPos+height/2))
			{
				loadedPart.moveY(1);
			}
			else if(currentDirection==DiverterDirection.BOTTOM && loadedPart.getY()<(yPos+height/2))
			{
				loadedPart.moveY(-1);
			}
			else if(loadedPart.getX()>(xPos+width-loadedPart.getWidth()))
			{
				loadedPart.moveX(-1);
				loadedPart.draw(g);
			}
			else
			{
				loadingPart=false;
				partIsLoaded=true;
			}
		}
		//This animates the orientation changing
		else if(changingOrientation)
		{
			//System.out.println("guiDiverter changingOrientation");
			if (nextDirection == DiverterDirection.TOP)
			{
					if(angle<HIGH)
					{
						angle+=1;
						//if(partIsLoaded)
						if (loadedPart != null)
						{
							if(loadedPart.getY()<yPos-height)
							{
								loadedPart.moveY(-1);
							}
							if(loadedPart.getY()>yPos-height/2+20)
							{
								loadedPart.moveY(1);
							}
						}
					}
					else if(angle>=HIGH)
					{
						currentDirection=nextDirection;
						changingOrientation=false;
						diverterAgent.msgGuiDoneChangingOrientation();
					}
			}
			
			if (nextDirection == DiverterDirection.BOTTOM)
			{
					if(angle>LOW)
					{
						angle-=1;
						//if(partIsLoaded)
						if (loadedPart != null)
						{
							if(loadedPart.getY()<yPos+height-20)
							{
								loadedPart.moveY(-1);
							}
							//if(loadedPart.getY()>yPos+height/2)
							//{
							//	loadedPart.moveY(-1);
							//}
						}
					}
					else if(angle<=LOW)
					{
						currentDirection=nextDirection;
						changingOrientation=false;
						diverterAgent.msgGuiDoneChangingOrientation();
					}
			}
		}
		//This animates the part moving from the diverter to the lane
		else if(deliveringPart)
		{
			//System.out.println("guiDiverter deliveringPart");
			if(laneOne!=null && laneTwo!=null)
			{
				int laneOneXCheck=laneOne.getX()+laneOne.getWidth()-loadedPart.getWidth()/2;
				int laneTwoXCheck=laneTwo.getX()+laneTwo.getWidth()-loadedPart.getWidth()/2;
				int laneOneYCheck=laneOne.getY()+laneOne.getHeight()/2-loadedPart.getHeight()/2;
				int laneTwoYCheck=laneTwo.getY()+laneTwo.getHeight()/2-loadedPart.getHeight()/2;
				boolean xFinished=false;
				boolean yFinished=false;
			
				/*if(currentDirection == DiverterDirection.TOP && loadedPart.getX()>laneOneXCheck)
				{
					loadedPart.moveX(-1);
				} 
				else if(currentDirection == DiverterDirection.BOTTOM && loadedPart.getX()>laneTwoXCheck)
				{
					loadedPart.moveX(-1);
				}
				else
				{
					xFinished=true;
				}
			
				if(currentDirection == DiverterDirection.TOP && loadedPart.getY()>laneOneYCheck)
				{
					loadedPart.moveY(1);
				} 
				else if(currentDirection == DiverterDirection.BOTTOM && loadedPart.getY()<laneTwoYCheck)
				{
					loadedPart.moveY(-1);
				}
				else
				{
					yFinished=true;
				}*/
				
				if(currentDirection == DiverterDirection.TOP && loadedPart.getX()>xPos+width-width*Math.cos(Math.toRadians(HIGH)))
				{
					loadedPart.moveX(-1);
			/*		if(loadedPart.getY()<Math.tan(Math.toRadians(HIGH))*width)
					{
						loadedPart.moveY(1);
					}*/
				} 
				else if(currentDirection == DiverterDirection.BOTTOM && loadedPart.getX()>xPos+width-width*Math.cos(Math.toRadians(LOW)))
				{
					loadedPart.moveX(-1);
			/*		if()
					{
						loadedPart.moveY(-1);
					}*/
				}
				else
				{
					xFinished=true;
				}
			
				if(currentDirection == DiverterDirection.TOP && loadedPart.getY()>laneOneYCheck)
				{
					loadedPart.moveY(1);
				} 
				else if(currentDirection == DiverterDirection.BOTTOM && loadedPart.getY()<laneTwoYCheck)
				{
					loadedPart.moveY(-1);
				}
				else
				{
					yFinished=true;
				}
			
				//loadedPart.draw(g);
			
				if(xFinished && yFinished)
				{
					deliveringPart=false;
					loadedPart=null;
					diverterAgent.msgGuiDoneMovingPart();
				}
			}
			else
			{
				if(loadedPart.getX()>xPos-18)
				{
					loadedPart.moveX(-1);
				}
				
				if(currentDirection == DiverterDirection.TOP && loadedPart.getY()>(yPos-height/4))
				{
					loadedPart.moveY(1);
				} 
			
				if(currentDirection == DiverterDirection.BOTTOM && loadedPart.getY()<(yPos+3*height/4))
				{
					loadedPart.moveY(-1);
				}
				
				//loadedPart.draw(g);
				
				if(loadedPart.getX()>xPos-18 && (loadedPart.getY()==(yPos-height/4)||loadedPart.getY()==(yPos+3*height/4)))
				{
					deliveringPart=false;
					loadedPart=null;
					diverterAgent.msgGuiDoneMovingPart();
				}
			}
		}
	}
	
	public void zoom(int amount) {
		width += amount;
		height += amount;
		xPos -= amount / 2;
		yPos -= amount / 2;
	}

	@Override
	public GUIPart getPart(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		draw((Graphics2D) g); //calls the other draw method
	}
	/*
	 * These are the various getters and setters for the diverter
	 */
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
	
	public void setLaneOne(GUILane lane)
	{
		laneOne=lane;
	}
	
	public void setLaneTwo(GUILane lane)
	{
		laneTwo=lane;
	}
	
	public GUILane getLaneOne(GUILane lane)
	{
		return laneOne;
	}
	
	public GUILane getLaneTwo(GUILane lane)
	{
		return laneTwo;
	}
}
