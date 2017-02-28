package me.Niek1e.Freerunning.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.material.Door;

import me.Niek1e.Freerunning.Freerunning;
import me.Niek1e.Freerunning.GameState;
import me.Niek1e.Freerunning.utilities.LocationUtilities;

public class PlayerEvents implements Listener {

	@EventHandler
	public void playerPreLogin(AsyncPlayerPreLoginEvent event) {
		if (!GameState.getState().canJoin()) {
			event.disallow(Result.KICK_OTHER, ChatColor.RED + "Op dit moment kan je niet joinen, probeer zo opnieuw!");
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Bukkit.broadcastMessage(Freerunning.PREFIX + event.getEntity().getName() + ChatColor.RED + " is gevallen!");
		event.setDeathMessage("");
		event.getDrops().clear();
		Freerunning.getInstance().getCurrentGame().removeActive(event.getEntity());
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
		event.getPlayer().updateInventory();
	}

	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event) {
		event.setHatching(false);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getPlayer().getItemInHand().getType() == Material.EGG) {
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.SPRUCE_DOOR) {
				if (event.getPlayer().hasPermission("freerunnen.deuren")) {
					Door door = (Door) event.getClickedBlock().getState().getData();
					if (door.isTopHalf()) {
						Door realdoor = (Door) event.getClickedBlock().getLocation().add(0, -1, 0).getBlock().getState()
								.getData();
						if (!realdoor.isOpen()) {
							event.getPlayer().sendMessage(
									Freerunning.PREFIX + ChatColor.RED + "Vergeet de deur niet te sluiten!");
						}
					} else {
						if (!door.isOpen()) {
							event.getPlayer().sendMessage(
									Freerunning.PREFIX + ChatColor.RED + "Vergeet de deur niet te sluiten!")
						}
					}
				} else {
					event.setCancelled(true);
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Freerunning.getInstance().getCurrentGame().addPlayer(player);

		Bukkit.broadcastMessage(Freerunning.PREFIX + player.getName() + " doet mee met het spel!");
		event.setJoinMessage("");

		LocationUtilities.teleportPlayer("Spawn", player);

		player.setCanPickupItems(false);

		player.getInventory().clear();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (GameState.isState(GameState.LOBBY)) {
			Freerunning.getInstance().getCurrentGame().canStart(Bukkit.getOnlinePlayers().size() - 1 > 1);
		}

		Player player = event.getPlayer();

		Freerunning.getInstance().getCurrentGame().removePlayer(player);

		event.setQuitMessage("");
		Bukkit.broadcastMessage(Freerunning.PREFIX + player.getName() + " heeft het spel verlaten!");
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Location to = new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY() - 1,
				event.getTo().getZ());

		if (to.getBlock() == null || to.getBlock().getType() == null) {
			return;
		}

		if (!Freerunning.getInstance().getCurrentGame().getActivePlayers().contains(event.getPlayer())) {
			return;
		}

		if (!Freerunning.getInstance().getCurrentGame().hasStarted()) {
			return;
		}

		if (to.getBlock().getType() == Material.OBSIDIAN || to.getBlock().getType() == Material.GOLD_BLOCK) {
			if (to.getBlock().getType() == Material.OBSIDIAN) {

				event.getPlayer().setHealth(0);

			} else if (to.getBlock().getType() == Material.GOLD_BLOCK) {

				Freerunning.getInstance().getCurrentGame().stop(event.getPlayer());

			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if (GameState.isState(GameState.IN_GAME)) {
			event.setRespawnLocation(LocationUtilities.getFullLocation("Spectator"));
		} else {
			event.setRespawnLocation(LocationUtilities.getFullLocation("Spawn"));
		}
	}

}