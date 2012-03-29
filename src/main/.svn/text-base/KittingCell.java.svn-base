package main;

import gui.panels.ControlPanel;
import gui.panels.FactoryPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class KittingCell extends JFrame {
	// public ControlPanel factoryControls;
	FactoryPanel factoryPanel;
	ControlPanel cPanel;

	public KittingCell() {
		// Set Frame attributes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setTitle("Kitting Cell v2.0");

		factoryPanel = new FactoryPanel();
		cPanel = new ControlPanel(this);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(factoryPanel), new JScrollPane(cPanel));

		factoryPanel.setMinimumSize(new Dimension(800, 750));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(1006);
		add(splitPane, BorderLayout.CENTER);
		pack();
		setVisible(true);
		factoryPanel.startAll();
		
	}
	  
	/** Main routine to get gui started */
	public static void main(String[] args) {
		KittingCell kittingCell = new KittingCell();
	}

	public FactoryPanel getFactoryPanel() {
		return this.factoryPanel;
	}

	public ControlPanel getControlPanel() {
		return this.cPanel;
	}

}
