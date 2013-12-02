package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBAccounts {
	protected static Statement statement;
	protected static ResultSet result;
	public static HashMap<Integer, Account> map;
	
	//SQL
	protected final static String sqlInsertAccount = "INSERT INTO accounts (account_name, account_password, player_name) VALUES (?, ?, ?)";
	protected final static String sqlMove = "UPDATE accounts SET map_x = ?, map_y = ? WHERE id = ?";
	
	DBAccounts() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		map = new HashMap<Integer, Account>();
		readTable();
	}
	
	protected static void readTable() throws SQLException{
		Statement statement = DataBase.connectionAccounts.createStatement();
		
		String sql = "SELECT * FROM accounts";
		result = statement.executeQuery(sql);
		
		
		//SQL: id, accountName, accountPassword, gm, online, player_name, map_id, units_id, thorium, metal, money
		while (result.next()) {
			map.put(result.getInt("id"), new Account(result.getInt("id"),
													 result.getString("account_name"), 
													 result.getString("account_password"), 
													 result.getInt("gm"),
													 result.getString("player_name"),
													 result.getString("map_id"),
													 result.getInt("units_id"),
													 result.getInt("recource_thorium"),
													 result.getInt("recource_metal"),
													 result.getInt("recource_money"),
													 result.getInt("map_x"),
													 result.getInt("map_y")));
		}
		System.out.println("DB.Accounts loaded. Fields: " + map.size());
	}
	
	protected void finalize() throws SQLException{
		if(statement != null)
			statement.close();
		if(result != null)
			result.close();
	}
	
	public static int searchId(String accountName){
		for(Account item: map.values()){
			if(item.accountName.equals(accountName)){
				return item.id;
			}
		}
		return -1; // negative result
	}
	
	public static boolean comparePassword(int id, String password){
		return map.get(id).accountPassword.equals(password);
	}
	
	public static void connect(int accountId, int clientId){
		map.get(accountId).online = true;
		map.get(accountId).clientId = clientId;
	}

	public static void disconnect(int accountId) {
		map.get(accountId).online = false;
		map.get(accountId).clientId = -1;
	}

	public static void showAllAccounts() {
		for(Account item: map.values()){
			System.out.println("id:"+item.id+"\tacc:"+item.accountName+"\tgm:"+item.gm+"\tzone:"+item.mapId+"\tonline:"+item.online+"\tB: "+item.battleId);
		}
	}

	public static boolean addAccount(String accountName, String accountPassword, String playerName) throws SQLException {
		if(DBAccounts.searchId(accountName) != -1){
			return false;
		}
		else{
			// Add account to MySQL
			PreparedStatement statement = DataBase.connectionAccounts.prepareStatement(sqlInsertAccount);
			statement.setString(1, accountName);
			statement.setString(2, accountPassword);
			statement.setString(3, playerName);
			statement.execute();
			
			// Add to memory
			map.put(map.size()+1, new Account(map.size()+1, accountName, accountPassword, 0, playerName, "default", 0, 0, 0, 0, 0, 0));
			System.out.println("Account added;\n");
			return true;
		}
	}
	
	public static void moveUnits(int mapX, int mapY, int accountId) throws SQLException{
		PreparedStatement statement = DataBase.connectionAccounts.prepareStatement(sqlMove);
		statement.setInt(1, mapX);
		statement.setInt(2, mapY);
		statement.setInt(3, accountId);
		statement.execute();
	}
}
