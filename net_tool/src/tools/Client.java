package tools;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client
{
	public static boolean is_runing;
	public static Scanner in;
	public static BufferedReader in_server;
	public static PrintWriter out;
	
	public static void main(String[] args) throws IOException 
	{
		final int PORT = 3450;
		
		System.out.print("Enter the server addres: ");
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		InetAddress addr = InetAddress.getByName(str);
		
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, PORT);
		
		try 
		{
			System.out.println("socket = " + socket);
			is_runing = true;
		
			
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			in_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Client_Listener();
		
			while(in.hasNextLine())
    		{
    			str = in.nextLine();
    			command_process(str);
			}
		}
		finally 
		{
			out.println("IN:LOGOUT");
			socket.close();
			in.close();
			out.close();
			is_runing = false;
		}
	}
	
	public static void command_process(String str){
		if(str.contains("login")){
			if(str.matches("^login [a-zA-Z0-9]+ [a-zA-Z0-9]+$")){
				String [] login = str.split(" ");
				out.println("IN:CONNECT:"+login[1]+":"+login[2]);
			}
			else{
				System.out.println(">login [account] [password]");
			}		
		}
		if(str.matches("^logout$")){
			out.println("IN:LOGOUT");
		}
		else if(str.contains("create")){
			if(str.matches("^create [a-zA-Z]+$")){
				String [] create = str.split(" ");
				out.println("CREATE:PLAYER:" + create[1]);
			}
			else{
				System.out.println(">create [player_name]");
			}
		}
		else if(str.matches("^chose player [a-zA-Z]+$")){
			String [] player = str.split(" ");
			out.println("IN:CHOSE:PLAYER:"+player[2]);
		}
	}
}

class Client_Listener extends Thread{
	Client_Listener(){
		this.start();
	}
	
	public void run(){
		String str = null;
		try 
		{
			while(Client.is_runing){
				str = Client.in_server.readLine();
				String [] msg = str.split(":");
				
				if(!msg[0].equals("TIME")){
					System.out.println(str);
				}
			}
			
		}
		catch (IOException e)
		{
			System.out.println("Disconect by server.");
			Client.is_runing = false;
		}
	}
}