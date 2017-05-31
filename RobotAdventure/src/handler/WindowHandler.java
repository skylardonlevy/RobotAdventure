package handler;

import game.RobotFrame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class WindowHandler implements ComponentListener{

	private RobotFrame frameRef;
	
	public WindowHandler(RobotFrame frame)
	{
		frameRef = frame;
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension size = e.getComponent().getSize();
		//System.out.println(size);
		
		int deltaX = Math.abs(size.width - previousSize.width);
		int deltaY = Math.abs(size.height - previousSize.height);
		
		if(deltaX > deltaY)
		{
			e.getComponent().setSize(   (int)Math.max( RobotFrame.GAME_MINIMUM_SIZE.width , Math.min(size.width,RobotFrame.GAME_FULLSCREEN_SIZE.width) ),(int)Math.max( RobotFrame.GAME_MINIMUM_SIZE.height, Math.min(size.width,RobotFrame.GAME_FULLSCREEN_SIZE.width) * aspectRatioToWidth ) );
		}else
		{
			e.getComponent().setSize(   (int)Math.max( RobotFrame.GAME_MINIMUM_SIZE.width , Math.min(size.height,RobotFrame.GAME_FULLSCREEN_SIZE.height) * aspectRatioToHeight ),(int)Math.max( RobotFrame.GAME_MINIMUM_SIZE.height, Math.min(size.height,RobotFrame.GAME_FULLSCREEN_SIZE.height) ) );
		}
		
		MouseHandler.mouse = new Point(-1,-1);
		
		frameRef.updateDimension();
		//System.out.println(e.getComponent().getSize());
		previousSize = RobotFrame.GAME_SIZE;
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}
	
	private Dimension previousSize = RobotFrame.GAME_SIZE;
	
	public static final double aspectRatioToHeight = 16/9.0;
	public static final double aspectRatioToWidth = 9.0/16;
}
