package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings 
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	
	//FPS 
	int FPS = 60;
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	
	//constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		//Dimension() setting this class size
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //performance enhancer
		this.addKeyListener(keyH); // recognise the key input
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	
	//Game Loop Sleep
	
/*
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		//draw screen every 0.01666(drawInternal) seconds sleep
		
		double nextDrawTime = System.nanoTime()+drawInterval;
	
		while(gameThread != null) {
			
			update(); //update info
			
			repaint(); //draw w updated info
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime <0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	*/
	
	//Game Loop Delta
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount ++;
			}
			
			if(timer>= 1000000000) {
				System.out.println("FPS: "+drawCount);
				drawCount =0;
				timer =0;
			}
			
		}
		
	}
	
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//Graphics - Graphics2D similar but has more function
		Graphics2D g2 = (Graphics2D) g;
		
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose(); 
		
		
		
	}
	
}
