package shared;

import gui.components.GUIBin;
import gui.panels.FactoryPanel;
import shared.enums.PartType;
import v0.v1PurgeTestAnimation;

import java.util.ArrayList;



public class Bin {

	private ArrayList<Part> parts = new ArrayList<Part>();

	private PartType type;
	
	public final static int NUM_PARTS = 5;
	
	public GUIBin guiBin;
	
	public Bin(PartType type) {
		this.type = type;
		this.guiBin = new GUIBin(type);
		
		v1PurgeTestAnimation.bins.add(this.guiBin);
		synchronized(FactoryPanel.upperBins){
		FactoryPanel.upperBins.add(this.guiBin);
		}
		
		for (int i=0;i<NUM_PARTS;i++) {
			parts.add(new Part(type));
		}
	}	
	
	public PartType getPartType() {
		return this.type;
	}
	
	public ArrayList<Part> getParts() {
		return this.parts;
	}
	
	public void switchBin() {
		synchronized(FactoryPanel.upperBins){
		FactoryPanel.upperBins.remove(this.guiBin);
		}
		synchronized(FactoryPanel.lowerBins){
		FactoryPanel.lowerBins.add(this.guiBin);
		}
	}

}
