package leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import leveleditor.GUIText.GUI_TEXT_TYPE;

public class LevelEditorGUI {

	public static final int OBJECT_TYPE = 0;
	public static final int BOUNDS = 1;
	public static final int THETA = 2;
	public static final int VELOCITY = 3;
	public static final int MASS = 4;
	public static final int TORQUE = 5;
	public static final int DRAG = 6;
	
	private Rectangle bounds;
	private Color color;
	private ArrayList<GUIText> selectionBoxTexts;
	
	public LevelEditorGUI(int x, int y, int width, int height)
	{
		bounds = new Rectangle(x,y,width,height);
		color = new Color(0xff,0xff,0xff,0x88);
		selectionBoxTexts = new ArrayList<GUIText>();
		for(int i = 0; i < 7; i++)
		{
			selectionBoxTexts.add(new GUIText(256, (i*64+i*16)+16, 32, 32, 320, (i*64+i*16)+16,GUI_TEXT_TYPE.values()[i]));
		}
	}
	
	
	public GUIText getSelectedGUIOption()
	{
		for(int i = 0; i < selectionBoxTexts.size(); i++)
			if(selectionBoxTexts.get(i).isSelected())
				return selectionBoxTexts.get(i);
		return null;
	}
	
	public void tick()
	{
		for(int i = 0; i < selectionBoxTexts.size(); i++)
		{
			selectionBoxTexts.get(i).tick();
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
		for(int i = 0; i < selectionBoxTexts.size(); i++)
		{
			selectionBoxTexts.get(i).render(g);
		}
	}


	public void updateSelectedOption(int x, int y) {
		for(int i = 0; i < selectionBoxTexts.size(); i++)
		{
			if(selectionBoxTexts.get(i).isMouseOver(x, y))
			{
				if(selectionBoxTexts.get(i).isSelected())
					selectionBoxTexts.get(i).setSelected(false);
				else
					selectionBoxTexts.get(i).setSelected(true);
			}else
				selectionBoxTexts.get(i).setSelected(false);
		}
	}
}
