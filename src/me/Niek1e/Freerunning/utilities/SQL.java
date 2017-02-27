package me.Niek1e.Freerunning.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class SQL {

	public static String username;

	public static String password;

	public static String databaseHost;

	public static String databaseName;

	public static int port;

	public static boolean SQLEnabled;

	public static void addWin(Player player) {

		Database db = new Database();
		Connection connection = db.getConnection();

		try {
			if (dataContainsPlayer(player)) {
				int wins = 0;
				PreparedStatement sql = connection.prepareStatement("SELECT wins FROM `wins` WHERE uuid = ?;");
				sql.setString(1, player.getUniqueId().toString());

				ResultSet resultSet = sql.executeQuery();
				resultSet.next();

				wins = resultSet.getInt("wins");

				PreparedStatement updateWins = connection
						.prepareStatement("UPDATE wins SET `wins` = ? WHERE uuid = ?;");
				updateWins.setInt(1, wins + 1);
				updateWins.setString(2, player.getUniqueId().toString());

				updateWins.executeUpdate();

				sql.close();
				resultSet.close();
				updateWins.close();

			} else {

				PreparedStatement addPlayer = connection.prepareStatement("INSERT INTO `wins` values(?,1)");
				addPlayer.setString(1, player.getUniqueId().toString());

				addPlayer.executeUpdate();

				addPlayer.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
	}

	public static synchronized boolean dataContainsPlayer(Player player) {

		Database db = new Database();
		Connection connection = db.getConnection();

		try {

			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `wins` WHERE uuid = ?;");
			sql.setString(1, player.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();
			sql.close();
			resultSet.close();
			return containsPlayer;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection();
		}
	}
}
