package gui.interfaces;

import factory.CartAgent;

public interface GUICartInterface {

	public abstract void DoCome(int stopNum);

	public abstract void DoMove(int stopNum);

	public abstract void DoGo();

	void setCartAgent(CartAgent cartAgent);
}
