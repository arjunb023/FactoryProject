package gui.panels;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import agent.Agent;

//Import all package contents
import factory.*;
import factory.interfaces.Cart;
import gui.components.*;
import shared.Bin;
import shared.KitOrder;
import shared.enums.*;

@SuppressWarnings("serial")
public class FactoryPanel extends JPanel implements ActionListener, MouseListener
{
	private ArrayList<Agent> agents = new ArrayList<Agent>();
	
	// Absolute positioning around the Cart Rail
	private static final int CARTRAILX = 50;
	private static final int CARTRAILY = 0;
	// Absolute positioning around the Nest #1
	private static final int NEST1X = 475;
	private static final int NEST1Y = 50;
	
	// Try to get rid of diverter offset?
	private static final int DIVERTERXOFFSET = 37;
	private static final int DIVERTERYOFFSET = 33;
	
	// Constants
	private final int KITSIZE = 8;
	private static final int PANELX = 1000;
	private static final int PANELY = 750;
	
	private boolean explodingParts = false;
	
	// Paint-related ArrayLists (instantiated in order from bottom layer to top layer)
	public static ArrayList<GUIComponent> underParts = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> kits = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> parts = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> overParts  = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> lowerBins = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> upperBins = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> robots = new ArrayList<GUIComponent>();
	
	// Timer variable
	protected Timer timer = new Timer(3, this);
	
	// Master Bin list
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
			add(new Bin(PartType.I));
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
   	
   	PartsRobotAgent partsRobot = new PartsRobotAgent("Parts Robot");
	KitRobotAgent kitRobot = new KitRobotAgent("Kit Robot");
   	CameraSystemAgent cameraSystem = new CameraSystemAgent("CameraSystem");
   	
   	CellManagerAgent manager = new CellManagerAgent("Manager");
   	
   	// Define & instantiate all GUI components and set the positions in the right side of the factory relative to Nest #1
	GUINest guiNest1 = new GUINest(NEST1X, NEST1Y,0);
	GUINest guiNest2 = new GUINest(guiNest1.getX(), guiNest1.getY()+guiNest1.getHeight(), 0);
	GUINest guiNest3 = new GUINest(guiNest2.getX(), guiNest2.getY()+guiNest2.getHeight(), 0);
	GUINest guiNest4 = new GUINest(guiNest3.getX(), guiNest3.getY()+guiNest3.getHeight(), 0);
	GUINest guiNest5 = new GUINest(guiNest4.getX(), guiNest4.getY()+guiNest4.getHeight(), 0);
	GUINest guiNest6 = new GUINest(guiNest5.getX(), guiNest5.getY()+guiNest5.getHeight(), 0);
	GUINest guiNest7 = new GUINest(guiNest6.getX(), guiNest6.getY()+guiNest6.getHeight(), 0);
	GUINest guiNest8 = new GUINest(guiNest7.getX(), guiNest7.getY()+guiNest7.getHeight(), 0);
    GUILane guiLane1 = new GUILane(guiNest1.getX()+guiNest1.getWidth(), guiNest1.getY(), 3, guiNest1);
    GUILane guiLane2 = new GUILane(guiNest2.getX()+guiNest2.getWidth(), guiNest2.getY(), 3, guiNest2);
    GUILane guiLane3 = new GUILane(guiNest3.getX()+guiNest3.getWidth(), guiNest3.getY(), 3, guiNest3);
    GUILane guiLane4 = new GUILane(guiNest4.getX()+guiNest4.getWidth(), guiNest4.getY(), 3, guiNest4);
    GUILane guiLane5 = new GUILane(guiNest5.getX()+guiNest5.getWidth(), guiNest5.getY(), 3, guiNest5);
    GUILane guiLane6 = new GUILane(guiNest6.getX()+guiNest6.getWidth(), guiNest6.getY(), 3, guiNest6);
    GUILane guiLane7 = new GUILane(guiNest7.getX()+guiNest7.getWidth(), guiNest7.getY(), 3, guiNest7);
    GUILane guiLane8 = new GUILane(guiNest8.getX()+guiNest8.getWidth(), guiNest8.getY(), 3, guiNest8);
	GUIFeeder guiFeeder1 = new GUIFeeder("guiFeeder1", guiLane1.getX()+guiLane1.getWidth(), guiLane1.getY());
	GUIFeeder guiFeeder2 = new GUIFeeder("guiFeeder2", guiLane3.getX()+guiLane3.getWidth(), guiLane3.getY());
	GUIFeeder guiFeeder3 = new GUIFeeder("guiFeeder3", guiLane5.getX()+guiLane5.getWidth(), guiLane5.getY());
	GUIFeeder guiFeeder4 = new GUIFeeder("guiFeeder4", guiLane7.getX()+guiLane7.getWidth(), guiLane7.getY());
	GUIDiverter guiDiverter1 = new GUIDiverter("guiDiverter1",guiFeeder1.getX()-DIVERTERXOFFSET,guiFeeder1.getY()+DIVERTERYOFFSET);
	GUIDiverter guiDiverter2 = new GUIDiverter("guiDiverter2",guiFeeder2.getX()-DIVERTERXOFFSET,guiFeeder2.getY()+DIVERTERYOFFSET);
	GUIDiverter guiDiverter3 = new GUIDiverter("guiDiverter3",guiFeeder3.getX()-DIVERTERXOFFSET,guiFeeder3.getY()+DIVERTERYOFFSET);
	GUIDiverter guiDiverter4 = new GUIDiverter("guiDiverter4",guiFeeder4.getX()-DIVERTERXOFFSET,guiFeeder4.getY()+DIVERTERYOFFSET);
	
