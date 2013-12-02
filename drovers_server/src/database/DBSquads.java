package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import unit.Squad;

public class DBSquads{
	protected static Statement statement;
	protected static ResultSet result;
	public static HashMap<Integer, Squad> map;
		
	// SQL
	protected final static String sqlInsertSQ = "INSERT INTO squads (accountId, unit1, unit2, unit3) VALUES (?, ?, ?, ?)";
	protected final static String sqlDeleteSQ = "DELETE FROM squads WHERE id = ?";
	
	DBSquads() throws SQLException{
		map = new HashMap<Integer, Squad>();
		readTable();
	}
	
	private static void readTable() throws SQLException{
		Statement statement = DataBase.connectionUnits.createStatement();
		
		String sql = "SELECT * FROM squads";
		result = statement.executeQuery(sql);
		
		while(result.next()){
			map.put(result.getInt("id"), new Squad(result.getInt("id"),
												  result.getInt("accountId"),
												  result.getInt("unit1"),
												  result.getInt("unit2"),
												  result.getInt("unit3")));
		}
		System.out.println("DB.Squads loaded. Fields: " + map.size());
	}
	
	protected void finalize() throws SQLException{
		if(statement != null)
			statement.close();
		if(result != null)
			result.close();
	}
	
	public static void addSquad(Squad squad) throws SQLException{
		PreparedStatement statement = DataBase.connectionUnits.prepareStatement(sqlInsertSQ);
		
		// player_id
		statement.setInt(1, squad.accountId);
		statement.setInt(2, squad.unit1.id);
		statement.setInt(3, squad.unit2.id);
		statement.setInt(4, squad.unit3.id);
		statement.execute();
		
		// Add to memory
		map.put(map.size()+1, squad);
		System.out.println("Squad created;\n");
	
	}
	public static void addSquad(int playerId, int unit1, int unit2, int unit3) throws SQLException{
		PreparedStatement statement = DataBase.connectionUnits.prepareStatement(sqlInsertSQ);
		
		// player_id
		statement.setInt(1, playerId);
		statement.setInt(2, DBUnits.map.size()+1);
		statement.setInt(3, DBUnits.map.size()+2);
		statement.setInt(4, DBUnits.map.size()+3);
		statement.execute();
		
		// Add to memory
		// ID, playerId, name, mapX, mapY, areaX, areaY, bodyId, codeId
		map.put(map.size()+1, new Squad(playerId));
		System.out.println("Squad created;\n");
	}
	
	public static void shwoAllSquads(){
		for(Squad item: map.values()){
			System.out.println("id: " + item.id + " accId: " + item.accountId + " U1: " + item.unit1.id + " U2: "+ item.unit2.id + " U3: " + item.unit3.id);
		} 
	}
	public static void deleteSquad(int playerId){
		
	}
}