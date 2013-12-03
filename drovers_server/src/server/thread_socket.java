package server;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import database.Account;
import database.DBAccounts;
import database.DBSquads;
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
	public void sendMap(int battleId, int mapX1, int mapY1, int mapX2, int mapY2) throws IOException{
		synchronized(DBAccounts.map){
			synchronized(Server.battlesList){
				for(int i = 0; i < 10; ++i){
					for(int j = 0; j < 10; ++j){
						int type = World.areaMaps.get(WorldMap.map[mapX1][mapY1].areaName).map[i][j];
						send(Message.Type.BATTLEAREA1, ""+i+" "+j+" "+type);
					}
				}
				for(int i = 0; i < 10; ++i){
					for(int j = 0; j < 10; ++j){
						int type = World.areaMaps.get(WorldMap.map[mapX2][mapY2].areaName).map[i][j];
						send(Message.Type.BATTLEAREA2, ""+i+" "+j+" "+type);
					}
				}
				send(Message.Type.BATTLEAREAEND, null);
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
		for(Account item: DBAccounts.map.values()){
			if(item.online){
				send(Message.Type.PLAYERSPOSITION, ""+item.mapX+" "+item.mapY+" "+item.accountName);
				System.out.println(""+item.mapX+" "+item.mapY+" "+item.accountName);
			}
		}	
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