package game;

import handler.AudioHandler;
import handler.KeyHandler;
import handler.MouseHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import state.GameStateManager;
import utilities.RobotFont;
import engine.CollisionDetector;
import engine.PhysicsPoly;
import engine.Polygon;
import engine.Vector;
import entity.PhysicsRect;
import entity.PhysicsTriangle;

public class RobotCanvas extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5905093269733175517L;

	public RobotCanvas(int width, int height,RobotFrame frameRef)
	{
		setSize(width,height);
		running = true;
		
		mouseHandler = new MouseHandler(this);
		keyHandler = new KeyHandler(this);
		audioHandler = new AudioHandler();
		
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		addMouseWheelListener(mouseHandler);
		addKeyListener(keyHandler);
		
		manager = new GameStateManager(this);
		
		buffer = new BufferedImage(BUFFER_WIDTH,BUFFER_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		
		xRatio = 1;//((double)RobotFrame.GAME_FULLSCREEN_SIZE.width)/BUFFER_WIDTH;
		yRatio = 1;//((double)RobotFrame.GAME_FULLSCREEN_SIZE.height)/BUFFER_HEIGHT;
		
		System.out.println("Xr: " + xRatio + " Yr: " + yRatio);
		
		this.robotFrameReference = frameRef;
		
		activePolys.add(testBox1);
		activePolys.add(testBox2);
	}
	
	public void tick()
	{
		//TODO: Update things here.
		manager.tick();
		//testBox.tick();
		//testTriangle.tick();
		for(Polygon p:activePolys)
			p.tick();
		
		collisions.broadCheck(activePolys);
	
		if(first)
		{
			first = !first;
			System.out.println(buffer.getWidth() + "," + buffer.getHeight());
		}
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		
		
		Graphics b = buffer.getGraphics();
		b.setColor(Color.WHITE);
		b.fillRect(0, 0, BUFFER_WIDTH, BUFFER_HEIGHT);
		//TODO: Draw stuff here	
		manager.render(b);
		//testBox.render(b);
		//testTriangle.render(b);
		for(Polygon p:activePolys)
			p.render(b);
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(buffer, 0, 0,RobotFrame.GAME_WIDTH, RobotFrame.GAME_HEIGHT, null);
		
		RobotFont.drawString("(" + mouseHandler.getMouse().x + "," + mouseHandler.getMouse().y + ")",g,mouseHandler.getMouse().x+8,mouseHandler.getMouse().y-8);
		
		b.dispose();
		g.dispose();
		bs.show();
	}
	
	
	@Override
	public void run() {
		GameTimer gameTimer = new GameTimer();
		
		while(running)
		{
			if(!gamePaused)
			{
				if(gameTimer.getElapsedTime() > MILLISECOND_STEP)
				{
					tick();
					gameTimer.restart();
				}
				render();
			}
		}
	}
	
	/*
	 * getters / setters
	 */
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameStateManager getManager() {
		return manager;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
	
	
	public RobotFrame getRobotFrameReference() {
		return robotFrameReference;
	}

	public void updateDimension(int width, int height) {
		
		setSize(width,height);
		
	}


	/*
	 * Variables
	 */
	private boolean first = true;
	
	private BufferedImage buffer;
	
	public static final double timeStep=.016667;
	public static final int MILLISECOND_STEP=(int) (timeStep*1000);

	public static final int BUFFER_WIDTH = 16<<6;
	public static final int BUFFER_HEIGHT = 9<<6;
	
	public static final int X_CENTERED_TEXT = BUFFER_WIDTH/2 - BUFFER_WIDTH/8; //1/2 - 1/8
	public static final int Y_BOTTOM_TEXT = BUFFER_HEIGHT - BUFFER_HEIGHT/4; //1 - 1/4
	
	public static double xRatio;
	public static double yRatio;
	
	public static final double gravity=0;
	
	public static final double playerWeight=30;
	public static final double playerDragC=1;
	
	public static final double boxDragC=.9;
	
	private boolean running = false;
	private boolean gamePaused = false;
	
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private AudioHandler audioHandler;
	
	private GameStateManager manager;
	
	private RobotFrame robotFrameReference;
	
	private CollisionDetector collisions=new CollisionDetector();
	private ArrayList<Polygon> activePolys=new ArrayList<Polygon>();

	private PhysicsPoly testBox1=new PhysicsRect(0,0,200,200,30,new Vector(5,0),5,500,1.05);
	private PhysicsPoly testBox2=new PhysicsTriangle(500,100, 200, 275, 275, 275, 30, null, 0, 50, 1.05);
	

	//private PhysicsPoly testBox1=new PhysicsRect(0,0,200,200,30,new Vector(5,0),1,500,1.05);
	//private PhysicsPoly testBox2=new PhysicsRect(275,0,200,200,0,null,0,500,1.05);

}
