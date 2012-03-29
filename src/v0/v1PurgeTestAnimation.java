package v0;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import factory.*;
import gui.components.*;
import shared.Bin;
import shared.KitConfig;
import shared.enums.*;


/**
 * CSCI200: Kitting Cell Project
 * 
 * v0GantryAnimation is a panel class in which the components for the factory animation are instantiated and animated.
 * 
 * Variables:
 * 		ArrayList<GUIComponent> underParts, parts, overParts: Designate draw order such that parts don't overlap poorly
 * 		Timer timer
 * Agents and GUI Components:
 * 		Gantry, Feeder, Diverter, Lane x2, Nest x2
 * 
 * @author Jessica Yoshimi
 *
 */
@SuppressWarnings("serial")
public class v1PurgeTestAnimation extends JPanel implements ActionListener
{
	// ArrayLists of all GUI components in the factory, sectioned into ArrayLists based on paint order
	public static ArrayList<GUIComponent> underParts = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> parts = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> overParts  = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> bins = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> robots = new ArrayList<GUIComponent>();

	// Timer variable
	protected Timer timer = new Timer(1, this);
	
	//--------------------------------------------------------------------------------------
	
	ArrayList<Bin> gantryBins = new ArrayList<Bin>() {
		{
			add(new Bin(PartType.A));
			add(new Bin(PartType.B));
			add(new Bin(PartType.C));
			add(new Bin(PartType.D));
			add(new Bin(PartType.E));
			add(new Bin(PartType.F));
			add(new Bin(PartType.G));
			add(new Bin(PartType.H));			
		}
	};
	
	// Instantiate Agents
	GantryAgent gantry = new GantryAgent("Gantry");
	FeederAgent feeder = new FeederAgent("Feeder1");
   	DiverterAgent diverter = new DiverterAgent("Diverter1");
   	LaneAgent lane1 = new LaneAgent("Lane1");
   	LaneAgent lane2 = new LaneAgent("Lane2");
   	NestAgent nest1 = new NestAgent("Nest1");
   	NestAgent nest2 = new NestAgent("Nest2");
   	CameraSystemAgent cameraSystem = new CameraSystemAgent("CameraSystem");
   	KitRobotAgent kitRobot = new KitRobotAgent("KitRobot");
   	
   	
   	
   	
   	CellManagerAgent manager = new CellManagerAgent("Manager");
   	
   	
   	
   	
   	// Instantiate GUI components
   	GUIGantry guiGantry = new GUIGantry("guiGantry");
	GUIFeeder guiFeeder = new GUIFeeder("guiFeeder");
   	GUIDiverter guiDiverter = new GUIDiverter();
   	GUINest guiNest1 = new GUINest(100,300,0);
   	GUINest guiNest2 = new GUINest(100,360,0);
   	GUILane guiLane1 = new GUILane(178,300,3,guiNest1);
   	GUILane guiLane2 = new GUILane(178,360,3,guiNest2);

   	GUIFactoryFloor guiFactoryFloor = new GUIFactoryFloor();
   	
   	// Instantiate ImageIcons for the rail graphics
   	ImageIcon verticalRail = new ImageIcon("images/straight_rail.png");
   	ImageIcon railBranch = new ImageIcon("images/rail_branch.png");
   	
   	//------------------------------------------------------------------
   	
