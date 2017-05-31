package utilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.AllPermission;

/**
 * 
 * @author Jonathan
 *
 */

public class RobotFont {

	//Character string must have exact characters as font.png file even include the spaces
	private static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ   " +
									   "0123456789.,!?\"'<>#/{}[]:;-+=";
	private static int fontWidth, fontHeight;
	private static BufferedImage[][] fontMap;
	
	public static final int CENTER = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	
	private RobotFont(){}
	
	public static int getFontWidth(String text)
	{
		return (text.length() * fontWidth );
	}
	
	public static int getHalfFontWidth(String text)
	{
		return getFontWidth(text)/2;
	}
	
	public static void drawString(String text, Graphics g, int x, int y)
	{
		drawString(text, g, x, y, 1, 1);
	}
	
	public static void drawString(String text, Graphics g, int x, int y, double xScale, double yScale)
	{
		text = text.toUpperCase();
		int length = text.length();
		for(int i = 0; i < length; i++)
		{
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			if(index < 0) continue;
			
			g.drawImage(fontMap[index / 29][index % 29],x,y,(int)(xScale*fontWidth),(int)(yScale*fontHeight),null);
			x+=(int)(fontWidth * xScale);
		}
	}
	
	public static void drawStringOverlapped(String text, Graphics g, int x, int y, double xScale, double yScale)
	{
		text = text.toUpperCase();
		int length = text.length();
		for(int i = 0; i < length; i++)
		{
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			if(index < 0) continue;
			
			g.drawImage(fontMap[index / 29][index % 29],x,y,(int)(xScale*fontWidth),(int)(yScale*fontHeight),null);
			x+=(int)(fontWidth * xScale) - (xScale * 2);
		}
	}
	
	public static void drawStringOverlapped(String text, Graphics g, int x, int y)
	{
		drawStringOverlapped(text, g, x, y, 1, 1);
	}
	
	public static void drawStringOverlapped(String text, Graphics g, int x, int y, double xScale,double yScale, int xPixels)
	{
		text = text.toUpperCase();
		int length = text.length();
		for(int i = 0; i < length; i++)
		{
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			if(index < 0) continue;
			
			g.drawImage(fontMap[index / 29][index % 29],x,y,(int)(xScale*fontWidth),(int)(yScale*fontHeight),null);
			x+=(int)(fontWidth * xScale) - (xScale * 2) - (xPixels * xScale);
		}
	}
	
	public static void drawStringReversed(String text, Graphics g, int x, int y)
	{
		drawStringReversed(text, g, x, y, 1, 1);
	}
	
	public static void drawStringReversed(String text, Graphics g, int x, int y, double xScale, double yScale)
	{
		text = new StringBuffer(text).reverse().toString();
		drawString(text, g, x, y,xScale,yScale);
	}
	
	public static void drawMultiLineString(String text, Graphics g, int x, int y, int alignment)
	{
		drawMultiLineString(text, g, x, y,1,1,alignment);
	}
	
	public static void drawMultiLineString(String text, Graphics g, int x, int y, double xScale, double yScale, int alignment)
	{
		text = text.toUpperCase();
		int length = text.length();
		int temp = x;
		int lineCount = 0;
		String[] words = text.split("\n");
		
		for(int i = 0; i < length; i++)
		{
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			
			if(c == '\n')
			{
				y+=(fontHeight * yScale) + (2 * yScale);
				//System.out.println(prevLength);
				if(alignment == LEFT)
				{
					x= temp;					
				}
				else if(alignment == CENTER)
				{
					x = temp + getHalfFontWidth(words[0]) - getHalfFontWidth(words[++lineCount]);
				}
				else if(alignment == RIGHT)
				{
					x = temp + getFontWidth(words[0]) - getFontWidth(words[++lineCount]);
				}
				continue;
			}
			if(index < 0) continue; 
			
			g.drawImage(fontMap[index / 29][index % 29],x,y,(int)(xScale*fontWidth),(int)(yScale*fontHeight),null);
			x+=(int)(fontWidth * xScale);
		}
	}
	
	public static void drawCirclularString(String text, Graphics g, int centerX, int centerY,double xScale, double yScale,int circleWidth,int circleHeight )
	{
		
		int degree = 230;
		
		int x = (int)(fontWidth * circleWidth * Math.cos( Math.toRadians(degree)) + centerX),
			y = (int)(fontHeight * circleHeight * Math.sin( Math.toRadians(degree)) + centerY);
		
		
		
		text = text.toUpperCase();
		int length = text.length();
		for(int i = 0; i < length; i++)
		{
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			if(index < 0) continue;
			
			g.drawImage(fontMap[index / 29][index % 29],x,y,(int)(xScale*fontWidth),(int)(yScale*fontHeight),null);
			
			degree+=15;
			x = (int)(fontWidth * circleWidth * Math.cos( Math.toRadians(degree) ) + centerX);
			y = (int)(fontHeight * circleHeight * Math.sin( Math.toRadians(degree)) + centerY);
			
			
		}
	}
	
	static{
		fontWidth = fontHeight = 16;
		fontMap = Utility.loadFont("font/font.png", 16, 16);
	}
}
