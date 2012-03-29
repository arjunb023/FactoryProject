package shared;



import shared.enums.PartType;
import v0.v1PurgeTestAnimation;
import gui.components.GUIPart;
import gui.panels.FactoryPanel;

public class Part
{
	public PartType type;
	public int number;
	public GUIPart guiPart;
	
	/** static counter for how many parts have been created */
	static int partCounter = 0;
	
	/**
	 * Part constructor
	 * 
	 * partCounter is appended to the end of the 'name' parameter
	 * @param type partType
	 */
	@SuppressWarnings("static-access")
	public Part(PartType type)
	{
		this.partCounter++;
		this.type = type;
		this.number = partCounter;
		this.guiPart = new GUIPart(type);
		this.guiPart.setName(this.getName());
		
		synchronized(FactoryPanel.parts){
			FactoryPanel.parts.add(this.guiPart);
		}
		v1PurgeTestAnimation.parts.add(this.guiPart);
	}
	
	/**
	 * unique name for every part, by appending the partNumber to the end of the type
	 * @return name + number
	 */
	public String getName()
	{
		return type + "-" + number;
	}
}