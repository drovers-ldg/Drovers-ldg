package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

import database.DataBase;
import Logic.BattleThread;
import World.World;
import messages.MessagesBuffer;

public class Server
{
	// Debug mode
	public static boolean debug = false;
	
	// Socket settings
	static final int server_port = 3450;
	public static boolean is_runing;
	private static ServerSocket server_socket;
	
	// Events
	public static int event_id = 0;
	public static int client_id = 0;
	public static MessagesBuffer msg_buffer;
	public static HashMap<Integer, Client> client_list; 
	public static DB server_db;
	public static HashMap<Integer, BattleThread> battlesList;
	
	// World data
	public static World world;
	public static DataBase DB;
	
	public Server() throws IOException, InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		// Create data sets and tools
		DB = new DataBase();
		server_db = new DB();
		msg_buffer = new MessagesBuffer();
		client_list = new HashMap<Integer, Client>();
		battlesList = new HashMap<Integer, BattleThread>();
		world = new World();
		run_sockets();
	}
	
	public static void run_sockets() throws IOException, InterruptedException
	{
		// Run socket_thread
		try 
		{
			server_socket = new ServerSocket(server_port);
			Server.is_runing = true;
			new Thread_Logic();
			
			System.out.println("Server runing.");
			new Server_UI();
			
			while(Server.is_runing) 
			{
				Socket socket = server_socket.accept();
				try
				{
					client_list.put(client_id, new Client(socket));
				}
				catch(IOException e) 
				{
					System.out.println("Err! socket: " + socket.toString() + " is closed!");
					socket.close();
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	protected void finalize() throws IOException{
		if(client_list.size() != 0)
		{
			System.out.println("Err! not all connections is interupted!");
			client_list.clear();
		}
		
		Server.is_runing = false;
		server_socket.close();
		System.gc();
	}
}