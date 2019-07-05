/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.fairspectator.commands;

import java.util.List;
import java.util.UUID;
import me.cynicalpopcorn.fairspectator.Main;
import me.cynicalpopcorn.fairspectator.models.PlayerLocationYML;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nicole
 */
public class CommandSurvival implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //Some basic validation
        if(!cmnd.getName().equals("survival")) {
            return true;
        }
        
        if (args.length != 0) {
            cs.sendMessage(String.format("%sThis command requires no arguments.", ChatColor.RED));
            return true;
        }
        
        if (cs instanceof Player) {
            //Player variable
            Player pl = (Player) cs;
            
            //Ensure the player is in survival before using this command
            if (pl.getGameMode() != GameMode.SPECTATOR) {
                cs.sendMessage(String.format("%sYou must be in spectator to run this command.", ChatColor.RED));
                return true;
            }
            
            //Get the state of the player
            PlayerLocationYML loc = Main.getInstance().getPlayerLocation(pl.getUniqueId().toString());
            
            //Get their location
            List<Double> playerCoords = loc.getPlayerCoordinates();
            String worldUUID = loc.getPlayerWorldUID();
            List<Float> playerViewpoint = loc.getViewpoint();
            
            //Setup location
            Location target = new Location(Bukkit.getWorld(UUID.fromString(worldUUID)), playerCoords.get(0), playerCoords.get(1), playerCoords.get(2));
            
            //Set pitch and yaw
            target.setPitch(playerViewpoint.get(0));
            target.setYaw(playerViewpoint.get(1));
            
            //Teleport them first
            pl.teleport(target);
            
            //Set their gamemode
            pl.setGameMode(GameMode.SURVIVAL);
            //cs.sendMessage(String.format("%sYou have entered survival mode and returned to where you were when you last ran /spectator. Enjoy!", ChatColor.AQUA));
            return true;
        } else {
            cs.sendMessage(String.format("%sYou must run this command as a player.", ChatColor.RED));
            return true;
        }
    }
}