	GUIGantry guiGantry = new GUIGantry("guiGantry",PANELX,PANELY+guiFeeder1.getX());
	
	GUIPartsRobot guiPartsRobot = new GUIPartsRobot("guiPartsRobot");
	GUIKitStand guiKitStand = new GUIKitStand(0, KITSIZE);
	GUIKittingRobot guiKitRobot = new GUIKittingRobot("guiKitRobot");
	GUICamera guiCamera1 = new GUICamera();										// Cameras
	GUICamera guiCamera2 = new GUICamera();
	GUICamera guiCamera3 = new GUICamera();
	GUICamera guiCamera4 = new GUICamera();
	GUICamera guiCameraKitting = new GUICamera();
	
   	GUIFactoryFloor guiFactoryFloor = new GUIFactoryFloor();
   	
   	// Added for testing
   	//GUIKit tempKit = new GUIKit(0,0,0,KITSIZE);
   	
   	// Cart Rail Instantiation
   	ImageIcon cartRail = new ImageIcon("images/cart_rail.png");
   	// Gantry Rails Instantiation
   	
   	// Upper layer feeder instantiation
   	ImageIcon overFeeder = new ImageIcon("images/ab_feeder_top.png");
   	// Upper layer feeder instantiation
   	
   	// Background instantiation
   	ImageIcon background = new ImageIcon("images/floor.png");
   	// Background instantiation
   	
   	// The Parts Robot has rails, but is it going to use them? Or should I tack on an extra part of the image to
   	// indicate that it is standing on ground?
	
   	public static TracePanel tracePanel = new TracePanel();
   	public static StatesPanel statesPanel = new StatesPanel();
   	
   	
   	Point mouseClick;
   	
