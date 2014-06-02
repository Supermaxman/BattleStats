package me.supermaxman.battlestats.executors;

import me.supermaxman.battlestats.BattleStats;
import me.supermaxman.battlestats.utils.StatsUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StatsLookupExecutor {
	@SuppressWarnings("deprecation")
	public static void lookupStats(Player player, String name){
		if(BattleStats.plugin.getServer().getPlayer(name)!=null){
			StatsUtils.sendPlayerInfo(player, BattleStats.plugin.getServer().getPlayer(name));

		}else{
			player.sendMessage(ChatColor.RED+"[BattleStats]: No Player Found.");
		}
		
	}
}
