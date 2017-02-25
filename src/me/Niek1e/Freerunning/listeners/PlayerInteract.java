package me.Niek1e.Freerunning.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.Niek1e.Freerunning.utilities.Game;


public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && !Game.hasStarted()) {

				Inventory i = Bukkit.createInventory(null, 9, "Kies je kit!");

				event.getPlayer().openInventory(i);
			}
		}
	}
}
