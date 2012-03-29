package gui.components;

import java.awt.Graphics;
import java.util.ArrayList;
import gui.interfaces.GUIComponentInterface;

/**
 * Floor to keep track of parts that have been dropped by various components in the factory
 * @author Andrew Heiderscheit
 *
 */

@SuppressWarnings("serial")
public class GUIFactoryFloor extends GUIComponent implements GUIComponentInterface{

	ArrayList<GUIPart> partsOnFloor;
	long partsCounter; 
	
	@Override
	public void givePart(GUIPart part, GUIComponent giver)
	{
		// adds parts dropped on floor to it's list of parts
		partsOnFloor.add(part);
		//lets the giver know that the part was recieved
		giver.gotPart(part);
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
	public String getName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public GUIFactoryFloor()
	{
		//default constructor
		partsOnFloor = new ArrayList<GUIPart>();
		partsCounter = 0;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		partsCounter++;
		if (partsCounter == 10000){
			partsOnFloor.clear();
			System.gc();
		}
	}

	@Override
	public void draw(Graphics g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
