package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.Niek1e.Freerunning.GameState;

public class Players {

	private static List<Player> activePlayers = new ArrayList<Player>();

	private static List<Player> allPlayers = new ArrayList<Player>();

	public static List<Player> getActivePlayers() {
		return activePlayers;
	}

	public static List<Player> getAllPlayers() {
		return allPlayers;
	}

	public static boolean isActive(Player player) {
		return activePlayers.contains(player.getName());
	}

	public static void addActive(Player player) {
		activePlayers.add(player);
	}

	public static void addPlayer(Player player) {
		allPlayers.add(player);

		if (Players.getAllPlayers().size() == 1) {

			Game.canStart(true);
		}
	}

	public static void removeActive(Player player) {
		activePlayers.remove(player);

		if (activePlayers.size() == 1 && GameState.isState(GameState.IN_GAME)) {
			Game.stop(activePlayers.get(0));
		}
	}

	public static void removePlayer(Player player) {
		removeActive(player);
		allPlayers.remove(player);

		if (GameState.isState(GameState.LOBBY)) {
			if (!(allPlayers.size() > 1)) {
				Game.canStart(false);
			}
		}
	}

}
