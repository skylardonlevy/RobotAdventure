package state;

import game.RobotCanvas;
import handler.AudioHandler;
import handler.AudioHandler.SOUND;
import handler.MouseHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import utilities.RobotFont;
import button.BUTTONID;
import button.FauxButton;
import constants.RobotImageLoader;

public class Menu extends GameState {
	
	public Menu(STATE stateID,GameStateManager ref) {
		super(stateID,ref);
		buttonList = new ArrayList<FauxButton>();
		buttonList.add(new FauxButton(50, 300, 300, 100,BUTTONID.HOW_TO_PLAY,RobotImageLoader.HOW_TO_PLAY_IMAGE));
		buttonList.add(new FauxButton(650,300,300,100,BUTTONID.EXIT,RobotImageLoader.EXIT_IMAGE));
		buttonList.add(new FauxButton(50,100,300,100,BUTTONID.STARTGAME,RobotImageLoader.START_GAME_IMAGE));
		buttonList.add(new FauxButton(650,100,300,100,BUTTONID.SETTINGS,RobotImageLoader.SETTINGS_IMAGE));
		buttonList.add(new FauxButton(350,200,300,100,BUTTONID.LEVEL_EDITOR,RobotImageLoader.OVERLAY_IMAGE));
		menuPageList = new ArrayList<MenuPage>();
		menuPageList.add(new HowToPlayPage(MENUPAGEID.HOW_TO_PLAY));
		menuPageList.add(new SettingPage(MENUPAGEID.SETTINGS));
		currentPage = -1;

		AudioHandler.loopSound(SOUND.MENU_MUSIC);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, RobotCanvas.BUFFER_WIDTH, RobotCanvas.BUFFER_HEIGHT);
		
		if(fading) //Transition
		{			
			g.setColor(new Color(0,0,0,fadingTransparency));
			g.fillRect(0, 0, RobotCanvas.BUFFER_WIDTH, RobotCanvas.BUFFER_HEIGHT);
			
			fadingTransparency= Math.max(0, fadingTransparency-20);
			
			if(fadingTransparency == 0)
			{
				fading = false;
				fadingTransparency = 255;
			}
		}
		else
		{
			if(currentPage == -1)
			{
				RobotFont.drawMultiLineString("Robot Adventure\nBy:\nJonathan Collins\nSkylar Donlevy\nJennifer Vu", g, RobotCanvas.X_CENTERED_TEXT, RobotCanvas.Y_BOTTOM_TEXT,RobotFont.CENTER);

				for(FauxButton button : buttonList)
				{
					button.render(g);
					if(button.isMouseOver(MouseHandler.mouse))
					{
						g.drawImage(overlay,button.getX(), button.getY(), button.getWidth(), button.getHeight(),null);
					}
				}
			}
			else
			{
				//System.out.println(currentPage);
				menuPageList.get(currentPage).render(g);
				
				FauxButton button = menuPageList.get(currentPage).getBackButton();
				if(button.isMouseOver(MouseHandler.mouse))
				{
					g.drawImage(overlay,button.getX(), button.getY(), button.getWidth(), button.getHeight(),null);
				}

			}
		}
	}
	
	public ArrayList<FauxButton> getButtonList(){return buttonList;}
	
	public FauxButton getSelectedButton()
	{
		
		if(currentPage == -1)
		{
			for(int i = 0; i < buttonList.size(); i++)
			{
				if(buttonList.get(i).isSelected())
				{
					return buttonList.get(i);
				}
			}
		}
		else
		{
			if(menuPageList.get(currentPage).getBackButton().isSelected())
				return menuPageList.get(currentPage).getBackButton();
		}
	
		return null;
	}
	
	public void changePage(BUTTONID buttonID)
	{
		switch(buttonID)
		{
		case SETTINGS:
			
			currentPage = MENUPAGEID.SETTINGS.ordinal();
			
			break;
		case HOW_TO_PLAY:
			
			currentPage = MENUPAGEID.HOW_TO_PLAY.ordinal();
			
			break;
		case BACK:
			
			currentPage = -1;
			break;
		}
		
		fading = true;
	}
	
	public MenuPage getCurrentPage()
	{
		return menuPageList.get(currentPage);
	}
	
	public MENUPAGEID getCurrentPageID()
	{
		if(currentPage!= -1)
			return menuPageList.get(currentPage).getID();
		else 
			return null;
	}
	
	private Image overlay=RobotImageLoader.OVERLAY_IMAGE;
	
	private ArrayList<FauxButton> buttonList;
	private ArrayList<MenuPage> menuPageList;
	
	private int currentPage;
	
	private boolean fading = false;
	private int fadingTransparency = 255;
}
