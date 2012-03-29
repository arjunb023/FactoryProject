package gui.components;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.components.*;
import factory.*;
import factory.interfaces.KitRobot;
import gui.interfaces.*;

@Deprecated
public class GUIKitRobot extends GUIComponent implements GUIComponentInterface, GUIKitRobotInterface {
	public int semix, semiy, beginx, beginy, finalx, finaly, semitheta, theta;
	private double currentAngle;
	ImageIcon kitrobotarm, armBase, armTip, base, claw, gripH, gripV;
	boolean arrivedDestination, finishedClamping, clampHasObject;
	boolean doDump, doClamp, startMoving;
	int TRANSLATEX = 200;
	int TRANSLATEY = 200;
	KitRobot agent;
	GUIKitStand kitStand;
	GUIKit kit;
	GUICart cart;
	int i;
	Point kitStandDownPoint, kitStandUpPoint, kitStandInspection;
	String name ;
	double scale;
	JButton fromInspectionToCart, fromAssemblyOneToInspection, fromAssemblyTwoToInspection, fromCartToInspectionOne, fromCartToInspectionTwo;

	@Deprecated
	public GUIKitRobot(String name) {

		this.name = name;
		
		kitrobotarm = new ImageIcon("images/base.png", "robot arm");
		claw = new ImageIcon("images/kitting_robot_claw_rotated.png", "claw");
		currentAngle = 0;
		doDump = false;
		arrivedDestination = false;
		finishedClamping = true;
		clampHasObject = false;	
		doClamp = false;
		startMoving = false;
		i = 0;
		scale = 0.6;
		fromInspectionToCart = new JButton();
		fromAssemblyOneToInspection = new JButton();
		fromAssemblyTwoToInspection = new JButton();
		fromCartToInspectionOne = new JButton();
		fromCartToInspectionTwo = new JButton();
		
	}
	 public void setKitRobotAgent(KitRobot kitRobotAgent) {
		 this.agent = kitRobotAgent;
	 }
	 public void setKitStand(GUIKitStand kit){
		 this.kitStand = kit;
	 }
	public void DoMoveToStand(int i, GUICart cart) {
		// TODO Auto-generated method stub
		arrivedDestination = false;
		finishedClamping = true;
		doClamp = true;
		if(i == 2){
			semix = cart.getX();
			semiy = cart.getY();
			finalx = kitStand.getX() + 10;
			finaly = kitStand.getY() + i * kitStand.getHeight() + (i + 1) * 10;
		}
		else if(i == 1){
			semix = cart.getX();
			semiy = cart.getY();
			finalx = kitStand.getX() + 10;
			finaly = kitStand.getY() + i * kitStand.getHeight() + (i + 1) * 10;
		}
	}

	public void DoMoveToInspection(int location) {
		// TODO Auto-generated method stub
			semix = kitStand.getX() + 10;
			semiy = kitStand.getY() + i * kitStand.getHeight() + (i + 1) * 10;
			finalx = kitStand.getX() + 10;
			finaly = kitStand.getY() + 2 * kitStand.getHeight() + 30;
			doClamp = true;
	}

	public void DoMoveToCart(GUICart guiCart) {
		// TODO Auto-generated method stub
		finalx = guiCart.getX();
		finaly = guiCart.getY();
		semix = kitStand.getX() + 10;
		semiy = kitStand.getY() + i * kitStand.getHeight() + (i + 1) * 10;
		doClamp = true;
	}
	
	public void setX(int x) {
		TRANSLATEX = x;
	}

	public void setY(int y) {
		TRANSLATEY = y;
	}

	public int getX() {
		return TRANSLATEX;
	}

	public int getY() {
		return TRANSLATEY;
	}
	public void DoDump() {
		//finalx = some empty space;
		//finaly = some empty space;
	}

