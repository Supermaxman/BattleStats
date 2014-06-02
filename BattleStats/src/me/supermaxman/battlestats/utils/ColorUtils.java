package me.supermaxman.battlestats.utils;

import me.supermaxman.battlestats.BattleStats;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ColorUtils {

    public static String getColoredName(Player player){
    	if(BattleStats.chat!=null)return ChatColor.translateAlternateColorCodes('&', BattleStats.chat.getPlayerPrefix(player)) + player.getName();
    	return player.getName();
    }
    
}
