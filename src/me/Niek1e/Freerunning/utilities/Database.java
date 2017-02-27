package me.Niek1e.Freerunning.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private Connection connection;
	
	private static List<Database> databases = new ArrayList<Database>();
	
	public Database() {
		try {
			if(!connection.isClosed() && connection != null){
				connection.close();
			}
			openConnection();
			
			databases.add(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void openConnection() {
		String url = "jdbc:mysql://" + SQL.databaseHost + ":" + SQL.port + "/" + SQL.databaseName;
		try {
			connection = DriverManager.getConnection(url, SQL.username, SQL.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void closeConnection() {
		try {
			if(!connection.isClosed() && connection != null){
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	private static List<Database> getDatabases() {
		return databases;
	}
	
	public static void closeAllDatabases() {
		for(Database db : getDatabases()){
			Connection con = db.getConnection();
			try {
				if(!con.isClosed() && con != null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
