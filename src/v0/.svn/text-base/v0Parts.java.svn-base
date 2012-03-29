package v0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import shared.enums.PartType;

import factory.*;
import gui.components.*;

/**
 * CSCI200: Kitting Cell Project
 * 
 * This class extends JFrame and creates the main frame in which the panels are displayed for the parts robot testing
 * 
 * @author jessicayoshimi
 *
 */
@SuppressWarnings("serial")
public class v0Parts
{
	// Constants
    private final int WINDOWX = 1000;
    private final int WINDOWY = 750;
    
    private final int KITSIZE = 8;

    // MAIN FUNCTION
    public static void main(String[] args)
    {
    	// Instantiate the main JFrame
    	JFrame kittingCell = new JFrame();
    	kittingCell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	kittingCell.setTitle("Nest, Parts Robot, Kit Stand Demo");
    	kittingCell.setLayout(new BorderLayout());
    	
    	// Instantiate the graphics panel (v0PartsAnimation)
    	v0PartsAnimation partsTestPanel = new v0PartsAnimation();
    	
    	// Add the v0PartsAnimation panel to the main JFrame
    	kittingCell.add(partsTestPanel, BorderLayout.CENTER);
    	
    	kittingCell.pack();
    	kittingCell.setVisible(true);
    }

}
