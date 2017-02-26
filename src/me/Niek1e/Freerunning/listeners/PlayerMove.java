package me.Niek1e.Freerunning.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Niek1e.Freerunning.utilities.Game;
import me.Niek1e.Freerunning.utilities.Players;

public class PlayerMove implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Location to = new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY() - 1,
				event.getTo().getZ());

		if (to.getBlock() == null || to.getBlock().getType() == null) {
			return;
		}

		if (!Players.getActivePlayers().contains(event.getPlayer())) {
			return;
		}
		
		if (!Game.hasStarted()) {
			return;
		}

		if (to.getBlock().getType() == Material.OBSIDIAN || to.getBlock().getType() == Material.GOLD_BLOCK) {
			if (to.getBlock().getType() == Material.OBSIDIAN) {
				
				event.getPlayer().setHealth(0);
			
			}else if(to.getBlock().getType() == Material.GOLD_BLOCK){
				
				Game.stop(event.getPlayer());
				
			}
		}
	}

}
