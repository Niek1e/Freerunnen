package me.Niek1e.Freerunning.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

import me.Niek1e.Freerunning.utilities.ChatUtilities;


public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getPlayer().getItemInHand().getType() == Material.EGG){
			return;
		}
		
		
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.SPRUCE_DOOR){
				if(event.getPlayer().hasPermission("freerunnen.deuren")){
					Door door = (Door) event.getClickedBlock().getState().getData();
					if(door.isTopHalf()){
						Door realdoor = (Door) event.getClickedBlock().getLocation().add(0, -1, 0).getBlock().getState().getData();
						if(!realdoor.isOpen())
							ChatUtilities.playerMessage(ChatColor.RED + "Vergeet de deur niet te sluiten!", event.getPlayer());
					}else{
						if(!door.isOpen())
							ChatUtilities.playerMessage(ChatColor.RED + "Vergeet de deur niet te sluiten!", event.getPlayer());
					}
				}else{
					event.setCancelled(true);
				}
			}else{
				return;
			}
		}else{
			return;
		}
	}
}
