/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.fairspectator.models;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.cynicalpopcorn.fairspectator.Main;

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
    private double X;
    private double Y;
    private double Z;
    private String worldUID;
    private float pitch;
    private float yaw;
    /*
    Constructor for object creation programmatically
    */
    public PlayerLocationYML(String UUID, String playerName, double x, double y, double z, String worldUID, float pitch, float yaw) {
        this.UUID = UUID;
        this.playerName = playerName;
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.worldUID = worldUID;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    /*
    Constructor for object creation via deserialization
    */
    public PlayerLocationYML(Map<String, Object> map) {
        UUID = (String) map.get("UUID");
        playerName = (String) map.get("playerName");
        X = (double) map.get("X");
        Y = (double) map.get("Y");
        Z = (double) map.get("Z");
        worldUID = (String) map.get("worldUID");
        pitch = (float) map.get("pitch");
        yaw = (float) map.get("yaw");
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
        map.put("worldUID", worldUID);
        map.put("pitch", pitch);
        map.put("yaw", yaw);
        
        //Return the map
        return map;
    }
    
    /*
    Sets the co-ordinates for the player
    */
    public void setPlayerCoordinates(double x, double y, double z, String worldUID, float pitch, float yaw) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.worldUID = worldUID;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    /**
     * Get the co-ordinates stored for the player
     * @return List ordered X, Y, Z 
     */
    public List<Double> getPlayerCoordinates() {
        return Arrays.asList(this.X, this.Y, this.Z);
    }
    
    /**
     * Get the viewpoint stored for the player
     * @return List ordered pitch, yaw
     */
    public List<Float> getViewpoint() {
        return Arrays.asList(this.pitch, this.yaw);
    }
    
    /**
     * Get the WorldUID
     * @return String for the WorldUID
     */
    public String getPlayerWorldUID() {
        return this.worldUID;
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
