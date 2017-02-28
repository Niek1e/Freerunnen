package me.Niek1e.Freerunning.utilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtilities {

	// NAAM ; WORLD,X,Y,Z
	private static HashMap<String, String> Locations = new HashMap<String, String>();

	public static String getLocation(String name) {
		if (!(Locations.containsKey(name))) {
			return null;
		}

		return Locations.get(name);
	}

	public static void addLocation(String name, String location) {
		if (!(Locations.containsKey(name))) {
			Locations.put(name, location);
			return;
		}
	}

	public static void teleportPlayer(String location, Player player) {

		String loc = getLocation(location);

		String[] cords = loc.split(",");

		World world = Bukkit.getWorld(cords[0]);

		double x = Integer.valueOf(cords[1]);
		double y = Integer.valueOf(cords[2]);
		double z = Integer.valueOf(cords[3]);

		Location goTo = new Location(world, x, y, z);

		player.teleport(goTo);
	}

	public static Location getFullLocation(String name) {
		if (getLocation(name) == null) {
			return null;
		}

		String loc = getLocation(name);

		String[] cords = loc.split(",");

		World world = Bukkit.getWorld(cords[0]);

		double x = Integer.valueOf(cords[1]);
		double y = Integer.valueOf(cords[2]);
		double z = Integer.valueOf(cords[3]);

		Location goTo = new Location(world, x, y, z);

		return goTo;
	}

}