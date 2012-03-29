package gui.interfaces;

import gui.components.GUIPart;
import gui.components.GUIComponent;

/**
 * Interface to make sure GUIComponets implement proper give/get/got handshakes to keep track of parts
 * @author Andrew Heiderscheit
 *
 */

public interface GUIComponentInterface {
	
	public abstract void givePart(GUIPart part, GUIComponent giver);
	
	public abstract GUIPart getPart(int position);
	
	public abstract void gotPart(GUIPart part);

}
