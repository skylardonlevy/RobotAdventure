package button;

import game.RobotCanvas;
import game.RobotFrame;
import handler.AudioHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import utilities.RobotFont;
import constants.RobotImageLoader;

public class SliderBar implements MouseOverable{

	private Rectangle bounds;
	private Slider slider;
	private static final Image sliderImage = RobotImageLoader.SLIDER_SLIDER;
	private static final Image image = RobotImageLoader.SLIDER_BAR;
	
	public SliderBar(int x, int y, int width, int height, int min, int max,SLIDERID id)
	{
		if(id == SLIDERID.VOLUME)
		{	
			bounds = new Rectangle(x,y,width + (width / 100 * 10),height);
			slider = new Slider(x,y,width,height,min, max,this,id);
			slider.setValue((int) (AudioHandler.getVolume() * 100));
		}
		else
		{
			bounds = new Rectangle(x,y,width + (width / 100 * 10),height);
			slider = new Slider(x,y,width,height,min, max,this,id);
			slider.setValue(min);
		}
	}
	
	public SliderBar(int x, int y, int width, int height, int min, int max,SLIDERID id,int startValue)
	{
		bounds = new Rectangle(x,y,width + (width / 100 * 10),height);
		slider = new Slider(x,y,width,height,min, max,this,id);
		slider.setValue(startValue);
	}
	
	public boolean isMouseOver(int x, int y)
	{
//		if(RobotFrame.isMaximized())		
//		{
		//System.out.println(RobotCanvas.xRatio + " " + RobotCanvas.yRatio);
			 if(slider.bounds.contains((int)(x / RobotCanvas.xRatio), (int)(y / RobotCanvas.yRatio) ))
				return true; 
//		}else
//			if(slider.bounds.contains(x,y))
//				return true;
			// System.out.println("NOT MOUSED OVER");
//			
		return false;
	}
	
	public Slider getSlider(){
		return slider;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
//		g.drawRect(bounds.x,bounds.y, bounds.width, bounds.height);
		g.drawImage(image,bounds.x, bounds.y,bounds.width,bounds.height,null);
		slider.render(g);
		RobotFont.drawString(slider.min+"", g, bounds.x - RobotFont.getFontWidth(slider.min+"")-4, bounds.y+bounds.height/2-8);
		RobotFont.drawString(slider.max+"",g,bounds.x + bounds.width +  4,  bounds.y+bounds.height/2-8);
		RobotFont.drawString("Value: " + slider.value, g, bounds.x, bounds.y + bounds.height + 4);
		RobotFont.drawString(slider.getID().name(), g, bounds.x + bounds.width / 2 - RobotFont.getHalfFontWidth(slider.getID().name()), bounds.y - 20);
	}
	
	public void onUpdate()
	{
		switch (slider.getID()) {
		case VOLUME:
			float volumeLevel = slider.value/100.0f;
			AudioHandler.setVolume(volumeLevel);
			break;

		case OTHER:
			break;
		}
	}
	
	public class Slider implements MouseOverable
	{
		private int value;
		private int min, max;
		private Rectangle bounds;
		private double updateIncrement;
		private SliderBar bar;
		private SLIDERID id;
		
		public Slider(int x, int y,int width,int height, int min, int max,SliderBar bar, SLIDERID id)
		{
			this.min = min;
			this.max = max;
			value = min;
			updateIncrement = (double)width / Math.abs(min-max); 
			bounds = new Rectangle(x,y,width / 100 * 10,height);
			this.bar = bar;
			this.id = id;
		}
		
		public int getValue()
		{
			return value;
		}
		
		public void setValue(int value)
		{
			if(RobotFrame.isMaximized())
			{
				bounds.x =(int) (  (bar.bounds.x +  Math.abs(value-min) * updateIncrement)  / RobotCanvas.xRatio);
			}
			else
				bounds.x = (int)(  bar.bounds.x +  Math.abs(value-min) * updateIncrement);
			this.value = value;
		}
		
		public boolean isMouseOver(int x, int y)
		{
			//System.out.println(bounds.x);
			return bounds.contains(x,y);
		}
		
		public void update(int x)
		{
//			if(RobotFrame.isMaximized())
			{
				bounds.x = (int)(( (2 * x / RobotCanvas.xRatio) - bounds.width) / 2.0);
				if(bounds.x < bar.bounds.x)
					bounds.x = bar.bounds.x;
				if(bounds.x + bounds.width > bar.bounds.x + bar.bounds.width)
					bounds.x = bar.bounds.x + bar.bounds.width - bounds.width;
//				System.out.println("THIS BOUNDS: " + bounds.x );
//				System.out.println("BAR BOUNDS: " + bar.bounds.x);
//				System.out.println("UPDATE INCR: " + updateIncrement);
				value = (int) ((bounds.x - bar.bounds.x )/ updateIncrement)+ min;
//				System.out.println("VALUE: " + value);
			}
//			else
//			{
//				bounds.x = (2 * x - bounds.width) / 2;
//				if(bounds.x < bar.bounds.x)
//					bounds.x = bar.bounds.x;
//				if(bounds.x + bounds.width > bar.bounds.x + bar.bounds.width)
//					bounds.x = bar.bounds.x + bar.bounds.width - bounds.width;
//				value =  (int)((bounds.x - bar.bounds.x )/ updateIncrement)+ min;
//			}
		}
		
		public void render(Graphics g)
		{
			g.setColor(Color.CYAN);
//			g.drawRect(bounds.x,bounds.y, bounds.width, bounds.height);
			g.drawImage(sliderImage,bounds.x, bounds.y,bounds.width,bounds.height,null);
		}
		
		public SLIDERID getID()
		{
			return id;
		}
	}
	public enum SLIDERID
	{
		VOLUME,
		OTHER
	}
}


