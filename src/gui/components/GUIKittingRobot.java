package gui.components;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.ImageIcon;

import gui.components.*;
import gui.components.GUIKit.State;
import factory.*;
import factory.interfaces.*;
import gui.interfaces.*;

public class GUIKittingRobot extends GUIComponent implements GUIKitRobotInterface {
	
	enum states {
		MOVE_TO_STAND, MOVE_TO_INSPECTION, MOVE_TO_CART, MOVE_TO_DUMP, MOVE_FROM_INSPECTION, IDLE
	}
	enum MTS {
		MOVE_TO_CART, PICKUP, MOVE_TO_STAND, DROPOFF, IDLE
	}
	enum MTI {
		MOVE_TO_STAND, PICKUP, MOVE_TO_INSPECTION, DROPOFF, IDLE
	}
	enum MTC {
		MOVE_TO_INSPECTION, PICKUP, MOVE_TO_CART, DROPOFF, IDLE
	}
	enum MTD{ //move to dump
		MOVE_TO_INSPECTION, PICKUP, MOVE_TO_DUMP, DROPOFF, IDLE
	}
	enum MFI{//move from inspection
		MOVE_TO_INSPECTION, PICKUP, MOVE_TO_STAND, DROPOFF, IDLE
	}
	
	states myState;
	MTS mtsState;
	MTI mtiState;
	MTC mtcState;
	MTD mtdState;
	MFI mfiState;
	
	
	KitRobot agent;
	GUIKitStand kitStand;
	GUIKit myKit;
	GUICart myCart;
	
	public int xpos = 100;
	public int ypos = 100;
	
	int theta = 0;
	int armAngle = 0;
	int tempAngle = 0;
	int dumpX, dumpY;
	int zoom = 0;
	
	int inspectionX, inspectionY;
	int cartX, cartY, cartW, cartH;
	int standX, standY, standNumber;
	static final int KITHEIGHT = 60;
	
	public boolean hasKit;
	// Create ImageIcons
	ImageIcon arm = new ImageIcon("images/ab_kit_robot_arm.png");
	ImageIcon base = new ImageIcon("images/ab_kit_robot_base.png");
	ImageIcon claw = new ImageIcon("images/ab_kit_robot_claw.png");
	ImageIcon armBase = new ImageIcon("images/ab_kit_robot_armBase.png");
	
	public int armX = xpos + getBaseWidth()/2;
	public int armY = ypos + getBaseHeight()/2 - getArmWidth()/2;
	
	int destinationX, destinationY;
	
	int finalDistance = 0;		// Changed initial arm length to 0
	int armLength = 0;
	
	boolean clockwise, counterclockwise;
	
	public GUIKittingRobot(String name) {
		destinationX = setDestinationX(xpos + getBaseWidth()/2);
		destinationY = setDestinationY(ypos + getBaseHeight()/2);
		myState = states.IDLE;
	}
	
	public int rotateArm(int degrees) {
		// Check cw/ccw
		if(armAngle != degrees) 
		{
			if(armAngle >= 180) {
				if(armAngle - 180 < degrees && degrees < armAngle)
				{
					counterclockwise = true;
					clockwise = false;
				}
				else
				{
					clockwise = true;
					counterclockwise = false;
				}
			}
			else {
				if(armAngle + 180 > degrees && degrees > armAngle) 
				{
					clockwise = true;
					counterclockwise = false;
				}
				else
				{
					counterclockwise = true;
					clockwise = false;
				}
			}
		}
		
		if(clockwise)
		{
			armAngle += 1;
			if(armAngle >= 360) 
				armAngle %= 360;
			return armAngle;
		}
		else if(counterclockwise) 
		{
			armAngle -= 1;
			if(armAngle < 0) 
				armAngle += 360;
			return armAngle;
		}
		else
			return armAngle;
	}
	
	public int calculateDistance(int xdest, int ydest) 
	{
		int x = xdest + getClawWidth()/2;
		int y = ydest + getClawHeight()/2;
		double distance = Math.sqrt(Math.pow((x-getCenterX()), 2) + Math.pow((y-getCenterY()), 2));
		return (int)distance;
	}
	
