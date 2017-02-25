package me.Niek1e.Freerunning.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.Niek1e.Freerunning.utilities.ChatUtilities;
import me.Niek1e.Freerunning.utilities.Players;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		ChatUtilities.broadcast(event.getEntity().getName() + ChatColor.RED + " is gevallen!");
		event.setDeathMessage("");
		event.getDrops().clear();
		Players.removeActive(event.getEntity());
	}

}
