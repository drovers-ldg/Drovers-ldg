package Logic;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Set;
import messages.Message;
import server.Server;
import database.DBAccounts;

public class SQMove{
	
	public static void moveUp(int clientId) throws IOException, SQLException{
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerY - 1 > 0) && World.WorldMap.map[playerX][playerY-1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY--;
			}
			DBAccounts.moveUnits(playerX, playerY-1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEUP, null);
			updateSQRequest();
		}
	}
	
	public static void moveDown(int clientId) throws IOException, SQLException{
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerY + 1 < World.WorldMap.sizeY) && World.WorldMap.map[playerX][playerY+1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY++;
			}
			DBAccounts.moveUnits(playerX, playerY+1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEDOWN, null);
			updateSQRequest();
		}
	}
	
	public static void moveRight(int clientId) throws IOException, SQLException{
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerX + 1 < World.WorldMap.sizeX) && World.WorldMap.map[playerX+1][playerY].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX++;
			}
			DBAccounts.moveUnits(playerX+1, playerY, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVERIGHT, null);
			updateSQRequest();
		}
	}
	
	public static void moveLeft(int clientId) throws IOException, SQLException{
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if(playerX - 1 > 0 && World.WorldMap.map[playerX-1][playerY].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX--;
			}
			DBAccounts.moveUnits(playerX-1, playerY-1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVELEFT, null);
			updateSQRequest();
		}
	}

	public static void moveUpLeft(int clientId) throws IOException, SQLException {
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerX - 1 > 0) && (playerY - 1 > 0) && World.WorldMap.map[playerX-1][playerY-1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX--;
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY--;
			}
			DBAccounts.moveUnits(playerX, playerY+1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEUPLEFT, null);
			updateSQRequest();
		}
	}

	public static void moveUpRight(int clientId) throws IOException, SQLException {
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerX + 1 < World.WorldMap.sizeX) && (playerY - 1 > 0) && World.WorldMap.map[playerX+1][playerY-1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX++;
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY--;
			}
			DBAccounts.moveUnits(playerX+1, playerY-1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEUPRIGHT, null);
			updateSQRequest();
		}
	}

	public static void moveDownLeft(int clientId) throws IOException, SQLException {
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerX - 1 > 0) && (playerY + 1 <  World.WorldMap.sizeY) && World.WorldMap.map[playerX-1][playerY+1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX--;
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY++;
			}
			DBAccounts.moveUnits(playerX-1, playerY+1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEDOWNLEFT, null);
			updateSQRequest();
		}
	}

	public static void moveDownRight(int clientId) throws IOException, SQLException {
		synchronized(DBAccounts.map){
			int accountId = Server.client_list.get(clientId).get_account_id();
			int playerX = DBAccounts.map.get(accountId).mapX;
			int playerY = DBAccounts.map.get(accountId).mapY;
			if((playerX + 1 < World.WorldMap.sizeX) && (playerY + 1 <  World.WorldMap.sizeY) && World.WorldMap.map[playerX+1][playerY+1].movable){
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapX++;
				DBAccounts.map.get(Server.client_list.get(clientId).get_account_id()).mapY++;
			}	
			DBAccounts.moveUnits(playerX+1, playerY+1, accountId);
			Server.client_list.get(clientId).send(Message.Type.SQMOVEDOWNRIGHT, null);
			updateSQRequest();
		}
	}
	
	public static void updateSQRequest() throws IOException{
		synchronized(Server.client_list){
			Set<Integer> clients = Server.client_list.keySet();
			for(Integer index: clients){
				if(Server.client_list.get(index).get_connection()){
					Server.client_list.get(index).sendPlayersOnlineRequest();
				}
			}
		}
	}
	
	public static void updateSQ(int clientId) throws IOException{
		Server.client_list.get(clientId).sendPlayersOnline();
	}
}