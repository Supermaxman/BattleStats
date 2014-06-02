package me.supermaxman.battlestats.utils;

import me.supermaxman.battlestats.BattleStats;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StatsUtils {
	
	
	public static void addHit(Player p) {
		StatsUser s = getStatsUser(p);
	    
	    s.setHits(s.getHits()+1);
	    if(BattleStats.permission.has(p, BattleStats.alertPermission)) {
	    	if(Integer.parseInt(calculateHitMiss(p).replace("%", ""))>=BattleStats.alertNumber && !s.isAlert()) {
	            p.sendMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "BattleStats" + ChatColor.AQUA + "]" + ChatColor.AQUA + ": " + "Your accuracy is above "+ ChatColor.GOLD+BattleStats.alertNumber+ChatColor.AQUA+"!");
	            s.setAlert(true);
	    	}else if(Integer.parseInt(calculateHitMiss(p).replace("%", ""))<BattleStats.alertNumber) {
	            s.setAlert(false);
	    	}
	    }
	}
	
	public static void addSwing(Player p) {
		StatsUser s = getStatsUser(p);
	    
	    s.setSwings(s.getSwings()+1);
	}
	
    protected static String calculateHitMiss(Player p) {
    	StatsUser s = getStatsUser(p);
    	
        if (s.getSwings() != 0) {
            float hits = s.getHits();
            float swings = s.getSwings();
            float d = hits / swings;
            int percent = (int) (d * 100);
            
            return(percent + "%");
        } else {
            return("100%");
        }
        
    }
    
    public static void sendPlayerInfo(Player p) {    	
        String accuracy = calculateHitMiss(p);
        
        p.sendMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "BattleStats" + ChatColor.AQUA + "]" + ChatColor.AQUA + ": " + ColorUtils.getColoredName(p)+ ChatColor.AQUA + ":");
        
        p.sendMessage(ChatColor.AQUA + " - " + ChatColor.GOLD + "accuracy" + ChatColor.AQUA + ": " + ChatColor.GOLD + (accuracy));
        
    }
    
    public static void sendPlayerInfo(Player p, Player l) {
        String accuracy = calculateHitMiss(l);
        
        p.sendMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "BattleStats" + ChatColor.AQUA + "]" + ChatColor.AQUA + ": " + ColorUtils.getColoredName(l) + ChatColor.AQUA + ":");
        
        p.sendMessage(ChatColor.AQUA + " - " + ChatColor.GOLD + "accuracy" + ChatColor.AQUA + ": " + ChatColor.GOLD + (accuracy));
        
    }
    
    
    
    
    public static StatsUser getStatsUser(Player p){
    	if (BattleStats.statsUserMap.get(p.getName())==null){
    		BattleStats.statsUserMap.put(p.getName(), new StatsUser(p.getName(), 0,0));
    	}
		return BattleStats.statsUserMap.get(p.getName());
    }
    
}
