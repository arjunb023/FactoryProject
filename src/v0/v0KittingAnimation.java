package v0;

import factory.*;
import gui.components.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import shared.*;
import shared.enums.*;

public class v0KittingAnimation extends JPanel implements ActionListener {
	// Constants
	private final int KITSIZE = 8;

	// Variable for how many orders created
	private Integer amountOrders = 0;
	// Variable for how many carts have left
	private Integer amountCarts = 0;
	// ArrayLists of all GUI components in the factory, sectioned into
	// ArrayLists based on paint order
	protected ArrayList<GUIComponent> underParts = new ArrayList<GUIComponent>();
	protected ArrayList<GUIComponent> parts = new ArrayList<GUIComponent>();
	protected ArrayList<GUIComponent> overParts = new ArrayList<GUIComponent>();

	// ArrayLists of carts and cart agents
	protected ArrayList<GUICart> carts = new ArrayList<GUICart>();
	protected ArrayList<CartAgent> cartAgents = new ArrayList<CartAgent>();

	// ArrayList of kits
	public ArrayList<GUIComponent> kits = new ArrayList<GUIComponent>();

	// ArrayList of parts
	protected ArrayList<GUIPart> fullKitParts = new ArrayList<GUIPart>();

	// Timer variable
	protected Timer timer = new Timer(30, this);

	// Instantiate Agents
	KitRobotAgent kitRobot = new KitRobotAgent("Kit Robot"); // Kit robot agent
	CameraSystemAgent cameraSystem = new CameraSystemAgent("Camera System");

	// CartAgent cart = new CartAgent("Cart");

	// Instantiate GUI Components
	GUIKittingRobot guiKitRobot = new GUIKittingRobot("guiKitRobot"); // Kit Robot
	GUIKitStand guiKitStand = new GUIKitStand(0, KITSIZE); // Kit Stand
	GUICamera guiCamera = new GUICamera(); // Camera
	GUIFactoryFloor guiFactoryFloor = new GUIFactoryFloor(); // Factory floor
	public boolean Left = true;
	
