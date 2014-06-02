package me.supermaxman.battlestats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.supermaxman.battlestats.executors.StatsMainExecutor;
import me.supermaxman.battlestats.utils.StatsUser;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleStats extends JavaPlugin {
	
    public static Logger log;
    public static FileConfiguration conf;
    private final BattleStatsListener Listener = new BattleStatsListener(this);
    public static Chat chat = null;
    public static BattleStats plugin;
	public static Permission permission = null;
	
    public static final HashMap<String, StatsUser> statsUserMap = new HashMap<String, StatsUser>();
    public static final ArrayList<String> combat = new ArrayList<String>();
    public static int alertNumber;
    public static String alertPermission;
    
    @Override
    public void onDisable() {
    	saveStats();
        log.info("Disabled.");

    }


    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        conf = getConfig();
        if (!setupPermissions()) {
        	log.severe("Vault fail!");
            this.setEnabled(false);
            return;
        }
        if (!setupChat()) {
            log.severe("Vault fail!");
            this.setEnabled(false);
            return;
        }
		loadStats();
        getServer().getPluginManager().registerEvents(Listener, this);
        log.info("All systems go! Version:" + this.getDescription().getVersion());
        saveConfig();
        getCommand("stats").setExecutor(new StatsMainExecutor(this));
        getCommand("accuracy").setExecutor(new StatsMainExecutor(this));

    }
    public void loadStats() {
    	 if (!conf.contains("settings.alertpermission")) {
             conf.set("settings.alertpermission", "battlestats.alert.accuracy");
         }
         if (!conf.contains("settings.alertpercent")) {
             conf.set("settings.alertpercent", "95");
         }
         
         try {
			if (conf.isConfigurationSection("players")) {
		           for (Map.Entry<String, Object> entry : conf.getConfigurationSection("players").getValues(false).entrySet()) {
		               ConfigurationSection cs = conf.getConfigurationSection("players." + entry.getKey() +".hitmiss");
		               int h = Integer.parseInt(cs.getString("hits"));
		               int s = Integer.parseInt(cs.getString("swings"));
		               statsUserMap.put(entry.getKey(), new StatsUser(entry.getKey(), h, s));
		           }
			}
			alertNumber = Integer.parseInt(conf.getString("settings.alertpercent"));
			alertPermission = conf.getString("settings.alertpermission");

         } catch (Exception e) {
			log.warning("[" + plugin.getName() + "] Stats are invalid in config.yml! Could not load the values.");
         }
    }
    public void saveStats() {
    	for(StatsUser s : statsUserMap.values()) {
    		System.out.println(s.getUserName());
    		s.save();
    	}
    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatRegisteredServiceProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatRegisteredServiceProvider != null) {
            chat = chatRegisteredServiceProvider.getProvider();
            return true;
        }
        return false;
    }
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
            return true;
        }
        return false;
    }
}
