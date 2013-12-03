package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import GUI.AreaMapMenu;
import GUI.CharacterMenu;
import GUI.LoginMenu;
import messages.Message;
import messages.MessageDouble;
import player_data.Area_Map;
import player_data.Player;
import player_data.World;
import player_data.WorldMap;

class Thread_Socket extends Thread
{
	protected static ObjectInputStream in;
	protected static ObjectOutputStream out;
	protected static Socket socket;
	protected static boolean waitMap1Update;
	protected static boolean waitMap2Update;
	protected static boolean waitWorldUpdate;
	protected static boolean waitPlayerUpdate;
	protected static boolean waitSQUpdate;
	
	// Squad update
	protected static boolean waitUnitsUpdate;
	protected static boolean waitUnitsSoftUpdate;
	protected static boolean waitSQUptatePreUnits;
	protected static boolean waitRobotLoad;
	
	Thread_Socket() throws IOException
	{
		// port 3450
		InetAddress server_address = InetAddress.getByName(Game.address);
		socket = new Socket(server_address, Game.port);
	}
	
	public void run()
	{	
		try
		{
			in = new ObjectInputStream(socket.getInputStream());
			new Sender(new ObjectOutputStream(socket.getOutputStream()));
		
			while(Game.is_runing)
			{
				if(waitRobotLoad){
					Game.state.set_state("char");
					waitRobotLoad = false;
					World.squad.unit1.readExternal(in);
					World.squad.unit2.readExternal(in);
					World.squad.unit3.readExternal(in);
					Chat.add_to_msg_log("[SERVER] Connection to \""+ Game.address  + "\" sucess.");
				}
				else if(waitSQUptatePreUnits){
					waitSQUptatePreUnits = false;
					World.playersOnline.readExternal(in);
					waitUnitsUpdate = true;
					Sender.updateSquad();
				}
				else if(waitUnitsUpdate){
					waitUnitsUpdate = false;
					World.squad.unit1.readExternal(in);
					World.squad.unit2.readExternal(in);
					World.squad.unit3.readExternal(in);
					Chat.add_to_msg_log("[GAME] Send source code");
					Sender.sendCode(World.unit1, 1);
					Sender.sendCode(World.unit2, 2);
					Sender.sendCode(World.unit3, 3);
					CharacterMenu.showArea = true;
				}
				else if(waitPlayerUpdate){
					Player player = new Player();
					player.readExternal(in);
					processMsg(player);
				}
				else if(waitSQUpdate){
					waitSQUpdate = false;
					World.playersOnline.readExternal(in);
				}
				else if(waitMap1Update){
					Area_Map map = new Area_Map();
					map.readExternal(in);
					loadMap1(map);
				}
				else if(waitMap2Update){
					Area_Map map = new Area_Map();
					map.readExternal(in);
					loadMap2(map);
				}
				else if(waitWorldUpdate){
					WorldMap worldMap = new WorldMap();
					worldMap.readExternal(in);
					processMsg(worldMap);
				}
				else {
					Object msg = in.readObject();
					
					if(msg instanceof MessageDouble){
						processMsg((MessageDouble)msg);
					}
					else if(msg instanceof Message){
						processMsg((Message)msg);
					}
					else if(msg instanceof Player){
						processMsg((Player)msg);
					}
					else{
						Game.server_msg = "Unexpected type of message";
					}
				}
			}
		}
		catch (IOException e) 
		{
			
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try {
				Sender.logout();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				in.close();
				socket.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}	
	}
	
	private void loadMap2(Area_Map map) throws IOException {
		World.areaMap2 = map;
		waitMap2Update = false;
		waitSQUpdate = true;
		World.mergeAreas();
		
		waitSQUptatePreUnits = true;
		Sender.sendSQUpdate();
	}

	private void loadMap1(Area_Map map) throws IOException {
		World.areaMap1 = map;
		waitMap1Update = false;
		waitMap2Update = true;
		Sender.UpdateArea2();
	}

	public void processMsg(MessageDouble msg){
		msgChat(msg.data, msg.data2);
	}
		
	public void processMsg(WorldMap worldMap) throws IOException{
		World.worldMap = worldMap;
		waitWorldUpdate = false;
		waitRobotLoad = true;
		Sender.sendRobotsLoad();
	}
	
	public void processMsg(Player player) throws IOException{
		waitPlayerUpdate = false;
		World.playerData = player;	
		waitWorldUpdate = true;
		Sender.updateWorld();
	}
	
	public void processMsg(Message msg) throws IOException{
		if(msg.type.equals(Message.Type.DEFAULT)){
			msgDefault(msg.data);
		}
		else if(msg.type.equals(Message.Type.TIME)){
			msgTime(msg.data);
		}
		else if(msg.type.equals(Message.Type.CONNECTIONSUCESS)){
			msgConnectionSucess();
		}
		else if(msg.type.equals(Message.Type.CONNECTIONFAILED)){
			msgConnectionFailed();
		}
		else if(msg.type.equals(Message.Type.SQMOVEUP)){
			Player.mapY--;
		}
		else if(msg.type.equals(Message.Type.SQMOVEDOWN)){
			Player.mapY++;
		}
		else if(msg.type.equals(Message.Type.SQMOVELEFT)){
			Player.mapX--;
		}
		else if(msg.type.equals(Message.Type.SQMOVERIGHT)){
			Player.mapX++;
		}
		else if(msg.type.equals(Message.Type.SQMOVEUPLEFT)){
			Player.mapY--;
			Player.mapX--;
		}
		else if(msg.type.equals(Message.Type.SQMOVEUPRIGHT)){
			Player.mapY--;
			Player.mapX++;
		}
		else if(msg.type.equals(Message.Type.SQMOVEDOWNLEFT)){
			Player.mapY++;
			Player.mapX--;
		}
		else if(msg.type.equals(Message.Type.SQMOVEDOWNRIGHT)){
			Player.mapY++;
			Player.mapX++;
		}
		else if(msg.type.equals(Message.Type.UPDATESQUADS)){
			waitSQUpdate = true;
			Sender.sendSQUpdate();
		}
		else if(msg.type.equals(Message.Type.BATTLEAREA1)){
			AreaMapMenu.topology = msg.data;
			waitMap1Update = true;
			Sender.UpdateArea1();
		}
		else if(msg.type.equals(Message.Type.AREAUPDATEUNITS)){
			waitUnitsSoftUpdate = true;
		}

	}
	private void msgDefault(String data){
		Game.server_msg = data;
	}
	private void msgChat(String player, String data){
		Chat.add_to_msg_log("["+player+"]: " + data);
	}
	private void msgTime(String data){
		Game.server_time = Long.parseLong(data);
		Game.ping();
	}
	private void msgConnectionSucess() throws IOException{
		waitPlayerUpdate = true;
		Sender.updatePlayer();
	}
	private void msgConnectionFailed(){
		Game.state.set_state("login");
		LoginMenu.errString = "Wrong login or password";
	}
	
	public void send(Message.Type type, String msg) throws IOException{
		new Message(type, msg).send(out);
	}
}