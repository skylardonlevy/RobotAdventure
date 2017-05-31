package leveleditor;

import game.RobotCanvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import utilities.RobotFont;

public class GUIText {

	private String text;
	private boolean selected;
	private Point location;
	private Rectangle selectionBounds;
	private static Color blackT;
	GUI_TEXT_TYPE type;
	
	public GUIText(int sX, int sY, int sW,int sH, int tX, int tY,GUI_TEXT_TYPE type)
	{
		location = new Point(tX,tY);
		selectionBounds = new Rectangle(sX,sY,sW,sH);
		text = "";
		this.type = type;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public boolean isMouseOver(int x, int y)
	{
		return selectionBounds.contains(x/RobotCanvas.xRatio,y/RobotCanvas.yRatio);
	}
	
	public void tick()
	{
		
	}
	
	public String getText(){return text;}
	public void setText(String text){this.text = text;}
	
	
	public void render(Graphics g)
	{
		g.setColor(blackT);
		g.fillRect(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
		RobotFont.drawString(text, g, location.x, location.y,1.5,1.5);
		RobotFont.drawString(type.name(),g,selectionBounds.x - RobotFont.getFontWidth(type.name()),selectionBounds.y);
	}
	
	static{
		blackT = new Color(0x00,0x00,0x00,0xcc);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	enum GUI_TEXT_TYPE
	{
		OBJECT_TYPE,
		DIMENSIONS,
		VELOCITY,
		TORQUE,
		ANGLE,
		MASS,
		DRAG
	}
}
