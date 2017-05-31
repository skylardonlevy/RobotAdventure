package game;

import handler.WindowHandler;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import jsonstuff.Writer;

public class RobotFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9039879089105886906L;

	public RobotFrame() {
		super(GAME_NAME);
		setSize(GAME_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(false); //so we can just click and press on canvas
		Toolkit.getDefaultToolkit().setDynamicLayout(false);
		windowHandler = new WindowHandler(this);
		addComponentListener(windowHandler);
		//setResizable(false);
		setLayout(null);
		init();
	}
	
	public void init()
	{
		Writer w = new Writer();
		
		robotCanvas = new RobotCanvas(GAME_WIDTH,GAME_HEIGHT,this);
		System.out.println(GAME_SIZE);
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
		add(robotCanvas);
		gameloop = new Thread(robotCanvas);
		gameloop.start();
	}

	public static void main(String[] args) {
		new RobotFrame().setVisible(true);
	}

	/*
	 * Variables
	 */
	
	public void updateDimension()
	{
		GAME_SIZE = getSize();
		setLocationRelativeTo(null);
		
		GAME_HEIGHT = GAME_SIZE.height;
		GAME_WIDTH = GAME_SIZE.width;
		RobotCanvas.xRatio = GAME_WIDTH / (double)RobotCanvas.BUFFER_WIDTH;
		RobotCanvas.yRatio = GAME_HEIGHT / (double)RobotCanvas.BUFFER_HEIGHT;
		if(robotCanvas != null)
			robotCanvas.updateDimension(GAME_WIDTH, GAME_HEIGHT);
		//System.out.println(RobotCanvas.xRatio + " :: " + RobotCanvas.yRatio);
		//System.out.println(RobotCanvas.xRatio + " -- " + RobotCanvas.yRatio);

	}
/*	
	public void updateDimension(int width, int height)
	{
		GAME_WIDTH = width;
		GAME_HEIGHT = height;
		if(robotCanvas != null)
			robotCanvas.updateDimension(GAME_WIDTH,GAME_HEIGHT);
		setSize(GAME_WIDTH,GAME_HEIGHT);
	}
	
	public void updateDimensionW(int width)
	{
		updateDimension(width, (int)getSize().getHeight());
	}
	
	public void updateDimensionH(int height)
	{
		updateDimension((int)getSize().getWidth(),height);
	}
	
	public void snapBack(int xRatio, int yRatio)
	{
		double xRatiod = (xRatio == 0) ? .5 : xRatio;
		double yRatiod = (xRatio == 0) ? .5 : yRatio;
		
		GAME_WIDTH = (int) (GAME_SIZE.width * xRatiod);
		GAME_HEIGHT = (int) (GAME_SIZE.height * yRatiod);
		GAME_SIZE.width = GAME_WIDTH;
		GAME_SIZE.height = GAME_HEIGHT;
		
		if(robotCanvas != null)
			robotCanvas.updateDimension(GAME_WIDTH,GAME_HEIGHT);
		setSize(GAME_SIZE);
	}
	*/
	
	public static Dimension getMaximizedSize()
	{
		Dimension size;
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int maxX = width / 16;
		int maxY = height / 9;
		
		return new Dimension(maxX * 16, (int) (maxX * 16 *  WindowHandler.aspectRatioToWidth) );
		
	}
	
	public static boolean isMaximized()
	{
		return GAME_SIZE.equals(GAME_FULLSCREEN_SIZE);
	}
	
	private WindowHandler windowHandler;
	
	private RobotCanvas robotCanvas;
	private Thread gameloop;
	
	
	public static final Dimension GAME_WINDOWED_SIZE = new Dimension(16<<6,9<<6);
	public static final Dimension GAME_MINIMUM_SIZE = GAME_WINDOWED_SIZE;
	public static final Dimension GAME_FULLSCREEN_SIZE = getMaximizedSize();//Toolkit.getDefaultToolkit().getScreenSize();
	public static Dimension GAME_SIZE = GAME_WINDOWED_SIZE;
	public static int GAME_WIDTH = GAME_SIZE.width;
	public static int GAME_HEIGHT = GAME_SIZE.height;
	
	
	public static final String GAME_NAME = "Robot Adventure";
	
	
}