	public int stretchArm(int distance) 
	{
		if(distance > armLength) 
			armLength++;
		else if(distance < armLength) 
			armLength--;
		return armLength;
	}
	
	// Base getters
	public int getBaseWidth() 
	{
		return base.getIconWidth()/2;
	}
	public int getBaseHeight() 
	{
		return base.getIconHeight()/2;
	}
	
	// Center of base coordinates
	public int getCenterX() 
	{
		return xpos + getBaseWidth()/2;
	}
	public int getCenterY() 
	{
		return ypos + getBaseHeight()/2;
	}
	
	// Arm getters
	public int getArmLength()
	{
		return arm.getIconWidth()/2;
	}
	public int getArmWidth() 
	{
		return arm.getIconHeight()/2;
	}

	// Claw getters
	public int getClawWidth() 
	{
		return claw.getIconWidth()/2;
	}
	public int getClawHeight() 
	{
		return claw.getIconHeight()/2;
	}
	
	// Arm base getters
	public int getArmBaseWidth() 
	{
		return armBase.getIconWidth()/2;
	}
	public int getArmBaseHeight() 
	{
		return armBase.getIconHeight()/2;
	}
	
	// Claw placement
	public int getClawX() 
	{
		tempAngle = armAngle % 90;
		if(armAngle >= 360)
			armAngle %= 360;
		if(armAngle == 0)
			return getCenterX() + armLength - getClawWidth()/2;
		else if(armAngle > 0 && armAngle < 90) 	// Quadrant 1 
			return (int)(getCenterX() + armLength*Math.cos(Math.toRadians(tempAngle)) - getClawWidth()/2);
		else if(armAngle == 90 || armAngle == 270) 
			return getCenterX() - getClawWidth()/2;
		else if(armAngle > 90 && armAngle < 180) 
			return (int)(getCenterX() - armLength*Math.sin(Math.toRadians(tempAngle)) - getClawWidth()/2);
		else if(armAngle == 180) 
			return getCenterX() - armLength - getClawWidth()/2;
		else if(armAngle > 180 && armAngle < 270) 
			return (int)(getCenterX() - armLength*Math.cos(Math.toRadians(tempAngle)) - getClawWidth()/2);
		else if(armAngle > 270 && armAngle < 360) 
			return (int)(getCenterX() + armLength*Math.sin(Math.toRadians(tempAngle)) - getClawWidth()/2);
		else
			return getCenterX() - getClawWidth()/2;
	}
	public int getClawY() 
	{
		if(armAngle >= 360)
			armAngle %= 360;
		if(armAngle == 0 || armAngle == 180) 
			return getCenterY() - getClawHeight()/2;
		else if(armAngle > 0 && armAngle < 90) 	// Quadrant 1 
			return (int)(getCenterY() - armLength*Math.sin(Math.toRadians(tempAngle)) - getClawHeight()/2);
		else if(armAngle == 90) 
			return getCenterY() - armLength - getClawHeight()/2;
		else if(armAngle > 90 && armAngle < 180) 
			return (int)(getCenterY() - armLength*Math.cos(Math.toRadians(tempAngle)) - getClawHeight()/2);
		else if(armAngle > 180 && armAngle < 270) 
			return (int)(getCenterY() + armLength*Math.sin(Math.toRadians(tempAngle)) - getClawHeight()/2);
		else if(armAngle == 270) 
			return getCenterY() + armLength - getClawHeight()/2;
		else if(armAngle > 270 && armAngle < 360) 
			return (int)(getCenterY() + armLength*Math.cos(Math.toRadians(tempAngle)) - getClawHeight()/2);
		else
			return getCenterY() - getClawHeight()/2;
	}
	
	// Set destination
	public int setDestinationX(int xfinal) 
	{
		destinationX = xfinal - getClawWidth()/2;
		return destinationX;
	}
	public int setDestinationY(int yfinal)
	{
		destinationY = yfinal - getClawHeight()/2;
		return destinationY;
	}
	public int setEndTheta(int x, int y) 
	{
		int tempTheta;
		if((x < getCenterX() && y < getCenterY()) || (x > getCenterX() && y > getCenterY())) // Q2/4
			tempTheta = (int)Math.abs(Math.toDegrees(Math.atan((double)(x-getCenterX())/(double)(y-getCenterY()))));
		else	// Q1/Q3
			tempTheta = (int)Math.abs(Math.toDegrees(Math.atan((double)(y-getCenterY())/(double)(x-getCenterX()))));
		if(x < getCenterX() && y < getCenterY()) //Q2
			tempTheta += 90;
		else if(x < getCenterX() && y > getCenterY()) //Q3
			tempTheta += 180;
		else if(x > getCenterX() && y > getCenterY()) // Q4
			tempTheta += 270;
		
		return tempTheta;
	}
	
