package me.supermaxman.battlestats.executors;

import me.supermaxman.battlestats.utils.StatsUtils;

import org.bukkit.entity.Player;

public class StatsPlayerExecutor {
	public static void personalStats(Player player){
		StatsUtils.sendPlayerInfo(player);
	}
}
