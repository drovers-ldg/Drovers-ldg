package Logic;

import java.io.IOException;
import java.util.Vector;

import server.Server;
import messages.Message;
import database.DBAccounts;

public class BattleThread extends Thread{
	public int id;
	public Vector<Integer> players;
	public int mapX1;
	public int mapY1;
	public int mapX2;
	public int mapY2;
	public int topology;
	
	public BattleThread(int id, int mapX1, int mapY1, int mapX2, int mapY2, Vector<Integer> addPlayers, int topology){
		this.players = addPlayers;
		this.id = id;
		this.mapX1 = mapX1;
		this.mapY1 = mapY1;
		this.mapX2 = mapX2;
		this.mapY2 = mapY2;
		this.topology = topology; // 1 - Up, 2 - Down, 3 - Left, 4 - Right
		addPlayers();
		this.run();
	}
	
	public void run(){
		try{
			sendMaps();		
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{	
			this.interrupt();
		}
	}
	
	protected void finalize(){
		exitPlayers();
	}
	
	public synchronized void addPlayers(){
		for(Integer playerId: this.players){
			DBAccounts.map.get(playerId).battleId = this.id;
		}
	}
	
	public synchronized void exitPlayers(){
		for(Integer playerId: this.players){
			DBAccounts.map.get(playerId).battleId = -1;
		}
	}
	
	public synchronized void sendMaps() throws IOException{
		for(Integer playerId: this.players){
			Server.client_list.get(DBAccounts.map.get(playerId).clientId).send(Message.Type.BATTLEAREA1, new String() + this.topology);
		}
	}
}