package ca.vanzeben.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import ca.vanzeben.game.entities.Player;
import ca.vanzeben.game.gfx.Screen;
import ca.vanzeben.game.level.Level;
import ca.vanzeben.game.level.PLACE;
public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9209323034321384989L;

	
	//GLOBAL FIELDS
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 4;
	
	public static final String NAME = "Game";
	
	
	//FIELDS
	public static GAMESTATE state = GAMESTATE.Playing;
	public static PLACE Place = PLACE.PalletTown;
	
	private JFrame frame;
	private Thread thread;
	
	
	private boolean running;
	public int tickCount;
	
	private BufferedImage image;
	private int[] pixels;
	
	private Screen screen;
	private Level level;
	private Player player;
	
	private InputHandler handler;
	
	
	public static void main(String[] args) {
		
		new Game().start();;
		
	}

	//CONSTRUCTOR
	public Game(){
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		
		frame = new JFrame(NAME);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);;
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setFocusable(true);
		requestFocus();

		
		
		
	}
	
	
	public void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		screen = new Screen(WIDTH, HEIGHT, pixels);
		handler = new InputHandler(this);


		level = new Level(20, 40, handler);
		player = new Player(level, WIDTH/2, HEIGHT/2 + (1<<4), handler);

		level.addEntity(player);
		

	}
	
	//FUNCTIONS
	private synchronized void start(){
		running = true;
		thread = new Thread(this);
		thread.start();
		
		
	}
	
	
	private synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void run() {
		
		init();
		
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		
		while(running){
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/nsPerTick;
			lastTime = currentTime;
			
			boolean shouldRender = false;

			
			//Continues to tick 60 times since nsPerTick is 1000000000/60
			while(delta >= 1){
				ticks++;
				tick();
				delta--;
				shouldRender = true;
			}
			

			if(shouldRender){
				frames++;
				render();
			}
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(ticks + ", " + frames);
				frames = 0;
				ticks = 0;
			}
			
		}
		
		stop();
		
		
	}
	

	
	public void tick(){
//		tickCount++;
		switch(state){
		case Playing:
			level.tick();
			break;
			
		case Cutscene:
			
			
			break;
			
		case Listening:
			
			level.textBox.tick();
			break;
			
		case Battling:
			
			
			break;
			
		}

	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		
		
		int xOffset = player.getX() - screen.getWidth()/2;
		int yOffset = player.getY() - screen.getHeight()/2;

		level.render(screen,xOffset,yOffset);
		
		
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		
		
		g.dispose();
		bs.show();
	}
	
	

}
