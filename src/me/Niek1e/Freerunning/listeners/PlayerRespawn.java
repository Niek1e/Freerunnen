package me.Niek1e.Freerunning.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Niek1e.Freerunning.GameState;
import me.Niek1e.Freerunning.utilities.LocationUtilities;

public class PlayerRespawn implements Listener {

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if (GameState.isState(GameState.IN_GAME)){
			event.setRespawnLocation(LocationUtilities.getFullLocation("Spectator"));
		}else{
			event.setRespawnLocation(LocationUtilities.getFullLocation("Spawn"));
		}
	}

}
