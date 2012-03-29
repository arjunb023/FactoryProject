package v0;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import factory.*;
import factory.interfaces.KitRobot;
import factory.test.mock.MockKitRobot;
import gui.components.*;
import shared.KitConfig;
import shared.KitOrder;
import shared.enums.*;

/**
 * CSCI200: Kitting Cell Project
 * 
 * Creates a panel in which the GUI/Agent components are instantiated and animated.  Should be created with its 
 * constructor from the frame class.
 * 
 * @author jessicayoshimi
 *
 */
public class v0PartsAnimation extends JPanel implements ActionListener
{
	// Constants
	private final int KITSIZE = 8;
	
	// ArrayLists of all GUI components in the factory, sectioned into ArrayLists based on paint order
	protected ArrayList<GUIComponent> underParts = new ArrayList<GUIComponent>();
	protected ArrayList<GUIComponent> parts = new ArrayList<GUIComponent>();
	protected ArrayList<GUIComponent> overParts  = new ArrayList<GUIComponent>();
	
	// ArrayLists of parts for the nests
	protected ArrayList<GUIPart> nest1Parts = new ArrayList<GUIPart>();
	protected ArrayList<GUIPart> nest2Parts = new ArrayList<GUIPart>();
	
	// ArrayList of kits
	protected ArrayList<GUIKit> kits = new ArrayList<GUIKit>();
	
	// Timer variable
	protected Timer timer = new Timer(50, this);
	
	// Instantiate Agents
	// Nest Agents
   	private NestAgent nest1 = new NestAgent("Nest1");
   	private NestAgent nest2 = new NestAgent("Nest2");
   	// Parts Robot Agent
   	PartsRobotAgent partsRobot = new PartsRobotAgent("Parts Robot");
   	// Camera System Agent
   	CameraSystemAgent camera = new CameraSystemAgent("Camera");
   	//
   	KitRobot	mockKitRobot	= new MockKitRobot("Kit Robot");
   	
   	// Instantiate GUI Components
   	// Nests
   	GUINest guiNest1 = new GUINest(400,160,0);
   	GUINest guiNest2 = new GUINest(400,220,0);
   	// Parts Robot
   	GUIPartsRobot guiPartsRobot = new GUIPartsRobot("guiPartsRobot");
   	// Kits
   	GUIKit guiKit1 = new GUIKit(220,180,0,KITSIZE);
   	GUIKit guiKit2 = new GUIKit(220,280,0,KITSIZE);
   	// Kitting Stand
   	GUIKitStand guiKitStand = new GUIKitStand(0, KITSIZE);
   	// Camera
   	GUICamera guiCamera = new GUICamera();
   	// Factory Floor
   	GUIFactoryFloor guiFactoryFloor = new GUIFactoryFloor();
   	// Parts
   	GUIPart part1 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part2 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part3 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part4 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part5 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part6 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part7 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part8 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part9 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part10 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part11 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part12 = new GUIPart(PartType.A,0,0,0);
   	GUIPart part13 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part14 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part15 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part16 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part17 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part18 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part19 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part20 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part21 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part22 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part23 = new GUIPart(PartType.B,0,0,0);
   	GUIPart part24 = new GUIPart(PartType.B,0,0,0);
   	
   	/**
   	 * Sets panel settings, adds components to the paint ArrayLists, set dependencies
   	 */
	public v0PartsAnimation()
	{
		// Edit panel settings
		setPreferredSize(new Dimension(700,700));
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout());
       	
       	// Add GUI Components to the ArrayLists
       	addUnderParts(guiFactoryFloor);
       	addUnderParts(guiNest1);
       	addUnderParts(guiNest2);
       	addUnderParts(guiKitStand);
       	addUnderParts(guiKit1);
       	addUnderParts(guiKit2);
       	       	
       	addOverParts(guiPartsRobot);
       	addOverParts(guiCamera);
       	
       	      	
       	// Set kit stand coordinates
       	guiKitStand.setX(220);
       	guiKitStand.setY(150);
       	
       	// Add kits to the kits ArrayList
       	kits.add(guiKit1);
       	kits.add(guiKit2);
       	
       	// Put the kits on the stand
       	guiKitStand.putKitOnStand(guiKit1, 1);
       	guiKitStand.putKitOnStand(guiKit2, 2);
       	
       	//Create Agent - Agent associations
       	partsRobot.setCameraSystemAgent(camera);
       	partsRobot.setKitRobotAgent(mockKitRobot);
       	//camera.setNest(2, nest2);
       	//camera.setNest(1, nest1);
       	camera.setNests(1, nest1, nest2);
       	camera.setPartsRobotAgent(partsRobot);
       	nest1.setCameraSystemAgent(camera);
       	nest2.setCameraSystemAgent(camera);
       	//Giving nests dummy lane to avoid null pointer
       	LaneAgent l = new LaneAgent("dummy1");
       	nest1.setLaneAgent(l);
       	nest2.setLaneAgent(l);
       	
    	// Create Agent - GUI Component associations
       	camera.setCameraGui1(guiCamera);
       	nest1.setGuiNest(guiNest1);
       	nest2.setGuiNest(guiNest2);
       	partsRobot.setGuiPartsRobot(guiPartsRobot);

       	// Create GUI Component - Agent associations
       	guiNest1.setNestAgent(nest1);
       	guiNest2.setNestAgent(nest2);
       	guiPartsRobot.setMyAgent(partsRobot);
       	guiCamera.setCameraSystemAgent(camera);
       	
       	// Parts Robot GUI association functions
       	guiPartsRobot.setNest(guiNest1, 1);
       	guiPartsRobot.setNest(guiNest2, 2);
       	guiPartsRobot.setKitStand(guiKitStand);
       	guiPartsRobot.setTheFloor(guiFactoryFloor);
       	guiPartsRobot.setX(300);
       	guiPartsRobot.setY(200);
       	
