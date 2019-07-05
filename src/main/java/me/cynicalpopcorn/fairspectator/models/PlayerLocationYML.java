/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.me.cynicalpopcorn.fairspectator.models;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.me.cynicalpopcorn.fairspectator.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 * Serializable location for the player
 * @author Nicole
 */
@SerializableAs("pLocation")
public class PlayerLocationYML implements ConfigurationSerializable {
    //Class vars
    private String UUID;
    private String playerName;
    private float X;
    private float Y;
    private float Z;
    
    /*
    Constructor for object creation programmatically
    */
    public PlayerLocationYML(String UUID, String playerName, float x, float y, float z) {
        this.UUID = UUID;
        this.playerName = playerName;
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    /*
    Constructor for object creation via deserialization
    */
    public PlayerLocationYML(Map<String, Object> map) {
        UUID = (String) map.get("UUID");
        playerName = (String) map.get("playerName");
        X = (float) map.get("X");
        Y = (float) map.get("Y");
        Z = (float) map.get("Z");
    }
    
    @Override
    public Map<String, Object> serialize() {
        //Create hashmap
        Map<String, Object> map = new HashMap<String, Object>();
        
        //Put all the values in the map
        map.put("UUID", UUID);
        map.put("playerName", playerName);
        map.put("X", X);
        map.put("Y", Y);
        map.put("Z", Z);
        
        //Return the map
        return map;
    }
    
    /*
    Sets the co-ordinates for the player
    */
    public void setPlayerCoordinates(float x, float y, float z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    /**
     * Get the co-ordinates stored for the player
     * @return List ordered X, Y, Z 
     */
    public List<Float> getPlayerCoordinates() {
        return Arrays.asList(this.X, this.Y, this.Z);
    }
    
    /**
     * Saves the player file
     * @return true if successful save 
     */
    public boolean savePlayerFile() {
        //Setup the file config
        File playerFile = new File(new File(Main.getInstance().getDataFolder(), "players"), UUID + ".psave.yml");
        FileConfiguration playerConf = YamlConfiguration.loadConfiguration(playerFile);
        playerConf.set("playerlocationyml", this);
        
        //Atempt to save the file
        try {
            playerConf.save(playerFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().warning("Unable to serialize player: " + playerName);
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public boolean deletePlayerFile() {
        File playerFolder = new File(Main.getInstance().getDataFolder(), "players");
        if(!playerFolder.exists() || !playerFolder.isDirectory()) return false;
        
        File[] playerFilesList = playerFolder.listFiles(new FileFilter() {
            public boolean accept(File file) { return file.getName().contains(".psave.yml"); }
        });
        
        for (File playerFile : playerFilesList) {
            if (playerFile.getName().equals(UUID + ".psave.yml")) {
                return playerFile.delete();
            }
        }
        
        return false;
    }
}
