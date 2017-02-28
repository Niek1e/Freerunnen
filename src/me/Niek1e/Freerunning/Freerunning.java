package me.Niek1e.Freerunning;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Niek1e.Freerunning.listeners.BlockEvents;
import me.Niek1e.Freerunning.listeners.EntityEvents;
import me.Niek1e.Freerunning.listeners.PlayerEvents;
import me.Niek1e.Freerunning.utilities.LocationUtilities;
import me.Niek1e.Freerunning.utilities.SignUtilities;
import me.Niek1e.Freerunning.utilities.StartCountdown;

public class Freerunning extends JavaPlugin {

	public static int startCoundownId;

	private static Freerunning instance;
	
	public static String getPrefix = ChatColor.translateAlternateColorCodes('&', "&7[&6Sprookjescraft&7]&f ");

	public void onEnable() {
		GameState.setState(GameState.LOBBY);

		registerListeners();

		setupConfig();

		startCountdown();

		registerLocation();
		
		registerSigns();

		instance = this;
		
	}
	
	public void onDisable() {
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
		World world = Bukkit.getWorld(config.getString(path + "World"));
		String signLoc = config.getString(path + "Sign");
		String[] cords = signLoc.split(",");
		Location s = new Location(world, Integer.valueOf(cords[0]), Integer.valueOf(cords[1]), Integer.valueOf(cords[2]));
		SignUtilities.addSign(s);
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

	public void startCountdown() {
		StartCountdown.timeUntilStart = 60;
		StartCountdown start = new StartCountdown(this);
		start.runTaskTimer(this, 20l, 20l);
	}

	public void stopCountdown() {
		getServer().getScheduler().cancelAllTasks();
	}

	public void restartCountdown() {
		stopCountdown();
		startCountdown();
	}

}