   	/**
   	 * The v0GantryAnimation() constructor sets the panel attributes and sets up the GUI/Agent connections.
   	 * Set connections between dependencies between agents, the X & Y coordinates of the elements, and
   	 * starts the Agent threads.
   	 */
	public v1PurgeTestAnimation()
	{
		// Edit panel settings
		setPreferredSize(new Dimension(700,700));
		setBackground(Color.GRAY);
		setLayout(new BorderLayout());
		
		int x = 0;
		for (Bin bin:gantryBins) {
			bin.guiBin.setX(x);
			bin.guiBin.setY(150);
			x+=50;
		}
		
		// Set the GUI components of the agents
    	gantry.setGUIGantry(guiGantry);
       	feeder.setGUIFeeder(guiFeeder);
       	diverter.setGuiDiverter(guiDiverter);
       	lane1.setGuiLane(guiLane1);
       	lane2.setGuiLane(guiLane2);
       	nest1.setGuiNest(guiNest1);
       	nest2.setGuiNest(guiNest2);
       	nest1.setCameraSystemAgent(cameraSystem);
       	nest2.setCameraSystemAgent(cameraSystem);
       	gantry.setGUIGantry(guiGantry);
       	
       	// Set the agent components' GUI components
    	guiGantry.setGantryAgent(gantry);
       	guiFeeder.setFeederAgent(feeder);
       	guiDiverter.setDiverterAgent(diverter);
       	guiDiverter.setLaneOne(guiLane1);
       	guiDiverter.setLaneTwo(guiLane2);
       	guiLane1.setLaneAgent(lane1);
       	guiLane2.setLaneAgent(lane2);
       	guiNest1.setNestAgent(nest1);
       	guiNest2.setNestAgent(nest2);
   
       	// Set the dependencies between agents
       	gantry.setGantryBins(gantryBins);
       	feeder.setDiverterAgent(diverter);
       	feeder.setGantryAgent(gantry);
       	diverter.setFeederAgent(feeder);
       	diverter.setLaneAgent1(lane1);
       	diverter.setLaneAgent2(lane2);
		lane1.setFeederAgent(feeder);		
       	lane1.setDiverterAgent(diverter);
       	lane1.setNestAgent(nest1);
		lane2.setFeederAgent(feeder);
       	lane2.setDiverterAgent(diverter);
       	lane2.setNestAgent(nest2);
       	
       	// Add GUI Components to the ArrayLists
       	addUnderParts(guiFactoryFloor);
       	addUnderParts(guiNest1);
       	addUnderParts(guiNest2);
       	addUnderParts(guiLane1);
       	addUnderParts(guiLane2);
       	addUnderParts(guiDiverter);
       	
       	addOverParts(guiFeeder);
       	robots.add(guiGantry);

       	// Set X & Y positions of the elements
       	guiLane1.setX(178);
       	guiLane1.setY(300);
       	guiLane2.setX(178);
       	guiLane2.setY(360);
       	guiFeeder.setX(guiLane1.getX() + guiLane1.getWidth()+20); //440
       	guiFeeder.setY(guiLane1.getY());
       	// @MIKE: HOW DO I SET THE DIVERTER POSITION WITH RESPECT TO THE FEEDER?
       	guiDiverter.setX(guiFeeder.getX()-37);
       	guiDiverter.setY(guiFeeder.getY()+33);
       	
       	
       	
       	manager.setKitRobot(kitRobot);
       	//kitRobot.setCameraSystemAgent(cameraSystem);
       	kitRobot.setCellManagerAgent(manager);
       	//kitRobot.setGuiKitRobot(guiKitRobot);
       	//kitRobot.setPartsRobotAgent(partsRobotAgent);
       	
       	manager.addConfiguredAgent(lane1);
       	manager.addConfiguredAgent(lane2);
       	manager.addConfiguredAgent(diverter);
       	manager.addConfiguredAgent(nest1);
       	manager.addConfiguredAgent(nest2);
       	manager.addConfiguredAgent(feeder);
       	manager.startThread();
       	diverter.setCellManagerAgent(manager);
       	lane1.setCellManagerAgent(manager);
       	lane2.setCellManagerAgent(manager);
       	nest1.setCellManagerAgent(manager);
       	nest1.setNestNum(1);
       	nest2.setCellManagerAgent(manager);
       	nest2.setNestNum(2);
       	feeder.setCellManagerAgent(manager);
       	
       	
       	
    	// Start Agent threads
       	gantry.startThread();
    	//gantry.msgGiveMeThisPart(PartType.A, feeder);
    	//feeder.msgSendMoreParts(lane1, PartType.A);
    	feeder.startThread();
    	diverter.startThread();
    	lane1.startThread();
    	lane2.startThread();
    	nest1.startThread();
    	nest2.startThread();
    	//cameraSystem.startThread();
    	//kitRobot.startThread();
    	
    	
    	ButtonsPanel bpan = new ButtonsPanel();
    	add(bpan, BorderLayout.EAST);
	}
	
	/**
	 * Runs the update functions of the three ArrayLists of parts to be painted and calls repaint()
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		for (int i = 0; i < underParts.size(); i++)
			underParts.get(i).update();
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).update();
		for (int i = 0; i < overParts.size(); i++)
			overParts.get(i).update();
		
		repaint();
		
	}
	
	/**
	 * Loops through the three paint ArrayLists (underParts, parts, and overParts) and calls the draw(Graphics g)
	 * function.
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < underParts.size(); i++)
			underParts.get(i).draw(g);
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).draw(g);
		for (int i = 0; i < overParts.size(); i++)
			overParts.get(i).draw(g);
		for (int i = 0 ; i < bins.size(); i++)
			bins.get(i).draw(g);
		for (int i = 0; i < robots.size(); i++)
			robots.get(i).draw(g);
		g.drawImage(verticalRail.getImage(),590,0,10,750,null);
		g.drawImage(railBranch.getImage(),500,322,100,50,null);
	}
	
	/**
	 * Adds GUIComponent cp to the underParts ArrayList
	 * @param cp
	 */
	protected void addUnderParts(GUIComponent cp)
	{
		underParts.add(cp);
	}
	
