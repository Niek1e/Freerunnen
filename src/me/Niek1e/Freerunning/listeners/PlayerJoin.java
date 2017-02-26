package me.Niek1e.Freerunning.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Niek1e.Freerunning.utilities.ChatUtilities;
import me.Niek1e.Freerunning.utilities.LocationUtilities;
import me.Niek1e.Freerunning.utilities.Players;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Players.addPlayer(player);

		ChatUtilities.broadcast(player.getName() + " doet mee met het spel!");
		event.setJoinMessage("");
		
		LocationUtilities.teleportPlayer("Spawn", player);

		player.setCanPickupItems(false);
		
		player.getInventory().clear();
	}

}
