package me.supermaxman.battlestats.executors;

import me.supermaxman.battlestats.BattleStats;

import org.bukkit.entity.Player;

public class StatsMainExecutor extends BaseExecutor {
    @Override
    protected void run(Player player, String[] args) {
        if (args.length > 0) {
        	StatsLookupExecutor.lookupStats(player, args[0]);
        } else if (args.length == 0) {
        	StatsPlayerExecutor.personalStats(player);
        }
    }

    public StatsMainExecutor(BattleStats XE) {
        super(XE);
    }
}
