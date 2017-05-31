package button;

import game.RobotCanvas;
import game.RobotFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class FauxButton implements MouseOverable{

	private Rectangle bounds;
	private Image image;
	private BUTTONID id;
	private boolean selected;
	
	public FauxButton(int x, int y, int width, int height, BUTTONID id,Image image)
	{
		bounds = new Rectangle(x,y,width,height);
		this.id = id;
		this.image = image;
	}
	
	public boolean isMouseOver(Point p)
	{
		return isMouseOver(p.x, p.y);
	}
	
	public boolean isMouseOver(int x, int y)
	{
		boolean is = false;
		
//		if(RobotFrame.isMaximized())		
//		{
			 if(bounds.contains(x / RobotCanvas.xRatio, y / RobotCanvas.yRatio))
			 {
				 is = selected = true;
			 }else
			 {
				 selected  = false;
			 }
				 
//		}
//		else
//			if(bounds.contains(x,y))
//			{
//				is = selected = true;
//				
//			}else
//			{
//				selected = false;
//			}
//		
		return is;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void tick(){}
	
	public void render(Graphics g)
	{
//		g.setColor(Color.GREEN);
//		g.drawRect(bounds.x,bounds.y,bounds.width,bounds.height);
		g.drawImage(image,bounds.x,bounds.y,bounds.width,bounds.height,null);
	}
	
	public int getX(){return bounds.x;}
	public int getY(){return bounds.y;}
	public int getWidth(){return bounds.width;}
	public int getHeight(){return bounds.height;}
	
	public BUTTONID getID()
	{
		return id;
	}
	
	@Override
	public String toString() {
		return id.name();
	}
}
