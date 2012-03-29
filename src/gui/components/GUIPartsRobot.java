package gui.components;

import factory.PartsRobotAgent;
import factory.interfaces.PartsRobotActor;
import gui.interfaces.GUIComponentInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import shared.KitPartPutInstruction;
import shared.NestPosition;
import shared.enums.PartType;

/**
 * GUI-side of the Parts robot in factory
 * @author Andrew Heiderscheit
 * 
 */

@SuppressWarnings("serial")
public class GUIPartsRobot extends GUIComponent implements GUIComponentInterface, PartsRobotActor{
	String name;
	//calculated position of part slots in hands
	int part1XPos, part2XPos, part3XPos, part4XPos;
	int part1YPos, part2YPos, part3YPos, part4YPos;
	//0 degrees means hand 1 is pointing up
	//part in hand 1
	GUIPart handOnePart;
	//part in hand 2
	GUIPart handTwoPart;
	//part in hand 3
	GUIPart handThreePart;
	//part in hand 4
	GUIPart handFourPart;
	//GUI Component for getting parts
	GUIComponent getterComponent;
	//PartsRobot Agent
	PartsRobotAgent myAgent;
	//Nests
	GUINest nest1, nest2, nest3, nest4, nest5, nest6, nest7, nest8;
	//KitStands
	GUIKitStand kitStand;
	//GUIFactoryFloor
	GUIComponent factoryFloor;
	//location and rotation information
	private int xPos, yPos, xDest, yDest;
	private int XIDLE = 100, YIDLE = 100;
	//0 degrees means hand 1 is pointing up
	private int rotationAngle, rotationDest;
	//image information
	private int width, heigth, armWidth, armHeigth, clampWidth, clampHeigth, beamWidth, beamHeigth;
	//default values for sizing
	private static final int stdArmWidth = 11, stdArmHeigth = 28;
	private static final int stdClampWidth = 14, stdClampHeigth = 28;
	private static final int stdBeamWidth = 28, stdBeamHeigth = 5;
	private static final int stdWidth = 80, stdHeight = 80;
	//images for robot
	private ImageIcon myBodyImage = new ImageIcon("images/ab_parts_robot_body.png");
	private final ImageIcon myArmImage = new ImageIcon("images/ab_parts_robot_arm.png");
	private final ImageIcon myLeftClampImage = new ImageIcon("images/ab_parts_robot_clampL.png");
	private final ImageIcon myRightClampImage = new ImageIcon("images/ab_parts_robot_clampR.png");
	private final ImageIcon myGripperImage = new ImageIcon("images/ab_parts_robot_gripBeam.png");
	//place holder for the robot
	private Rectangle2D.Double PartRobotPlaceHolder;
	private int myFace;
	
	public int getFace(){
		return myFace;
	}

	public void setIdlePos(int xPos, int yPos)
	{
		XIDLE = xPos;
		YIDLE = yPos;
	}
	
	public void setChinskiBot(){
		myBodyImage = new ImageIcon("images/parts_robot_body_chin.png");
		myFace = 1;
	}
	
	public void setClownBot(){
		myBodyImage = new ImageIcon("images/parts_robot_body_yay.png");
		myFace = 2;
	}
	
	public void setDefaultBot(){
		myBodyImage = new ImageIcon("images/ab_parts_robot_body.png");
		myFace = 0;
	}

