package me.Niek1e.Freerunning.utilities;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import me.Niek1e.Freerunning.Freerunning;

public class StartCountdown extends BukkitRunnable {

	Freerunning plugin;

	public StartCountdown(Freerunning pl){
		plugin = pl;
	}
	
	private static void updateTimeSigns(){
		String ln1 = "Tijd tot begin:";
		String ln2 = ChatColor.GREEN + "" + timeUntilStart + "s";
		String ln3 = "Spelers online:";
		String ln4 = null;
		if(Game.canStart())
			ln4 = ChatColor.GREEN + "" + Players.getAllPlayers().size() + " speler(s)";
		else
			ln4 = ChatColor.RED + "" + Players.getAllPlayers().size() + " speler(s)";
		SignUtilities.updateSigns(ln1, ln2, ln3, ln4);
	}
	
	public static int timeUntilStart;

	public void run() {
		if (timeUntilStart == 0) {
			if(!Game.canStart()){
				plugin.restartCountdown();
				ChatUtilities.broadcast("Het spel kan nu niet gestart worden, de timer wordt gereset!");
				return;
			}
			Game.start();
			plugin.stopCountdown();
		}

		if (timeUntilStart % 10 == 0 || timeUntilStart <= 10) {
			ChatUtilities.broadcast("Het spel begint over " + String.valueOf(timeUntilStart) + " seconden!");
		}
		
		updateTimeSigns();

		timeUntilStart -= 1;
	}
}
