package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.Niek1e.Freerunning.Freerunning;

public class StartCountdown implements Runnable {

	Freerunning plugin;

	public StartCountdown(Freerunning pl) {
		plugin = pl;
	}

	private static void updateTimeSigns() {
		List<String> lns = new ArrayList<String>();
		lns.add("Tijd tot begin:");
		lns.add(ChatColor.GREEN + "" + timeUntilStart + "s");
		lns.add("Spelers online:");
		if (Game.canStart()) {
			lns.add(ChatColor.GREEN + "" + Players.getAllPlayers().size() + " speler(s)");
		} else {
			lns.add(ChatColor.RED + "" + Players.getAllPlayers().size() + " speler(s)");
		}

		Freerunning.updateSigns(lns);
	}

	public static int timeUntilStart;

	public void run() {
		if (timeUntilStart == 0) {
			if (!Game.canStart()) {
				plugin.restartCountdown();
				Bukkit.broadcastMessage(
						Freerunning.PREFIX + "Het spel kan nu niet gestart worden, de timer wordt gereset!");
				return;
			}
			Game.start();
			plugin.stopCountdown();
		}

		if (timeUntilStart % 10 == 0 || timeUntilStart <= 10) {
			Bukkit.broadcastMessage(
					Freerunning.PREFIX + "Het spel begint over " + String.valueOf(timeUntilStart) + " seconden!");
		}

		updateTimeSigns();

		timeUntilStart -= 1;
	}
}
