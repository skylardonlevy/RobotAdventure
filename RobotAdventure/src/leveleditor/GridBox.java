package leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import utilities.Utility;

public class GridBox {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	private Point loc;
	private int x, y;
	private Color color;
	
	public GridBox(int x, int y)
	{
		this.x = x;
		this.y = y;
		loc = new Point(x,y);
		color = Utility.randomRGBColor();
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString(String.format("(%d,%d)",loc.x / WIDTH , loc.y/HEIGHT ),x,y+8);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getLocX()
	{
		return loc.x;
	}
	
	public int getLocY()
	{
		return loc.y;
	}
	
	@Override
	public String toString() {
		return String.format("x: %d y:%d",loc.x / WIDTH,loc.y/HEIGHT );
	}
}
