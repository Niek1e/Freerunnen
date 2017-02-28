package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class SignUtilities {

	private static List<Location> signs = new ArrayList<Location>();

	public static void addSign(Location location) {
		signs.add(location);
	}

	public static void updateSigns(List<String> lines) {
		for (Location s : signs) {
			Block block = s.getBlock();
			BlockState state = block.getState();
			if (!(state instanceof Sign)) {
			    return;
			}
			Sign sign = (Sign) state;
			sign.setLine(0, lines.get(0));
			sign.setLine(1, lines.get(1));
			sign.setLine(2, lines.get(2));
			sign.setLine(3, lines.get(3));
			sign.update();
		}
	}

}
