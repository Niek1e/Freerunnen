package me.Niek1e.Freerunning.utilities;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.WHITE;

import org.bukkit.entity.Player;

public class ChatUtilities {

	public static void broadcast(String message) {

		String bericht = prefix() + message;

		for (Player player : Players.getAllPlayers())
			player.sendMessage(bericht);

	}

	public static void playerMessage(String message, Player player) {
		String bericht = prefix() + message;

		player.sendMessage(bericht);
	}

	private static String prefix() {
		return GOLD + "[Sprookjescraft]" + WHITE + " ";
	}

}
