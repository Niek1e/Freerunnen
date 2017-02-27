package me.Niek1e.Freerunning.utilities;

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

	@SuppressWarnings("deprecation")
	public static void start() {
		hasStarted = true;
		GameState.setState(GameState.IN_GAME);

		for (Player activePlayer : Players.getAllPlayers()) {
			Players.addActive(activePlayer);
			LocationUtilities.teleportPlayer("Game", activePlayer);
			activePlayer.getInventory().clear();
			activePlayer.getInventory().addItem(new ItemStack(Material.EGG, 8));
			activePlayer.updateInventory();
			activePlayer.setGameMode(GameMode.SURVIVAL);
		}
	}

	public static void stop(Player player) {
		hasStarted = false;
		ChatUtilities.broadcast(player.getName() + ChatColor.GREEN + " heeft gewonnen!");
		GameState.setState(GameState.LOBBY);
		for (Player activePlayer : Players.getAllPlayers()) {
			Players.removeActive(activePlayer);
			LocationUtilities.teleportPlayer("Spawn", activePlayer);
			activePlayer.setGameMode(GameMode.SURVIVAL);
			activePlayer.getInventory().clear();
		}
		Freerunning.getInstance().startCountdown();
		
		ChatUtilities.broadcast(ChatColor.GREEN + "" + player.getName() + " heeft gewonnen!");
		
		//MAAK EEN TABEL MET uuid EN wins

		SQL.executeQuery("GEBRUIKERSNAAM", "WACHTWOORD", "HOSTNAME", "DATABASE", 3306, //3306 = DB_PORT
				"UPDATE wins SET wins = wins+1 WHERE uuid =  '" + player.getUniqueId() + "'");

	}

	public static boolean canStart() {
		return canStart;
	}

	public static boolean hasStarted() {
		return hasStarted;
	}

	public static void setCanStart(boolean b) {
		canStart = b;
	}

}
