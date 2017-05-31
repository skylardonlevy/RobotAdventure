package button;

import game.RobotCanvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MultiStateButton implements MouseOverable {

	private int currentState;
	private int[] values;
	private Rectangle bounds;
	
	public MultiStateButton(int x, int y, int width, int height, int... values)
	{
		this.values = values;
		bounds = new Rectangle(x, y, width, height);
		test = new Color(100,100,100);
	}
	private Color test;
	public void render(Graphics g)
	{
		g.setColor(test);
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
	}
	
	public void changeState()
	{
		if(currentState + 1 >= values.length)
		{
			currentState = 0;
		}else
		{
			currentState++;
		}
		test = new Color(values[currentState],values[currentState],values[currentState]);
	}
	
	public void tick()
	{
		
	}
	
	public boolean isMouseOver(int x ,int y)
	{
		return bounds.contains(x / RobotCanvas.xRatio, y / RobotCanvas.yRatio);
	}
	
	public enum MULTISTATEBUTTONID
	{
		
	}
}
