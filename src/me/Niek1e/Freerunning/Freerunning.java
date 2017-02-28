package me.Niek1e.Freerunning;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Niek1e.Freerunning.listeners.BlockEvents;
import me.Niek1e.Freerunning.listeners.EntityEvents;
import me.Niek1e.Freerunning.listeners.PlayerEvents;
import me.Niek1e.Freerunning.utilities.Game;
import me.Niek1e.Freerunning.utilities.LocationUtilities;
import me.Niek1e.Freerunning.utilities.StartCountdown;

public class Freerunning extends JavaPlugin {

	public static Game currentGame;

	public static int startCoundownId;

	private static Freerunning instance;

	public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&7[&6Sprookjescraft&7]&f ");

	private static List<Location> signs = new ArrayList<Location>();

	public void onEnable() {
		GameState.setState(GameState.LOBBY);

		registerListeners();

		setupConfig();

		startCountdown();

		registerLocation();

		registerSigns();

		instance = this;

		new Game();

	}

	public void onDisable() {
		stopCountdown();
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
		Location s = new Location(world, Integer.valueOf(cords[0]), Integer.valueOf(cords[1]),
				Integer.valueOf(cords[2]));
		signs.add(s);
	}
	public static void updateSigns(List<String> lines) {
		for (Location s : signs) {
			Block block = s.getBlock();
			BlockState state = block.getState();
			if (!(state instanceof Sign)) {
				return;
			}
			Sign sign = (Sign) state;
			for (int i = 0; i < 4; i++) {
				sign.setLine(i, lines.get(i));
			}
			sign.update();
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

	public void startCountdown() {
		StartCountdown.timeUntilStart = 60;
		StartCountdown start = new StartCountdown(this);
		getServer().getScheduler().runTaskTimer(this, start, 20l, 20l);
	}

	public void stopCountdown() {
		getServer().getScheduler().cancelAllTasks();
	}

	public void restartCountdown() {
		stopCountdown();
		startCountdown();
	}

}