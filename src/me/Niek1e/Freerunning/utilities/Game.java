package me.Niek1e.Freerunning.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Niek1e.Freerunning.Freerunning;
import me.Niek1e.Freerunning.GameState;

public class Game {

	private static boolean canStart = false;
	private static boolean hasStarted = false;

	public static void start() {
		hasStarted = true;
		GameState.setState(GameState.IN_GAME);

		for (Player activePlayer : Players.getAllPlayers()) {
			Players.addActive(activePlayer);
			LocationUtilities.teleportPlayer("Game", activePlayer);
			activePlayer.getInventory().clear();
			activePlayer.getInventory().addItem(new ItemStack(Material.EGG, 8));
			activePlayer.setGameMode(GameMode.SURVIVAL);
		}
	}

	public static void stop(Player player) {
		hasStarted = false;
		Bukkit.broadcastMessage(Freerunning.getPrefix + player.getName() + ChatColor.GREEN + " heeft gewonnen!");
		GameState.setState(GameState.LOBBY);
		for (Player activePlayer : Players.getAllPlayers()) {
			Players.removeActive(activePlayer);
			LocationUtilities.teleportPlayer("Spawn", activePlayer);
			activePlayer.setGameMode(GameMode.SURVIVAL);
			activePlayer.getInventory().clear();
		}
		Freerunning.getInstance().startCountdown();

		// MAAK EEN TABEL MET uuid EN wins
		MySQL sql = new MySQL();
		sql.addWin(player);
	}

	public static boolean canStart() {
		return canStart;
	}

	public static boolean hasStarted() {
		return hasStarted;
	}

	public static void canStart(boolean b) {
		canStart = b;
	}

}
