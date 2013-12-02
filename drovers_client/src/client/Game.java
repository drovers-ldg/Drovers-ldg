package client;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GUI.GUI;
import player_data.World;

public class Game
{	
	// Frame
	public static JFrame frame;
    public static int WIDTH = 1024;
    public static int HEIGHT = WIDTH / 16 * 9;
	
	// status
	public static boolean is_runing;
	public static BufferStrategy bs;
	public static Graphics g;
	public static State state;
	
	// Info
	public static String [] msg_log;
	public static String server_msg = "";
	public static long server_time = 0;
	public static long Ping = 0;
	public static long FPS = 0;
	public static long Time = 0;
	
	// Login options
	public static String address;
	public static int port;
	public static String login;
	public static String password;
	
	// Game data
	public static World game_data;
	public static int mouseX = 0;
	public static int mouseY = 0;

	Game() throws IOException, InterruptedException{
		read_config();
		new GUI();
		frame = new JFrame("Drovers");
		frame.setIconImage(new ImageIcon("gear.png").getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.getLocation();
		
		Canvas gui = new Canvas();
		frame.getContentPane().add(gui);
		frame.setResizable(false);
		frame.setVisible(true);
		
		Thread Main_Thread = new Thread(new GameLoop(gui));
		Main_Thread.setPriority(Thread.MIN_PRIORITY);
		Main_Thread.start();
	}
	
	public static void read_config() throws FileNotFoundException{
		Scanner in = new Scanner(new File("config\\connection.conf"));
		
		while(in.hasNext()){
			String str = in.next();
			
			switch(str){
				case "#address:":
					Game.address = in.next();
					break;
				case "#port:":
					Game.port = in.nextInt();
					break;
				default:
					break;
			}
		}
		
		in.close();
	}
	
	public static void ping()
	{
		if(System.currentTimeMillis() - Time > 100){
			Game.Ping = System.currentTimeMillis() - Game.server_time;
			Game.Time = System.currentTimeMillis();
		}
	}
}