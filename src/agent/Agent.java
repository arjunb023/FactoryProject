/**
 * @author Jai Bapna
 */

package agent;

import gui.panels.FactoryPanel;
import gui.panels.StatesPanel;
import gui.panels.TracePanel;

import java.util.concurrent.Semaphore;

import shared.KitConfig;

/** Base class for simple agents */
public abstract class Agent {
	Semaphore stateChange = new Semaphore(1, true);// binary semaphore, fair
	private AgentThread agentThread;
	private TracePanel tracePanel;
	private StatesPanel statesPanel;

	protected String name = StringUtil.shortName(this);

	private String previousState = "None";
	private String currentState = "None";
	private String previousState1 = "None";
	private String currentState1 = "None";
	private String previousState2 = "None";
	private String currentState2 = "None";
	private String previousState3 = "None";
	private String currentState3 = "None";
	private String previousState4 = "None";
	private String currentState4 = "None";

	/**
	 * CONSTRUCTOR
	 */
	protected Agent()
	{
		this.tracePanel = FactoryPanel.tracePanel;
		this.statesPanel = FactoryPanel.statesPanel;
		statesPanel.addAgent(this);
	}

	/**
	 * This should be called whenever state has changed that might cause the
	 * agent to do something.
	 */
	protected void stateChanged() {
		stateChange.release();
	}

	/**
	 * Agents must implement this scheduler to perform any actions appropriate
	 * for the current state. Will be called whenever a state change has
	 * occurred, and will be called repeated as long as it returns true.
	 * 
	 * @return true iff some action was executed that might have changed the
	 *         state.
	 */
	protected abstract boolean pickAndExecuteAnAction();

	/**
	 * Return agent name for messages. Default is to return java instance name.
	 */
	public String getName() {
		// return StringUtil.shortName(this);
		return this.name;
	}

	/**
	 * Return agent state for StatesPanel. Default is to return java instance name.
	 */
	public String[] getState() {
		String[] states = {"","","","",""};
		states[0] = currentState;
		states[1] = currentState1;
		states[2] = currentState2;
		states[3] = currentState3;
		states[4] = currentState4;
		return states;
	}
	
	/** The simulated action code */
	protected void Do(String msg) {
		print(msg, null);
	}

	/** Start agent scheduler thread. Should be called once at init time. */
	public synchronized void startThread() {
		if (agentThread == null) {
			agentThread = new AgentThread(getName());
			agentThread.start(); // causes the run method to execute in the
									// AgentThread below
		} else {
			agentThread.interrupt();// don't worry about this for now
		}
	}

	/** Stop agent scheduler thread. */
	// In this implementation, nothing calls stopThread().
	// When we have a user interface to agents, this can be called.
	public void stopThread() {
		if (agentThread != null) {
			agentThread.stopAgent();
			agentThread = null;
		}
	}

	/**
	 * Agent scheduler thread, calls respondToStateChange() whenever a state
	 * change has been signalled.
	 */
	private class AgentThread extends Thread {
		private volatile boolean goOn = false;

		private AgentThread(String name) {
			super(name);
		}

		public void run() {
			goOn = true;

			while (goOn) {
				try {
					// The agent sleeps here until someone calls,
					// stateChanged(),
					// which causes a call to stateChange.give(), which wakes up
					// agent.
					stateChange.acquire();
					// The next while clause is the key to the control flow.
					// When the agent wakes up it will call
					// respondToStateChange()
					// repeatedly until it returns FALSE.
					// You will see that pickAndExecuteAnAction() is the agent
					// scheduler.
					while (pickAndExecuteAnAction())
						;
					// only gets here if it returns FALSE
					//setCurrentState("Sleeping.");
				} catch (InterruptedException e) {
					// no action - expected when stopping or when deadline
					// changed
				} catch (Exception e) {
					print("Unexpected exception caught in Agent thread:", e);
				}
			}
		}

		private void stopAgent() {
			goOn = false;
			this.interrupt();
		}
	}

	public void msgThisIsCurrentConfiguration(KitConfig currentConfig) {
		// TODO Auto-generated method stub
		print("NOT IMPLEMENTED");
	}
	

	/** Print message */
	protected void print(String msg) {
		print(msg, null);
	}

	/** Print message with exception stack trace */
	protected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2;

		sb.append(getName());
		sb.append(": ");
		sb.append(msg);
		sb.append("\n");
		// If Exception...
		if (e != null)
		{
			sb.append(StringUtil.stackTraceString(e));
			System.out.print(sb.toString());
			
			sb2 = new StringBuffer();
			sb2.append(msg);
			sb2.append("\n");
			sb2.append(StringUtil.stackTraceString(e));
			if (tracePanel != null) {
				tracePanel.addPrint(sb2.toString(), this);
			}
			return;
		}
		// else...
		System.out.print(sb.toString());
		if (tracePanel != null) {
			tracePanel.addPrint(msg, this);
		} else {
			System.out.println("TRACE PANEL IS NULL");
		}
	}

	/**
	 * Writes a new String when the "state" of your agent has changed!
	 * 
	 * This is for displaying in the StatesPanel.
	 * @param s
	 */
	protected void setCurrentState(String s)
	{
		this.previousState = currentState;
		this.currentState = s;
		
		if ( !previousState.equals(currentState) )
		{
			statesPanel.updateStates();
		}
	}
	protected void setCurrentState1(String s)
	{
		this.previousState1 = currentState1;
		this.currentState1 = s;
		
		if ( !previousState1.equals(currentState1) )
		{
			statesPanel.updateStates();
		}
	}
	protected void setCurrentState2(String s)
	{
		this.previousState2 = currentState2;
		this.currentState2 = s;
		
		if ( !previousState2.equals(currentState2) )
		{
			statesPanel.updateStates();
		}
	}
	@Deprecated
	protected void setCurrentState3(String s)
	{
		this.previousState3 = currentState3;
		this.currentState3 = s;
		
		if ( !previousState3.equals(currentState3) )
		{
			statesPanel.updateStates();
		}
	}
	@Deprecated
	protected void setCurrentState4(String s)
	{
		this.previousState4 = currentState4;
		this.currentState4 = s;
		
		if ( !previousState4.equals(currentState4) )
		{
			statesPanel.updateStates();
		}
	}
	
	
	public void setTracePanel(TracePanel tracePanel) {
		this.tracePanel = tracePanel;
	}
	
	public void setStatesPanel(StatesPanel statesPanel) {
		this.statesPanel = statesPanel;
	}
}
