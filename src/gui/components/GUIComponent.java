package gui.components;

import gui.interfaces.GUIComponentInterface;
import java.awt.*;

import java.io.Serializable;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class GUIComponent extends JLabel implements Serializable, GUIComponentInterface
{
	protected GUIComponent()
	{
		
	}
	
	public abstract String getName();
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public abstract void setX(int x);
	
	public abstract void setY(int y);
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
}
