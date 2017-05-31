package state;

import java.awt.Graphics;

public abstract class GameState {
		
	private STATE stateID;
	private GameStateManager managerReference;
	
	public GameState(STATE stateID,GameStateManager managerReference)
	{
		this.stateID = stateID;
		this.managerReference = managerReference;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);

	public STATE getStateID() {return stateID;}
	
	public GameStateManager getGameStateManager()
	{
		return managerReference;
	}
	
}
