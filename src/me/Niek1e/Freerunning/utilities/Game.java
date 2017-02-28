package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Niek1e.Freerunning.Freerunning;
import me.Niek1e.Freerunning.GameState;

public class Game {

	private boolean canStart;
	private boolean hasStarted;
	
	private List<Player> activePlayers = new ArrayList<Player>();
	
	private List<Player> allPlayers = new ArrayList<Player>();

	public Game() {
		this.canStart = false;
		this.hasStarted = false;
	}

	public void start() {
		hasStarted = true;
		GameState.setState(GameState.IN_GAME);

		for (Player activePlayer : getAllPlayers()) {
			addActive(activePlayer);
			LocationUtilities.teleportPlayer("Game", activePlayer);
			activePlayer.getInventory().clear();
			activePlayer.getInventory().addItem(new ItemStack(Material.EGG, 8));
			activePlayer.setGameMode(GameMode.SURVIVAL);
		}
	}

	public void stop(Player player) {
		hasStarted = false;
		Bukkit.broadcastMessage(Freerunning.PREFIX + player.getName() + ChatColor.GREEN + " heeft gewonnen!");
		GameState.setState(GameState.LOBBY);
		for (Player activePlayer : getAllPlayers()) {
			removeActive(activePlayer);
			LocationUtilities.teleportPlayer("Spawn", activePlayer);
			activePlayer.setGameMode(GameMode.SURVIVAL);
			activePlayer.getInventory().clear();
		}
		Freerunning.getInstance().startCountdown();

		// MAAK EEN TABEL MET uuid EN wins
		MySQL sql = new MySQL();
		sql.addWin(player);
	}

	public boolean canStart() {
		return canStart;
	}

	public boolean hasStarted() {
		return hasStarted;
	}

	public static void canStart(boolean canStart) {
		Game.canStart = canStart;
	}
	
	public List<Player> getActivePlayers() {
		return activePlayers;
	}
	
	public List<Player> getAllPlayers() {
		return allPlayers;
	}
	
	public boolean isActive(Player player) {
		return activePlayers.contains(player.getName());
	}
	
	public void addActive(Player player) {
		activePlayers.add(player);
	}
	
	public void addPlayer(Player player) {
		allPlayers.add(player);

		if (getAllPlayers().size() > 1) {

			Freerunning.getInstance().getCurrentGame().canStart(true);
		}
	}
	
	public void removeActive(Player player) {
		activePlayers.remove(player);

		if (activePlayers.size() == 1 && GameState.isState(GameState.IN_GAME)) {
			Freerunning.getInstance().getCurrentGame().stop(activePlayers.get(0));
		}
	}
	
	public void removePlayer(Player player) {
		removeActive(player);
		allPlayers.remove(player);

		if (GameState.isState(GameState.LOBBY)) {
			if (!(allPlayers.size() > 1)) {
				Freerunning.getInstance().getCurrentGame().canStart(false);
			}
		}
	}

}
