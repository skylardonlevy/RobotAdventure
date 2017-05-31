package handler;

import game.RobotCanvas;
import game.RobotFrame;
import handler.AudioHandler.SOUND;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import leveleditor.GridBox;
import leveleditor.LevelEditor;
import state.MENUPAGEID;
import state.Menu;
import state.STATE;
import state.SettingPage;
import button.FauxButton;
import button.MouseOverable;
import button.MultiStateButton;
import button.SliderBar;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static Point mouse;
	private RobotCanvas canvasReference;
	
	public MouseHandler(RobotCanvas reference)
	{
		canvasReference = reference;
		mouse = new Point(-1,-1);
	}
	
	private SliderBar selectedBar;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		mouse = e.getPoint();
		switch(canvasReference.getManager().getCurrentState())
		{
		case MENU:

			Menu menu = (Menu)canvasReference.getManager().getCurrentGameState();

			if(menu.getCurrentPageID() == MENUPAGEID.SETTINGS)
			{
				SettingPage settings = (SettingPage)menu.getCurrentPage();
				
				if(selectedBar == null)
				{
					MouseOverable mo = settings.getSelectedButton();
					if(mo instanceof SliderBar)
						selectedBar = (SliderBar)mo;
				}
				
				
				if(selectedBar != null)
				{
					selectedBar.getSlider().update(p.x);
					selectedBar.onUpdate();
				}

			}
			break;
		}

		

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		Point p = e.getPoint();
		
		switch(canvasReference.getManager().getCurrentState())
		{
		case MENU:
			
			if(e.getButton() == MouseEvent.BUTTON1) //LEFT MOUSE BUTTON
			{
				//System.out.println("MENU SCREEN CLICKED W/ LEFT MOUSE");
				
				Menu menu = (Menu)canvasReference.getManager().getCurrentGameState();
				
				if(menu.getCurrentPageID() == null)
				{
					FauxButton button = menu.getSelectedButton();
					
					if(button != null)
					{
						//System.out.println(button);
						
						switch(button.getID())
						{
						case STARTGAME:
							
							menu.getGameStateManager().setCurrentState(STATE.GAMEPLAY);
							AudioHandler.stopSound(SOUND.MENU_MUSIC);
							break;
						case LEVEL_EDITOR:
							
							menu.getGameStateManager().setCurrentState(STATE.LEVEL_EDITOR);
							AudioHandler.stopSound(SOUND.MENU_MUSIC);
							
							break;
						case EXIT:
							AudioHandler.stopAllSound();
							AudioHandler.playSound(SOUND.EXIT);
							try{Thread.sleep(3000);}catch(Exception ex){} //lets sound play
							System.exit(0);
							break;
						case SETTINGS: //how to play or settings
						case HOW_TO_PLAY:
						case BACK:
							AudioHandler.playSound(SOUND.MENU_SELECT);
							menu.changePage(button.getID());
							break;
						}
						
					}
				}else
				{				
					FauxButton button = menu.getSelectedButton();
					
					if(button != null)
					{
						//System.out.println(button);
						
						switch(button.getID())
						{
						case BACK:
							AudioHandler.playSound(SOUND.MENU_SELECT);
							menu.changePage(button.getID());
							break;
						}
						
						return;
						
					}
					
					switch(menu.getCurrentPageID())
					{
					case SETTINGS:
						
						SettingPage settings = (SettingPage)menu.getCurrentPage();
						
						MouseOverable mo = settings.getSelectedButton();
						
						if(mo != null)
						{
							if(mo instanceof MultiStateButton)
							{
								if(mo.isMouseOver(p.x, p.y));
								{
									((MultiStateButton)mo).changeState();
								}
							}
						}
						
						
						
						break;
					}
				}
				
				
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("MENU SCREEN CLICKED W/ RIGHT MOUSE");
			}else if(e.getButton()==MouseEvent.BUTTON2) //MIDDLE MOUSE BUTTON
			{
				System.out.println("RESIZING WINDOW... (TEMP)");
				if(canvasReference.getRobotFrameReference().getSize().equals(RobotFrame.GAME_FULLSCREEN_SIZE))
				{
					canvasReference.getRobotFrameReference().setSize(RobotFrame.GAME_WINDOWED_SIZE);
					RobotFrame.GAME_SIZE = RobotFrame.GAME_WINDOWED_SIZE;
					canvasReference.setSize(RobotFrame.GAME_WINDOWED_SIZE);
				}else
				{
					canvasReference.getRobotFrameReference().setSize(RobotFrame.GAME_FULLSCREEN_SIZE);
					RobotFrame.GAME_SIZE = RobotFrame.GAME_FULLSCREEN_SIZE;
					canvasReference.setSize(RobotFrame.GAME_FULLSCREEN_SIZE);
				}
				canvasReference.getRobotFrameReference().setLocationRelativeTo(null);
				canvasReference.getRobotFrameReference().updateDimension();
			}
			break;
		case GAMEPLAY:
			
			System.out.println("GAMEPLAY SCREEN CLICKED");
			
			break;
		case LEVEL_EDITOR:
			
			LevelEditor lv = (LevelEditor)canvasReference.getManager().getCurrentGameState();
			if(e.getButton() == MouseEvent.BUTTON1) //handle left click on a box
			{
				
				if(lv.isGUIOpen())
				{
					lv.getGUI().updateSelectedOption(p.x,p.y);
					
				}else
				{
					GridBox b = lv.getGridArray().getBoxClicked(p.x, p.y);

					if(b != null)
					{
						System.out.println(b);
						lv.setGUIOpen(true);
					}
				}
				
			}else if(e.getButton() == MouseEvent.BUTTON2)
			{
				lv.setScrollMode(1 - lv.getScrollMode());
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				lv.getGameStateManager().setCurrentState(STATE.MENU);
			}
			
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		Point p = e.getPoint();
		mouse = e.getPoint();
		switch(canvasReference.getManager().getCurrentState())
		{
		case MENU:

			Menu menu = (Menu)canvasReference.getManager().getCurrentGameState();

			if(menu.getCurrentPageID() == MENUPAGEID.SETTINGS)
			{
				SettingPage settings = (SettingPage)menu.getCurrentPage();
				
				if(selectedBar != null)
				{
					selectedBar = null;
				}
			}
			break;
		}
	}

	/////
	
	public Point getMouse()
	{
		return mouse;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println(e.getWheelRotation());
		
		switch(canvasReference.getManager().getCurrentState())
		{
		case LEVEL_EDITOR:
			
			LevelEditor lv = (LevelEditor)canvasReference.getManager().getCurrentGameState();
			lv.changeIndexOffset(e.getWheelRotation()/Math.abs(e.getWheelRotation()));
			
			break;
		}
		
		
	}
	
}
