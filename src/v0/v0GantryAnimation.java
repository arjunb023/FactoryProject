package v0;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import factory.*;
import gui.components.*;
import shared.Bin;
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
public class v0GantryAnimation extends JPanel implements ActionListener
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
	FeederAgent feeder1 = new FeederAgent("Feeder1");
	FeederAgent feeder2 = new FeederAgent("Feeder2");
	FeederAgent feeder3 = new FeederAgent("Feeder3");
	FeederAgent feeder4 = new FeederAgent("Feeder4");
   	DiverterAgent diverter1 = new DiverterAgent("Diverter1");
   	DiverterAgent diverter2 = new DiverterAgent("Diverter2");
   	DiverterAgent diverter3 = new DiverterAgent("Diverter3");
   	DiverterAgent diverter4 = new DiverterAgent("Diverter4");
   	LaneAgent lane1 = new LaneAgent("Lane1");
   	LaneAgent lane2 = new LaneAgent("Lane2");
   	LaneAgent lane3 = new LaneAgent("Lane3");
   	LaneAgent lane4 = new LaneAgent("Lane4");
   	LaneAgent lane5 = new LaneAgent("Lane5");
   	LaneAgent lane6 = new LaneAgent("Lane6");
   	LaneAgent lane7 = new LaneAgent("Lane7");
   	LaneAgent lane8 = new LaneAgent("Lane8");
   	NestAgent nest1 = new NestAgent("Nest1");
   	NestAgent nest2 = new NestAgent("Nest2");
   	NestAgent nest3 = new NestAgent("Nest3");
   	NestAgent nest4 = new NestAgent("Nest4");
   	NestAgent nest5 = new NestAgent("Nest5");
   	NestAgent nest6 = new NestAgent("Nest6");
   	NestAgent nest7 = new NestAgent("Nest7");
   	NestAgent nest8 = new NestAgent("Nest8");
   	CameraSystemAgent cameraSystem = new CameraSystemAgent("CameraSystem");
   	
   	// Instantiate GUI components
   	GUIGantry guiGantry = new GUIGantry("guiGantry");
   	GUINest guiNest1 = new GUINest(100,300,0);
   	GUINest guiNest2 = new GUINest(100,360,0);
   	GUINest guiNest3 = new GUINest(100,420,0);
   	GUINest guiNest4 = new GUINest(100,480,0);
   	GUINest guiNest5 = new GUINest(100,540,0);
   	GUINest guiNest6 = new GUINest(100,600,0);
   	GUINest guiNest7 = new GUINest(100,660,0);
   	GUINest guiNest8 = new GUINest(100,720,0);
   	GUILane guiLane1 = new GUILane(178,300,3,guiNest1);
   	GUILane guiLane2 = new GUILane(178,360,3,guiNest2);
   	GUILane guiLane3 = new GUILane(178,420,3,guiNest3);
   	GUILane guiLane4 = new GUILane(178,480,3,guiNest4);
   	GUILane guiLane5 = new GUILane(178,540,3,guiNest5);
   	GUILane guiLane6 = new GUILane(178,600,3,guiNest6);
   	GUILane guiLane7 = new GUILane(178,660,3,guiNest7);
   	GUILane guiLane8 = new GUILane(178,720,3,guiNest8);
	GUIFeeder guiFeeder1 = new GUIFeeder("guiFeeder1",guiLane1.getX() + guiLane1.getWidth()+20,guiLane1.getY());
	GUIFeeder guiFeeder2 = new GUIFeeder("guiFeeder2",guiLane3.getX() + guiLane3.getWidth()+20,guiLane3.getY());
	GUIFeeder guiFeeder3 = new GUIFeeder("guiFeeder3",guiLane5.getX() + guiLane5.getWidth()+20,guiLane5.getY());
	GUIFeeder guiFeeder4 = new GUIFeeder("guiFeeder4",guiLane7.getX() + guiLane7.getWidth()+20,guiLane7.getY());
   	GUIDiverter guiDiverter1 = new GUIDiverter("guiDiverter1",guiFeeder1.getX()-37,guiFeeder1.getY()+33);
   	GUIDiverter guiDiverter2 = new GUIDiverter("guiDiverter2",guiFeeder2.getX()-37,guiFeeder2.getY()+33);
   	GUIDiverter guiDiverter3 = new GUIDiverter("guiDiverter3",guiFeeder3.getX()-37,guiFeeder3.getY()+33);
   	GUIDiverter guiDiverter4 = new GUIDiverter("guiDiverter4",guiFeeder4.getX()-37,guiFeeder4.getY()+33);
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
	public v0GantryAnimation()
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
       	feeder1.setGUIFeeder(guiFeeder1);
       	feeder2.setGUIFeeder(guiFeeder2);
       	feeder3.setGUIFeeder(guiFeeder3);
       	feeder4.setGUIFeeder(guiFeeder4);
       	diverter1.setGuiDiverter(guiDiverter1);
       	diverter2.setGuiDiverter(guiDiverter2);
       	diverter3.setGuiDiverter(guiDiverter3);
       	diverter4.setGuiDiverter(guiDiverter4);
       	lane1.setGuiLane(guiLane1);
       	lane2.setGuiLane(guiLane2);
       	lane3.setGuiLane(guiLane3);
       	lane4.setGuiLane(guiLane4);
       	lane5.setGuiLane(guiLane5);
       	lane6.setGuiLane(guiLane6);
       	lane7.setGuiLane(guiLane7);
       	lane8.setGuiLane(guiLane8);
       	nest1.setGuiNest(guiNest1);
       	nest2.setGuiNest(guiNest2);
       	nest3.setGuiNest(guiNest3);
       	nest4.setGuiNest(guiNest4);
       	nest5.setGuiNest(guiNest5);
       	nest6.setGuiNest(guiNest6);
       	nest7.setGuiNest(guiNest7);
       	nest8.setGuiNest(guiNest8);
       	nest1.setCameraSystemAgent(cameraSystem);
       	nest2.setCameraSystemAgent(cameraSystem);
       	nest3.setCameraSystemAgent(cameraSystem);
       	nest4.setCameraSystemAgent(cameraSystem);
       	nest5.setCameraSystemAgent(cameraSystem);
       	nest6.setCameraSystemAgent(cameraSystem);
       	nest7.setCameraSystemAgent(cameraSystem);
       	nest8.setCameraSystemAgent(cameraSystem);
       	gantry.setGUIGantry(guiGantry);
       	
       	// Set the agent components' GUI components
    	guiGantry.setGantryAgent(gantry);
       	guiFeeder1.setFeederAgent(feeder1);
       	guiFeeder2.setFeederAgent(feeder2);
       	guiFeeder3.setFeederAgent(feeder3);
       	guiFeeder4.setFeederAgent(feeder4);
       	guiDiverter1.setDiverterAgent(diverter1);
       	guiDiverter2.setDiverterAgent(diverter2);
       	guiDiverter3.setDiverterAgent(diverter3);
       	guiDiverter4.setDiverterAgent(diverter4);
       	
       	guiDiverter1.setLaneOne(guiLane1);
       	guiDiverter1.setLaneTwo(guiLane2);
       	guiDiverter2.setLaneOne(guiLane3);
       	guiDiverter2.setLaneTwo(guiLane4);
       	guiDiverter3.setLaneOne(guiLane5);
       	guiDiverter3.setLaneTwo(guiLane6);
       	guiDiverter4.setLaneOne(guiLane7);
       	guiDiverter4.setLaneTwo(guiLane8);
       	
       	guiLane1.setLaneAgent(lane1);
       	guiLane2.setLaneAgent(lane2);
       	guiLane3.setLaneAgent(lane3);
       	guiLane4.setLaneAgent(lane4);
       	guiLane5.setLaneAgent(lane5);
       	guiLane6.setLaneAgent(lane6);
       	guiLane7.setLaneAgent(lane7);
       	guiLane8.setLaneAgent(lane8);       	
       	guiNest1.setNestAgent(nest1);
       	guiNest2.setNestAgent(nest2);
       	guiNest3.setNestAgent(nest3);
       	guiNest4.setNestAgent(nest4);
       	guiNest5.setNestAgent(nest5);
       	guiNest6.setNestAgent(nest6);
       	guiNest7.setNestAgent(nest7);
       	guiNest8.setNestAgent(nest8);
   
       	// Set the dependencies between agents
       	gantry.setGantryBins(gantryBins);
       	feeder1.setGantryAgent(gantry);
       	feeder2.setGantryAgent(gantry);
       	feeder3.setGantryAgent(gantry);
       	feeder4.setGantryAgent(gantry);
       	feeder1.setDiverterAgent(diverter1);
       	feeder2.setDiverterAgent(diverter2);
       	feeder3.setDiverterAgent(diverter3);
       	feeder4.setDiverterAgent(diverter4);       	
       	diverter1.setFeederAgent(feeder1);
       	diverter2.setFeederAgent(feeder2);
       	diverter3.setFeederAgent(feeder3);
       	diverter4.setFeederAgent(feeder4);
       	diverter1.setLaneAgent1(lane1);
       	diverter1.setLaneAgent2(lane2);
       	diverter2.setLaneAgent1(lane3);
       	diverter2.setLaneAgent2(lane4);
       	diverter3.setLaneAgent1(lane5);
       	diverter3.setLaneAgent2(lane6);
       	diverter4.setLaneAgent1(lane7);
       	diverter4.setLaneAgent2(lane8);
		lane1.setFeederAgent(feeder1);		
       	lane1.setDiverterAgent(diverter1);
       	lane1.setNestAgent(nest1);
       	lane2.setFeederAgent(feeder1);		
       	lane2.setDiverterAgent(diverter1);
       	lane2.setNestAgent(nest2);
		lane3.setFeederAgent(feeder2);		
       	lane3.setDiverterAgent(diverter2);
       	lane3.setNestAgent(nest3);
       	lane4.setFeederAgent(feeder2);		
       	lane4.setDiverterAgent(diverter2);
       	lane4.setNestAgent(nest4);
		lane5.setFeederAgent(feeder3);		
       	lane5.setDiverterAgent(diverter3);
       	lane5.setNestAgent(nest5);
       	lane6.setFeederAgent(feeder3);		
       	lane6.setDiverterAgent(diverter3);
       	lane6.setNestAgent(nest6);
		lane7.setFeederAgent(feeder4);		
       	lane7.setDiverterAgent(diverter4);
       	lane7.setNestAgent(nest7);
       	lane8.setFeederAgent(feeder4);		
       	lane8.setDiverterAgent(diverter4);
       	lane8.setNestAgent(nest8);       	
       	       	
       	lane1.setPartType(PartType.A);
       	lane2.setPartType(PartType.B);
       	lane3.setPartType(PartType.C);
       	lane4.setPartType(PartType.D);
       	lane5.setPartType(PartType.E);
       	lane6.setPartType(PartType.F);
       	lane7.setPartType(PartType.G);
       	lane8.setPartType(PartType.H);
       	
       	// Add GUI Components to the ArrayLists
       	addUnderParts(guiFactoryFloor);
       	addUnderParts(guiNest1);
       	addUnderParts(guiNest2);
       	addUnderParts(guiNest3);
       	addUnderParts(guiNest4);
       	addUnderParts(guiNest5);
       	addUnderParts(guiNest6);
       	addUnderParts(guiNest7);
       	addUnderParts(guiNest8);
       	addUnderParts(guiLane1);
       	addUnderParts(guiLane2);
       	addUnderParts(guiLane3);
       	addUnderParts(guiLane4);
       	addUnderParts(guiLane5);
       	addUnderParts(guiLane6);
       	addUnderParts(guiLane7);
       	addUnderParts(guiLane8);
       	addUnderParts(guiDiverter1);
       	addUnderParts(guiDiverter2);
       	addUnderParts(guiDiverter3);
       	addUnderParts(guiDiverter4);
       	
       	addOverParts(guiFeeder1);
       	addOverParts(guiFeeder2);
       	addOverParts(guiFeeder3);
       	addOverParts(guiFeeder4);
       	robots.add(guiGantry);

    	// Start Agent threads
       	gantry.startThread();
    	//feeder1.msgSendMoreParts(lane1, PartType.A);
       	feeder3.msgSendMoreParts(lane5, PartType.E);
    	feeder1.startThread();
    	feeder2.startThread();
    	feeder3.startThread();
    	feeder4.startThread();
    	diverter1.startThread();
    	diverter2.startThread();
    	diverter3.startThread();
    	diverter4.startThread();
    	lane1.startThread();
    	lane2.startThread();
    	lane3.startThread();
    	lane4.startThread();
    	lane5.startThread();
    	lane6.startThread();
    	lane7.startThread();
    	lane8.startThread();
    	nest1.startThread();
    	nest2.startThread();
    	nest3.startThread();
    	nest4.startThread();
    	nest5.startThread();
    	nest6.startThread();
    	nest7.startThread();
    	nest8.startThread();
    	//cameraSystem.startThread();
    	
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
		JButton butt1 = new JButton("get type A");
		JButton butt2 = new JButton("get type B");
		JButton butt3 = new JButton("purge Nests");
		
		public ButtonsPanel()
		{
			setBackground(Color.DARK_GRAY);
			setPreferredSize(new Dimension(100,750));
			
			// Set up the ActionListener functionality
			butt1.addActionListener(this);
			butt2.addActionListener(this);
			butt3.addActionListener(this);
			
			// Add the buttons to the button panel
			add(butt1);
			add(butt2);
			add(butt3);
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand() == "get type A")
			{
				feeder1.msgSendMoreParts(lane1, PartType.A);	
			}
			else if(ae.getActionCommand() == "get type B")
			{
				feeder1.msgSendMoreParts(lane1, PartType.B);	
			}
			else if(ae.getActionCommand() == "purge Nests")
			{
				guiNest1.dumpParts();
				guiNest2.dumpParts();
			}
		}
	}
}
