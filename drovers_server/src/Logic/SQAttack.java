package Logic;

import java.io.IOException;
import java.util.Set;
import java.util.Vector;

import server.Server;
import database.DBAccounts;

public class SQAttack{
	public static int battleIds = 0;
	
	public static void checkAndRunBattle(int clientId, int mapX1, int mapY1, int mapX2, int mapY2, int topology){
		Vector<Integer> playersEnemies = new Vector<Integer>();
		Set<Integer> keys = DBAccounts.map.keySet();
		
		playersEnemies.add(Server.client_list.get(clientId).get_account_id());
		for(Integer key: keys){
			if(!DBAccounts.map.get(key).equals(Server.client_list.get(clientId).get_account_id())
				&& DBAccounts.map.get(key).online
				&& DBAccounts.map.get(key).mapX == mapX2 
				&& DBAccounts.map.get(key).mapY == mapY2){
					playersEnemies.add(key);
			}
		}

		synchronized(Server.battlesList){
			int id = battleIds++;
			if(playersEnemies.size() > 1){
				// ID, mapX(to), mapY(to), mapX(from), mapY(from), begginerId, allEnemies
				Server.battlesList.put(id, new BattleThread(id, mapX1, mapY1, mapX2, mapY2, playersEnemies, topology));
			}
		}
	}
	
	public static void attackUp(int clientId) {
		synchronized(DBAccounts.map){
			int mapX = -1;
			int mapY = -1;
			
			synchronized(Server.client_list){
				mapX = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX;
				mapY = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY;
				if(mapY-1 >= 0){
					checkAndRunBattle(clientId, mapX, mapY, mapX, mapY-1, 1);
				}
			}
		}		
	}

	public static void attackDown(int clientId) {
		synchronized(DBAccounts.map){
			int mapX = -1;
			int mapY = -1;
			
			synchronized(Server.client_list){
				mapX = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX;
				mapY = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY;
				if(mapY+1 < World.WorldMap.sizeY){
					checkAndRunBattle(clientId, mapX, mapY, mapX, mapY+1, 2);
				}
			}
		}
	}

	public static void attackLeft(int clientId) {
		synchronized(DBAccounts.map){
			int mapX = -1;
			int mapY = -1;
			
			synchronized(Server.client_list){
				mapX = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX;
				mapY = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY;
				if(mapX-1 >= 0){
					checkAndRunBattle(clientId, mapX, mapY, mapX-1, mapY, 3);
				}
			}
		}
	}
	
	public static void attackRight(int clientId) {
		synchronized(DBAccounts.map){
			int mapX = -1;
			int mapY = -1;
			
			synchronized(Server.client_list){
				mapX = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX;
				mapY = DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY;
				if(mapX+1 < World.WorldMap.sizeX){
					checkAndRunBattle(clientId, mapX, mapY, mapX+1, mapY, 4);
				}
			}
		}
	}

	public static void attackUpLeft(int clientId) throws IOException {
		Server.client_list.get(clientId).send("[SERVER]", "Пока нельзя атаковать по диагонали.");
	}

	public static void attackUpRight(int clientId) throws IOException {
		Server.client_list.get(clientId).send("[SERVER]", "Пока нельзя атаковать по диагонали.");
	}

	public static void attackDownLeft(int clientId) throws IOException {
		Server.client_list.get(clientId).send("[SERVER]", "Пока нельзя атаковать по диагонали.");
	}

	public static void attackDownRight(int clientId) throws IOException {
		Server.client_list.get(clientId).send("[SERVER]", "Пока нельзя атаковать по диагонали.");
	}
	
	public static void updateLocalUnits(int clientId) throws IOException{
		Server.client_list.get(clientId).sendSquadSoftUpdate();
	}
}