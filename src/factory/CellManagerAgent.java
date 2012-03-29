package factory;

import java.util.ArrayList;

import factory.interfaces.KitRobot;

import agent.Agent;

import shared.KitConfig;
import shared.KitOrder;
import shared.enums.PartType;

public class CellManagerAgent extends Agent
{
	//////////
	// DATA //
	//////////
	
	enum	CellState{INITIALIZING, READY, CHECKING_CONFIG, RECONFIGURING, WORKING, IDLE}
	enum	JobState{PENDING, IN_PROGRESS, COMPLETED}
	enum	ConfigState{CONFIGURED, NOT_CONFIGURED, UNKNOWN}
	
	public class KitOrderJob
	{
		public	KitOrder	jobOrder;
		public	JobState	jobState;
		public 	int			numOrdersTotal;
		public	int			numOrdersCompleted;		
		
		public KitOrderJob(KitOrder order, int numKits)
		{
			this.jobOrder			= order;
			this.jobState			= JobState.PENDING;
			this.numOrdersTotal		= numKits;
			this.numOrdersCompleted	= 0;
		}
	}
	
	public class ConfiguredAgent
	{
		public	Agent		agent;
		public	ConfigState	state;
		
		public ConfiguredAgent(Agent a, ConfigState s)
		{
			agent	= a;
			state	= s;
		}
	}
	
	CellState					currentCellState;
	
	ArrayList<KitOrderJob>		kitJobs				= new ArrayList<KitOrderJob>();
	KitOrderJob					currentJob;
	KitOrderJob					nextJob;
	
	KitConfig					currentConfig;
	
	ArrayList<ConfiguredAgent>	configuredAgents	= new ArrayList<ConfiguredAgent>();
	KitRobot					cellKitRobot;
	int							numLanes;
	
	//String	name;
	
	/////////////////
	// CONSTRUCTOR //
	/////////////////
	
	public CellManagerAgent(String n)
	{
		super();
		this.name			= n;
		this.currentJob		= null;
		
		currentCellState	= CellState.IDLE;
		
		ArrayList<PartType>	initConfig	= new ArrayList<PartType>();
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		initConfig.add(null);
		this.currentConfig	= new KitConfig(initConfig);
	}
	
	//////////////
	// MESSAGES //
	//////////////
	
	/*
	 * Message that comes from the panels when the user submits a new kit order job from the menu.
	 */
	public void msgCreateNewJob(ArrayList<PartType> kitSpec, int numToProduce)
	{
		print("msgCreateNewJob recieved.");
		
		StringBuilder s2 = new StringBuilder();
		s2.append("Manager SAYS: Cell is " + this.currentCellState + " and has the jobs with the following states:");
		
		this.kitJobs.add(new KitOrderJob(new KitOrder(kitSpec), numToProduce));
		for(KitOrderJob kj : this.kitJobs)
		{
			s2.append("\n\t"+kj.jobState);
		}
		print(s2.toString());
		stateChanged();
	}
	
	/*
	 * Message that comes from a factory agent when their current configuration matches the configuration specified by this manager.
	 */
	public void msgIAmProperlyConfigured(Agent agent)
	{
		print("msgIAmProperlyConfigured recieved from " + agent);
		
		for(ConfiguredAgent a : this.configuredAgents)
		{
			if(a.agent == agent)
			{
				a.state	= ConfigState.CONFIGURED;
			}
		}
		
		stateChanged();
	}
	
	/*
	 * Message that comes from kit robot when it is done successfully completing all of the kit orders for the current job
	 */
	public void msgCompletedAllKitsForCurrentJob()
	{
		print("msgCompletedAllKitsForCurrentJob recieved.");
		
		this.currentCellState		= CellState.IDLE;
		this.currentJob.jobState	= JobState.COMPLETED;
		
		stateChanged();
	}
	
	///////////////
	// SCHEDULER //
	///////////////
	
	@Override
	protected boolean pickAndExecuteAnAction()
	{
		System.out.println("CellManagerAgent SCHEDULER:");
		
		if(this.currentCellState == CellState.INITIALIZING)
		{
			broadcastInitialConfiguration();
			return true;
		}
		
		if(this.currentCellState == CellState.RECONFIGURING)
		{
			if(checkAgentConfigurations())
				return true;
		}
		
		if(this.currentJob == null || this.currentJob.jobState == JobState.COMPLETED)
		{
			for(KitOrderJob job : this.kitJobs)
			{
				if(job.jobState == JobState.PENDING)
				{
					if(this.currentCellState == CellState.IDLE)
					{
						determineConfigurationForNextJob(job);
						return true;
					}
					else if(this.currentCellState == CellState.READY)
					{
						sendOutNextJob(job);
						return true;
					}
				}
			}
		}
		
		return false;
	}	
	
	/////////////
	// ACTIONS //
	/////////////
	
	public void broadcastInitialConfiguration()
	{
		StringBuilder s = new StringBuilder();
		s.append("BroadcastInitialConfiguration");
		this.currentCellState = CellState.RECONFIGURING;
		for(ConfiguredAgent a : this.configuredAgents)
		{
			a.state	= ConfigState.UNKNOWN;
			a.agent.msgThisIsCurrentConfiguration(this.currentConfig);	
			s.append("\nBroadcasted Initial Configuration to " + a.agent);
		}
		print(s.toString());
	}
	
