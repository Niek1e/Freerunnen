package me.Niek1e.Freerunning.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Niek1e.Freerunning.GameState;
import me.Niek1e.Freerunning.utilities.ChatUtilities;
import me.Niek1e.Freerunning.utilities.Game;
import me.Niek1e.Freerunning.utilities.Players;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (GameState.isState(GameState.LOBBY)) 
			Game.setCanStart(Bukkit.getOnlinePlayers().size() - 1 > 1);
		
		Player player = event.getPlayer();
		
		Players.removePlayer(player);
		
		event.setQuitMessage("");
		ChatUtilities.broadcast(player.getName() + " heeft het spel verlaten!");
	}

}
