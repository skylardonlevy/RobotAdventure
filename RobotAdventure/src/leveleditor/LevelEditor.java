package leveleditor;

import game.RobotCanvas;
import game.RobotFrame;

import java.awt.Graphics;

import state.GameState;
import state.GameStateManager;
import state.STATE;

public class LevelEditor extends GameState {

	public static final int SCROLL_HORIZONTAL = 0, SCROLL_VERTICAL = 1;
	
	private GridArray grid;
	private int indexOffset;
	private int scrollMode;
	private boolean isGUIOpen;
	private LevelEditorGUI leGUI;
	
	public LevelEditor(STATE stateID, GameStateManager managerReference) {
		super(stateID, managerReference);
		grid = new GridArray(40, 30);
		leGUI = new LevelEditorGUI(64, 0, RobotCanvas.BUFFER_WIDTH-128,RobotCanvas.BUFFER_HEIGHT-39);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		grid.render(g);
		if(isGUIOpen)
			leGUI.render(g);
	}
	
	public void changeIndexOffset(int offset)
	{
		indexOffset = -offset;
		if(scrollMode == SCROLL_VERTICAL)
		{
			if( (indexOffset < 0 && grid.getLowY()  < RobotCanvas.BUFFER_HEIGHT) || (indexOffset > 0 && grid.getTopY()  <0) ) //if scrolling down || scrolling up
				indexOffset = 0;

			grid.setStartYIndex(indexOffset);
			
		}else if(scrollMode == SCROLL_HORIZONTAL)
		{
			if( (indexOffset < 0 && grid.getRightX()  < RobotCanvas.BUFFER_WIDTH) || (indexOffset > 0 && grid.getLeftX()  <0) ) //if scrolling down || scrolling up
				indexOffset = 0;
			
			grid.setStartXIndex(indexOffset);
		}
	}

	public GridArray getGridArray(){return grid;}
	
	public void setScrollMode(int mode)
	{
		this.scrollMode = mode;
	}
	
	public int getScrollMode(){return scrollMode;}
	
	public boolean isGUIOpen(){return isGUIOpen;}
	public void setGUIOpen(boolean guiOpen){this.isGUIOpen = guiOpen;}

	public LevelEditorGUI getGUI() {
		return leGUI;
	}
	
}
