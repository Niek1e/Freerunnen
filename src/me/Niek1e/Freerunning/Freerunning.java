package me.Niek1e.Freerunning;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Niek1e.Freerunning.listeners.AsyncPlayerPreLogin;
import me.Niek1e.Freerunning.listeners.BlockBreak;
import me.Niek1e.Freerunning.listeners.EntityDamageByBlock;
import me.Niek1e.Freerunning.listeners.EntityDamageByEntity;
import me.Niek1e.Freerunning.listeners.PlayerDeath;
import me.Niek1e.Freerunning.listeners.PlayerDropItem;
import me.Niek1e.Freerunning.listeners.PlayerEggThrow;
import me.Niek1e.Freerunning.listeners.PlayerInteract;
import me.Niek1e.Freerunning.listeners.PlayerJoin;
import me.Niek1e.Freerunning.listeners.PlayerMove;
import me.Niek1e.Freerunning.listeners.PlayerQuit;
import me.Niek1e.Freerunning.listeners.PlayerRespawn;
import me.Niek1e.Freerunning.utilities.LocationUtilities;
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
	}

	public static Freerunning getInstance() {
		return instance;
	}

	public void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new AsyncPlayerPreLogin(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerDropItem(), this);
		pm.registerEvents(new PlayerEggThrow(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerRespawn(), this);
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new EntityDamageByBlock(), this);
		pm.registerEvents(new BlockBreak(), this);
	}

	public void registerSigns() {
		String path = "Locaties.";
		FileConfiguration config = getConfig();
		String world = config.getString(path + "World") + ",";
		SignUtilities.addSign(world + config.getString(path + "Sign"));
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