	/**
	 * Adds GUIComponent cp to the overParts ArrayList
	 * @param cp
	 */
	protected void addOverParts(GUIComponent cp)
	{
		overParts.add(cp);
	}
	
	/**
	 * Adds GUIComponent cp to the parts ArrayList
	 * @param cp
	 */
	protected void addParts(GUIComponent cp)
	{
		parts.add(cp);
	}
	
	/**
	 * Starts the timer
	 */
	public void startAll() 
	{
		timer.start();
	}
	
	public class ButtonsPanel extends JPanel implements ActionListener
	{
		JButton butt1 = new JButton("nest okay");
		JButton butt2 = new JButton(".");
		JButton butt3 = new JButton("purge Nests");
		JButton butt4 = new JButton("Config B,C");
		JButton butt5 = new JButton("Config F,G");
		
		public ButtonsPanel()
		{
			setBackground(Color.DARK_GRAY);
			setPreferredSize(new Dimension(100,750));
			
			// Set up the ActionListener functionality
			butt1.addActionListener(this);
			butt2.addActionListener(this);
			butt3.addActionListener(this);
			butt4.addActionListener(this);
			butt5.addActionListener(this);
			
			// Add the buttons to the button panel
			add(butt1);
			add(butt2);
			add(butt3);
			add(butt4);
			add(butt5);
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand() == "nest okay")
			{
				manager.msgIAmProperlyConfigured(nest1);
				manager.msgIAmProperlyConfigured(nest2);
			}
			else if(ae.getActionCommand() == ".")
			{
				//feeder.msgSendMoreParts(lane1, PartType.B);	
			}
			else if(ae.getActionCommand() == "purge Nests")
			{
				System.out.println("*******************************\nPURGE NESTS BUTTON\n************************************");
				nest1.msgDumpParts();
				nest2.msgDumpParts();

				//guiNest1.dumpParts();
				//guiNest1.fullState = false;
				//guiNest2.dumpParts();
				//guiNest2.fullState = false;

				// WHAT THE ****
				//lane1.msgGuiLaneNoLongerFull();
				//lane2.msgGuiLaneNoLongerFull();
			}
			else if(ae.getActionCommand() == "Config B,C")
			{
				//manager.msgCompletedAllKitsForCurrentJob();
				
				ArrayList<PartType> kitSpec = new ArrayList();
				kitSpec.add(PartType.B);
				kitSpec.add(PartType.C);
				kitSpec.add(PartType.B);
				kitSpec.add(PartType.C);
				kitSpec.add(PartType.B);
				kitSpec.add(PartType.C);
				kitSpec.add(PartType.B);
				kitSpec.add(PartType.C);
				manager.msgCreateNewJob(kitSpec, 20);
				
				KitConfig newConfig = new KitConfig(kitSpec);
				lane1.msgThisIsCurrentConfiguration(newConfig);
				lane2.msgThisIsCurrentConfiguration(newConfig);
				diverter.msgThisIsCurrentConfiguration(newConfig);
				feeder.msgThisIsCurrentConfiguration(newConfig);
				nest1.msgThisIsCurrentConfiguration(newConfig);
				nest2.msgThisIsCurrentConfiguration(newConfig);
			}
			else if(ae.getActionCommand() == "Config F,G")
			{
				//manager.msgCompletedAllKitsForCurrentJob();
				
				ArrayList<PartType> kitSpec = new ArrayList();
				kitSpec.add(PartType.F);
				kitSpec.add(PartType.G);
				kitSpec.add(PartType.F);
				kitSpec.add(PartType.G);
				kitSpec.add(PartType.F);
				kitSpec.add(PartType.G);
				kitSpec.add(PartType.F);
				kitSpec.add(PartType.G);
				manager.msgCreateNewJob(kitSpec, 20);
				
				KitConfig newConfig = new KitConfig(kitSpec);
				lane1.msgThisIsCurrentConfiguration(newConfig);
				lane2.msgThisIsCurrentConfiguration(newConfig);
				diverter.msgThisIsCurrentConfiguration(newConfig);
				feeder.msgThisIsCurrentConfiguration(newConfig);
				nest1.msgThisIsCurrentConfiguration(newConfig);
				nest2.msgThisIsCurrentConfiguration(newConfig);
			}
		}
	}
}
