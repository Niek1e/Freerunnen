package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getPlayer().hasPermission("freerunnen.block")){
			return;
		}
		
		event.setCancelled(true);
	}

}
