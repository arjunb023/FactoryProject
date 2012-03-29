package gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

import main.KittingCell;

import shared.KitOrder;
import shared.KitPanelOrder;

public class OrderPanel extends JPanel
{
	private static ArrayList<KitPanelOrder> currentOrders;
	static JLabel namesLabel;
	static JLabel orderedLabel;
	static JLabel completeLabel;
	static JLabel statusLabel;
	
	public OrderPanel() 
	{		
		currentOrders = new ArrayList<KitPanelOrder>();
		JPanel printKits = new JPanel(new BorderLayout());
		printKits.setPreferredSize(new Dimension(250, 200));
		printKits.setBorder(new LineBorder(Color.BLACK, 2, true));
		JPanel topPanel = new JPanel(new GridLayout(1, 4));
		//top panel 1
		JPanel topPanel1 = new JPanel(new FlowLayout());
		topPanel1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.BLACK));
		topPanel1.add(new JLabel("Kit Name"));
		//top panel 2
		JPanel topPanel2 = new JPanel(new FlowLayout());
		topPanel2.setBorder(BorderFactory.createMatteBorder(0, 1, 2, 1, Color.BLACK));
		topPanel2.add(new JLabel("<html><p align=center>Completed /<br>Ordered</p></html>"));
		//top panel 4
		JPanel topPanel4 = new JPanel(new FlowLayout());
		topPanel4.setBorder(BorderFactory.createMatteBorder(0, 1, 2, 0, Color.BLACK));
		topPanel4.add(new JLabel("Status"));
		topPanel.add(topPanel1);
		topPanel.add(topPanel2);
		topPanel.add(topPanel4);
		
		//center panel
		JPanel centerPanel = new JPanel(new GridLayout(1, 3));
		//center panel 1
		JPanel centerPanel1 = new JPanel(new FlowLayout());
		centerPanel1.setBackground(Color.WHITE);
		centerPanel1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		namesLabel = new JLabel(getNames());
		centerPanel1.add(namesLabel);
		//center panel 2
		JPanel centerPanel2 = new JPanel(new FlowLayout());
		centerPanel2.setBackground(Color.WHITE);
		centerPanel2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		orderedLabel = new JLabel(getOrderedAndCompleted());
		centerPanel2.add(orderedLabel);
		//center panel 4
		JPanel centerPanel4 = new JPanel(new FlowLayout());
		centerPanel4.setBackground(Color.WHITE);
		centerPanel4.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		statusLabel = new JLabel(getStatus());
		centerPanel4.add(statusLabel);
		
		centerPanel.add(centerPanel1);
		centerPanel.add(centerPanel2);
		centerPanel.add(centerPanel4);
		
		printKits.add(topPanel, BorderLayout.NORTH);
		printKits.add(centerPanel, BorderLayout.CENTER);
		add(printKits);
	}
	
	public static void acquireOrder(KitPanelOrder newOrder)
	{
		//boolean flag = false;
		//for (int i = 0; i < currentOrders.size(); i++){
			//if (newOrder.myKitName.equals(currentOrders.get(i).myKitName)){
				//currentOrders.get(i).numberOrdered += newOrder.numberOrdered;
				//flag = true;
				//break;
//			}
//		}
//		//if doesn't exist in current list
//		if (flag == false) {
			currentOrders.add(newOrder);
			//add new kit to list
		//}
		update();
	}
	
	public static void updateStatus()
	{
		for (int i = 0; i < currentOrders.size(); i++){
			if (currentOrders.get(i).numberCompleted == 0){
				currentOrders.get(i).myState = "In Queue";
			}
			else if (currentOrders.get(i).numberCompleted < currentOrders.get(i).numberOrdered){
				currentOrders.get(i).myState = "In Progress";
			}
			else{
				currentOrders.get(i).myState = "Completed";
			}
		}
	}
	
	public static void orderComplete(KitOrder doneOrder)
	{
		boolean equalsFlag = true;
		//playSound("sound/VICTORY.wav");
		for (int i = 0; i < currentOrders.size(); i++){
			equalsFlag = true;
			if (doneOrder.getKitOrder().size() != currentOrders.get(i).myOrder.getKitOrder().size() || currentOrders.get(i).numberOrdered == currentOrders.get(i).numberCompleted){
				equalsFlag = false;
			} else {
				for (int j = 0; j < doneOrder.getKitOrder().size(); j++){
					if (doneOrder.getKitOrder().get(j) != currentOrders.get(i).myOrder.getKitOrder().get(j)){
						equalsFlag = false;
					}
				}
			}
			if (equalsFlag)
			{
				currentOrders.get(i).numberCompleted += 1;
				update();
				equalsFlag = true;
				return;
			}
		}
		update();
	}
	
	  public static synchronized void playSound(final String fileName) {
		    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
		      public void run() {
		        try {
		          Clip clip = AudioSystem.getClip();
		          File fileIn = new File(fileName);
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(fileIn);
		          clip.open(inputStream);
		          //clip.start();
		          clip.start();
		        } catch (Exception e) {
		          System.err.println(e.getMessage());
		        }
		      }
		    }).start();
		  }

	
	public static String getNames()
	{
		String namesText = "<html><p align=center>";
		for (int i = 0; i < currentOrders.size(); i++){
			namesText += currentOrders.get(i).myKitName + "<br>";
		}
		namesText += "</p></html>";
		return (namesText);
	}
	
	public static String getOrderedAndCompleted()
	{		
		String statesText = "<html><p align=center>";
		for (int i = 0; i < currentOrders.size(); i++)
			statesText += currentOrders.get(i).numberCompleted + " / " + currentOrders.get(i).numberOrdered + "<br>";
		statesText += "</p></html>";
		return (statesText);
	}
	
	public static String getStatus()
	{		
		String statesText = "<html><p align=center>";
		for (int i = 0; i < currentOrders.size(); i++)
			statesText += currentOrders.get(i).myState + "<br>";
		statesText += "</p></html>";
		return (statesText);
	}
	
	public static void update()
	{
		updateStatus();
		namesLabel.setText(getNames());
		orderedLabel.setText(getOrderedAndCompleted());
		statusLabel.setText(getStatus());
	}
}
