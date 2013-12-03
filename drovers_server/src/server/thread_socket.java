package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import database.DBAccounts;
import database.DBSquads;
import World.PlayersOnline;
import World.World;
import World.WorldMap;
import messages.Message;
import messages.MessageDouble;
import messages.MessageIn;

class Thread_Socket extends Thread
{
	private Socket socket;
	private int client_id;
	private int accountId;
	
	// IO-streams
	private ObjectOutputStream out;
	private ObjectInputStream in;
	

	Thread_Socket(Socket socket, int client_id) throws IOException{
    	this.socket = socket;
    	this.client_id = client_id;
    	this.accountId = -1;

    	System.out.println(this.socket.getInetAddress().toString() + ":" + this.socket.getPort() + " id:" + this.client_id + " is connected;");
  
    	out = new ObjectOutputStream(this.socket.getOutputStream());
    	in = new ObjectInputStream(this.socket.getInputStream());
    	this.start();
	}
	
	public void run(){
		try{		
			while(this.socket.isConnected()){
				Object pack = in.readObject();
					
				if(pack instanceof Message){
					Message msg = (Message)pack;
					Server.msg_buffer.add(new MessageIn(msg, client_id));
				}
			}
		}
		catch(IOException e) {
		} 
		catch (ClassNotFoundException e) {
		}
		finally{
			Server.msg_buffer.add(new MessageIn(Message.Type.LOGOUT, client_id));
			Server.msg_buffer.add(new MessageIn(Message.Type.DISCONNECT, client_id));
			this.interrupt();
		}
	}
	public void setAccountId(int accountId){
		this.accountId = accountId;
	}
	public void send(Message.Type type, String msg) throws IOException{
		new Message(type, msg).send(out);
	}
	public void send(String player, String data) throws IOException{
		new MessageDouble(player, data).send(out);
	}
	public void sendMap(int type) throws IOException{
		synchronized(DBAccounts.map){
			synchronized(Server.battlesList){
				int battleId = DBAccounts.map.get(this.accountId).battleId;
				if(type == 1){
					int mapX = Server.battlesList.get(battleId).mapX1;
					int mapY = Server.battlesList.get(battleId).mapY1;
					World.areaMaps.get(WorldMap.map[mapX][mapY].areaName).writeExternal(out);
				}
				else if(type == 2){
					int mapX = Server.battlesList.get(battleId).mapX2;
					int mapY = Server.battlesList.get(battleId).mapY2;
					World.areaMaps.get(WorldMap.map[mapX][mapY].areaName).writeExternal(out);
				}
			}
		}
	}

	public void sendWorld() throws IOException {
		World.worldMap.writeExternal(out);
	}
	
	public void sendPlayer() throws IOException {
		synchronized(DBAccounts.map){
			DBAccounts.map.get(Server.client_list.get(client_id).get_account_id()).writeExternal(out);
		}
	}

	public void sendPlayersOnlineRequest()  throws IOException{
		new Message(Message.Type.UPDATESQUADS).send(out);
	}
	
	public void sendPlayersOnline() throws IOException {
		PlayersOnline set = new PlayersOnline(this.client_id);
		set.writeExternal(out);
	}
	
	public void sendSquad() throws IOException{
		DBSquads.map.get(this.accountId).unit1.writeExternal(out);
		DBSquads.map.get(this.accountId).unit2.writeExternal(out);
		DBSquads.map.get(this.accountId).unit3.writeExternal(out);
	}
	
	public void sendSquadSoftUpdate() throws IOException{
		DBSquads.map.get(this.accountId).softUpdate(out);
	}
}