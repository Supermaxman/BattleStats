package me.supermaxman.battlestats;

import me.supermaxman.battlestats.utils.StatsUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BattleStatsListener implements Listener {
    final BattleStats plugin;

    public BattleStatsListener(BattleStats plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        StatsUtils.getStatsUser(event.getPlayer());
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        StatsUtils.getStatsUser(event.getPlayer()).save();
        
    }
    
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player && !event.isCancelled()) {
            Player player = (Player) event.getDamager();
            if(!BattleStats.combat.contains(player.getName())) {
            	StatsUtils.addSwing(player);
            }else {
            	BattleStats.combat.remove(player.getName());
            }
            StatsUtils.addHit(player);
        }
    }
    
    
    @EventHandler
    public void onSwing(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            StatsUtils.addSwing(event.getPlayer());
            BattleStats.combat.add(event.getPlayer().getName());
        }
	}
}
