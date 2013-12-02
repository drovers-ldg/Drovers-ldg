package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import player_data.World;

public class GameLoop implements Runnable {
		private static Canvas gui;
		private static long cycleTime;

		// FPS
		private static final int FRAME_DELAY = 17;
		private static long LastTime = System.currentTimeMillis();
		private static long el_FPS = 0;
		private static long ElapsedTime = 0;
		@SuppressWarnings("unused")
		private static long Last_Update = 0;
 
		public GameLoop(Canvas canvas) {
			this.init();
			Game.is_runing = true;
			gui = canvas;
			gui.addMouseListener(new Mouse());
			gui.addMouseMotionListener(new MouseMotion());
			gui.addKeyListener(new key_input());	
		}
 
		public void run(){			
			try{
				new Thread_Socket().start();
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
			cycleTime = System.currentTimeMillis();
			gui.createBufferStrategy(2);
			BufferStrategy strategy = gui.getBufferStrategy();
 
			while (Game.is_runing) {
				render(strategy);
				synchFramerate();
				
				ElapsedTime = System.currentTimeMillis() - LastTime;
				if (ElapsedTime >= 1000){
					Game.FPS = el_FPS;
				    el_FPS = 0;
				    LastTime = System.currentTimeMillis();
				}
			}
		}
		
		private void init(){
			Game.Time = System.currentTimeMillis();
			Game.game_data = new World();
			Game.msg_log = new String[10];
			for(int i = 0; i < Game.msg_log.length; ++i){
				Game.msg_log[i] = "";
			}
			
			Game.state = new State("login");
		}
 
		private void render(BufferStrategy strategy) {
			Graphics g = strategy.getDrawGraphics();

			g.setColor(Color.black);
			g.fillRect(0, 0, gui.getWidth(), gui.getHeight());
			
			Game.state.set_graphic(g);	
			Game.state.draw();
			
			g.dispose();
			strategy.show();
			
			el_FPS++;
			Last_Update = System.currentTimeMillis();
		}
 
		private void synchFramerate() {
			cycleTime = cycleTime + FRAME_DELAY;
			long difference = cycleTime - System.currentTimeMillis();
			try {
				Thread.sleep(Math.max(0, difference));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
