package gui.panels;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.*;

import main.KittingCell;

import shared.*;
import shared.enums.*;

public class DisasterPanel extends JPanel implements ActionListener {
	FactoryPanel myFactory;
	
	String disaster1name = "Camera Shoots Too Soon";
	String disaster2name = "Surprise!";
	String disaster3name = "";
	String disaster4name = "Broken Gantry";
	String disaster5name1 = "Parts Robot Misses Kit";
	String disaster5name2 = "Fix Parts Robot";
	String disaster6name = "Explode Parts";
	
	private Clip clip;
	
	JButton disaster1 = new JButton(disaster1name);
	JButton disaster2 = new JButton(disaster2name);
	JButton disaster3 = new JButton(disaster3name);
	JButton disaster4 = new JButton(disaster4name);
	JButton disaster5 = new JButton(disaster5name1);
	JButton disaster6 = new JButton(disaster6name);

	public DisasterPanel(FactoryPanel factory)
	{
		myFactory = factory;
		
		// Set panel attributes
		setPreferredSize(new Dimension(250,750));
		setLayout(new GridLayout(8,1,5,5));
		
		// Add action listeners
		disaster1.addActionListener(this);
		disaster2.addActionListener(this);
		disaster3.addActionListener(this);
		disaster4.addActionListener(this);
		disaster5.addActionListener(this);
		disaster6.addActionListener(this);
		
		add(disaster1);
		//add(disaster3);
		//add(disaster4);
		add(disaster5);
		add(disaster6);
		add(disaster2);
	}
	  
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand() == disaster1name)
		{
			System.out.println("Disaster 1 triggered");
			/*
			myFactory.cameraSystem.msgIAmFull(myFactory.nest1, myFactory.guiNest1.doGetGuiParts());
			myFactory.cameraSystem.msgIAmFull(myFactory.nest2, myFactory.guiNest2.doGetGuiParts());
			
			myFactory.guiLane1.msgDoIncreaseAmplitude();
			myFactory.guiLane2.msgDoIncreaseAmplitude();
			*/
			
			myFactory.guiNest1.changeNormative();
			myFactory.guiNest2.changeNormative();
		}
		else if(ae.getActionCommand() == disaster2name)
		{
			System.out.println("Disaster 2 triggered");
			if (myFactory.guiPartsRobot.getFace() == 0){
				myFactory.guiPartsRobot.setChinskiBot();
				try {
			          clip = AudioSystem.getClip();
			          File fileIn = new File("sound/trololo.wav");
			          AudioInputStream inputStream = AudioSystem.getAudioInputStream(fileIn);
			          clip.open(inputStream);
			          //clip.start();
			          clip.loop(Clip.LOOP_CONTINUOUSLY);
			        } catch (Exception e) {
			          System.err.println(e.getMessage());
			        }
				//playSound("sound/yay.wav");
			} else if (myFactory.guiPartsRobot.getFace() == 1){
				myFactory.guiPartsRobot.setClownBot();
				clip.stop();
				try {
			          clip = AudioSystem.getClip();
			          File fileIn = new File("sound/yay.wav");
			          AudioInputStream inputStream = AudioSystem.getAudioInputStream(fileIn);
			          clip.open(inputStream);
			          //clip.start();
			          clip.loop(Clip.LOOP_CONTINUOUSLY);
			        } catch (Exception e) {
			          System.err.println(e.getMessage());
			        }
				
			} else {
				myFactory.guiPartsRobot.setDefaultBot();
				clip.stop();	
			}
		}
		else if(ae.getActionCommand() == disaster3name)
		{
			System.out.println("Disaster 3 triggered");
		}
		else if(ae.getActionCommand() == disaster4name)
		{
			System.out.println("Disaster 4 triggered");
			myFactory.guiGantry.msgBreakGantry();
		}
		else if(ae.getActionCommand() == disaster5name1)
		{
				System.out.println("Disaster 5 triggered");
				myFactory.partsRobot.setFaulty(true);
				disaster5.setText(disaster5name2);
		}
		else if(ae.getActionCommand() == disaster5name2)
		{
				System.out.println("Fix parts robot triggered");
				myFactory.partsRobot.setFaulty(false);
				disaster5.setText(disaster5name1);
		}
		else if(ae.getActionCommand() == disaster6name)
		{
			System.out.println("Disaster 6 triggered");
			myFactory.explodeParts();
		}
	}
}
