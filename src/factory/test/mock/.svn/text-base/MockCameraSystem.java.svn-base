package factory.test.mock;

import java.util.ArrayList;

import shared.KitConfig;
import shared.KitOrder;
import shared.enums.PartQuality;
import shared.enums.PartType;
import factory.NestAgent;
import factory.interfaces.CameraSystem;
import factory.interfaces.KitRobot;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;
import gui.components.GUICamera;
import gui.components.GUIPart;

public class MockCameraSystem extends MockAgent implements CameraSystem
{
	public EventLog log = new EventLog();
	
	public MockCameraSystem(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgIAmFull(Nest nest, ArrayList<GUIPart> p)
	{
		log.add(new LoggedEvent("msgIAmFull\n"));		
	}

	@Override
	public void msgKitIsGood()
	{
		log.add(new LoggedEvent("msgKitIsGood\n"));		
	}

	@Override
	public void msgKitIsBad()
	{
		log.add(new LoggedEvent("msgKitIsBad\n"));		
	}

	@Override
	public void setKitRobotAgent(KitRobot kr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPartsRobotAgent(PartsRobot pr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgTakeKitPicture(KitOrder k) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgHereIsNestList(ArrayList<PartQuality> pq1,
			ArrayList<PartQuality> pq2, GUICamera gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgThisIsCurrentConfiguration(KitConfig k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCameraGui1(GUICamera c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCameraGui2(GUICamera c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCameraGui3(GUICamera c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCameraGui4(GUICamera c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setKitCamera(GUICamera c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIAmNoLongerFull(Nest n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneTakingGoodPartsFromNest(int num)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNests(int num, Nest n1, Nest n2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgKitHasMissingParts(ArrayList<PartType> mp) {
		// TODO Auto-generated method stub
		
	}
	
	

}
