package me.Niek1e.Freerunning.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQL {
	
	public static void executeQuery(String username, String password, String databaseHost, String databaseName, int port, String query){
		  Connection conn;
		  String url = "jdbc:mysql://" + databaseHost + ":" + port + "/" + databaseName;
		 
		  //Attempt to connect
		  try{
		    //Connection succeeded
		    conn = DriverManager.getConnection(url, username, password);
		    PreparedStatement statement = conn.prepareStatement(query);
		    statement.executeQuery();
		  } catch(Exception e){
		    //Couldn't connect to the database
		  }
		}

}
