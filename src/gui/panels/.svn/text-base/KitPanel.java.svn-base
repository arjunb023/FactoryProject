package gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main.KittingCell;
import shared.KitOrder;
import shared.KitPanelOrder;
import shared.enums.PartType;

public class KitPanel extends JPanel implements ActionListener {

	// GUI field instantiation
	private JTextField kitName = new JTextField(10);
	private JButton addPartsToKit = new JButton("Add Part(s)");
	private JTextField numKits = new JTextField(2);
	private JTextField numParts = new JTextField(2);
	private JButton submitOrder = new JButton("Submit Order");
	private String[] partTypes = { "Bomb Bird", "Blue Bird", "Egg", "Toucan", "Helmet Pig", 
			"Red Bird", "TNT", "White Bird", "Yellow Bird" };
	private JComboBox partType = new JComboBox(partTypes);
	private String[] kitList = { "None", "Default Kit" };
	private JComboBox premadeKitList = new JComboBox(kitList);
	private JButton clearCurrentParts = new JButton("Clear Current Parts");
	private JLabel typesLabel, numPartsLabel;

	String myKitName = "";
	int myNumKits = 0;
	int myNumParts = 0;
	PartType myType = PartType.A;

	ArrayList<PartType> typesOfParts = new ArrayList<PartType>();
	ArrayList<Integer> numPartsOfEachType = new ArrayList<Integer>();
	ArrayList<PartType> order;

	ArrayList<String> allKits = new ArrayList<String>();

	KittingCell myKittingCell;
	FactoryPanel myFactory;

	KitPanelOrder myOrder;