	public String getName() {
		return this.name;
	}

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(path, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	public void update(){
		double distance;

		distance = distance(beginx, beginy, semix, semiy);

		theta = (int)Math.toDegrees(Math.acos(((2 * 210 * 210) - distance * distance) / (2 * 210 * 210)));
		theta = checkQuadrant(semix, semiy, theta);
		if(currentAngle % 360 == 0)
			currentAngle = 0;
		if(startMoving){
			if(theta < 0){
				currentAngle -= 1;
			}else if(theta == 0){}
			else{
				currentAngle += 1;
			}
		}
	}

	private int checkQuadrant(int x, int y, int theta){
		if(x > beginx && y > beginy){
			return -theta;
		}
		else if(x > beginx && y < beginy){
			return -theta;
		}
		else if(x < beginx && y < beginy){
			return -theta;
		}
		else if( x < beginx && y > beginy){
			return theta;
		}
		return theta;
	}

	public double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	private void getFinalCenter(ImageIcon image){
		int tx = -(image.getIconWidth() / 2);
		int ty = -(image.getIconHeight() / 2);

		double rot = currentAngle * Math.PI / 180;
		double newX = (tx)*Math.cos(rot) - (ty)*Math.sin(rot) + image.getIconWidth()/2;
		double newY = (tx)*Math.sin(rot) + (ty)*Math.cos(rot) + image.getIconHeight()/2;	    
		beginx = (int)newX;
		beginy = (int)newY;
	}

/*	private void clamping(Graphics2D g2){
		//	if(finishedMoving == true && clamp == true){
		AffineTransform scalingTransform = new AffineTransform();
		//		System.out.println(rect.getX() + ((rect.getWidth() - (rect.getWidth() * scaling)) / 2));
		//		rect.setRect(rect.getX() + ((rect.getWidth() - (rect.getWidth() * scaling)) / 2), rect.getY() + ((rect.getHeight() - (rect.getHeight() * scaling)) / 2), rect.getHeight() * scaling, rect.getWidth() * scaling);
		//		}
		//		System.out.printf("%f %f\n", rect.getX(), rect.getY());
		g2.setTransform(scalingTransform);
		g2.draw(rect);
		if(clampHasObject == true){
			//move the object from clamp to kit stand / cart
			clampHasObject = false;		
		}
		else{
			crawled = rect;
			clampHasObject = true;
		}
		arrivedDestination = false;
	}
*/


	private void clamp(){
		if(clampHasObject == false){							//clamp doesn't have an object
			if(semiy == kitStand.getY() + 2 * kitStand.getHeight() + 30 && semix == kitStand.getX() + 10){
				agent.msgFinishedMovingToStand(2);
				kit = kitStand.getKitFromStand(2);
			}else if(semix == kitStand.getY() + 1 * kitStand.getHeight() + 10 && semix == kitStand.getX() + 10){
				agent.msgFinishedMovingToInspection();
				kit = kitStand.getKitFromStand(0);
			}//move to inspection
			else if(semiy == kitStand.getY() + 1 * kitStand.getHeight() + 20){
				agent.msgFinishedMovingToStand(1);
				kit = kitStand.getKitFromStand(1);
			}else if(semiy == cart.getY()){
				kit = cart.getKit();
			}
			clampHasObject = true;
		}
		else{												//clamp has an object
			if(finaly == kitStand.getY() + 10 && finalx == kitStand.getX() + 10){		//go to inspection stand with kit
				kitStand.putKitOnStand(kit, 2);
				agent.msgFinishedMovingToStand(2);
			}
			else if(finalx == cart.getX() && finaly == cart.getY()){							//go to cart with kit
				cart.giveKit(kit);
			}
			else if(finalx == kitStand.getX() + 10 && finaly == kitStand.getY() + 1 * kitStand.getHeight() + 20 ){				//check kitstand 1 
				kit = kitStand.getKitFromStand(1);
				agent.msgFinishedMovingToStand(1);
			}
			else if(finalx == kitStand.getX() + 10 && finaly == kitStand.getX() + 2 * kitStand.getHeight() + 10){
				kit = kitStand.getKitFromStand(0);
				agent.msgFinishedMovingToInspection();
			}
			else if(doDump == true){
				kit = null;	
				doDump = false;									//do dump
			}
			clampHasObject = false;
		}
		doClamp = false;
	}

	public void setScale(double scale){
		this.scale = scale;
	}
	public double getScale(){return this.scale;}
	
	private void drawImages(AffineTransform newXform, Graphics2D g2){
		g2.setTransform(newXform);
		g2.drawImage(kitrobotarm.getImage(), 0, 0,
				(int) kitrobotarm.getIconWidth(),
				(int) kitrobotarm.getIconHeight(), null);
		newXform.translate(80, 0);
		g2.setTransform(newXform);
		g2.drawImage(claw.getImage(), 0, 0, (int)claw.getIconWidth(), (int)claw.getIconHeight(), null);
	}

	private void rotateClamp(Graphics2D g2) {

		AffineTransform newXform = new AffineTransform();
		newXform.setToScale(scale, scale);
		newXform.translate(TRANSLATEX, TRANSLATEY);
		
		if(theta < 0){											//for semitheta < 0
			if((int)currentAngle == theta){
				newXform.rotate(Math.toRadians(theta), 25,
						kitrobotarm.getIconHeight() / 2);
				beginx = semix;
				beginy = semiy;
				semix = finalx;
				semiy = finaly;
				if(doClamp == true)
					clamp();
			}else{
				newXform.rotate(Math.toRadians(currentAngle), 25,
						kitrobotarm.getIconHeight() / 2);
				arrivedDestination = false;
			}
		}
		else{																		//for semitheta > 0
			if (currentAngle < theta) {
				newXform.rotate(Math.toRadians(currentAngle), 25,
						kitrobotarm.getIconHeight() / 2);

			} else if(currentAngle == theta){

				newXform.rotate(Math.toRadians(theta), 25,
						kitrobotarm.getIconHeight() / 2);
				beginx = semix;
				beginy = semiy;
				semix = finalx;
				semiy = finaly;
				if(doClamp == true)
					clamp();
			}
		}
		drawImages(newXform, g2);
	}

	public void draw(Graphics g) {
		
		if(i != 0){	
			Graphics2D temp = (Graphics2D)g;
			AffineTransform original = temp.getTransform();
			rotateClamp((Graphics2D)g);
			((Graphics2D)g).setTransform(original);
		}
		i++;
	}
	
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
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getHeight() {
		return 0;
	}
	@Override
	public void DoMoveFromInspectionToStand(int locate) {
		// TODO Auto-generated method stub
		
	}
}