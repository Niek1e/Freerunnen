package me.Niek1e.Freerunning;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Niek1e.Freerunning.listeners.BlockEvents;
import me.Niek1e.Freerunning.listeners.EntityEvents;
import me.Niek1e.Freerunning.listeners.PlayerEvents;
import me.Niek1e.Freerunning.utilities.Database;
import me.Niek1e.Freerunning.utilities.LocationUtilities;
import me.Niek1e.Freerunning.utilities.SQL;
import me.Niek1e.Freerunning.utilities.SignUtilities;
import me.Niek1e.Freerunning.utilities.StartCountdown;

public class Freerunning extends JavaPlugin {

	public static int startCoundownId;

	private static Freerunning instance;

	public void onEnable() {
		GameState.setState(GameState.LOBBY);

		registerListeners();

		setupConfig();

		startCountdown();

		registerLocation();
		
		registerSigns();

		instance = this;
		
		setupSQL();
		
	}
	
	public void onDisable() {
		Database.closeAllDatabases();
	}

	public static Freerunning getInstance() {
		return instance;
	}

	public void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerEvents(), this);
		pm.registerEvents(new BlockEvents(), this);
		pm.registerEvents(new EntityEvents(), this);
	}

	public void registerSigns() {
		String path = "Locaties.";
		FileConfiguration config = getConfig();
		String world = config.getString(path + "World") + ",";
		SignUtilities.addSign(world + config.getString(path + "Sign"));
	}
	
	public void setupSQL() {
		String path = "SQL.";
		FileConfiguration config = getConfig();
		
		SQL.port = 0;
		SQL.SQLEnabled = false;
		
		SQL.username = config.getString(path + "Username");
		SQL.password = config.getString(path + "Password");
		SQL.databaseHost = config.getString(path + "DatabaseHost");
		SQL.databaseName = config.getString(path + "DatabaseName");
		SQL.port = config.getInt(path + "Port");
		
		if(SQL.username == null || SQL.username == null || SQL.username == null || SQL.username == null || SQL.port == 0){
			SQL.SQLEnabled = false;
		}else{
			SQL.SQLEnabled = true;
		}
	}

	public void setupConfig() {
		saveDefaultConfig();
	}

	public void registerLocation() {
		String path = "Locaties.";
		FileConfiguration config = getConfig();
		String world = config.getString(path + "World") + ",";
		LocationUtilities.addLocation("Spawn", world + config.getString(path + "Spawn"));
		LocationUtilities.addLocation("Spectator", world + config.getString(path + "Spectator"));
		LocationUtilities.addLocation("Game", world + config.getString(path + "Game"));
	}

	@SuppressWarnings("deprecation")
	public void startCountdown() {
		StartCountdown.timeUntilStart = 60;
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new StartCountdown(this), 20l, 20l);
	}

	public void stopCountdown() {
		getServer().getScheduler().cancelAllTasks();
	}

	public void restartCountdown() {
		stopCountdown();
		startCountdown();
	}

}