	KitButtonsPanel kButtPanel;
	/*
	GUIPart part1 = new GUIPart(PartType.B); // Parts
	GUIPart part2 = new GUIPart(PartType.B);
	GUIPart part3 = new GUIPart(PartType.B);
	GUIPart part4 = new GUIPart(PartType.B);
	GUIPart part5 = new GUIPart(PartType.B);
	GUIPart part6 = new GUIPart(PartType.B);
	GUIPart part7 = new GUIPart(PartType.B);
	GUIPart part8 = new GUIPart(PartType.B);
*/
	public v0KittingAnimation() {
		// Edit panel settings
		setPreferredSize(new Dimension(700, 750));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		// Add parts to the parts ArrayList
		/*fullKitParts.add(part1);
		fullKitParts.add(part2);
		fullKitParts.add(part3);
		fullKitParts.add(part4);
		fullKitParts.add(part5);
		fullKitParts.add(part6);
		fullKitParts.add(part7);
		fullKitParts.add(part8);*/

		// Add GUI Components to the ArrayLists
		addUnderParts(guiFactoryFloor); // Add GUI factory components under
										// parts
		addUnderParts(guiKitStand);
/*
		addParts(part1); // Add parts
		addParts(part2);
		addParts(part3);
		addParts(part4);
		addParts(part5);
		addParts(part6);
		addParts(part7);
		addParts(part8);
	*/
		addOverParts(guiKitRobot); // Add GUI factory components over the parts
		addOverParts(guiCamera);

		// Set kit stand coordinates
		guiKitStand.setX(300);
		guiKitStand.setY(150);
		
		// Set camera coordinates
		guiCamera.setX(310);
		guiCamera.setY(160);

		// Create GUI Component - Agent associations
		guiCamera.setCameraSystemAgent(cameraSystem);
		
		// Create Agent to GUI associations
		cameraSystem.setCameraGui1(guiCamera);
		kitRobot.setGuiKitRobot(guiKitRobot);
		
		// Set dependencies
		cameraSystem.setKitRobotAgent(kitRobot);
		guiKitRobot.setKitRobotAgent(kitRobot);
		guiKitRobot.setKitStand(guiKitStand);
		guiCamera.setKitStand(guiKitStand);
		kitRobot.setCameraSystemAgent(cameraSystem);

		// Start Agent threads
		kitRobot.startThread();
		cameraSystem.startThread();
		// cart.startThread();

		// Instantiate and add the buttons panel to the factory panel
		kButtPanel = new KitButtonsPanel();
		add(kButtPanel, BorderLayout.SOUTH);
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < underParts.size(); i++)
			underParts.get(i).update();
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).update();
		for (int i = 0; i < overParts.size(); i++)
			overParts.get(i).update();
		repaint();
		
		if(kitRobot.Left == true)
			{
			   Left = true;
			   kButtPanel.setEnable(Left);
			}
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < underParts.size(); i++)
			underParts.get(i).draw(g);
		for (int i = 0; i < parts.size(); i++)
			parts.get(i).draw(g);
		for (int i = 0; i < overParts.size(); i++)
			overParts.get(i).draw(g);
	}

	protected void addUnderParts(GUIComponent cp) {
		underParts.add(cp);
	}

	protected void addOverParts(GUIComponent cp) {
		overParts.add(cp);
	}

	/**
	 * 
	 * @param cp
	 */
	protected void addParts(GUIComponent cp) {
		parts.add(cp);
	}

	public void startAll() {
		timer.start();
	}

	// this is to indicate that one of the cart has gone by Jerry
	public class KitButtonsPanel extends JPanel implements ActionListener {
		JButton butt1 = new JButton("Fill Kitting Stand");
		JButton butt2 = new JButton("Create Kit Order");
		JButton butt3 = new JButton("---");

		public KitButtonsPanel() {
			setBackground(Color.BLACK);
			setPreferredSize(new Dimension(1000, 50));

			// Set up the ActionListener functionality
			butt1.addActionListener(this);
			butt2.addActionListener(this);
			butt3.addActionListener(this);

			// Add the buttons to the button panel
			add(butt1);
			add(butt2);
			add(butt3);
		}
        public void setEnable(boolean left)
        {
        	butt2.setEnabled(left);
        }
		public void actionPerformed(ActionEvent ae) {
			
			if (ae.getActionCommand() == "Fill Kitting Stand") {
				for (int i = 1; i < 3; i++) {
					for (int j = 0; j < kits.size(); j++) {
						if (guiKitStand.getKitFromStand(i) == null)
							System.out.println("\t OMG THERE IS A NULL KIT.");
						else if (kits.get(j).equals(
								guiKitStand.getKitFromStand(i))) {
							FillStand(guiKitStand.getKitFromStand(i));
							//kitRobot.msgThisKitIsFull(i);
						}
					}
				}
				for(int i = 1; i < 3;  i++){
					if(guiKitStand.getKitFromStand(i) != null)
					 kitRobot.msgThisKitIsFull(i);
				}
			} else if (ae.getActionCommand() == "Create Kit Order") {
				amountOrders++;
				//butt2.setEnabled(false);
				createNewOrder();
			} else if (ae.getActionCommand() == "---") {
				System.out.println("---");
			}
		}
	}

	// Function to create a new kit order
	public void createNewOrder() {
		CartAgent cart = new CartAgent("Cart" + amountOrders.toString());
		cart.startThread();
		cart.setKitRobotAgent(kitRobot);
		cartAgents.add(cart);

		GUICart guiCart = new GUICart(cart, KITSIZE, kits);
		// kitRobot.NewOrders(kitOrder, amount)
		// guiCart.DoCome(kits.size());
		// Add to the paint ArrayList
		underParts.add(guiCart);
		underParts.add(kits.get(kits.size() - 1));

		cart.setGuiCart(guiCart);
		guiCart.setCartAgent(cart);
		kitRobot.setCartAgent(cart);
        KitOrder kitOrder = new KitOrder();
        //kitOrder.setKitOrder();
        ArrayList<PartType>	parts1	= new ArrayList<PartType>();
    	
        for(int i = 0; i < 8; i++)
        {
        	parts1.add(PartType.B);
        }
        kitOrder.setKitOrder(parts1);
		kitRobot.msgHereIsNewJob(kitOrder, 1);
		
		

	}

	public void FillStand(GUIKit kit) {
		GUIPart part1 = new GUIPart(PartType.B); // Parts
		GUIPart part2 = new GUIPart(PartType.B);
		GUIPart part3 = new GUIPart(PartType.B);
		GUIPart part4 = new GUIPart(PartType.B);
		GUIPart part5 = new GUIPart(PartType.B);
		GUIPart part6 = new GUIPart(PartType.B);
		GUIPart part7 = new GUIPart(PartType.B);
		GUIPart part8 = new GUIPart(PartType.B);
		
		if(!fullKitParts.isEmpty())
		{
			for(int i = 0 ; i < 8 ; i++)
				fullKitParts.remove(0);
		}
		
		fullKitParts.add(part1);
		fullKitParts.add(part2);
		fullKitParts.add(part3);
		fullKitParts.add(part4);
		fullKitParts.add(part5);
		fullKitParts.add(part6);
		fullKitParts.add(part7);
		fullKitParts.add(part8);
		
		addParts(part1); // Add parts
		addParts(part2);
		addParts(part3);
		addParts(part4);
		addParts(part5);
		addParts(part6);
		addParts(part7);
		addParts(part8);
		
		kit.addParts(fullKitParts);
	}

	public void DoGo(int number) {
		cartAgents.get(number).msgHereIsAFullKit();

	}
}
