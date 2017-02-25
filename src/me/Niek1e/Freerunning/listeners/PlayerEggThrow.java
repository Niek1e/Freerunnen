package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class PlayerEggThrow implements Listener {

	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event) {
		event.setHatching(false);
	}

}
