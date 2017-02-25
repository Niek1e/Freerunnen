package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class SignUtilities {

	private static List<String> signs = new ArrayList<String>();

	public static void addSign(String location) {
		signs.add(location);
	}

	public static void updateSigns(String ln1, String ln2, String ln3, String ln4) {
		for (String s : signs) {
			String[] cords = s.split(",");
			Block block = Bukkit.getWorld(cords[0]).getBlockAt(Integer.valueOf(cords[1]), Integer.valueOf(cords[2]),
					Integer.valueOf(cords[3]));
			BlockState state = block.getState();
			if (!(state instanceof Sign)) {
			    return;
			}
			Sign sign = (Sign) state;
			sign.setLine(0, ln1);
			sign.setLine(1, ln2);
			sign.setLine(2, ln3);
			sign.setLine(3, ln4);
			sign.update();
		}
	}

}