	public FactoryPanel()
	{
		
		this.addMouseListener(this);
		
		// Edit panel settings
		setSize(new Dimension(1000,750));
		setPreferredSize(new Dimension(1000,750));
		setBackground(Color.DARK_GRAY);
		
		// Create the bins lined up at the bottom of the screen
		// @ARJUN: Your getHeight() function seems to be incorrect for GUILane
		//		   What is the black stuff at the end of the lane?
		//		   You also seem to be drawing GUIParts... That's not good! Also, your lane line colors change
		//		   @MIKE: The Feeder might be drawing parts too -- this shouldn't be happening (I think)
		int binX = guiLane8.getY()+guiLane8.getHeight();
		int locations[][]= new int[9][2];
		for (Bin bin : gantryBins) {
			bin.guiBin.setX(binX);
			bin.guiBin.setY(guiLane8.getY()+guiLane8.getHeight()*2);
			locations[(binX-(guiLane8.getY()+guiLane8.getHeight()))/50][0]=binX;
			locations[(binX-(guiLane8.getY()+guiLane8.getHeight()))/50][1]=guiLane8.getY()+guiLane8.getHeight()*2;
			binX += 50;
		}
		guiGantry.setBinLocations(locations);
		 
		// Set each Agent's GUI counterpart
    	gantry.setGUIGantry(guiGantry);				// Gantry Robot
       	feeder1.setGUIFeeder(guiFeeder1);			// Feeders
       	feeder2.setGUIFeeder(guiFeeder2);
       	feeder3.setGUIFeeder(guiFeeder3);
       	feeder4.setGUIFeeder(guiFeeder4);
       	diverter1.setGuiDiverter(guiDiverter1);		// Diverters
       	diverter2.setGuiDiverter(guiDiverter2);
       	diverter3.setGuiDiverter(guiDiverter3);
       	diverter4.setGuiDiverter(guiDiverter4);
       	lane1.setGuiLane(guiLane1);					// Lanes
       	lane2.setGuiLane(guiLane2);
       	lane3.setGuiLane(guiLane3);
       	lane4.setGuiLane(guiLane4);
       	lane5.setGuiLane(guiLane5);
       	lane6.setGuiLane(guiLane6);
       	lane7.setGuiLane(guiLane7);
       	lane8.setGuiLane(guiLane8);
       	nest1.setGuiNest(guiNest1);					// Nests
       	nest2.setGuiNest(guiNest2);	
       	nest3.setGuiNest(guiNest3);
       	nest4.setGuiNest(guiNest4);
       	nest5.setGuiNest(guiNest5);
       	nest6.setGuiNest(guiNest6);
       	nest7.setGuiNest(guiNest7);
       	nest8.setGuiNest(guiNest8);
       	
       	
       	partsRobot.setGuiPartsRobot(guiPartsRobot);	// Parts Robot
       	kitRobot.setGuiKitRobot(guiKitRobot);		// Kit Robot
       	
       	// Set each GUI component's Agent
    	guiGantry.setGantryAgent(gantry);				// Gantry Robot
       	guiFeeder1.setFeederAgent(feeder1);				// Feeders
       	guiFeeder2.setFeederAgent(feeder2);
       	guiFeeder3.setFeederAgent(feeder3);
       	guiFeeder4.setFeederAgent(feeder4);
       	guiDiverter1.setDiverterAgent(diverter1);		// Diverters
       	guiDiverter2.setDiverterAgent(diverter2);
       	guiDiverter3.setDiverterAgent(diverter3);
       	guiDiverter4.setDiverterAgent(diverter4);
       	guiLane1.setLaneAgent(lane1);					// Lanes
       	guiLane2.setLaneAgent(lane2);
       	guiLane3.setLaneAgent(lane3);
       	guiLane4.setLaneAgent(lane4);
       	guiLane5.setLaneAgent(lane5);
       	guiLane6.setLaneAgent(lane6);
       	guiLane7.setLaneAgent(lane7);
       	guiLane8.setLaneAgent(lane8);       	
       	guiNest1.setNestAgent(nest1);					// Nests
       	guiNest2.setNestAgent(nest2);
       	guiNest3.setNestAgent(nest3);
       	guiNest4.setNestAgent(nest4);
       	guiNest5.setNestAgent(nest5);
       	guiNest6.setNestAgent(nest6);
       	guiNest7.setNestAgent(nest7);
       	guiNest8.setNestAgent(nest8);
       	guiPartsRobot.setMyAgent(partsRobot);			// Parts Robot
       	guiKitRobot.setKitRobotAgent(kitRobot);			// Kit Robot
       	guiCamera1.setCameraSystemAgent(cameraSystem);	// Camera System
       	guiCamera2.setCameraSystemAgent(cameraSystem);
       	guiCamera3.setCameraSystemAgent(cameraSystem);
       	guiCamera4.setCameraSystemAgent(cameraSystem);
       	guiCameraKitting.setCameraSystemAgent(cameraSystem);
       	
       	// Set each Diverter to its two lanes
       	guiDiverter1.setLaneOne(guiLane1);
       	guiDiverter1.setLaneTwo(guiLane2);
       	guiDiverter2.setLaneOne(guiLane3);
       	guiDiverter2.setLaneTwo(guiLane4);
       	guiDiverter3.setLaneOne(guiLane5);
       	guiDiverter3.setLaneTwo(guiLane6);
       	guiDiverter4.setLaneOne(guiLane7);
       	guiDiverter4.setLaneTwo(guiLane8);
       	
       	// Set the dependencies between Agents
       	gantry.setGantryBins(gantryBins);			// Set the Gantry's bins
       	feeder1.setGantryAgent(gantry);				// Set the Feeders' Gantry Agent
       	feeder2.setGantryAgent(gantry);
       	feeder3.setGantryAgent(gantry);
       	feeder4.setGantryAgent(gantry);
       	feeder1.setDiverterAgent(diverter1);		// Set the Feeders' Diverter Agents
       	feeder2.setDiverterAgent(diverter2);
       	feeder3.setDiverterAgent(diverter3);
       	feeder4.setDiverterAgent(diverter4);       	
       	diverter1.setFeederAgent(feeder1);			// Set the Diverters' Feeder Agents
       	diverter2.setFeederAgent(feeder2);
       	diverter3.setFeederAgent(feeder3);
       	diverter4.setFeederAgent(feeder4);
       	diverter1.setLaneAgent1(lane1);				// Set the Diverter Agents to the appropriate Lane Agents
       	diverter1.setLaneAgent2(lane2);
       	diverter2.setLaneAgent1(lane3);
       	diverter2.setLaneAgent2(lane4);
       	diverter3.setLaneAgent1(lane5);
       	diverter3.setLaneAgent2(lane6);
       	diverter4.setLaneAgent1(lane7);
       	diverter4.setLaneAgent2(lane8);
		lane1.setFeederAgent(feeder1);				// Set the Lanes' Feeder, Nest, and Diverter Agents
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
       	
       	// Set the Camera-Nest Agent dependencies
       	nest1.setNestNum(1);
       	nest2.setNestNum(2);
       	nest3.setNestNum(3);
       	nest4.setNestNum(4);
       	nest5.setNestNum(5);
       	nest6.setNestNum(6);
       	nest7.setNestNum(7);
       	nest8.setNestNum(8);
       	nest1.setCameraSystemAgent(cameraSystem);
       	nest2.setCameraSystemAgent(cameraSystem);
       	nest3.setCameraSystemAgent(cameraSystem);
       	nest4.setCameraSystemAgent(cameraSystem);
       	nest5.setCameraSystemAgent(cameraSystem);
       	nest6.setCameraSystemAgent(cameraSystem);
       	nest7.setCameraSystemAgent(cameraSystem);
       	nest8.setCameraSystemAgent(cameraSystem);
       	
       	// THIS WILL CHANGE WHEN ELIAS CHANGES HIS CODE
       	// FOR NOW, THE CAMERA ONLY WORKS FOR NESTS 1 & 2?
       	cameraSystem.setNests(1, nest1, nest2);
       	cameraSystem.setNests(2, nest3, nest4);
       	cameraSystem.setNests(3, nest5, nest6);
       	cameraSystem.setNests(4, nest7, nest8);
       	
       	cameraSystem.setKitRobotAgent(kitRobot);
       	
       	// Set the Parts Robot Agent dependencies
       	partsRobot.setCameraSystemAgent(cameraSystem);
       	partsRobot.setKitRobotAgent(kitRobot);
       	cameraSystem.setPartsRobotAgent(partsRobot);
       	
       	// Set Kit Robot GUI dependencies
       	guiKitRobot.setKitStand(guiKitStand);
		guiCameraKitting.setKitStand(guiKitStand);
		kitRobot.setCameraSystemAgent(cameraSystem);
		kitRobot.setPartsRobotAgent(partsRobot);

       	addAgents();
       	
       	// Set additional GUI coordinates/attributes
       	guiCamera1.setX(guiNest1.getX()+5);					// Set the GUI Camera coordinates to hang it over the nests
       	guiCamera1.setY(guiNest1.getY()+25);
    	
       	guiCamera2.setX(guiNest3.getX()+5);					// Set the GUI Camera coordinates to hang it over the nests
       	guiCamera2.setY(guiNest3.getY()+25);
    	
       	guiCamera3.setX(guiNest5.getX()+5);					// Set the GUI Camera coordinates to hang it over the nests
       	guiCamera3.setY(guiNest5.getY()+25);
    	
       	guiCamera4.setX(guiNest7.getX()+5);					// Set the GUI Camera coordinates to hang it over the nests
       	guiCamera4.setY(guiNest7.getY()+25);
       	
       	
       	
       	guiKitRobot.setX(CARTRAILX + guiKitRobot.getWidth()); // Set coordinates of Kit Robot (35==0.5*cart width!)
       	guiKitRobot.setY(PANELY/3);
       	guiKitStand.setX(guiKitRobot.getX() + guiKitRobot.getWidth()*3/2);			// Set coordinates of Kit Stand
    	guiKitStand.setY(PANELY/4);
       	
    	guiCameraKitting.setX(guiKitStand.getX()+guiKitStand.getWidth()/12);
       	guiCameraKitting.setY(guiKitStand.getY()+guiKitStand.getHeight()/22);
    	
    	guiPartsRobot.setNest(guiNest1, 1);					// Parts Robot GUI association functions
    	guiPartsRobot.setNest(guiNest2, 2);
    	guiPartsRobot.setNest(guiNest3, 3);
    	guiPartsRobot.setNest(guiNest4, 4);
    	guiPartsRobot.setNest(guiNest5, 5);
    	guiPartsRobot.setNest(guiNest6, 6);
    	guiPartsRobot.setNest(guiNest7, 7);
    	guiPartsRobot.setNest(guiNest8, 8);
    	guiPartsRobot.setKitStand(guiKitStand);
    	guiPartsRobot.setTheFloor(guiFactoryFloor);
    	guiPartsRobot.setX(guiKitStand.getX()+guiKitStand.getWidth()*7/4);
    	guiPartsRobot.setY(PANELY/2-guiPartsRobot.getHeight()/2);
    	
       	guiNest1.setNestNum(1);						    	// Set the GUI Nest numbers
       	guiNest2.setNestNum(2);
       	guiNest3.setNestNum(3);
       	guiNest4.setNestNum(4);
       	guiNest5.setNestNum(5);
       	guiNest6.setNestNum(6);
       	guiNest7.setNestNum(7);
       	guiNest8.setNestNum(8);
       	
       	cameraSystem.setCameraGui1(guiCamera1);		// Camera
       	cameraSystem.setCameraGui2(guiCamera2);
       	cameraSystem.setCameraGui3(guiCamera3);
       	cameraSystem.setCameraGui4(guiCamera4);
       	cameraSystem.setKitCamera(guiCameraKitting);
       	
       	
       	// Add GUI Components to the ArrayLists
       	underParts.add(guiFactoryFloor);
       	underParts.add(guiNest1);
       	underParts.add(guiNest2);
       	underParts.add(guiNest3);
       	underParts.add(guiNest4);
       	underParts.add(guiNest5);
       	underParts.add(guiNest6);
       	underParts.add(guiNest7);
       	underParts.add(guiNest8);
       	underParts.add(guiLane1);
       	underParts.add(guiLane2);
       	underParts.add(guiLane3);
       	underParts.add(guiLane4);
       	underParts.add(guiLane5);
       	underParts.add(guiLane6);
       	underParts.add(guiLane7);
       	underParts.add(guiLane8);
       	underParts.add(guiDiverter1);
       	underParts.add(guiDiverter2);
       	underParts.add(guiDiverter3);
       	underParts.add(guiDiverter4);
       	underParts.add(guiKitStand);
       	
       	overParts.add(guiFeeder1);
       	overParts.add(guiFeeder2);
       	overParts.add(guiFeeder3);
       	overParts.add(guiFeeder4);
       	overParts.add(guiCamera1);
       	overParts.add(guiCamera2);
       	overParts.add(guiCamera3);
       	overParts.add(guiCamera4);
       	overParts.add(guiCameraKitting);
       	
       	robots.add(guiPartsRobot);
       	robots.add(guiGantry);
       	robots.add(guiKitRobot);
       	
       	// Test
       	
       //guiKitStand.putKitOnStand(tempKit, 1);
       	//System.out.println(guiKitStand.getKitFromStand(1).getX() + " " + guiKitStand.getKitFromStand(1).getY());
       	///kits.add(tempKit);
		
       	setCMAgentConnections();

       	statesPanel.initialize();
		statesPanel.updateStates();
		
       	//startThreads();
       	
       	/**@TODO
       	 * take this out block of code below when the kit panel is done
       	 */
       	//Create a kit order for testing until the panel is done
		/*KitOrder	tempOrder	= new KitOrder();
		ArrayList<PartType>	tempOrderParts	= new ArrayList<PartType>();
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
			tempOrderParts.add(PartType.A);
		tempOrder.setKitOrder(tempOrderParts);
		//manager.msgCreateNewJob(tempOrderParts, 40);
		*/
	}
	
