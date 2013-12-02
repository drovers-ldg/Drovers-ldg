package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import unit.Unit;

public class DBUnits{
	protected static Statement statement;
	protected static ResultSet result;
	public static HashMap<Integer, Unit> map;
		
	// SQL
	protected final static String sqlInsertUnit = "INSERT INTO units (player_id, bodyType, code_id) VALUES (?, ?, ?)";
	protected final static String sqlDeleteUnit = "DELETE FROM units WHERE id = ?";
	protected final static String sqlMoveUnitMap = "UPDATE units SET mapX = ?, mapY = ? WHERE id = ?";
	
	DBUnits() throws SQLException{
		map = new HashMap<Integer, Unit>();
		readTable();
	}
	
	private static void readTable() throws SQLException{
		// ID, playerId, name, mapX, mapY, areaX, areaY, bodyId, codeId
		Statement statement = DataBase.connectionUnits.createStatement();
		
		String sql = "SELECT * FROM units";
		result = statement.executeQuery(sql);
		
		while(result.next()){
			map.put(result.getInt("id"), new Unit(result.getInt("id"),
												  result.getInt("player_id"),
												  result.getInt("areaX"),
												  result.getInt("areaY"),
												  result.getString("bodyType")));
		}
		System.out.println("DB.Units loaded. Fields: " + map.size());
	}
	
	protected void finalize() throws SQLException{
		if(statement != null)
			statement.close();
		if(result != null)
			result.close();
	}
	
	public static void addUnit(int playerId, String bodyType) throws SQLException{
		PreparedStatement statement = DataBase.connectionUnits.prepareStatement(sqlInsertUnit);
		// player_id
		statement.setInt(1, playerId);
		statement.setString(2, bodyType);
		statement.setInt(3, map.size()+1);
		statement.execute();
		
		// Add to memory
		// ID, playerId, name, mapX, mapY, areaX, areaY, bodyId, codeId
		map.put(map.size()+1, new Unit(map.size()+1, playerId, 0, 0, bodyType));
		System.out.println("Unit created;\n");
	}
	
	public static void deleteItem(int id) throws SQLException{
		PreparedStatement statement = DataBase.connectionAccounts.prepareStatement(sqlDeleteUnit);
		statement.setInt(1, id);
		statement.execute();
		map.remove(id);
		System.out.println("Unit deleted;\n");
	}
	
	public static void showAllUnits(){
		for(Unit item: map.values()){
			System.out.println("id: " + item.id 
							+ "\taccount_id: " + item.playerId 
							+ "\tname:" + item.name 
							+ "\tmap:" + "." + item.areaX + "." + item.areaY 
							+ "\tcode_id: " + item.codeId
							+ "\ttype: " + item.type
							+ "\tname: " + item.name
							+ "\thp: " + item.hp
							+ "\thpMax: " + item.hpMax 
							+ "\tarmor: " + item.armor
							+ "\tspeed: " + item.speed
							+ "\tAS: " + item.attackSpeed
							+ "\tDmg: " + item.damage + "\n");
		}
	}
	
	public static void moveUnits(int mapX, int mapY, int id) throws SQLException{
		PreparedStatement statement = DataBase.connectionAccounts.prepareStatement(sqlMoveUnitMap);
		statement.setInt(1, mapX);
		statement.setInt(2, mapY);
		statement.setInt(3, id);
		statement.execute();
	}
}