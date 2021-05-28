package com.csn_server.mcmmotags;

import java.io.File;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author SrNox_
 */
public class Main extends JavaPlugin {
//com.csn_server.mcmmotags
    
    public static Main plugin;
    
    
    @Override
    public void onEnable() {
        plugin = this;
        configFiles();
        
        Config.carregar();
        
        getServer().getPluginManager().registerEvents(new Events(), this);
    }
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
    
    private void configFiles() {
        File dbFiles = new File (getDataFolder(), "database.yml");
        if(!dbFiles.exists()) {
            this.saveResource("database.yml", false);
        }
        
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    
}