	/**
	 * a counter which is incremented every time FactoryPanel's actionPerformed is called.
	 * Designed so tracePanel does not print on every "frame"
	 */
	int tracePrintCounter = 0;
	
	// Call update functions on each timer call
	public void actionPerformed(ActionEvent e)
	{
		if(explodingParts)
		{
			for (GUIComponent c : parts)
			{
				if(!((GUIPart)c).explode())
					explodingParts = false;
			}
		}
		if (++tracePrintCounter > 100)
		{
			tracePrintCounter = 0;
			tracePanel.reprint();
		}
		
		synchronized(underParts){
			for (GUIComponent c : underParts)
				c.update();
		}
		synchronized(kits){
			for (GUIComponent c : kits)
				c.update();
		}
		synchronized(parts){
			for (GUIComponent c : parts)
				c.update();
		}
		synchronized(lowerBins){
			for (GUIComponent c : lowerBins)
				c.update();
		}
		synchronized(overParts){
			for (GUIComponent c : overParts)
				c.update();
		}
		synchronized(upperBins){
			for (GUIComponent c : upperBins)
				c.update();
		}
		synchronized(robots){
			for (GUIComponent c : robots)
				c.update();
		}
		repaint();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(background.getImage(), 0, 0, 1200, 750, null);
		
		g.drawImage(cartRail.getImage(), CARTRAILX, CARTRAILY, cartRail.getIconWidth()/3, PANELY, null);
		synchronized(underParts){
			for (GUIComponent c : underParts)
				c.draw(g);
		}
		synchronized(kits){
			for (GUIComponent c : kits)
				c.draw(g);
		}
		synchronized(parts)
		{
			for (GUIComponent c : parts)
				c.draw(g);
		}
		synchronized(lowerBins){
			for (GUIComponent c : lowerBins)
				c.draw(g);
		}
		synchronized(overParts){
			for (GUIComponent c : overParts)
				c.draw(g);
		}
		synchronized(upperBins){
			for (GUIComponent c : upperBins)
				c.draw(g);
		}
		
		// upper layer feeder 1
		g.drawImage(overFeeder.getImage(), 804, 50, 125, 120, null);
		// upper layer feeder 2
		g.drawImage(overFeeder.getImage(), 804, 170, 125, 120, null);
		// upper layer feeder 3
		g.drawImage(overFeeder.getImage(), 804, 290, 125, 120, null);
		// upper layer feeder 4
		g.drawImage(overFeeder.getImage(), 804, 410, 125, 120, null);
		
		// Handle kits/parts overlapping
		if(guiKitRobot.hasKit)
		{
			guiKitRobot.drawMyKit(g);
		}
		
		for (GUIComponent c : robots)
			c.draw(g);
	}
	
