package state;

import engine.PhysicsPoly;
import engine.Polygon;
import game.RobotCanvas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import jsonstuff.Reader;
import utilities.RobotFont;

public class Gameplay extends GameState {

	private ArrayList<PhysicsPoly> stuff = new ArrayList<PhysicsPoly>();
	
	public Gameplay(STATE stateID,GameStateManager ref) {
		super(stateID,ref);
		Reader r = new Reader();
		stuff.addAll(r.getPhysicsObjects(""));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		for(int i = 0; i < stuff.size(); i++)
			stuff.get(i).tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, RobotCanvas.BUFFER_WIDTH, RobotCanvas.BUFFER_HEIGHT);
		RobotFont.drawString("GamePlay stuff goes here!", g, 100, 100);
		if (paused)
		{
			g.setColor(Color.BLUE);
			g.fillRect(RobotCanvas.BUFFER_WIDTH/5, RobotCanvas.BUFFER_HEIGHT/5, RobotCanvas.BUFFER_WIDTH * 3 / 5, RobotCanvas.BUFFER_HEIGHT * 3 / 5);
			RobotFont.drawString("Esc - main menu", g, RobotCanvas.BUFFER_WIDTH/5+10, RobotCanvas.BUFFER_HEIGHT/5 + 10, 2, 3);
			RobotFont.drawString("P - pause", g, RobotCanvas.BUFFER_WIDTH/5+10, RobotCanvas.BUFFER_HEIGHT/5 + 70, 2, 3);
			RobotFont.drawString("R - resume game", g, RobotCanvas.BUFFER_WIDTH/5+10, RobotCanvas.BUFFER_HEIGHT/5 + 130, 2, 3);
			RobotFont.drawString("Q - quit game", g, RobotCanvas.BUFFER_WIDTH/5+10, RobotCanvas.BUFFER_HEIGHT/5 + 190, 2, 3);
		}
		
		for(int i = 0; i < stuff.size(); i++)
			stuff.get(i).render(g);
	}
	
	private boolean paused;

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	

}
