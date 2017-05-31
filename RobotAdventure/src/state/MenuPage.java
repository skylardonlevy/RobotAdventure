package state;

import java.awt.Graphics;

import constants.RobotImageLoader;
import button.BUTTONID;
import button.FauxButton;

public abstract class MenuPage {
	
	private MENUPAGEID id;
	protected FauxButton back;
	
	public MenuPage(MENUPAGEID id)
	{
		this.id = id;
		back = new FauxButton(30,400,300,100,BUTTONID.BACK,RobotImageLoader.BACK_IMAGE);
	}
	
	public MENUPAGEID getID()
	{
		return id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public FauxButton getBackButton(){return back;}
	
	
}