	public KitPanel(KittingCell kitCell) {
		// Edit panel attributes
		setPreferredSize(new Dimension(250, 750));
		setSize(new Dimension(250, 750));

		// Add ActionListeners
		kitName.addActionListener(this);
		addPartsToKit.addActionListener(this);
		numKits.addActionListener(this);
		partType.setActionCommand("Part Type Changed");
		premadeKitList.addActionListener(this);
		premadeKitList.setActionCommand("Premade Kit Selected");
		clearCurrentParts.addActionListener(this);
		partType.addActionListener(this);
		numParts.addActionListener(this);
		submitOrder.addActionListener(this);

		myKittingCell = kitCell;
		myFactory = kitCell.getFactoryPanel();

		// Create the Kit Creation && Kit Order panel (fill with: addParts,
		// kitContents)
		JPanel kitCreation = new JPanel(new BorderLayout(10, 10));
		Border lineBorder = new LineBorder(Color.BLACK, 1);
		kitCreation.setBorder(BorderFactory.createTitledBorder(lineBorder,
				"Kit Creation"));

		JPanel kitOrdering = new JPanel();
		kitOrdering.setBorder(BorderFactory.createTitledBorder(lineBorder,
				"Kit Order"));

		JPanel premadeKitsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		premadeKitsPanel.setPreferredSize(new Dimension(250, 100));
		premadeKitsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder,
				"Premade Kit Order:"));
		premadeKitsPanel.add(new JLabel("Select a Kit:"));
		premadeKitsPanel.add(premadeKitList);

		// Add parts: Type of parts to add JComboBox, number of parts to add
		// JTextField, Add JButton
		JPanel insidePanel = new JPanel(new GridLayout(2, 2, 5, 5));
		insidePanel.add(new JLabel("Add Parts:"));
		insidePanel.add(partType);
		insidePanel.add(new JLabel("#"));
		insidePanel.add(numParts);

		JPanel addParts = new JPanel(new BorderLayout(10, 10));
		addParts.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		addParts.add(insidePanel, BorderLayout.CENTER);
		addParts.add(addPartsToKit, BorderLayout.SOUTH);

		// Container in which the kit name is entered into a JTextField
		Container kitNaming = new Container();
		kitNaming.setLayout(new FlowLayout(FlowLayout.LEFT));
		kitNaming.add(new JLabel("Kit Name: "));
		kitNaming.add(kitName);

		// Number of kits to order
		Container numberOfKits = new Container();
		numberOfKits.setLayout(new FlowLayout(FlowLayout.LEFT));
		numberOfKits.add(new JLabel("Number of Kits: "));
		numberOfKits.add(numKits);

		JPanel innerKitOrdering = new JPanel(new GridLayout(3, 1));
		innerKitOrdering.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		innerKitOrdering.add(kitNaming);
		innerKitOrdering.add(numberOfKits);
		innerKitOrdering.add(submitOrder);

		// Add containers to the Kit Creation panel (addParts, kitContents)
		kitCreation.add(addParts, BorderLayout.CENTER);

		// Add containers to the Kit Order panel (kitNaming, numberOfKits,
		// submitOrder)
		kitOrdering.add(innerKitOrdering);

		// Add the display of the types and number of parts added
		JPanel partsPanel = new JPanel(new BorderLayout());
		partsPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
		// top panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		// top left panel
		JPanel topLeftPanel = new JPanel(new FlowLayout());
		topLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1,
				Color.BLACK));
		topLeftPanel.add(new JLabel("Part Types"));
		// top right panel
		JPanel topRightPanel = new JPanel(new FlowLayout());
		topRightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 2, 0,
				Color.BLACK));
		topRightPanel.add(new JLabel("Number of Each"));

		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		// center panel
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		// center left panel
		JPanel centerLeftPanel = new JPanel(new FlowLayout());
		centerLeftPanel.setBackground(Color.WHITE);
		centerLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
				Color.BLACK));
		typesLabel = new JLabel(getTypes());
		centerLeftPanel.add(typesLabel);
		// center right panel
		JPanel centerRightPanel = new JPanel(new FlowLayout());
		centerRightPanel.setBackground(Color.WHITE);
		centerRightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
				Color.BLACK));
		numPartsLabel = new JLabel(getNumParts());
		centerRightPanel.add(numPartsLabel);

		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);

		// Create a panel containing the partsPanel and the clear current parts
		// button
		JPanel displayPanel = new JPanel(new BorderLayout(10, 10));
		displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		displayPanel.add(partsPanel, BorderLayout.CENTER);
		displayPanel.add(clearCurrentParts, BorderLayout.SOUTH);

		partsPanel.add(topPanel, BorderLayout.NORTH);
		partsPanel.add(centerPanel, BorderLayout.CENTER);
		kitCreation.add(displayPanel, BorderLayout.SOUTH);

		// Add the two main panels (kitCreation, kitOrdering) to main panel
		add(premadeKitsPanel);
		add(kitCreation);
		add(kitOrdering);
	}

	public void actionPerformed(ActionEvent ae) {
		int index = -1;
		if (ae.getActionCommand() == "Part Type Changed") {
			JComboBox jcb = (JComboBox) ae.getSource();
			String pType = (String) jcb.getSelectedItem();

			// Grab the part type from the combobox
			if (pType == "Bomb Bird") {
				myType = PartType.A;
			} else if (pType == "Blue Bird") {
				myType = PartType.B;
			} else if (pType == "Egg") {
				myType = PartType.C;
			} else if (pType == "Toucan") {
				myType = PartType.D;
			} else if (pType == "Helmet Pig") {
				myType = PartType.E;
			} else if (pType == "Red Bird") {
				myType = PartType.F;
			} else if (pType == "TNT") {
				myType = PartType.G;
			} else if (pType == "White Bird") {
				myType = PartType.H;
			} else if (pType == "Yellow Bird") {
				myType = PartType.I;
			}
		}

		if (ae.getActionCommand() == "Premade Kit Selected") {
			JComboBox jcb = (JComboBox) ae.getSource();
			String kitType = (String) jcb.getSelectedItem();

			// Clears parts added before it
			typesOfParts.clear();
			numPartsOfEachType.clear();

			// Disable customization if using a preset
			if (kitType != "None") {
				partType.setEnabled(false);
				numParts.setEnabled(false);
				addPartsToKit.setEnabled(false);
			} else {
				partType.setEnabled(true);
				numParts.setEnabled(true);
				addPartsToKit.setEnabled(true);
			}

			// Create a Kit Order based on the selected kit
			if (kitType == "Default Kit") {
				typesOfParts.add(PartType.A);
				typesOfParts.add(PartType.B);
				typesOfParts.add(PartType.C);
				typesOfParts.add(PartType.D);
				typesOfParts.add(PartType.E);
				typesOfParts.add(PartType.F);
				typesOfParts.add(PartType.G);
				typesOfParts.add(PartType.H);

				for (int i = 0; i < 8; i++) {
					numPartsOfEachType.add(1);
				}

				// Set the kit name
				kitName.setText("Default Kit");
				numKits.setText("5");
			}
		}

		if (ae.getActionCommand() == "Add Part(s)") {
			// Grab numParts from the appropriate text field
			myNumParts = 0;
			String numberOfParts = numParts.getText();
			if (!numberOfParts.isEmpty()) {
				try {
					myNumParts = Integer.parseInt(numberOfParts);
				} catch (NumberFormatException nfe) {
					// Error check if not an integer
					JOptionPane
							.showMessageDialog(
									myKittingCell,
									"Invalid input: The number of parts must be an integer value.",
									"Invalid Input", JOptionPane.ERROR_MESSAGE);
				}
			}

			// First check that the maximum kit size is not reached
			boolean checkNumParts = true, checkPartTypes = true;
			checkNumParts = checkLimitReached();
			checkPartTypes = checkMaxPartTypes();

			if (checkNumParts && checkPartTypes) {
				// Add part type and number of that type to their respective
				// lists if
				if (myNumParts > 0 && myNumParts < 9) {
					index = getIndex(myType);
					if (index != -1) {
						int temp = numPartsOfEachType.get(index)
								+ (Integer) myNumParts;
						numPartsOfEachType.set(index, temp);
					} else {
						numPartsOfEachType.add((Integer) myNumParts);
						typesOfParts.add(myType);
					}
				}
			} else if (!checkNumParts && checkPartTypes) {
				// Cannot add more than 9 parts to a kit
				JOptionPane.showMessageDialog(myKittingCell,
						"The number of parts in a kit cannot exceed 8.",
						"Invalid Number of Parts", JOptionPane.ERROR_MESSAGE);
			} else if (checkNumParts && !checkPartTypes) {
				// Cannot add more than 8 part types to a kit
				JOptionPane.showMessageDialog(myKittingCell,
						"The number of part types cannot exceed 8.",
						"Invalid Number of Parts", JOptionPane.ERROR_MESSAGE);
			} else if (checkNumParts && checkPartTypes) {
				// Cannot add more than 9 parts to a kit & cannot exceed 8 part
				// types
				JOptionPane.showMessageDialog(myKittingCell,
						"The number of parts in a kit cannot exceed 8.<br />"
								+ "The number of part types cannot exceed 8.",
						"Invalid Number of Parts", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (ae.getActionCommand() == "Submit Order") {
			// Process the kit name
			myKitName = kitName.getText();

			// Process the number of kits and parse it to an integer
			String numberOfKits = numKits.getText();
			if (!numberOfKits.isEmpty()) {
				try {
					myNumKits = Integer.parseInt(numberOfKits);
				} catch (NumberFormatException nfe) {
					// Error check if not an integer
					JOptionPane
							.showMessageDialog(
									myKittingCell,
									"Invalid input: The number of parts must be an integer value.",
									"Invalid Input", JOptionPane.ERROR_MESSAGE);
				}
			}

			if (myKitName.isEmpty()) {
				JOptionPane.showMessageDialog(myKittingCell,
						"Please enter a name for your kit.",
						"Invalid Kit Name", JOptionPane.ERROR_MESSAGE);
			} else if (myNumKits <= 0) {
				JOptionPane.showMessageDialog(myKittingCell,
						"No kits will be created.", "Zero Kits",
						JOptionPane.ERROR_MESSAGE);
			} else if (typesOfParts.size() == 0) {
				JOptionPane.showMessageDialog(myKittingCell,
						"There are no parts in this kit.", "Empty Kit",
						JOptionPane.ERROR_MESSAGE);
			} else {

				order = createKitList(typesOfParts, numPartsOfEachType);
				myFactory.getCMA().msgCreateNewJob(order, myNumKits);

				// Create the KitPanelOrder
				myOrder = new KitPanelOrder();
				myOrder.myKitName = myKitName;
				myOrder.myOrder = new KitOrder(order);
				myOrder.numberOrdered = myNumKits;
				myOrder.myState = "IN PROGRESS";

				// Update the Order Panel
				OrderPanel.acquireOrder(myOrder);
				// OrderPanel.update();

				typesOfParts.clear();
				numPartsOfEachType.clear();
				premadeKitList.setSelectedIndex(0);
				kitName.setText("");
				numKits.setText("");
				numParts.setText("");
			}
		} else if (ae.getActionCommand() == "Clear Current Parts") {
			typesOfParts.clear();
			numPartsOfEachType.clear();

			numParts.setText("");
			numKits.setText("");
			kitName.setText("");
		}

		// Updates panel
		typesLabel.setText(getTypes());
		numPartsLabel.setText(getNumParts());

	}

	// returns the string of types
	public String getTypes() {
		String typesText = "<html><p align=center>";
		for (int i = 0; i < typesOfParts.size(); i++) {
			switch(typesOfParts.get(i))
			{
			case A:
				typesText += "Bomb Bird" + "<br>";
				break;
			case B:
				typesText += "Blue Bird" + "<br>";
				break;
			case C:
				typesText += "Egg" + "<br>";
				break;
			case D:
				typesText += "Toucan" + "<br>";
				break;
			case E:
				typesText += "Helmet Pig" + "<br>";
				break;
			case F:
				typesText += "Red Bird" + "<br>";
				break;
			case G:
				typesText += "TNT" + "<br>";
				break;
			case H:
				typesText += "White Bird" + "<br>";
				break;
			case I:
				typesText += "Yellow Bird" + "<br>";
				break;
			default:
				typesText += "Bomb Bird" + "<br>";
				break;
			}
		}
		typesText += "</p></html>";
		return (typesText);
	}

	// returns the string of number of parts
	public String getNumParts() {
		String numPartsText = "<html><p align=center>";
		for (int i = 0; i < numPartsOfEachType.size(); i++)
			numPartsText += numPartsOfEachType.get(i) + "<br>";
		numPartsText += "</p></html>";
		return (numPartsText);
	}

	public ArrayList<PartType> createKitList(ArrayList<PartType> pt,
			ArrayList<Integer> i) {
		order = new ArrayList<PartType>();
		// Loop through list of part types
		for (int j = 0; j < pt.size(); j++) {
			// For each part type, add the part type to the order list the
			// correct number of times
			// according to the AL of Integers
			for (int k = 0; k < i.get(j); k++) {
				order.add(pt.get(j));
			}
		}

		return order;
	}

	public int getIndex(PartType type) {
		for (int i = 0; i < typesOfParts.size(); i++)
			if (typesOfParts.get(i) == type)
				return (i);
		return (-1);
	}

	public boolean checkLimitReached() {
		int totalNumParts = 0;
		for (int i = 0; i < numPartsOfEachType.size(); i++) {
			totalNumParts += (int) numPartsOfEachType.get(i);
		}

		if (totalNumParts + myNumParts > 8)
			return false;

		return true;
	}

	public boolean checkMaxPartTypes() {
		if (typesOfParts.size() >= 8)
			return false;

		return true;
	}
}