	/**
	 * Starts the timer
	 */
	public void startAll() 
	{
		timer.start();
	}
	
	/**
	 * Stops the timer
	 */
	public void stopAll() 
	{
		timer.stop();
	}
	
	/**
	 * Add agents to the agents AL upon initialization
	 */
	public void addAgents() {
		// Add all agents initially
		
		/*
		 ******************************************************
		 * DO NOT CHANGE THE ORDER OF THIS WITHOUT GOOD REASON
		 ******************************************************
		 */
		agents.add(gantry);
		agents.add(feeder1);
		agents.add(feeder2);
		agents.add(feeder3);
		agents.add(feeder4);
		agents.add(diverter1);
		agents.add(diverter2);
		agents.add(diverter3);
		agents.add(diverter4);
		agents.add(lane1);
		agents.add(lane2);
		agents.add(lane3);
		agents.add(lane4);
		agents.add(lane5);
		agents.add(lane6);
		agents.add(lane7);
		agents.add(lane8);
		agents.add(nest1);
		agents.add(nest2);
		agents.add(nest3);
		agents.add(nest4);
		agents.add(nest5);
		agents.add(nest6);
		agents.add(nest7);
		agents.add(nest8);
		
		agents.add(partsRobot);
		agents.add(kitRobot);
		agents.add(cameraSystem);
		
		ArrayList<Cart> cartAgents = kitRobot.getCartAgents();
		synchronized(cartAgents)
		{
			for (Cart c : cartAgents)
			{
				agents.add((Agent) c);
				System.out.println("FactoryPanel added a CartAgent " + c.getName());
			}
			//System.out.print("FactoryPanel DONE WITH CARTS");
		}
		
		agents.add(manager);
		/*
		 ******************************************************
		 * DO NOT CHANGE THE ORDER OF ABOVE WITHOUT GOOD REASON
		 ******************************************************
		 */
	}
	
