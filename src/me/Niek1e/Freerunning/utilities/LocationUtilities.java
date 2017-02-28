package me.Niek1e.Freerunning.utilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtilities {

	private static HashMap<String, Location> locations = new HashMap<String, Location>();

	public static void addLocation(String name, String location) {
		if (!(locations.containsKey(name))) {
			String[] cord = location.split(",");
			locations.put(name, new Location(Bukkit.getWorld(cord[0]), Integer.valueOf(cord[1]),
					Integer.valueOf(cord[2]), Integer.valueOf(cord[3])));
			return;
		}
	}

	public static void teleportPlayer(String location, Player player) {

		Location loc = getLocation(location);

		player.teleport(loc);

	}

	public static Location getLocation(String name) {
		return locations.get(name);
	}

}