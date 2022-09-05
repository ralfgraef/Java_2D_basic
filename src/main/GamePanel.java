package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// Screen Settings
	
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	
	public final int maxScreenCol = 16; 
	public final int maxScreenRow = 12;
	
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	// FPS
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
    public void run() {
		
		double drawIntervall = 1000000000/FPS;
		double delta = 0;
		
		long lastTime = System.nanoTime();
		long currentTime;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/ drawIntervall;
			lastTime = currentTime;
			
			if (delta >=1) {
				
				update();
				
				repaint();
				
				delta --;
			}
			
			
		}
	}

//	@Override
//	public void run() {
//		
//		double drawIntervall = 1000000000/FPS;
//		double nextDrawTime = System.nanoTime() + drawIntervall;
//		
//		
//		while (gameThread != null) {
//			
//			update();
//			
//			repaint();
//			
//			
//			
//			try {
//				
//				double remainingTime = nextDrawTime - System.nanoTime();
//				
//				remainingTime = remainingTime / 1000000;
//				
//				if (remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawIntervall;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}

}
