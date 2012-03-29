package gui.panels;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import factory.*;

import agent.Agent;


public class StatesPanel extends JPanel
{
	private class MyState
	{
		Agent agent;
		String state;
		
		public MyState(Agent agent)
		{
			this.agent = agent;
		}
	};
	
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> states = new ArrayList<String>();
	ArrayList<Agent> agents = new ArrayList<Agent>();
	JLabel namesLabel;
	JLabel statesLabel;
	
	public StatesPanel()
	{
		JPanel inner = new JPanel(new BorderLayout());
		//setLayout(new BorderLayout());
		inner.setMinimumSize(new Dimension(250, 1250));
		inner.setMaximumSize(new Dimension(280, 1250));
		//inner.setPreferredSize(new Dimension(280,900));
		inner.setPreferredSize(new Dimension(280,1250));
		inner.setBorder(new LineBorder(Color.BLACK, 2, true));
		//inner.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		//top panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		//top left panel
		JPanel topLeftPanel = new JPanel(new FlowLayout());
		topLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.BLACK));
		topLeftPanel.add(new JLabel("Agent"));
		//top right panel
		JPanel topRightPanel = new JPanel(new FlowLayout());
		topRightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 2, 0, Color.BLACK));
		topRightPanel.add(new JLabel("State"));
		
		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		//center panel
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		//center left panel
		JPanel centerLeftPanel = new JPanel(new FlowLayout());
		centerLeftPanel.setBackground(Color.WHITE);
		centerLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		namesLabel = new JLabel(getNames());
		centerLeftPanel.add(namesLabel);
		//center right panel
		JPanel centerRightPanel = new JPanel(new FlowLayout());
		centerRightPanel.setBackground(Color.WHITE);
		centerRightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		statesLabel = new JLabel(getStates());
		centerRightPanel.add(statesLabel);
		
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		
		inner.add(topPanel, BorderLayout.NORTH);
		inner.add(centerPanel, BorderLayout.CENTER);
		add(inner);
	}
	
	/**
	 * Returns the names of agents formatted to be added to the JLabel.
	 * @return
	 */
	public String getNames()
	{
//		String namesText = "<html><p align=center>";
//		for (int i = 0; i < names.size(); i++)
//			namesText += names.get(i) + "<br>";
//		namesText += "</p></html>";
		StringBuffer namesText = new StringBuffer("<html><p align=center>");
		synchronized(names){
		for (int i = 0; i < names.size(); i++)
			namesText.append(names.get(i)).append("<br>");
		}
		namesText.append("</p></html>");

		return (namesText).toString();
	}
	
	/**
	 * Returns the states of the agents formatted to be added to the JLabel.
	 * @return
	 */
	public String getStates()
	{
/*		String statesText = "<html><p align=center>";
		for (int i = 0; i < states.size(); i++)
			statesText += states.get(i) + "<br>";
		statesText += "</p></html>";
*/
		StringBuffer statesText = new StringBuffer ("<html><p align=center>");
		synchronized(states){
		for(int i = 0; i < states.size(); i++){
			statesText.append(states.get(i)).append("<br>");
		}
		}
		statesText.append("</p></html>");
		
		return statesText.toString();
	}
	
	/**
	 * Adds an agent to the panel.  Do this BEFORE initialize() is called.
	 * @param agent
	 */
	public void addAgent(Agent agent)
	{
		//System.out.println(agent.getName() + "ADDED TO STATES PANEL");

		agents.add(agent);
	}
	
	/**
	 * initialize the States Panel after ALL agents have been created.
	 */
	public void initialize()
	{
		synchronized(names){
		names.clear();}

		synchronized(agents){
			StringBuilder s = new StringBuilder();
			
			for (Agent agent : agents)
			{
				
				if (agent instanceof DiverterAgent
						|| agent instanceof CartAgent
						|| agent instanceof KitRobotAgent
						|| agent instanceof PartsRobotAgent
						|| agent instanceof CameraSystemAgent
						|| agent instanceof CellManagerAgent)
				// only 1 state
				{
					s.append("Hi, " + agent.getName() + "\n");
					names.add("______" + agent.getName() + "______");
				}
				else
				// 3 states
				{
					s.append("Hi, " + agent.getName() + "\n");
					names.add(agent.getName());
					names.add(" ");
					//names.add(" ");
					//names.add(" ");
					names.add("______________________");
				}
				
			}
			System.out.println(s.toString());
		}
		
		namesLabel.setText(getNames());
	}

	/**
	 * updates states
	 */
	public void updateStates()
	{
		synchronized(states){
			states.clear();
			synchronized(agents){
				for (Agent agent : agents)
				{
					if (agent instanceof DiverterAgent
						|| agent instanceof CartAgent
						|| agent instanceof KitRobotAgent
						|| agent instanceof PartsRobotAgent
						|| agent instanceof CameraSystemAgent
						|| agent instanceof CellManagerAgent)
					// only 1 state
					{
							states.add("_________" + agent.getState()[0] + "_________");
					}
					else
					// 3 states
					{
						for (int i = 0; i < 3; i++)
						{
							states.add(agent.getState()[i]);
						}
					}
					
				}
			}
			statesLabel.setText(getStates());
		}

		
	}
}

	