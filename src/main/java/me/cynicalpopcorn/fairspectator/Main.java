/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.me.cynicalpopcorn.fairspectator;

import java.io.File;
import main.java.me.cynicalpopcorn.fairspectator.commands.CommandSpectator;
import main.java.me.cynicalpopcorn.fairspectator.listeners.EventListeners;
import main.java.me.cynicalpopcorn.fairspectator.models.PlayerLocationYML;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A plugin for spectating
 * @author Nicole
 */
public class Main extends JavaPlugin {
    //Instance
    private static Main instance;
    
    @Override
    public void onEnable() { 
        //Get instance
        instance = this;
        
        //Initialise folders
        File dataFolder = new File(this.getDataFolder(), "");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        
        File playerFolder = new File(this.getDataFolder(), "players");
        if (!playerFolder.exists()) {
            playerFolder.mkdir();
        }
        
        //Register serializable
        ConfigurationSerialization.registerClass(PlayerLocationYML.class, "pLocation");
        
        //Commands
        this.getCommand("spectator").setExecutor(new CommandSpectator());
        
        //Event listeners
        Bukkit.getServer().getPluginManager().registerEvents(new EventListeners(), this);
        
        //We ready
        getLogger().info("Enabled FairSpectator");
    }
    
    @Override
    public void onDisable() {
        //Ending it
        getLogger().info("Disabled FairSpectator.");
    }
    
    public static Main getInstance() {
        return instance;
    }
    
    public PlayerLocationYML getPlayerLocation(String UUID) {
        //If we're getting it, it should exist
        File file = new File(new File(instance.getDataFolder(), "players"), UUID + ".psave.yml");
        
        //Oh no
        if (!file.exists()) {
            getLogger().warning("Could not find file: " + file.getAbsolutePath());
            return null;
        }
        
        //Oh yes
        try {
            FileConfiguration playerConf = YamlConfiguration.loadConfiguration(file);
            Object playerState = playerConf.get("playerlocationyml");
            if (playerState instanceof PlayerLocationYML) {
                return (PlayerLocationYML) playerState;
            }
        } catch (IllegalArgumentException ex) {
            getLogger().warning(ex.getMessage());
        }
        
        return null;
    }
    
    public PlayerLocationYML createPlayerLocation(String UUID, String PlayerName, double x, double y, double z, String worldName) {
        //Check if it exists, if it does just return it
        PlayerLocationYML getCheck = getPlayerLocation(UUID);
        
        if (getCheck != null) {
            return getCheck;
        } else {
            PlayerLocationYML newPlayerLocation = new PlayerLocationYML(UUID, PlayerName, x, y, z, worldName);
            newPlayerLocation.savePlayerFile();
            return newPlayerLocation;
        }
    }
}
