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

public class Freerunning extends JavaPlugin {

	private Game currentGame;

	public static int startCoundownId;

	private static Freerunning instance;

	public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&7[&6Sprookjescraft&7]&f ");

	private static List<Location> signs = new ArrayList<Location>();
	
	private int timeUntilStart;
	
	public Game getCurrentGame() {
		return currentGame;
	}

	public void onEnable() {
		GameState.setState(GameState.LOBBY);

		registerListeners();

		setupConfig();

		startCountdown();

		registerLocation();

		registerSigns();

		instance = this;

		currentGame = new Game();

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

	private void updateTimeSigns() {
		List<String> lns = new ArrayList<String>();
		lns.add("Tijd tot begin:");
		lns.add(ChatColor.GREEN + "" + timeUntilStart + "s");
		lns.add("Spelers online:");
		if (Freerunning.getInstance().getCurrentGame().canStart()) {
			lns.add(ChatColor.GREEN + "" + getCurrentGame().getAllPlayers().size() + " speler(s)");
		} else {
			lns.add(ChatColor.RED + "" + getCurrentGame().getAllPlayers().size() + " speler(s)");
		}
		
		for (Location s : signs) {
			Block block = s.getBlock();
			BlockState state = block.getState();
			if (!(state instanceof Sign)) {
				return;
			}
			Sign sign = (Sign) state;
			for (int i = 0; i < 4; i++) {
				sign.setLine(i, lns.get(i));
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
		timeUntilStart = 60;
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			
			public void run() {
				if (timeUntilStart == 0) {
					if (!Freerunning.getInstance().getCurrentGame().canStart()) {
						restartCountdown();
						Bukkit.broadcastMessage(
								Freerunning.PREFIX + "Het spel kan nu niet gestart worden, de timer wordt gereset!");
						return;
					}
					Freerunning.getInstance().getCurrentGame().start();
					stopCountdown();
				}

				if (timeUntilStart % 10 == 0 || timeUntilStart <= 10) {
					Bukkit.broadcastMessage(
							Freerunning.PREFIX + "Het spel begint over " + String.valueOf(timeUntilStart) + " seconden!");
				}

				updateTimeSigns();

				timeUntilStart -= 1;
			}
		}, 20l, 20l);
	}

	public void stopCountdown() {
		getServer().getScheduler().cancelAllTasks();
	}

	public void restartCountdown() {
		stopCountdown();
		startCountdown();
	}

}