	/**
	 * Start all Agent threads
	 */
	public void startThreads()
	{
		synchronized(agents)
		{
			for (Agent a : agents)
			{
				a.startThread();
			}
		}
	}
	
	/**
	 * Stop all Agent threads
	 */
	public void stopThreads()
	{
		synchronized(agents)
		{
			for (Agent a : agents)
				a.stopThread();
		}
	}
	
	/**
	 * Calls .addConfiguredAgent on all relevant Agents and sets each Agent to the CellManagerAgent
	 */
	private void setCMAgentConnections()
	{
		manager.addConfiguredAgent(feeder1);
       	manager.addConfiguredAgent(feeder2);
       	manager.addConfiguredAgent(feeder3);
       	manager.addConfiguredAgent(feeder4);
       	manager.addConfiguredAgent(lane1);
       	manager.addConfiguredAgent(lane2);
       	manager.addConfiguredAgent(lane3);
       	manager.addConfiguredAgent(lane4);
       	manager.addConfiguredAgent(lane5);
       	manager.addConfiguredAgent(lane6);
       	manager.addConfiguredAgent(lane7);
       	manager.addConfiguredAgent(lane8);
       	manager.addConfiguredAgent(nest1);
       	manager.addConfiguredAgent(nest2);
       	manager.addConfiguredAgent(nest3);
       	manager.addConfiguredAgent(nest4);
       	manager.addConfiguredAgent(nest5);
       	manager.addConfiguredAgent(nest6);
       	manager.addConfiguredAgent(nest7);
       	manager.addConfiguredAgent(nest8);
       	manager.addConfiguredAgent(cameraSystem);
       	manager.addConfiguredAgent(partsRobot);
       	manager.setKitRobot(kitRobot);
       	manager.setNumLanes(8);
       	
		lane1.setCellManagerAgent(manager);
       	lane2.setCellManagerAgent(manager);
       	lane3.setCellManagerAgent(manager);
       	lane4.setCellManagerAgent(manager);
       	lane5.setCellManagerAgent(manager);
       	lane6.setCellManagerAgent(manager);
       	lane7.setCellManagerAgent(manager);
       	lane8.setCellManagerAgent(manager);
       	nest1.setCellManagerAgent(manager);
       	nest2.setCellManagerAgent(manager);
       	nest3.setCellManagerAgent(manager);
       	nest4.setCellManagerAgent(manager);
       	nest5.setCellManagerAgent(manager);
       	nest6.setCellManagerAgent(manager);
       	nest7.setCellManagerAgent(manager);
       	nest8.setCellManagerAgent(manager);
       	partsRobot.setCellManagerAgent(manager);
       	feeder1.setCellManagerAgent(manager);
       	feeder2.setCellManagerAgent(manager);
       	feeder3.setCellManagerAgent(manager);
       	feeder4.setCellManagerAgent(manager);
       	cameraSystem.setCellManagerAgent(manager);
       	kitRobot.setCellManagerAgent(manager);
	}
	
	public CellManagerAgent getCMA()
	{
		return this.manager;
	}


	/**
	 * The GUI component that gets clicked will print stuff to the console
	 */
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		this.mouseClick = arg0.getLocationOnScreen();
		
		for (GUIComponent part : this.parts)
		{
			//if (((GUIPart)part).contains(mouseClick)  )
			if (	(part.getX() < arg0.getX()  &&  part.getX()+part.getWidth() > arg0.getX()) 
					&& (part.getY() < arg0.getY()  &&  part.getY()+part.getHeight() > arg0.getY()) 
				)
			{
				System.out.println("CLICKED: "
					+ ((GUIPart)part).getName());
				//break;
			}
			/*
			else
			{
				System.out.println(arg0.getX() + "," + arg0.getY() + " MISSED:  " + part.getX() + "," + part.getY());
			}
			*/
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void explodeParts()
	{		
		explodingParts = true;
	}
}