	public boolean checkAgentConfigurations()
	{
		print("CheckAgentConfigurations");
		boolean	allAgentsAreConfigured	= true;
		for(ConfiguredAgent a : this.configuredAgents)
		{
			if(a.state != ConfigState.CONFIGURED)
				allAgentsAreConfigured	= false;
		}
		
		if(allAgentsAreConfigured)
		{
			if(this.currentCellState == CellState.INITIALIZING)
				this.currentCellState	= CellState.IDLE;
			else
				this.currentCellState	= CellState.READY;
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void determineConfigurationForNextJob(KitOrderJob nextJob)
	{
		print("DetermineConfigurationForNextJob");
		ArrayList<PartType>	partTypesForNextJob	= new ArrayList<PartType>();
		for(PartType jobType : nextJob.jobOrder.kitOrder)
		{
			boolean	typeAlreadyInConfig	= false;
			for(PartType accountedForType : partTypesForNextJob)
			{
				if(jobType == accountedForType)
					typeAlreadyInConfig	= true;
			}
			if(!typeAlreadyInConfig)
			{
				partTypesForNextJob.add(jobType);
			}
		}
		
		//StringBuilder s2 = new StringBuilder();
		System.out.println("Manager SAYS: The next job needs the following part types:");		
		boolean allNeededTypesAlreadyInCurrentConfig	= true;
		for(PartType neededType : partTypesForNextJob)
		{
			System.out.println("\n\t" + neededType);
			
			boolean	neededTypeAlreadyInCurrentConfig	= false;
			for(PartType configType : this.currentConfig.kitConfig)
			{
				if(neededType == configType)
				{
					neededTypeAlreadyInCurrentConfig	= true;
				}
			}
			if(!neededTypeAlreadyInCurrentConfig)
			{
				System.out.println("\n\t\tManager SAYS: Part type " + neededType + " is not in the current configuration");
				allNeededTypesAlreadyInCurrentConfig	= false;
			}
		}
		//System.out.println(s2.toString());
		
		//StringBuilder s3 = new StringBuilder();
		if(allNeededTypesAlreadyInCurrentConfig)
		{
			System.out.println("Manager SAYS: All needed types for next job are already in current config");
			this.currentCellState	= CellState.READY;
		}
		else
		{
			System.out.println("Manager SAYS: The factory needs to reconfigure to do the next job");
			this.currentCellState	= CellState.RECONFIGURING;
			this.currentConfig		= createNewConfig(partTypesForNextJob); 
			
			System.out.println("\nManager SAYS: Reconfiguring to this new config:");
			for(PartType t : currentConfig.kitConfig)
				System.out.println("\t" + t + "\n");
			
			for(ConfiguredAgent a : this.configuredAgents)
			{
				System.out.println("\nManager SAYS: Informing " + a.agent + " of new configuration");
				a.state	= ConfigState.UNKNOWN;
				a.agent.msgThisIsCurrentConfiguration(this.currentConfig);			
			}
		}
		//System.out.println(s3.toString());
		
		this.nextJob	= nextJob;
	}
	
	public void sendOutNextJob(KitOrderJob nextJob)
	{
		print("SendOutNextJob");
		this.currentCellState	= CellState.WORKING;
		this.currentJob			= nextJob;
		this.nextJob			= null;
		this.cellKitRobot.msgHereIsNewJob(nextJob.jobOrder, nextJob.numOrdersTotal);
		//To quit right away before my console buffer is overrun with spam
		//System.exit(0);
	}
	
	public KitConfig createNewConfig(ArrayList<PartType> neededPartTypes)
	{
		print("CreateNewConfig");
		ArrayList<PartType>	newConfigSpec	= new ArrayList<PartType>();
		
		if(neededPartTypes.size() < this.numLanes)
		{
			/*
			//option 3 would save time by not having gantry fill unneeded lanes while ensuring pairs of nests fill up to take pictures
			for(int t = 0; t < neededPartTypes.size(); t++)
			{
				newConfigSpec.add(neededPartTypes.get(t));
			}
			if((newConfigSpec.size() % 2) != 0)
			{
				newConfigSpec.add(neededPartTypes.get(neededPartTypes.size()-1));
			}
			*/
			
			
			//option 2 works but not best way
			for(int t = 0, l = 0; l < this.numLanes; t = ((t+1)%neededPartTypes.size()), l++)
			{
				newConfigSpec.add(neededPartTypes.get(t));
			}
						
			/* 
			//option 1 broken
			for(int i = 0; i < neededPartTypes.size(); i++)
			{
				newConfigSpec.add(neededPartTypes.get(i));
			}
			for(int j = 0; j < (this.numLanes - neededPartTypes.size()); j++)
			{
				newConfigSpec.add(neededPartTypes.get(j));
			}
			*/
		}
		else
		{
			for(int i = 0; i < this.numLanes; i++)
			{
				newConfigSpec.add(neededPartTypes.get(i));
			}
		}
		
		return (new KitConfig(newConfigSpec));
	}
	
	///////////////////////////
	// ACCESSORS & MODIFIERS //
	///////////////////////////
	
	public void addConfiguredAgent(Agent a)
	{
		this.configuredAgents.add(new ConfiguredAgent(a, ConfigState.UNKNOWN));
	}
	
	public void setKitRobot(KitRobot kr)
	{
		this.cellKitRobot	= kr;
	}
	
	public void setNumLanes(int n)
	{
		this.numLanes	= n;
	}
}
