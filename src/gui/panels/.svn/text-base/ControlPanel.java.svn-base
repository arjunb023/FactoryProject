package gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.KittingCell;

public class ControlPanel extends JPanel implements ActionListener
{
	JTabbedPane pane;
	
	JButton startStopButton;
	
	KittingCell myKittingCell;
	FactoryPanel myFactory;
	
	OrderPanel myOrderPanel;
	
	DisasterPanel myDisasterPanel;
	
	JScrollPane scroller;
	
	public ControlPanel(KittingCell kitCell)
	{
		myKittingCell = kitCell;
		myFactory = kitCell.getFactoryPanel();
		myDisasterPanel = new DisasterPanel(myFactory);
		myOrderPanel = new OrderPanel();
		
		setPreferredSize(new Dimension(310,750));
		setLayout(new BorderLayout());
		startStopButton = new JButton("Start");
		startStopButton.addActionListener(this);
		JPanel startStopPanel = new JPanel();
		startStopPanel.add(startStopButton);
		add(startStopPanel, BorderLayout.NORTH);
		pane = new JTabbedPane();
		pane.setPreferredSize(new Dimension(280,750));
		pane.add("Kits", new KitPanel(myKittingCell));
		pane.add("Orders", myOrderPanel);
		
		scroller = new JScrollPane(FactoryPanel.statesPanel);
		scroller.setPreferredSize(new Dimension(250,750));
		pane.add("States", scroller);
		pane.add("Disasters", myDisasterPanel);
		pane.add("Trace", FactoryPanel.tracePanel);
		add(pane, BorderLayout.CENTER);
	}
	
	//Sets the action to be taken when an action is performed by the user.
	public void actionPerformed(ActionEvent e)
	{
		// Start
		if ("Start".equals(startStopButton.getText()))
		{
			startStopButton.setText("Stop");
			myFactory.startThreads();
			myFactory.startAll();
		}
		// Stop
		else
		{
			startStopButton.setText("Start");
			myFactory.tracePanel.reprint();
			myFactory.stopThreads();
			myFactory.stopAll();
		}
	}
	
	// Getters
	//Returns the OrderPanel.
	public OrderPanel getOrderPanel()
	{
		return this.myOrderPanel;
	}
}