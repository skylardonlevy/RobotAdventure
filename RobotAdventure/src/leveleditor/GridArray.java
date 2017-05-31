package leveleditor;

import game.RobotCanvas;

import java.awt.Graphics;

public class GridArray {

	private GridBox[][] levelEditorGrid;
	public int width;
	public int height; 
	
	public GridArray(int width, int height)
	{
		levelEditorGrid = new GridBox[height][width];
		
		for(int y = 0; y < levelEditorGrid.length; y++)
		{
			for(int x = 0; x < levelEditorGrid[y].length;x++)
			{
				levelEditorGrid[y][x] = new GridBox(x*GridBox.WIDTH,y*GridBox.HEIGHT);
			}
		}
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g)
	{
		for(int y = 0; y < levelEditorGrid.length; y++)
		{
			
			for(int x = 0; y >= 0 && x < levelEditorGrid[y].length ;x++)
			{
				levelEditorGrid[y][x].render(g);
			}
		}
	}
	
	public void setStartYIndex(int startIndex)
	{
		
		for(int y = 0; y < levelEditorGrid.length; y++)
		{
			
			for(int x = 0;x < levelEditorGrid[y].length ;x++)
			{
				levelEditorGrid[y][x].setY(levelEditorGrid[y][x].getY() + (startIndex * GridBox.HEIGHT));
			}
		}
	}
	
	public void setStartXIndex(int startIndex)
	{
		
		
		for(int y = 0; y < levelEditorGrid.length; y++)
		{
			
			for(int x = 0;x < levelEditorGrid[y].length ;x++)
			{
				levelEditorGrid[y][x].setX(levelEditorGrid[y][x].getX() + (startIndex * GridBox.WIDTH));
			}
		}
	}
	
	public int getLeftX()
	{
		return levelEditorGrid[0][0].getLocX() + (levelEditorGrid[0][0].getLocX() - levelEditorGrid[0][0].getX()) - GridBox.WIDTH;
	}
	
	public int getRightX()
	{
		return levelEditorGrid[0][levelEditorGrid[0].length-1].getLocX() - (levelEditorGrid[0][levelEditorGrid[0].length-1].getLocX() - levelEditorGrid[0][levelEditorGrid[0].length-1].getX()) + GridBox.WIDTH;
	}
	
	public int getTopY()
	{
		return levelEditorGrid[0][0].getLocY() + (levelEditorGrid[0][0].getLocY() - levelEditorGrid[0][0].getY()) - GridBox.HEIGHT;
	}
	
	public int getLowY()
	{
		return levelEditorGrid[levelEditorGrid.length-1][0].getLocY() - (levelEditorGrid[levelEditorGrid.length-1][0].getLocY() - levelEditorGrid[levelEditorGrid.length-1][0].getY()) + GridBox.HEIGHT;
	}
	
	public GridBox getBoxClicked(int x, int y)
	{
		GridBox box00 = get00();
		
		if(box00 == null)
			return null;
		
		int realX = box00.getLocX()/GridBox.WIDTH;
		int realY = box00.getLocY()/GridBox.HEIGHT;
		
		double adjMseCrdsX =  x/RobotCanvas.xRatio, adjMseCrdsY = y/RobotCanvas.yRatio;
		int indexX = (int) (adjMseCrdsX/GridBox.WIDTH + realX);
		int indexY = (int) (adjMseCrdsY/GridBox.HEIGHT + realY);
		
		if(indexX >= levelEditorGrid[0].length|| indexY >= levelEditorGrid.length)
			return null;
		
		return levelEditorGrid[indexY][indexX];
		
	}
	
	private GridBox get00()
	{
		for(int y = 0; y < levelEditorGrid.length; y++)
		{
			
			for(int x = 0;x < levelEditorGrid[y].length ;x++)
			{
				if(levelEditorGrid[y][x].getY() == 0 && levelEditorGrid[y][x].getX() == 0)
					return levelEditorGrid[y][x];
			}
		}
		
		return null;
	}
}