	public void draw(Graphics g)
	{
		//updates parts in hands
		findPartPos();
		//updates parts locations if they exist
		if (handOnePart != null){
			handOnePart.setX(part1XPos);
			handOnePart.setY(part1YPos);
		}
		if (handTwoPart != null){
			handTwoPart.setX(part2XPos);
			handTwoPart.setY(part2YPos);
		}
		if (handThreePart != null){
			handThreePart.setX(part3XPos);
			handThreePart.setY(part3YPos);
		}
		if (handFourPart != null){
			handFourPart.setX(part4XPos);
			handFourPart.setY(part4YPos);
		}

		//moves the robot
		fancyMove();

		double myCenterX = (xPos + (width / 2));
		double myCenterY = (yPos + (heigth / 2));
		//rotates canvas for drawing
		((Graphics2D) g).rotate(Math.toRadians(rotationAngle), myCenterX, myCenterY);
		//draw hands set 1
		g.drawImage(myLeftClampImage.getImage(), xPos + (width/2) - (beamWidth/2)+ 2, yPos - armHeigth - (clampHeigth/2) + 8, clampWidth, clampHeigth, null);
		g.drawImage(myRightClampImage.getImage(), xPos + (width/2) + 2, yPos - armHeigth - (clampHeigth/2) + 8, clampWidth, clampHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw hands set 2
		g.drawImage(myLeftClampImage.getImage(), xPos + (width/2) - (beamWidth/2)+ 2, yPos - armHeigth - (clampHeigth/2) + 8, clampWidth, clampHeigth, null);
		g.drawImage(myRightClampImage.getImage(), xPos + (width/2)+ 2, yPos - armHeigth - (clampHeigth/2)+ 8, clampWidth, clampHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw hands set 3
		g.drawImage(myLeftClampImage.getImage(), xPos + (width/2) - (beamWidth/2)+ 2, yPos - armHeigth - (clampHeigth/2) + 8, clampWidth, clampHeigth, null);
		g.drawImage(myRightClampImage.getImage(), xPos + (width/2)+ 2, yPos - armHeigth - (clampHeigth/2)+ 8, clampWidth, clampHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw hands set 4
		g.drawImage(myLeftClampImage.getImage(), xPos + (width/2) - (beamWidth/2)+ 2, yPos - armHeigth - (clampHeigth/2) + 8, clampWidth, clampHeigth, null);
		g.drawImage(myRightClampImage.getImage(), xPos + (width/2), yPos - armHeigth - (clampHeigth/2)+ 8, clampWidth, clampHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw gripper 1
		g.drawImage(myGripperImage.getImage(), xPos + (width/2) - (beamWidth/2), yPos - armHeigth - (beamHeigth/2)+ 8, beamWidth, beamHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw gripper 2
		g.drawImage(myGripperImage.getImage(), xPos + (width/2) - (beamWidth/2), yPos - armHeigth - (beamHeigth/2)+ 8, beamWidth, beamHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw gripper 3
		g.drawImage(myGripperImage.getImage(), xPos + (width/2) - (beamWidth/2), yPos - armHeigth - (beamHeigth/2)+ 8, beamWidth, beamHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw gripper 4
		g.drawImage(myGripperImage.getImage(), xPos + (width/2) - (beamWidth/2), yPos - armHeigth - (beamHeigth/2)+ 8, beamWidth, beamHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw arm 1
		g.drawImage(myArmImage.getImage(), xPos + (width/2) - (armWidth/2), yPos - armHeigth, armWidth, armHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw arm 2
		g.drawImage(myArmImage.getImage(), xPos + (width/2) - (armWidth/2), yPos - armHeigth, armWidth, armHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw arm 3
		g.drawImage(myArmImage.getImage(), xPos + (width/2) - (armWidth/2), yPos - armHeigth, armWidth, armHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw arm 4
		g.drawImage(myArmImage.getImage(), xPos + (width/2) - (armWidth/2), yPos - armHeigth, armWidth, armHeigth, null);
		((Graphics2D) g).rotate(Math.toRadians(90), myCenterX, myCenterY);
		//draw the body
		g.drawImage(myBodyImage.getImage(), xPos, yPos, width, heigth, null);
		//rotate the cancas back
		((Graphics2D) g).rotate(Math.toRadians(-1 * rotationAngle), myCenterX, myCenterY);
	}

	//calculates center of robot
	public int getCenterX()
	{
		int x = xPos + width/2; 
		return x;
	}

	//calculates center of robot
	public int getCenterY()
	{
		int y = yPos + width/2;
		return y;
	}

	//method to find the x and y positions of the gripper slots to move rotated parts appropriately
	public void findPartPos()
	{
		rotationAngle %= 360;
		//normalizes rotation angle for quadrants
		int tempAngle = rotationAngle % 90;
		//arm 1
		//calculate x-pos based on quadrant
		if(rotationAngle == 0 || rotationAngle == 180)
			part1XPos = getCenterX() - 5;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part1XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90)
			part1XPos = getCenterX() + armHeigth + heigth/2 - 11;
		else if(rotationAngle == 270)
			part1XPos = getCenterX() - armHeigth - heigth/2;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part1XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part1XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part1XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else
			part1XPos = getCenterX();
		//calculate y-pos based on quadrant
		if(rotationAngle == 0)
			part1YPos = getCenterY() - armHeigth - heigth/2;
		else if (rotationAngle == 180)
			part1YPos = getCenterY() + armHeigth + heigth/2 - 15;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part1YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90 || rotationAngle == 270)
			part1YPos = getCenterY() - 8;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part1YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part1YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part1YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else
			part1YPos = getCenterY();

		//part 2
		//calculate x-pos based on quadrant
		if(rotationAngle == 0)
			part2XPos = getCenterX() + armHeigth + heigth/2 - 11;
		else if (rotationAngle == 180)
			part2XPos = getCenterX() - armHeigth - heigth/2;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part2XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90 || rotationAngle == 270)
			part2XPos = getCenterX() - 5;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part2XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part2XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part2XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else
			part2XPos = getCenterX();
		//calculate y-pos based on quadrant
		if(rotationAngle == 0 || rotationAngle == 180)
			part2YPos = getCenterY() - 8;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part2YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90)
			part2YPos = getCenterY() + armHeigth + heigth/2 - 15;
		else if(rotationAngle == 270)
			part2YPos = getCenterY() - armHeigth - heigth/2;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part2YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part2YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part2YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else
			part2YPos = getCenterY();

		//part 3
		//calculate x-pos based on quadrant
		if(rotationAngle == 0 || rotationAngle == 180)
			part3XPos = getCenterX()- 5;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part3XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90)
			part3XPos = getCenterX() - armHeigth - heigth/2;
		else if(rotationAngle == 270)
			part3XPos = getCenterX() + armHeigth + heigth/2 - 11;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part3XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part3XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part3XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else
			part3XPos = getCenterX();
		//calculate y-pos based on quadrant
		if(rotationAngle == 0)
			part3YPos = getCenterY() + armHeigth + heigth/2 - 15;
		else if (rotationAngle == 180)
			part3YPos = getCenterY() - armHeigth - heigth/2;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part3YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90 || rotationAngle == 270)
			part3YPos = getCenterY()- 8;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part3YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part3YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part3YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else
			part3YPos = getCenterY();

		//part 4
		//calculate x-pos based on quadrant
		if(rotationAngle == 0)
			part4XPos = getCenterX() - armHeigth - heigth/2;
		else if (rotationAngle == 180)
			part4XPos = getCenterX() + armHeigth + heigth/2 - 11;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part4XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90 || rotationAngle == 270)
			part4XPos = getCenterX()- 5;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part4XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part4XPos = (int)(getCenterX() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part4XPos = (int)(getCenterX() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else
			part4XPos = getCenterX();
		//calculate y-pos based on quadrant
		if(rotationAngle == 0 || rotationAngle == 180)
			part4YPos = getCenterY()- 8;
		else if(rotationAngle > 0 && rotationAngle < 90)
			part4YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle == 90)
			part4YPos = getCenterY() - armHeigth - heigth/2;
		else if(rotationAngle == 270)
			part4YPos = getCenterY() + armHeigth + heigth/2 - 15;
		else if(rotationAngle > 90 && rotationAngle < 180) 
			part4YPos = (int)(getCenterY() - (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else if(rotationAngle > 180 && rotationAngle < 270) 
			part4YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.sin(Math.toRadians(tempAngle)));
		else if(rotationAngle > 270 && rotationAngle < 360) 
			part4YPos = (int)(getCenterY() + (armHeigth + heigth/2)*Math.cos(Math.toRadians(tempAngle)));
		else
			part4YPos = getCenterY();

	}

	//updates the placeholder of the robot and links it to current frame
	public void update()
	{
		PartRobotPlaceHolder.setFrame(this.xPos, this.yPos, this.width, this.heigth);
	}

	//getter for xPos
	public int getX() 
	{
		return xPos;
	}

	//getter for yPos
	public int getY() 
	{
		return yPos;
	}

	//increments rotationDest and then normalizes to 360 degrees
	public void rotate(int degrees)
	{
		rotationDest += degrees;
		rotationDest %= 360;
	}

	//agent call for the parts robot to go to various lanes and pick up parts
	public void doPickUpPartsFromLanes(ArrayList<NestPosition> lanesToGrab)
	{//System.out.println("doUpPartsFromLanes");
		ArrayList<NestPosition> partsPickedUp = new ArrayList<NestPosition>();	// (JY)
		for(NestPosition np : lanesToGrab)
		{
			System.out.println("SPONGEBOB Lane " + np.nestNumber + ", pos " + np.nestPosition);
		}


		//get list of parts from the agent
		NestPosition tempPos = null;
		int nestX, nestY;
		//for each part in list
		while (!lanesToGrab.isEmpty())
		{	
			tempPos = lanesToGrab.remove(0);
			//get appropriate nest location
			switch (tempPos.nestNumber)
			{
			case 1:
				nestX = nest1.getX();
				nestY = nest1.getY();
				break;
			case 2:
				nestX = nest2.getX();
				nestY = nest2.getY();
				break;
			case 3:
				nestX = nest3.getX();
				nestY = nest3.getY();
				break;
			case 4:
				nestX = nest4.getX();
				nestY = nest4.getY();
				break;
			case 5:
				nestX = nest5.getX();
				nestY = nest5.getY();
				break;
			case 6:
				nestX = nest6.getX();
				nestY = nest6.getY();
				break;
			case 7:
				nestX = nest7.getX();
				nestY = nest7.getY();
				break;
			case 8:
				nestX = nest8.getX();
				nestY = nest8.getY();
				break;
			default:
				//alerts agent and returns if nest was not found
				ArrayList<PartType> partsInHand = new ArrayList<PartType>();
				if (handOnePart != null){
					partsInHand.add(handOnePart.getType());
				}
				if (handTwoPart != null){
					partsInHand.add(handTwoPart.getType());
				}
				if (handThreePart != null){
					partsInHand.add(handThreePart.getType());
				}
				if (handFourPart != null){
					partsInHand.add(handFourPart.getType());
				}
				myAgent.msgDonePickUpFromLanes(partsInHand, partsPickedUp);
				return;
			}
			//sets destination next to target nest
			xDest = nestX - width - armHeigth + (clampHeigth/2);
			yDest = nestY;
			//checks for first open hand and then gets part
			if (handOnePart == null){
				rotationDest = 90;
				//System.out.println("Hand 1 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
				while (xPos != xDest || yPos != yDest || rotationDest != rotationAngle){
					//moving to position
					//System.out.println("Hand 1 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
				}
				//gets part from target lane
				switch (tempPos.nestNumber){
				case 1:
					//System.out.println("PartRobotGUI: Picking up part from nest one for hand 1");
					handOnePart = nest1.getPart(tempPos.nestPosition);
					break;
				case 2:
					//System.out.println("PartRobotGUI: Picking up part from nest two for hand 1");
					handOnePart = nest2.getPart(tempPos.nestPosition);
					break;
				case 3:
					//System.out.println("PartRobotGUI: Picking up part from nest three for hand 1");
					handOnePart = nest3.getPart(tempPos.nestPosition);
					break;
				case 4:
					//System.out.println("PartRobotGUI: Picking up part from nest four for hand 1");
					handOnePart = nest4.getPart(tempPos.nestPosition);
					break;
				case 5:
					//System.out.println("PartRobotGUI: Picking up part from nest five for hand 1");
					handOnePart = nest5.getPart(tempPos.nestPosition);
					break;
				case 6:
					//System.out.println("PartRobotGUI: Picking up part from nest six for hand 1");
					handOnePart = nest6.getPart(tempPos.nestPosition);
					break;
				case 7:
					//System.out.println("PartRobotGUI: Picking up part from nest seven for hand 1");
					handOnePart = nest7.getPart(tempPos.nestPosition);
					break;
				case 8:
					//System.out.println("PartRobotGUI: Picking up part from nest eight for hand 1");
					handOnePart = nest8.getPart(tempPos.nestPosition);
					break;
				}
				findPartPos();
				if (handOnePart != null){
					handOnePart.setX(part1XPos);
					handOnePart.setY(part1YPos);
					partsPickedUp.add(tempPos);
				}
			} else if (handTwoPart == null) {
				rotationDest = 180;
				//System.out.println("Im at " + rotationAngle + " and I need to be " + rotationDest);
				//System.out.println("Hand 2 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
				while (xPos != xDest || yPos != yDest || rotationDest != rotationAngle){
					//System.out.println("Hand 2 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
					//moving to position
				}
				switch (tempPos.nestNumber){
				case 1:
					//System.out.println("PartRobotGUI: Picking up part from nest one for hand 2");
					handTwoPart = nest1.getPart(tempPos.nestPosition);
					break;
				case 2:
					//System.out.println("PartRobotGUI: Picking up part from nest two for hand 2");
					handTwoPart = nest2.getPart(tempPos.nestPosition);
					break;
				case 3:
					handTwoPart = nest3.getPart(tempPos.nestPosition);
					break;
				case 4:
					handTwoPart = nest4.getPart(tempPos.nestPosition);
					break;
				case 5:
					handTwoPart = nest5.getPart(tempPos.nestPosition);
					break;
				case 6:
					handTwoPart = nest6.getPart(tempPos.nestPosition);
					break;
				case 7:
					handTwoPart = nest7.getPart(tempPos.nestPosition);
					break;
				case 8:
					handTwoPart = nest8.getPart(tempPos.nestPosition);
					break;
				}
				findPartPos();
				if (handTwoPart != null){
					handTwoPart.setX(part2XPos);
					handTwoPart.setY(part2YPos);
					partsPickedUp.add(tempPos);
				}
			} else if (handThreePart == null) {
				rotationDest = 270;
				//System.out.println("Im at " + rotationAngle + " and I need to be " + rotationDest);
				//System.out.println("Hand 3 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
				while (xPos != xDest || yPos != yDest || rotationDest != rotationAngle){
					//System.out.println("Hand 3 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
					//moving to position
				}
				switch (tempPos.nestNumber){
				case 1:
					//System.out.println("PartRobotGUI: Picking up part from nest one for hand 3");
					handThreePart = nest1.getPart(tempPos.nestPosition);
					break;
				case 2:
					//System.out.println("PartRobotGUI: Picking up part from nest two for hand 3");
					handThreePart = nest2.getPart(tempPos.nestPosition);
					break;
				case 3:
					handThreePart = nest3.getPart(tempPos.nestPosition);
					break;
				case 4:
					handThreePart = nest4.getPart(tempPos.nestPosition);
					break;
				case 5:
					handThreePart = nest5.getPart(tempPos.nestPosition);
					break;
				case 6:
					handThreePart = nest6.getPart(tempPos.nestPosition);
					break;
				case 7:
					handThreePart = nest7.getPart(tempPos.nestPosition);
					break;
				case 8:
					handThreePart = nest8.getPart(tempPos.nestPosition);
					break;
				}
				findPartPos();
				if (handThreePart != null){
					handThreePart.setX(part3XPos);
					handThreePart.setY(part3YPos);
					partsPickedUp.add(tempPos);
				}
			} else if (handFourPart == null) {
				rotationDest = 0;
				//System.out.println("Im at " + rotationAngle + " and I need to be " + rotationDest);
				//System.out.println("Hand 4 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
				while (xPos != xDest || yPos != yDest || rotationDest != rotationAngle){
					//System.out.println("Hand 4 " + (xPos != xDest && yPos != yDest && rotationDest != rotationAngle));
					//moving to position
				}
				switch (tempPos.nestNumber){
				case 1:
					//System.out.println("PartRobotGUI: Picking up part from nest one for hand 4");
					handFourPart = nest1.getPart(tempPos.nestPosition);
					break;
				case 2:
					//System.out.println("PartRobotGUI: Picking up part from nest two for hand 4");
					handFourPart = nest2.getPart(tempPos.nestPosition);
					break;
				case 3:
					handFourPart = nest3.getPart(tempPos.nestPosition);
					break;
				case 4:
					handFourPart = nest4.getPart(tempPos.nestPosition);
					break;
				case 5:
					handFourPart = nest5.getPart(tempPos.nestPosition);
					break;
				case 6:
					handFourPart = nest6.getPart(tempPos.nestPosition);
					break;
				case 7:
					handFourPart = nest7.getPart(tempPos.nestPosition);
					break;
				case 8:
					handFourPart = nest8.getPart(tempPos.nestPosition);
					break;
				}
				findPartPos();
				if (handFourPart != null){
					handFourPart.setX(part4XPos);
					handFourPart.setY(part4YPos);
					partsPickedUp.add(tempPos);
				}
			} else {
				break;
			}
		}
		//upon completion
		//build list of currently held parts for agent
		ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
		if (handOnePart != null){	// (JY)
			partsInHand.add(handOnePart.getType());
			////partsPickedUp.add(tempPos);
		}
		if (handTwoPart != null){
			partsInHand.add(handTwoPart.getType());
			////partsPickedUp.add(tempPos);
		}
		if (handThreePart != null){
			partsInHand.add(handThreePart.getType());
			////partsPickedUp.add(tempPos);
		}
		if (handFourPart != null){
			partsInHand.add(handFourPart.getType());
			////partsPickedUp.add(tempPos);
		}

		xDest = XIDLE;
		//yDest = YIDLE;

		while (xPos != xDest || yPos != yDest){

		}

		//alerts agent that task has been finished, and passes a list of currently held parts
		myAgent.msgDonePickUpFromLanes(partsInHand, partsPickedUp);

	}

	//agent call for the robot to place parts in hand in a Kit
	public void doPutPartsInKit(ArrayList<KitPartPutInstruction> daList)
	{
		ArrayList<PartType> partsPutDown = new ArrayList<PartType>();
		while (!daList.isEmpty()){
			KitPartPutInstruction operatingInst = daList.remove(0);
			int KitStandLoc = operatingInst.getDestKitStandLoc();
			System.out.println("GUIPartsRobot: KitStandLoc for putPartsInKit " + KitStandLoc);
			ArrayList<PartType> listOParts = operatingInst.getPartsToPut();
			//receive kit number and list of parts to drop off from agent
			GUIKit targetKit;
			//list of parts put down
			//moves next to appropriate kit stand
			switch (KitStandLoc){
			case 1:
				xDest = kitStand.getKitFromStand(1).getX() + (width/2) + armWidth;
				yDest = kitStand.getKitFromStand(1).getY() - (heigth/2);
				targetKit = kitStand.getKitFromStand(1);
				break;
			case 2:
				xDest = kitStand.getKitFromStand(2).getX() + (width/2) + armWidth;
				yDest = kitStand.getKitFromStand(2).getY() - (heigth/2);
				targetKit = kitStand.getKitFromStand(2);
				break;
			default:
				//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
				ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
				if (handOnePart != null){
					partsInHand.add(handOnePart.getType());
				}
				if (handTwoPart != null){
					partsInHand.add(handTwoPart.getType());
				}
				if (handThreePart != null){
					partsInHand.add(handThreePart.getType());
				}
				if (handFourPart != null){
					partsInHand.add(handFourPart.getType());
				}
				targetKit = null;
				myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
				//break;
			}
			//move to target kitStand
			while (xDest != xPos || yDest != yPos){
				//wait to move
			}
			//for each part on list
			PartType tempPart;
			while (!listOParts.isEmpty()){
				//if a hand is not empty, and has the appropriate part
				tempPart = listOParts.remove(0);
				if (handOnePart != null && handOnePart.getType() == tempPart){
					rotationDest = 270;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handOnePart.getType());
					targetKit.addPart(handOnePart);
					handOnePart = null;
				} else if (handTwoPart != null && handTwoPart.getType() == tempPart){
					rotationDest = 180;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handTwoPart.getType());
					targetKit.addPart(handTwoPart);
					handTwoPart = null;
				} else if (handThreePart != null && handThreePart.getType() == tempPart){
					rotationDest = 90;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handThreePart.getType());
					targetKit.addPart(handThreePart);
					handThreePart = null;
				} else if (handFourPart != null && handFourPart.getType() == tempPart){
					rotationDest = 0;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handFourPart.getType());
					targetKit.addPart(handFourPart);
					handFourPart = null;
				} else {
					//if part does not exist in hand, or all hands are empty, alert agent what what has been put down, then returns
					ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
					if (handOnePart != null){
						partsInHand.add(handOnePart.getType());
					}
					if (handTwoPart != null){
						partsInHand.add(handTwoPart.getType());
					}
					if (handThreePart != null){
						partsInHand.add(handThreePart.getType());
					}
					if (handFourPart != null){
						partsInHand.add(handFourPart.getType());
					}
					myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);return;
				}
			}
			//upon completion	
			//build list of parts in hand
			ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
			if (handOnePart != null){
				partsInHand.add(handOnePart.getType());
			}
			if (handTwoPart != null){
				partsInHand.add(handTwoPart.getType());
			}
			if (handThreePart != null){
				partsInHand.add(handThreePart.getType());
			}
			if (handFourPart != null){
				partsInHand.add(handFourPart.getType());
			}

			xDest = XIDLE;
			//yDest = YIDLE;

			while (xPos != xDest || yPos != yDest){

			}

			myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
		}
	}

	public void doBadTrip(ArrayList<KitPartPutInstruction> daList)
	{
		ArrayList<PartType> partsPutDown = new ArrayList<PartType>();
		while (!daList.isEmpty()){
			KitPartPutInstruction operatingInst = daList.remove(0);
			int KitStandLoc = operatingInst.getDestKitStandLoc();
			System.out.println("GUIPartsRobot: KitStandLoc for putPartsInKit " + KitStandLoc);
			ArrayList<PartType> listOParts = operatingInst.getPartsToPut();
			//receive kit number and list of parts to drop off from agent
			GUIKit targetKit;
			//list of parts put down
			//moves next to appropriate kit stand
			switch (KitStandLoc){
			case 1:
				xDest = kitStand.getKitFromStand(1).getX() + (width/2) + armWidth;
				yDest = kitStand.getKitFromStand(1).getY() - (heigth/2);
				targetKit = kitStand.getKitFromStand(1);
				break;
			case 2:
				xDest = kitStand.getKitFromStand(2).getX() + (width/2) + armWidth;
				yDest = kitStand.getKitFromStand(2).getY() - (heigth/2);
				targetKit = kitStand.getKitFromStand(2);
				break;
			default:
				//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
				ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
				if (handOnePart != null){
					partsInHand.add(handOnePart.getType());
				}
				if (handTwoPart != null){
					partsInHand.add(handTwoPart.getType());
				}
				if (handThreePart != null){
					partsInHand.add(handThreePart.getType());
				}
				if (handFourPart != null){
					partsInHand.add(handFourPart.getType());
				}
				targetKit = null;
				myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
				//break;
			}
			//move to target kitStand
			while (xDest != xPos || yDest != yPos){
				//wait to move
			}
			//for each part on list
			PartType tempPart;
			while (!listOParts.isEmpty()){
				//if a hand is not empty, and has the appropriate part
				tempPart = listOParts.remove(0);
				if (handOnePart != null && handOnePart.getType() == tempPart){
					switch (KitStandLoc){
					case 1:
						xDest = kitStand.getKitFromStand(1).getX() + 50;
						yDest = kitStand.getKitFromStand(1).getY() + 310;
						targetKit = kitStand.getKitFromStand(1);
						break;
					case 2:
						xDest = kitStand.getKitFromStand(2).getX() + 95;
						yDest = kitStand.getKitFromStand(2).getY() + 350;
						targetKit = kitStand.getKitFromStand(2);
						break;
					default:
						//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
						ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
						if (handOnePart != null){
							partsInHand.add(handOnePart.getType());
						}
						if (handTwoPart != null){
							partsInHand.add(handTwoPart.getType());
						}
						if (handThreePart != null){
							partsInHand.add(handThreePart.getType());
						}
						if (handFourPart != null){
							partsInHand.add(handFourPart.getType());
						}
						targetKit = null;
						myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
						//break;
					}
					//move to target kitStand
					while (xDest != xPos || yDest != yPos){
						//wait to move
					}
					rotationDest = 270;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handOnePart.getType());
					factoryFloor.givePart(handOnePart, this);
					handOnePart = null;
				} else if (handTwoPart != null && handTwoPart.getType() == tempPart){
					switch (KitStandLoc){
					case 1:
						xDest = kitStand.getKitFromStand(1).getX() + (width/2) + armWidth;
						yDest = kitStand.getKitFromStand(1).getY() - (heigth/2);
						targetKit = kitStand.getKitFromStand(1);
						break;
					case 2:
						xDest = kitStand.getKitFromStand(2).getX() + (width/2) + armWidth;
						yDest = kitStand.getKitFromStand(2).getY() - (heigth/2);
						targetKit = kitStand.getKitFromStand(2);
						break;
					default:
						//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
						ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
						if (handOnePart != null){
							partsInHand.add(handOnePart.getType());
						}
						if (handTwoPart != null){
							partsInHand.add(handTwoPart.getType());
						}
						if (handThreePart != null){
							partsInHand.add(handThreePart.getType());
						}
						if (handFourPart != null){
							partsInHand.add(handFourPart.getType());
						}
						targetKit = null;
						myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
						//break;
					}
					rotationDest = 180;
					while (rotationDest != rotationAngle || xPos != xDest || yPos != yDest){
						//wait for rotate
					}
					partsPutDown.add(handTwoPart.getType());
					targetKit.addPart(handTwoPart);
					handTwoPart = null;
				} else if (handThreePart != null && handThreePart.getType() == tempPart){
					switch (KitStandLoc){
					case 1:
						xDest = kitStand.getKitFromStand(1).getX() + 75;
						yDest = kitStand.getKitFromStand(1).getY() + 360;
						targetKit = kitStand.getKitFromStand(1);
						break;
					case 2:
						xDest = kitStand.getKitFromStand(2).getX() + 87;
						yDest = kitStand.getKitFromStand(2).getY() + 270;
						targetKit = kitStand.getKitFromStand(2);
						break;
					default:
						//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
						ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
						if (handOnePart != null){
							partsInHand.add(handOnePart.getType());
						}
						if (handTwoPart != null){
							partsInHand.add(handTwoPart.getType());
						}
						if (handThreePart != null){
							partsInHand.add(handThreePart.getType());
						}
						if (handFourPart != null){
							partsInHand.add(handFourPart.getType());
						}
						targetKit = null;
						myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
						//break;
					}
					//move to target kitStand
					while (xDest != xPos || yDest != yPos){
						//wait to move
					}
					rotationDest = 90;
					while (rotationDest != rotationAngle){
						//wait for rotate
					}
					partsPutDown.add(handThreePart.getType());
					factoryFloor.givePart(handThreePart, this);
					handThreePart = null;
				} else if (handFourPart != null && handFourPart.getType() == tempPart){
					switch (KitStandLoc){
					case 1:
						xDest = kitStand.getKitFromStand(1).getX() + (width/2) + armWidth;
						yDest = kitStand.getKitFromStand(1).getY() - (heigth/2);
						targetKit = kitStand.getKitFromStand(1);
						break;
					case 2:
						xDest = kitStand.getKitFromStand(2).getX() + (width/2) + armWidth;
						yDest = kitStand.getKitFromStand(2).getY() - (heigth/2);
						targetKit = kitStand.getKitFromStand(2);
						break;
					default:
						//if invalid kit stand, tells agent of lack of action taken through empty list, then returns
						ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
						if (handOnePart != null){
							partsInHand.add(handOnePart.getType());
						}
						if (handTwoPart != null){
							partsInHand.add(handTwoPart.getType());
						}
						if (handThreePart != null){
							partsInHand.add(handThreePart.getType());
						}
						if (handFourPart != null){
							partsInHand.add(handFourPart.getType());
						}
						targetKit = null;
						myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
						//break;
				}
					rotationDest = 0;
					while (rotationDest != rotationAngle || xPos != xDest || yPos != yDest){
						//wait for rotate
					}
					partsPutDown.add(handFourPart.getType());
					targetKit.addPart(handFourPart);
					handFourPart = null;
				} else {
					//if part does not exist in hand, or all hands are empty, alert agent what what has been put down, then returns
					ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
					if (handOnePart != null){
						partsInHand.add(handOnePart.getType());
					}
					if (handTwoPart != null){
						partsInHand.add(handTwoPart.getType());
					}
					if (handThreePart != null){
						partsInHand.add(handThreePart.getType());
					}
					if (handFourPart != null){
						partsInHand.add(handFourPart.getType());
					}
					myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);return;
				}
			}
			//upon completion	
			//build list of parts in hand
			ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
			if (handOnePart != null){
				partsInHand.add(handOnePart.getType());
			}
			if (handTwoPart != null){
				partsInHand.add(handTwoPart.getType());
			}
			if (handThreePart != null){
				partsInHand.add(handThreePart.getType());
			}
			if (handFourPart != null){
				partsInHand.add(handFourPart.getType());
			}

			xDest = XIDLE;
			//yDest = YIDLE;

			while (xPos != xDest || yPos != yDest){

			}

			myAgent.msgDonePutPartsInKit(KitStandLoc, partsPutDown, partsInHand);
		}
	}

	public void doDropTheseParts(ArrayList<PartType> toDrop)
	{
		//drops specified parts on the ground
		//goes through each hand, checking if part matches
		PartType tempPart;

		if (handOnePart == null)
			System.out.println("NULL EXCEPTION handOnePart");
		if (handTwoPart == null)
			System.out.println("NULL EXCEPTION handTwoPart");
		if (handThreePart == null)
			System.out.println("NULL EXCEPTION handThreePart");
		if (handFourPart == null)
			System.out.println("NULL EXCEPTION handFourPart");


		while (!toDrop.isEmpty()){
			tempPart = toDrop.remove(0);
			if (handOnePart != null && handOnePart.getType() == tempPart){
				handOnePart.setX(100);
				handOnePart.setY(45);
				factoryFloor.givePart(handOnePart, this);
				handOnePart = null;
			} else if (handTwoPart != null && handTwoPart.getType() == tempPart){
				handTwoPart.setX(100);
				handTwoPart.setY(45);
				factoryFloor.givePart(handTwoPart, this);
				handTwoPart = null;
			} else if (handThreePart != null && handThreePart.getType() == tempPart){
				handThreePart.setX(100);
				handThreePart.setY(45);
				factoryFloor.givePart(handThreePart, this);
				handThreePart = null;
			} else if (handFourPart != null && handFourPart.getType() == tempPart){
				handFourPart.setX(100);
				handFourPart.setY(45);
				factoryFloor.givePart(handFourPart, this);
				handFourPart = null;
			} else {
				break;
			}
		}

		//see whats in your hands
		ArrayList<PartType> partsInHand = new ArrayList<PartType>();
		if (handOnePart != null){
			partsInHand.add(handOnePart.getType());
		}
		if (handTwoPart != null){
			partsInHand.add(handTwoPart.getType());
		}
		if (handThreePart != null){
			partsInHand.add(handThreePart.getType());
		}
		if (handFourPart != null){
			partsInHand.add(handFourPart.getType());
		}
		//upon completion call 
		myAgent.msgDoneDroppingParts(partsInHand);
	}

	public void doDropAllParts()
	{
		//drops all currently held parts on the ground
		factoryFloor.givePart(handOnePart, this);
		factoryFloor.givePart(handTwoPart, this);
		factoryFloor.givePart(handThreePart, this);
		factoryFloor.givePart(handFourPart, this);

		handOnePart = null;
		handTwoPart = null;
		handThreePart = null;
		handFourPart = null;
		//build list of parts in hand
		ArrayList<PartType> partsInHand = new ArrayList<PartType>();;
		if (handOnePart != null){
			partsInHand.add(handOnePart.getType());
		}
		if (handTwoPart != null){
			partsInHand.add(handTwoPart.getType());
		}
		if (handThreePart != null){
			partsInHand.add(handThreePart.getType());
		}
		if (handFourPart != null){
			partsInHand.add(handFourPart.getType());
		}
		//upon completion call 
		myAgent.msgDoneDroppingParts(partsInHand);
	}

	public void doGoIdle()
	{
		//tells the robot to go idle, moves to idle'ing position
		xDest = XIDLE;
		yDest = YIDLE;
		rotationDest = 0;
		//upon completion call 
		myAgent.msgDoneGoingIdle();
	}

	public void doReset()
	{
		//resets robot to default
		//drops all parts
		factoryFloor.givePart(handOnePart, this);
		factoryFloor.givePart(handTwoPart, this);
		factoryFloor.givePart(handThreePart, this);
		factoryFloor.givePart(handFourPart, this);

		handOnePart = null;
		handTwoPart = null;
		handThreePart = null;
		handFourPart = null;

		//returns to idle position
		xDest = XIDLE;
		yDest = YIDLE;
		rotationDest = 0;

		//upon completion call 
		myAgent.msgDoneResetting();
	}

	//to be used for complex movement of the robot, allowing it to move and rotate an angle to match the movement distance, 
	//preventing the need to do a moveX followed by a moveY, followed by a rotate (and looking way cooler)
	private void fancyMove()
	{
		//compares yPos and moves robot accordingly
		if (yPos < yDest) {
			yPos++;
			//moves parts if they exist
			//			if (handOnePart != null)
			//			{
			//				handOnePart.moveY(-1);
			//			}
			//			if (handTwoPart != null)
			//			{
			//				handTwoPart.moveY(-1);
			//			}
			//			if (handThreePart != null)
			//			{
			//				handThreePart.moveY(-1);
			//			}
			//			if (handFourPart != null)
			//			{
			//				handFourPart.moveY(-1);
			//			}
			//compares yPos and moves accordingly
		} else if (yPos > yDest) {
			yPos--;
			//moves parts if they exist
			//			if (handOnePart != null)
			//			{
			//				handOnePart.moveY(1);
			//			}
			//			if (handTwoPart != null)
			//			{
			//				handTwoPart.moveY(1);
			//			}
			//			if (handThreePart != null)
			//			{
			//				handThreePart.moveY(1);
			//			}
			//			if (handFourPart != null)
			//			{
			//				handFourPart.moveY(1);
			//			}
		}
		//compares xPos and moves accordingly
		if (xPos < xDest) {
			xPos++;
			//moves parts if they exist
			//			if (handOnePart != null)
			//			{
			//				handOnePart.moveX(1);
			//			}
			//			if (handTwoPart != null)
			//			{
			//				handTwoPart.moveX(1);
			//			}
			//			if (handThreePart != null)
			//			{
			//				handThreePart.moveX(1);
			//			}
			//			if (handFourPart != null)
			//			{
			//				handFourPart.moveX(1);
			//			}
			//compares xPos and moves accordingly
		} else if (xPos > xDest) {
			xPos--;
			//moves parts if they exist
			//			if (handOnePart != null)
			//			{
			//				handOnePart.moveX(-1);
			//			}
			//			if (handTwoPart != null)
			//			{
			//				handTwoPart.moveX(-1);
			//			}
			//			if (handThreePart != null)
			//			{
			//				handThreePart.moveX(-1);
			//			}
			//			if (handFourPart != null)
			//			{
			//				handFourPart.moveX(-1);
			//			}
		}
		double toRotate;
		//checks if rotation of necessary
		if (rotationDest != rotationAngle)
		{
			//makes sure that a divide by zero doesnt occur
			if (xPos != xDest || yPos != yDest){
				if (rotationDest > rotationAngle){
					toRotate = (rotationDest - rotationAngle) / (Math.sqrt(Math.pow(Math.abs(xDest-xPos),2) + Math.pow(Math.abs(yDest-yPos),2)));
					rotationAngle += toRotate;
					rotationAngle %= 360;
				} else {
					toRotate = (rotationAngle - rotationDest) / (Math.sqrt(Math.pow(Math.abs(xDest-xPos),2) + Math.pow(Math.abs(yDest-yPos),2)));
					rotationAngle -= toRotate;
					rotationAngle %= 360;
				}
				//catch case for div by zero
			} else {
				if (rotationDest > rotationAngle){
					rotationAngle += 5;
					rotationAngle %= 360;
				} else {
					rotationAngle -= 5;
					rotationAngle %= 360;	
				}
			}
		}
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public void givePart(GUIPart part, GUIComponent giver) 
	{
		giver.gotPart(part);
	}

	@Override
	public GUIPart getPart(int position) 
	{
		return null;
	}

	@Override
	public void gotPart(GUIPart part)
	{

	}

	public void setMyAgent(PartsRobotAgent myNewAgent)
	{
		myAgent = myNewAgent;
	}

	public void setNest(GUINest nest, int nestNumber)
	{
		switch(nestNumber){
		case 1:
			nest1 = nest;
			break;
		case 2:
			nest2 = nest;
			break;
		case 3:
			nest3 = nest;
			break;
		case 4:
			nest4 = nest;
			break;
		case 5:
			nest5 = nest;
			break;
		case 6:
			nest6 = nest;
			break;
		case 7:
			nest7 = nest;
			break;
		case 8:
			nest8 = nest;
			break;
		}
	}

	public void setKitStand(GUIKitStand kitStand)
	{
		this.kitStand = kitStand;
		XIDLE = kitStand.getX()+kitStand.getWidth()*7/4;
		YIDLE = kitStand.getY();
	}

	public void setTheFloor(GUIFactoryFloor daFloor){
		factoryFloor = daFloor;
	}

	public GUIPartsRobot(String name)
	{
		this.name = name;
		width = stdWidth;
		heigth = stdHeight;
		armWidth = stdArmWidth;
		armHeigth = stdArmHeigth;
		clampWidth = stdClampWidth;
		clampHeigth = stdClampHeigth;
		beamWidth = stdBeamWidth;
		beamHeigth = stdBeamHeigth;
		PartRobotPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, heigth);
		myFace = 0;
	}

	public GUIPartsRobot(String name, int xPos, int yPos, GUIFactoryFloor daFloor, PartsRobotAgent myAgent, GUINest nest1, GUINest nest2, GUINest nest3, GUINest nest4, GUINest nest5, GUINest nest6, GUINest nest7, GUINest nest8, GUIKitStand kitStand)
	{
		this.name = name;
		factoryFloor = daFloor;
		this.myAgent = myAgent;
		this.nest1 = nest1;
		this.nest2 = nest2;
		this.nest3 = nest3;
		this.nest4 = nest4;
		this.nest5 = nest5;
		this.nest6 = nest6;
		this.nest7 = nest7;
		this.nest8 = nest8;
		this.kitStand = kitStand;
		this.xPos = xPos;
		this.yPos = yPos;
		width = stdWidth;
		heigth = stdHeight;
		armWidth = stdArmWidth;
		armHeigth = stdArmHeigth;
		clampWidth = stdClampWidth;
		clampHeigth = stdClampHeigth;
		beamWidth = stdBeamWidth;
		beamHeigth = stdBeamHeigth;
		PartRobotPlaceHolder = new Rectangle2D.Double(xPos, yPos, width, heigth);
		myFace = 0;
	}

	@Override
	public void setX(int x) {
		xPos = x;
		xDest = x;
	}

	@Override
	public void setY(int y) {
		yPos = y;
		yDest = y;
	}

	@Override
	public int getWidth() {
		return (width + (2 * armHeigth));
	}

	@Override
	public int getHeight() {
		return (heigth + (2 * armHeigth));
	}

}
