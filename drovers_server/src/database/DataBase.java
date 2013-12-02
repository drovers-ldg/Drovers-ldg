package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DataBase{
	
	// Connections
	public static Connection connectionAccounts;
	public static Connection connectionUnits;
	
	// DB's
	public static DBAccounts accounts;
	public static DBUnits units;
	public static DBItems items;
	public static DBSquads squads;
	// Setup
	public static String mysqlAddress;
	public static String mysqlPort;
	public static String mysqlUser;
	public static String mysqlPassword;
	// DB paths
	public static String mysqlDBAccounts;
	public static String mysqlDBUnits;
	
	public DataBase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException{
		System.out.println("---------------MySQL---------------");
		readConfig();
		
		try{
			Properties setup = new Properties();
			setup.put("characterEncoding","UTF8");
			setup.put("user", mysqlUser);
			setup.put("password", mysqlPassword);
			
			String urlAccounts 	= "jdbc:mysql://"+mysqlAddress+":"+mysqlPort+"/"+mysqlDBAccounts;
			String urlUnits 	= "jdbc:mysql://"+mysqlAddress+":"+mysqlPort+"/"+mysqlDBUnits;
			
			// Set connections from MySQL
			connectionAccounts 	= DriverManager.getConnection(urlAccounts, setup);
			connectionUnits 	= DriverManager.getConnection(urlUnits, setup);
			// add other connections here
		}
		catch(Exception e){
			System.out.println("MySQL connection error. Check MySQL server status.");
		}
		System.out.println("---------------DB Status-----------");
		accounts = new DBAccounts();
		units = new DBUnits();
		items = new DBItems();
		squads = new DBSquads();
	}
	
	protected void readConfig() throws FileNotFoundException{
		Scanner in = new Scanner(new File("config\\server.conf"));
		while(in.hasNext()){
			String str = in.next();
			String data = in.nextLine();
			data = data.trim();
			switch(str){
				case "#MySQL_Address:":
					mysqlAddress = data;
					System.out.println("mysqlAddress:\t" + mysqlAddress);
					break;
				case "#MySQL_Port:":
					mysqlPort = data;
					System.out.println("mysqlAddress:\t" + mysqlPort);
					break;
				case "#MySQL_User:":
					mysqlUser = data;
					System.out.println("mysqlUser:\t" + mysqlUser);
					break;
				case "#MySQL_Password:":
					mysqlPassword = data;
					System.out.println("mysqlPassword:\t" + mysqlPassword);
					break;
				case "#MySQL_Accounts_DB:":
					mysqlDBAccounts = data;
					System.out.println("mysqlDBAccounts:" + mysqlDBAccounts);
					break;
				case "#MySQL_Units_DB:":
					mysqlDBUnits = data;
					System.out.println("mysqlDBUnits:\t" + mysqlDBUnits);
					break;
				default:
					break;
			}
		}
		in.close();
	}
	
	protected void finalize() throws SQLException{
		if(connectionAccounts != null){
			connectionAccounts.close();
		}
		if(connectionUnits != null){
			connectionUnits.close();
		}
	}
}