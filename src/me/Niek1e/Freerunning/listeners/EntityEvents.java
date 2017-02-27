package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityEvents implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
		event.setCancelled(true);
	}

}