       	// Set the GUI Nest numbers
       	guiNest1.setNestNum(1);
       	guiNest2.setNestNum(2);
       	
       	// Set the GUI Camera coordinates to hang it over the nests
       	guiCamera.setX(guiNest1.getX()+20);
       	guiCamera.setY(guiNest1.getY()+30);
       	
    	// Start Agent threads
       	nest1.startThread();
    	nest2.startThread();
    	partsRobot.startThread();
    	camera.startThread();
    	
    	// Set Nests full
       	//guiNest1.fillNest(nest1Parts);
       	//guiNest2.fillNest(nest2Parts);
    	
    	//Set up scenario
    	
    	
    	
    	
    	ButtonsPanel buttPanel = new ButtonsPanel();
    	add(buttPanel, BorderLayout.SOUTH);
    	
    	this.startAll();
	}
	
	/**
	 * Calls update for all parts and then calls repaint()
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
	 * Calls draw(Graphics g) functions of all components in the ArrayLists
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
	}
	
	protected void addUnderParts(GUIComponent cp)
	{
		underParts.add(cp);
	}
	protected void addOverParts(GUIComponent cp)
	{
		overParts.add(cp);
	}
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
	
	/**
	 * Inner class of three buttons with ability to send messages to Agents in the factory graphics panel for test
	 * purposes.
	 * 
	 * @author jessicayoshimi
	 *
	 */
	public class ButtonsPanel extends JPanel implements ActionListener
	{
		JButton butt1 = new JButton("Initialize");
		JButton butt2 = new JButton("New Kit Order");
		JButton butt3 = new JButton("Sim Filling Nests");
		
		public ButtonsPanel()
		{
			setBackground(Color.BLACK);
			setPreferredSize(new Dimension(1000,50));
			
			// Set up the ActionListener functionality
			butt1.setEnabled(true);
			butt1.addActionListener(this);
			butt2.setEnabled(false);
			butt2.addActionListener(this);
			butt3.setEnabled(false);
			butt3.addActionListener(this);
			
			// Add the buttons to the button panel
			add(butt1);
			add(butt2);
			add(butt3);		
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand() == "Initialize")
			{
				ArrayList<PartType> configSpec	= new ArrayList<PartType>();
		    	configSpec.add(PartType.A);
		    	configSpec.add(PartType.B);
		    	KitConfig	initialConfig	= new KitConfig(configSpec);
				partsRobot.msgThisIsCurrentConfiguration(initialConfig);
				
				butt1.setEnabled(false);
		       	butt2.setEnabled(true);
		       	butt3.setEnabled(true);
			}
			else if(ae.getActionCommand() == "New Kit Order")
			{
				//Create a kit order
				KitOrder	order1	= new KitOrder();
				ArrayList<PartType>	parts1	= new ArrayList<PartType>();
				for(int i = 0; i < 6; i++)
					parts1.add(PartType.A);
				for(int i = 0; i < 2; i++)
					parts1.add(PartType.B);
				order1.setKitOrder(parts1);
				partsRobot.msgHereIsANewKitOrderToFill(order1, 1);
				
				/*
				//Create a kit order
				KitOrder	order2	= new KitOrder();
				ArrayList<PartType>	parts2	= new ArrayList<PartType>();
				for(int i = 0; i < 6; i++)
					parts2.add(PartType.A);
				for(int i = 0; i < 2; i++)
					parts2.add(PartType.B);
				order2.setKitOrder(parts2);
				partsRobot.msgHereIsANewKitOrderToFill(order2, 2);
				*/
				butt1.setEnabled(false);
		       	butt2.setEnabled(false);
			}
			else if(ae.getActionCommand() == "Sim Filling Nests")
			{
				System.out.println("B3 pressed");
				
				addParts(part1);
		       	addParts(part2);
		       	addParts(part3);
		       	addParts(part4);
		       	addParts(part5);
		       	addParts(part6);
		       	addParts(part7);
		       	addParts(part8);
		       	addParts(part9);
		       	addParts(part10);
		       	addParts(part11);
		       	addParts(part12);
		       	addParts(part13);
		       	addParts(part14);
		       	addParts(part15);
		       	addParts(part16);
		       	addParts(part17);
		       	addParts(part18);
		       	addParts(part19);
		       	addParts(part20);
		       	addParts(part21);
		       	addParts(part22);
		       	addParts(part23);
		       	addParts(part24);
		
		     // Add parts to part arrays to fill nests
		       	nest1Parts.add(part1);
		       	nest1Parts.add(part2);
		       	nest1Parts.add(part3);
		       	nest1Parts.add(part4);
		       	nest1Parts.add(part5);
		       	nest1Parts.add(part6);
		       	nest1Parts.add(part7);
		       	nest1Parts.add(part8);
		       	nest1Parts.add(part9);
		       	nest1Parts.add(part10);
		       	nest1Parts.add(part11);
		       	nest1Parts.add(part12);
		       	
		       	nest2Parts.add(part13);
		       	nest2Parts.add(part14);
		       	nest2Parts.add(part15);
		       	nest2Parts.add(part16);
		       	nest2Parts.add(part17);
		       	nest2Parts.add(part18);
		       	nest2Parts.add(part19);
		       	nest2Parts.add(part20);
		       	nest2Parts.add(part21);
		       	nest2Parts.add(part22);
		       	nest2Parts.add(part23);
		       	nest2Parts.add(part24);
		       	
				// Set Nests full
		       	guiNest1.fillNest(nest1Parts);
		       	guiNest2.fillNest(nest2Parts);
		       	
		       	butt1.setEnabled(false);
		       	butt3.setEnabled(false);
			}
		}
	}
}


