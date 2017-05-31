package handler;

import game.RobotCanvas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import leveleditor.GUIText;
import leveleditor.LevelEditor;
import leveleditor.LevelEditorGUI;
import state.Gameplay;
import state.STATE;

public class KeyHandler implements KeyListener {

	private RobotCanvas canvasReference;
	
	public KeyHandler(RobotCanvas reference)
	{
		canvasReference = reference;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		char keyChar = e.getKeyChar();
		
		switch(canvasReference.getManager().getCurrentState())
		{
		case GAMEPLAY:
			Gameplay g = (Gameplay) canvasReference.getManager().getCurrentGameState();
			switch(keyCode)
			{
			case KeyEvent.VK_ESCAPE:
				canvasReference.getManager().setCurrentState(STATE.MENU);
				g.setPaused(false);
				break;
			case KeyEvent.VK_P:
				g.setPaused(true);
				break;
			case KeyEvent.VK_R:
				g.setPaused(false);
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			}
			break;
		case LEVEL_EDITOR:
			
			LevelEditor lvl = (LevelEditor) canvasReference.getManager().getCurrentGameState();
			
			if(lvl.isGUIOpen())
			{
				LevelEditorGUI gui = lvl.getGUI();
				
				GUIText text = gui.getSelectedGUIOption();
				if(text != null)
				{
					switch(keyCode)
					{
					case KeyEvent.VK_BACK_SPACE:
						if(!text.getText().equals(""))
							text.setText(text.getText().substring(0,text.getText().length()-1));
						break;
					case KeyEvent.VK_ESCAPE:
						text.setText("");
						break;
					case KeyEvent.VK_SPACE:
						text.setText(text.getText().trim() + " ");
						break;
					default:
						text.setText(text.getText()+keyChar);
						break;
					}
					
				}
				
			}
			
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
