/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.me.cynicalpopcorn.fairspectator;

import java.io.File;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
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
        
        //TODO: Add commands
        
        
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
}
