package state;

import java.awt.Graphics;

import utilities.RobotFont;

public class HowToPlayPage extends MenuPage {

	public HowToPlayPage(MENUPAGEID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		RobotFont.drawString("Click Start Game to begin your robot adventure!", g, 100, 200);
		RobotFont.drawString("Click Levels to choose a level.", g, 100, 300);
		back.render(g);
	}

}
 