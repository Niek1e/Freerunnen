package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class EntityDamageByBlock implements Listener {

	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
		event.setCancelled(true);
	}

}
