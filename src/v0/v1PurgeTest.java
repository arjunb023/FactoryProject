package v0;

import javax.swing.*;

import java.awt.*;
/**
 * CSCI200: Kitting Cell Project
 * 
 * This class extends JFrame and creates the main frame in which the panels are displayed
 * 
 * @author Jessica Yoshimi
 *
 */
public class v1PurgeTest
{
	// Constants
    private static final int WINDOWX = 1000;
    private static final int WINDOWY = 750;
    
    /**
     * The main function instantiates a JFrame and sets its frame specifications and displays the frame.
     * The function then calls the startAll() function on its factory panel.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
    	// Instantiate the main JFrame
    	JFrame kittingCell = new JFrame();
    	kittingCell.setPreferredSize(new Dimension(WINDOWX, WINDOWY));
    	kittingCell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	kittingCell.setTitle("Gantry, Feeder/Diverter Demo");
    	
    	// Instantiate the graphics panel (v0PartsAnimation)
    	v1PurgeTestAnimation gantryTestPanel = new v1PurgeTestAnimation();
    	// Add the v0PartsAnimation panel to the main JFrame
    	kittingCell.add(gantryTestPanel);
    	
    	kittingCell.pack();
    	kittingCell.setVisible(true);
    	
    	gantryTestPanel.startAll();
    }

}
