package me.Niek1e.Freerunning.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Niek1e.Freerunning.Freerunning;

public class MySQL {

	private Connection connection;

	public MySQL() {
		try {
			if (connection == null || connection.isClosed()) {
				openConnection();
			}
		} catch (SQLException e) {
			Bukkit.getLogger().warning(ChatColor.RED + "SQL Error");
		}
	}

	public void addWin(Player player) {
		if (doesPlayerExist(player)) {
			updatePlayer(player);
		} else {
			addPlayer(player);
		}
	}

	private void updatePlayer(Player player) {
		try {
			int wins = 0;
			PreparedStatement getWins = connection.prepareStatement("SELECT wins FROM wins WHERE uuid = ?;");
			getWins.setString(1, player.getUniqueId().toString());

			ResultSet resultSet = getWins.executeQuery();
			resultSet.next();

			wins = resultSet.getInt("wins");

			PreparedStatement updateWins = connection.prepareStatement("UPDATE wins SET wins = ? WHERE uuid = ?;");
			updateWins.setInt(1, wins + 1);
			updateWins.setString(2, player.getUniqueId().toString());

			updateWins.executeUpdate();

			getWins.close();
			resultSet.close();
			updateWins.close();
		} catch (SQLException e) {
			Bukkit.getLogger().warning(ChatColor.RED + "SQL Error");
		}
	}

	private void addPlayer(Player player) {
		try {
			PreparedStatement newPlayer;
			newPlayer = connection.prepareStatement("INSERT INTO wins values(?,1)");
			newPlayer.setString(1, player.getUniqueId().toString());

			newPlayer.executeUpdate();

			newPlayer.close();
		} catch (SQLException e) {
			Bukkit.getLogger().warning(ChatColor.RED + "SQL Error");
		}
	}

	private void openConnection() {
		FileConfiguration config = Freerunning.getInstance().getConfig();
		String path = "SQL.";
		String url = "jdbc:mysql://" + config.getString(path + "DatabaseHost") + ":" + config.getInt(path + "Port")
				+ "/" + config.getString(path + "DatabaseName");
		try {
			connection = DriverManager.getConnection(url, config.getString(path + "Username"),
					config.getString(path + "Password"));
		} catch (Exception e) {
			Bukkit.getLogger().warning(ChatColor.RED + "SQL Error");
		}
	}

	private boolean doesPlayerExist(Player player) {
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM wins WHERE uuid = ?;");
			sql.setString(1, player.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();
			sql.close();
			resultSet.close();
			return containsPlayer;
		} catch (Exception e) {
			Bukkit.getLogger().warning(ChatColor.RED + "SQL Error");
		}
		return false;
	}
}