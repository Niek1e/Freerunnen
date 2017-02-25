package me.Niek1e.Freerunning.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.Niek1e.Freerunning.GameState;

public class AsyncPlayerPreLogin implements Listener {

	@EventHandler
	public void playerPreLogin(AsyncPlayerPreLoginEvent event) {
		if (!GameState.getState().canJoin()) {
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "Op dit moment kan je niet joinen, probeer zo opnieuw!");
		}
	}
}