	public void update() 
	{
		if(myState == states.IDLE)
		{
			setDestinationX(getX() + getBaseWidth()/2);
			setDestinationY(getY() + getBaseHeight()/2);
			finalDistance = calculateDistance(destinationX, destinationY);
			theta = setEndTheta(destinationX, destinationY);
			if(armLength == finalDistance && armAngle == theta)
			{
				if(mtiState == MTI.DROPOFF)
				{
					mtiState = MTI.IDLE;
					agent.msgFinishedMovingToInspection();
				}
			}
		}
		
		if(myState == states.MOVE_TO_STAND)
		{
			// First move to cart	
			if(mtsState == MTS.MOVE_TO_CART)
			{
				setDestinationX(cartX + cartW/2);
				setDestinationY(cartY + cartH/2);
				finalDistance = calculateDistance(destinationX, destinationY);
				if(getClawX() == getCenterX() && getClawY() == getCenterY())	// Remove excessive swivel if from IDLE
					armAngle = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				theta = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				if(finalDistance == armLength && theta == armAngle)
					mtsState = MTS.PICKUP;
			}
			else if(mtsState == MTS.PICKUP)
			{
				hasKit = true;
				myKit = myCart.getKit();
				mtsState = MTS.MOVE_TO_STAND;
			}
			else if(mtsState == MTS.MOVE_TO_STAND)
			{
				setDestinationX(standX);
				setDestinationY(standY);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(standX, standY);
				if(finalDistance == armLength && theta == armAngle)
					mtsState = MTS.DROPOFF;
			}
			else if(mtsState == MTS.DROPOFF)
			{
				hasKit = false;
				kitStand.putKitOnStand(myKit, standNumber);
				myKit = null;
				mtsState = MTS.IDLE;
				myState = states.IDLE;
				agent.msgFinishedMovingToStand(standNumber);
			}
		}
		
		if(myState == states.MOVE_TO_INSPECTION)
		{
			if(mtiState == MTI.MOVE_TO_STAND)
			{
				setDestinationX(standX);
				setDestinationY(standY);
				finalDistance = calculateDistance(destinationX, destinationY);
				if(getClawX() == getCenterX() && getClawY() == getCenterY())
					armAngle = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				theta = setEndTheta(standX, standY);
				if(finalDistance == armLength && theta == armAngle)
					mtiState = MTI.PICKUP;
			}
			else if(mtiState == MTI.PICKUP)
			{
				hasKit = true;
				myKit = kitStand.getKitFromStand(standNumber);
				kitStand.deleteKit(standNumber);
				mtiState = MTI.MOVE_TO_INSPECTION;
			}
			else if(mtiState == MTI.MOVE_TO_INSPECTION)
			{
				setDestinationX(inspectionX);
				setDestinationY(inspectionY);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(inspectionX, inspectionY);
				if(finalDistance == armLength && theta == armAngle)
					mtiState = MTI.DROPOFF;
			}
			else if(mtiState == MTI.DROPOFF)
			{
				hasKit = false;
				kitStand.putKitOnStand(myKit, 0);
				myKit = null;
				myState = states.IDLE;
			}
		}
		if(myState == states.MOVE_TO_CART)
		{
			if(mtcState == MTC.MOVE_TO_INSPECTION)
			{
				setDestinationX(inspectionX);
				setDestinationY(inspectionY);
				finalDistance = calculateDistance(destinationX, destinationY);
				if(getClawX() == getCenterX() && getClawY() == getCenterY())
					armAngle = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				theta = setEndTheta(inspectionX, inspectionY);
				if(finalDistance == armLength && theta == armAngle)
					mtcState = MTC.PICKUP;
			}
			else if(mtcState == MTC.PICKUP)
			{
				hasKit = true;
				myKit = kitStand.getKitFromStand(0);
				mtcState = MTC.MOVE_TO_CART;
			}
			else if(mtcState == MTC.MOVE_TO_CART)
			{
				setDestinationX(cartX + cartW/2);
				setDestinationY(cartY + cartH/2);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				if(finalDistance == armLength && theta == armAngle)
					mtcState = MTC.DROPOFF;
			}
			else if(mtcState == MTC.DROPOFF)
			{
				hasKit = false;
				myCart.giveKit(myKit);
				myKit = null;
				mtcState = MTC.IDLE;
				myState = states.IDLE;
				agent.msgBackToCart();
			}
		}
		
		if(myState == states.MOVE_FROM_INSPECTION){
			if(mfiState == MFI.MOVE_TO_INSPECTION){
				setDestinationX(inspectionX);
				setDestinationY(inspectionY);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(inspectionX, inspectionY);
				if(finalDistance == armLength && theta == armAngle)
					mfiState = MFI.PICKUP;
			}
			else if(mfiState == MFI.PICKUP){
				hasKit = true;
				myKit = kitStand.getKitFromStand(0);
				mfiState = MFI.MOVE_TO_STAND;

			}
			else if(mfiState == MFI.MOVE_TO_STAND){
				setDestinationX(standX);
				setDestinationY(standY);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(standX, standY);
				if(finalDistance == armLength && theta == armAngle)
					mfiState = MFI.DROPOFF;
				
			}
			else if(mfiState == MFI.DROPOFF){
				hasKit = false;
				kitStand.putKitOnStand(myKit, standNumber);
				myKit = null;
				mfiState = MFI.IDLE;
				myState = states.IDLE;
				agent.msgFinishedMovingBackToStand(standNumber);
			}
		}
		
		if(myState == states.MOVE_TO_DUMP){
			if(mtdState == MTD.MOVE_TO_INSPECTION){
				setDestinationX(inspectionX);
				setDestinationY(inspectionY);
				finalDistance = calculateDistance(destinationX, destinationY);
				if(getClawX() == getCenterX() && getClawY() == getCenterY())
					armAngle = setEndTheta(cartX + cartW/2, cartY + cartH/2);
				theta = setEndTheta(inspectionX, inspectionY);
				if(finalDistance == armLength && theta == armAngle)
					mtdState = MTD.PICKUP;
			}
			else if(mtdState == MTD.PICKUP)
			{
				hasKit = true;
				myKit = kitStand.getKitFromStand(0);
				mtdState = MTD.MOVE_TO_DUMP;
			}
			else if(mtdState == MTD.MOVE_TO_DUMP)
			{
				setDestinationX(dumpX);
				setDestinationY(dumpY);
				finalDistance = calculateDistance(destinationX, destinationY);
				theta = setEndTheta(dumpX, dumpY);
				if(finalDistance == armLength && theta == armAngle)
					mtdState = MTD.DROPOFF;
			}
			else if(mtdState == MTD.DROPOFF)
			{

				hasKit = false;
				if(zoom > -10){
					zoom -= 1;
				}
				else{
					agent.msgDoneDump();
					mtdState = MTD.IDLE;
					myState = states.IDLE;
					myKit = null;
				}

			}
		}

		if(armLength != finalDistance)
			armLength = stretchArm(finalDistance);
		if(armAngle != theta)
			armAngle = rotateArm(theta);
		
		if(mtdState == MTD.DROPOFF){
			System.out.printf("zoom out\n");
			myKit.zoom(zoom);
		}

		else if(hasKit)
		{
				myKit.setX(getClawX()+getClawWidth()/2-myKit.getWidth()/2);
				myKit.setY(getClawY()+getClawHeight()/2-myKit.getHeight()/2);
			
		}
	}
	
