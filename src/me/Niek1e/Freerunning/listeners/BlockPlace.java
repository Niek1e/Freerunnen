package me.Niek1e.Freerunning.listeners;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("freerunnen.block")) {
			event.setCancelled(true);
			player.spawnParticle(Particle.CRIT, event.getBlock().getLocation(), 4);
		}
	}

}
