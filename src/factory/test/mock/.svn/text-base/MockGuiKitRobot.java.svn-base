package factory.test.mock;
import factory.KitRobotAgent;
import factory.interfaces.KitRobot;
import gui.components.GUICart;
import gui.interfaces.*;

public class MockGuiKitRobot implements GUIKitRobotInterface{
    KitRobot kitRobotAgent;
    String name;
	public void setKitRobotAgent(KitRobot kitRobotAgent) {
		// TODO Auto-generated method stub
	 this.kitRobotAgent  = kitRobotAgent;
	}
    
    public void setName(String name)
    {
    	this.name = name;
    }
    public EventLog log = new EventLog();
    
	public void DoMoveToStand(int i, GUICart cart) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("DoMoveToStand from KitRobotAgent"));
		kitRobotAgent.msgFinishedMovingToStand(i);
	}
	public void DoMoveToInspection(int location) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent ("DoMoveToInspection from KitRobotAgent"));
		kitRobotAgent.msgFinishedMovingToInspection();
	}

	public void DoMoveToCart(GUICart guiCart) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent ("DoMoveToCart from KitRobotAgent"));
		kitRobotAgent.msgBackToCart();
	}

	public void DoDump() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent ("DoDump from KitRobotAgent"));
		kitRobotAgent.msgDoneDump();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void DoMoveFromInspectionToStand(int locate) {
		// TODO Auto-generated method stub
		
	}

}