	 public void setKitRobotAgent(KitRobot kitRobotAgent) 
	 {
		 this.agent = kitRobotAgent;
	 }
	 public void setKitStand(GUIKitStand kit)
	 {
		 this.kitStand = kit;
	 }

	 public void DoMoveFromInspectionToStand(int location){
			standX = kitStand.getX() + 10 + KITHEIGHT/2;
			standY = kitStand.getY() + (location+1)*10 + location*KITHEIGHT + KITHEIGHT/2;
			
			inspectionX = kitStand.getX()+10 + KITHEIGHT/2;
			inspectionY = kitStand.getY()+10 + KITHEIGHT/2;
			
			standNumber = location;

			myState = states.MOVE_FROM_INSPECTION;
			mfiState = MFI.MOVE_TO_INSPECTION;
			
	 }
	public void DoMoveToStand(int i, GUICart cart) {
		cartX = cart.getX();
		cartY = cart.getY();
		cartW = cart.getWidth();
		cartH = cart.getHeight();
		myCart = cart;
		
		standX = kitStand.getX() + 10 + KITHEIGHT/2;
		standY = kitStand.getY() + (i+1)*10 + i*KITHEIGHT + KITHEIGHT/2;
		standNumber = i;
		
		myState = states.MOVE_TO_STAND;
		mtsState = MTS.MOVE_TO_CART;
	}

