package state;

import game.RobotCanvas;

import java.awt.Graphics;
import java.util.ArrayList;

import leveleditor.LevelEditor;

public class GameStateManager {

	private RobotCanvas canvasReference;
	private ArrayList<GameState> gameStates;
	private STATE currentState;
	
	public GameStateManager(RobotCanvas ref)
	{
		canvasReference = ref;
		gameStates = new ArrayList<GameState>();
		gameStates.add(new Menu(STATE.MENU,this));
		gameStates.add(new Gameplay(STATE.GAMEPLAY,this));
		gameStates.add(new LevelEditor(STATE.LEVEL_EDITOR, this));
		currentState = STATE.MENU;
	}
	
	public void tick()
	{
		if(currentState.ordinal() < gameStates.size() && gameStates.get(currentState.ordinal()) != null)
			gameStates.get(currentState.ordinal()).tick();
	}
	
	public void render(Graphics g)
	{
		if(currentState.ordinal() < gameStates.size() && gameStates.get(currentState.ordinal()) != null)
			gameStates.get(currentState.ordinal()).render(g);
	}

	
	
	/*
	 * Getters / Setters
	 */
	public STATE getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(STATE state)
	{
		currentState = state;
	}
	
	public GameState getCurrentGameState(){
		return gameStates.get(currentState.ordinal());
	}
	
}
