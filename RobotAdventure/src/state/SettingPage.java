package state;

import handler.MouseHandler;

import java.awt.Graphics;
import java.util.ArrayList;

import utilities.RobotFont;
import button.MouseOverable;
import button.MultiStateButton;
import button.SliderBar;
import button.SliderBar.SLIDERID;

public class SettingPage extends MenuPage {

	public SettingPage(MENUPAGEID id) {
		super(id);
		 
		buttonList.add(new SliderBar(100, 100, 200, 50, 0, 100,SLIDERID.VOLUME));
		buttonList.add(new SliderBar(500, 100, 200, 50, -10, 10,SLIDERID.OTHER));
		buttonList.add(new MultiStateButton(200,300,200,50,50,100,150,200));
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		RobotFont.drawString("SETTINGS GO HERE!", g, 200, 200);
		back.render(g);
		for(MouseOverable bar : buttonList)
			bar.render(g);
	}
	
	public MouseOverable getSelectedButton() {
		for(int i = 0; i < buttonList.size(); i++)
		{
			if(buttonList.get(i).isMouseOver(MouseHandler.mouse.x, MouseHandler.mouse.y))
				return buttonList.get(i);
		}
		return null;
	}
	
	private ArrayList<MouseOverable> buttonList = new ArrayList<MouseOverable>();
}
