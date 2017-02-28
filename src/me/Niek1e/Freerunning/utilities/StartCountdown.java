package me.Niek1e.Freerunning.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import me.Niek1e.Freerunning.Freerunning;

public class StartCountdown extends BukkitRunnable {

	Freerunning plugin;
	
	private static Game currentGame = new Game();
	
	public static Game getCurrentGame() {
		return currentGame;
	}
	
	public static void setCurrentGame(Game game) {
		currentGame = game;
	}

	public StartCountdown(Freerunning pl){
		plugin = pl;
	}
	
	private static void updateTimeSigns(){
		List<String> lns = new ArrayList<String>();
		lns.add("Tijd tot begin:");
		lns.add(ChatColor.GREEN + "" + timeUntilStart + "s");
		lns.add("Spelers online:");
		if(getCurrentGame().canStart()){
			lns.add(ChatColor.GREEN + "" + Players.getAllPlayers().size() + " speler(s)");
		}else{
			lns.add(ChatColor.RED + "" + Players.getAllPlayers().size() + " speler(s)");
		}
		
		SignUtilities.updateSigns(lns);
	}
	
	public static int timeUntilStart;

	public void run() {
		if (timeUntilStart == 0) {
			if(!getCurrentGame().canStart()){
				plugin.restartCountdown();
				Bukkit.broadcastMessage(Freerunning.getPrefix + "Het spel kan nu niet gestart worden, de timer wordt gereset!");
				return;
			}
			getCurrentGame().start();
			plugin.stopCountdown();
		}

		if (timeUntilStart % 10 == 0 || timeUntilStart <= 10) {
			Bukkit.broadcastMessage(Freerunning.getPrefix + "Het spel begint over " + String.valueOf(timeUntilStart) + " seconden!");
		}
		
		updateTimeSigns();

		timeUntilStart -= 1;
	}
}
