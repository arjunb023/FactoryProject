package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import agent.Agent;
import factory.CameraSystemAgent;
import factory.CartAgent;
import factory.CellManagerAgent;
import factory.DiverterAgent;
import factory.FeederAgent;
import factory.GantryAgent;
import factory.KitRobotAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.PartsRobotAgent;

/*
 * TracePanel contains JCheckBoxes to choose which agent messages to display and a JTextArea which displays the desired messages.
 * @author Reetika Rastogi
 */
public class TracePanel extends JPanel {

	private final int MESSAGELIMIT = 8000;
	private int counter = 0;

	private Queue<messageAgentPair> messages = new ArrayBlockingQueue<messageAgentPair>(
			MESSAGELIMIT, true);

	private JTextArea messageArea;
	private JScrollPane messageScrollPane;
	private JPanel options;

	private JCheckBox gantryB;
	private JCheckBox feederB;
	private JCheckBox diverterB;
	private JCheckBox laneB;
	private JCheckBox nestB;
	private JCheckBox partsRobotB;
	private JCheckBox kitRobotB;
	private JCheckBox cameraB;
	private JCheckBox cartB;
	private JCheckBox managerB;

	public TracePanel() {

		this.setSize(500, 300);
		this.setPreferredSize(new Dimension(500, 300));

		options = new JPanel();

		gantryB = new JCheckBox("Gantry");
		options.add(gantryB);
		gantryB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		feederB = new JCheckBox("Feeder");
		options.add(feederB);
		feederB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		diverterB = new JCheckBox("Diverter");
		options.add(diverterB);
		diverterB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		laneB = new JCheckBox("Lane");
		options.add(laneB);
		laneB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		nestB = new JCheckBox("Nest");
		options.add(nestB);
		nestB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		partsRobotB = new JCheckBox("Parts Robot");
		options.add(partsRobotB);
		partsRobotB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		kitRobotB = new JCheckBox("Kit Robot");
		options.add(kitRobotB);
		kitRobotB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		cameraB = new JCheckBox("Camera System");
		options.add(cameraB);
		cameraB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		cartB = new JCheckBox("Cart");
		options.add(cartB);
		cartB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		managerB = new JCheckBox("Cell Manager");
		options.add(managerB);
		managerB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counter++;
				reprint();
			}
		});

		options.setPreferredSize(new Dimension(500, 120));
		options.setMaximumSize(new Dimension(500, 120));

		messageArea = new JTextArea(
				"There are no agent messages to display right now.\n", 10, 500);

		messageArea.setSize(500, 250);
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		messageArea.setEditable(false);
		messageArea.setSelectionColor(Color.black);
		messageScrollPane = new JScrollPane(this.messageArea);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(options);
		add(messageScrollPane);
	}

	/*
	 * Not used: reprint is called from the factory frame by that class's timer
	 * Every X calls of the timer, checks to see if any new messages were sent
	 * to be print. If new messages were printed by agents, the actionPerformed
	 * will call reprint() to refresh the JTextArea.
	 */

	public void addPrint(String message, Agent agent) {
		messageAgentPair pair = new messageAgentPair(message, agent);
		if (messages.size() == MESSAGELIMIT) {
			messages.remove();
		}
		synchronized (messages) {
			messages.offer(pair);
		}
		counter++;
	}

	public void reprint() {
		if (counter == 0)
			return;

		counter = 0;

		StringBuilder stringBuilder = new StringBuilder();

		for (messageAgentPair pair : messages) {
			if (pair.getAgent() instanceof GantryAgent && gantryB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof FeederAgent && feederB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof DiverterAgent
					&& diverterB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof LaneAgent && laneB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof NestAgent && nestB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof PartsRobotAgent
					&& this.partsRobotB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof KitRobotAgent
					&& this.kitRobotB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof CameraSystemAgent
					&& this.cameraB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof CartAgent && this.cartB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}

			if (pair.getAgent() instanceof CellManagerAgent
					&& this.managerB.isSelected()) {
				synchronized (messages) {
					stringBuilder.append(pair.getMessage());
				}
			}
		}

		try {
			messageArea.setText(stringBuilder.toString());
		} catch (Exception e) {

		}
		try {
			messageArea.setCaretPosition(messageArea.getDocument().getLength());
		} catch (Exception e) {
		}
	}

	private class messageAgentPair {
		private String message;
		private Agent agent;

		public messageAgentPair(String m, Agent a) {
			message = m;
			agent = a;
		}

		public String getMessage() {
			return "[" + agent.getName() + "] " + message + "\n";
		}

		public Agent getAgent() {
			return agent;
		}
	}
}