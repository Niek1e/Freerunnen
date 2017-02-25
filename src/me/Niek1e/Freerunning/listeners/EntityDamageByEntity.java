package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		event.setCancelled(true);
	}
	
}
