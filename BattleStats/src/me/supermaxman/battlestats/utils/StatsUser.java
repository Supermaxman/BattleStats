package me.supermaxman.battlestats.utils;

import me.supermaxman.battlestats.BattleStats;

import org.bukkit.configuration.ConfigurationSection;



public class StatsUser {

    private String userName;
    
    private long hits;

    private long swings;

    private boolean alert = false;

    public StatsUser(String userName, long hits, long swings) {
        this.userName = userName;
        this.hits = hits;
        this.swings = swings;
        setupPlayerConfig(userName);
    }


    public String getUserName() {
        return userName;
    }
    

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getSwings() {
        return swings;
    }

    public void setSwings(long swings) {
        this.swings = swings;
    }
    
    public void save() {
        ConfigurationSection cs = BattleStats.conf.getConfigurationSection("players." + userName);
        cs.set(".hitmiss.hits", BattleStats.statsUserMap.get(userName).getHits());
        cs.set(".hitmiss.swings", BattleStats.statsUserMap.get(userName).getSwings());
        BattleStats.plugin.saveConfig();
    }
    
    protected static void calculateHitMiss(ConfigurationSection cs) {

        if (Integer.parseInt(cs.getString(".hitmiss.swings")) != 0) {
            double hits = Integer.parseInt(cs.getString(".hitmiss.hits"));
            double swings = Integer.parseInt(cs.getString(".hitmiss.swings"));
            double d = hits / swings;
            int percent = (int) (d * 100);
            cs.set("accuracy", percent + "%");
        } else {
            cs.set("accuracy", "100%");
        }
        BattleStats.plugin.saveConfig();
    }
    
    protected static void setupPlayerConfig(String s) {
        if (BattleStats.conf.getConfigurationSection("players." + s) == null) {
        	BattleStats.conf.set("players." + s + ".hitmiss.hits", "0");
        	BattleStats.conf.set("players." + s + ".hitmiss.swings", "0");
            BattleStats.plugin.saveConfig();
        }
    }


	public boolean isAlert() {
		return alert;
	}


	public void setAlert(boolean alert) {
		this.alert = alert;
	}
    
}
