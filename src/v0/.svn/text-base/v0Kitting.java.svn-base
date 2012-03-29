package v0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import shared.enums.PartType;

import factory.*;
import gui.components.*;

@SuppressWarnings("serial")
public class v0Kitting
{
	// Constants
    private final int WINDOWX = 1000;
    private final int WINDOWY = 750;

    // MAIN FUNCTION
    public static void main(String[] args)
    {
    	// Instantiate the main JFrame
    	JFrame kittingCell = new JFrame();
    	kittingCell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	kittingCell.setTitle("Kitting Demo");
    	
    	// Instantiate the graphics panel (v0PartsAnimation)
    	v0KittingAnimation kitTestPanel = new v0KittingAnimation();
    	// Add the v0PartsAnimation panel to the main JFrame
    	kittingCell.add(kitTestPanel);
    	
    	kittingCell.pack();
    	kittingCell.setVisible(true);
    	
    	kitTestPanel.startAll();
    }

}
