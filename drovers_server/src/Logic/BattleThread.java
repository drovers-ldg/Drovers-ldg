package Logic;

import java.io.IOException;
import java.util.Vector;

import server.Server;
import unit.Squad;
import messages.Message;
import database.DBAccounts;
import database.DBSquads;

public class BattleThread extends Thread{
	public int id;
	public int playerId1;
	public int playerId2;
	public int mapX1;
	public int mapY1;
	public int mapX2;
	public int mapY2;
	public int topology;
	
	public Squad player1;
	public Squad player2;
	
	
	public boolean ready1;
	public boolean ready2;
	
	public BattleThread(int id, int mapX1, int mapY1, int mapX2, int mapY2, Vector<Integer> addPlayers, int topology){
		this.playerId1 = addPlayers.get(0);
		this.playerId2 = addPlayers.get(1);
		this.id = id;
		this.mapX1 = mapX1;
		this.mapY1 = mapY1;
		this.mapX2 = mapX2;
		this.mapY2 = mapY2;
		this.topology = topology; // 1 - Up, 2 - Down, 3 - Left, 4 - Right
		addPlayers();
		player1 = DBSquads.map.get(addPlayers.get(0));
		player2 = DBSquads.map.get(addPlayers.get(1));
		
		this.run();
	}
	
	public void run(){
		try{
			System.out.println("Send Map");
			sendMaps();
			
			while(true){
				if(ready1 && ready2)
					BattleLogic();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{	
			this.interrupt();
		}
	}
	
	private void BattleLogic() throws IOException {
		sendUnitsMove();
	}

	protected void finalize(){
		exitPlayers();
	}
	
	public synchronized void addPlayers(){
		DBAccounts.map.get(playerId1).battleId = this.id;
		DBAccounts.map.get(playerId2).battleId = this.id;
	}
	
	public synchronized void exitPlayers(){
		DBAccounts.map.get(playerId1).battleId = -1;
		DBAccounts.map.get(playerId2).battleId = -1;
	}
	
	public synchronized void sendMaps() throws IOException{
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEAREA, "" + this.topology);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEAREA, "" + this.topology);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).sendMap(this.id, this.mapX1, this.mapY1, this.mapX2, this.mapY2);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).sendMap(this.id, this.mapX1, this.mapY1, this.mapX2, this.mapY2);
	}
	
	public synchronized void sendUnitsMove() throws IOException{
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+1+" "+player1.unit1.areaX+" "+player1.unit1.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+2+" "+player1.unit2.areaX+" "+player1.unit2.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+3+" "+player1.unit3.areaX+" "+player1.unit3.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+1+" "+player2.unit1.areaX+" "+player1.unit1.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+2+" "+player2.unit2.areaX+" "+player1.unit2.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId1).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+3+" "+player2.unit3.areaX+" "+player1.unit3.areaY);
	
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+1+" "+player1.unit1.areaX+" "+player1.unit1.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+2+" "+player1.unit2.areaX+" "+player1.unit2.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+1+" "+3+" "+player1.unit3.areaX+" "+player1.unit3.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+1+" "+player2.unit1.areaX+" "+player1.unit1.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+2+" "+player2.unit2.areaX+" "+player1.unit2.areaY);
		Server.client_list.get(DBAccounts.map.get(playerId2).clientId).send(Message.Type.BATTLEUNITMOVE, ""+0+" "+3+" "+player2.unit3.areaX+" "+player1.unit3.areaY);
	}
}