	public void DoMoveToInspection(int location) {
		System.out.println("Being Called by KitRobotAgent");
		standX = kitStand.getX() + 10 + KITHEIGHT/2;
		standY = kitStand.getY() + (location+1)*10 + location*KITHEIGHT + KITHEIGHT/2;
		
		inspectionX = kitStand.getX()+10 + KITHEIGHT/2;
		inspectionY = kitStand.getY()+10 + KITHEIGHT/2;
		standNumber = location;
		
		myState = states.MOVE_TO_INSPECTION;
		mtiState = MTI.MOVE_TO_STAND;
		//System.out.println("MSG ACCEPTED: " + myState);
	}

	public void DoMoveToCart(GUICart guiCart) {
		cartX = guiCart.getX();
		cartY = guiCart.getY();
		cartW = guiCart.getWidth();
		cartH = guiCart.getHeight();
		myCart = guiCart;
		myState = states.MOVE_TO_CART;
		mtcState = MTC.MOVE_TO_INSPECTION;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g.drawImage(base.getImage(), xpos,ypos, getBaseWidth(),getBaseHeight(), null);
		
		g2.rotate(Math.toRadians(-armAngle),getCenterX(),getCenterY());
		g2.drawImage(arm.getImage(), armX,armY, armLength,getArmWidth(), null);
		g2.rotate(Math.toRadians(armAngle),getCenterX(),getCenterY());
		
		g2.drawImage(armBase.getImage(), xpos + getBaseWidth()/2 - getArmBaseWidth()/2,
				ypos + getBaseHeight()/2 - getArmBaseHeight()/2,
				getArmBaseWidth(), getArmBaseHeight(), null);
		g2.drawImage(claw.getImage(), getClawX(),getClawY(), getClawWidth(), getClawHeight(), null);
	}
	
	// Override functions and extra functions
	
	public String getName() {
		return "GUIKitRobot";
	}
	public int getWidth() {
		return getBaseWidth();
	}
	public int getHeight() {
		return getBaseHeight();
	}

	public void setX(int x) 
	{
		xpos = x;
		armX = xpos + getBaseWidth()/2;
	}
	public void setY(int y) 
	{
		ypos = y;
		armY = ypos + getBaseHeight()/2 - getArmWidth()/2;
	}
	public int getX() 
	{
		return xpos;
	}
	public int getY() 
	{
		return ypos;
	}

	
	// GUIComponent overrides
	@Override
	public void givePart(GUIPart part, GUIComponent giver) {
		// TODO Auto-generated method stub
		
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
	public void DoDump() {
		// TODO Auto-generated method stub
		System.out.println("At Do Dump");
		zoom = 0;
//		myKit.setState(State.BAD);
		inspectionX = kitStand.getX()+10 + KITHEIGHT/2;
		inspectionY = kitStand.getY()+10 + KITHEIGHT/2;
		dumpX = inspectionX;
		dumpY = inspectionY - 100;

		myState = states.MOVE_TO_DUMP;
		mtdState = MTD.MOVE_TO_INSPECTION;
	}
	
	public void drawMyKit(Graphics g)
	{
		myKit.draw(g);
		for(GUIPart p : myKit.getPartsList())
		{
			p.draw(g);
		}
	}
}
