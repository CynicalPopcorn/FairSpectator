/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.fairspectator.commands;

import me.cynicalpopcorn.fairspectator.Main;
import me.cynicalpopcorn.fairspectator.models.PlayerLocationYML;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nicole
 */
public class CommandSpectator implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //Some basic validation
        if(!cmnd.getName().equals("spectator")) {
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
            if (pl.getGameMode() != GameMode.SURVIVAL) {
                cs.sendMessage(String.format("%sYou must be in survival to run this command.", ChatColor.RED));
                return true;
            }
            
            //Get the state of the player
            PlayerLocationYML loc = Main.getInstance().getPlayerLocation(pl.getUniqueId().toString());
            
            //Save their location
            loc.setPlayerCoordinates(pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), pl.getLocation().getWorld().getUID().toString(), pl.getLocation().getPitch(), pl.getLocation().getYaw());
            loc.savePlayerFile();
            
            //Now spectator them
            pl.setGameMode(GameMode.SPECTATOR);
            cs.sendMessage(String.format("%sYou have entered spectator mode. Use /survival to return.", ChatColor.AQUA));
            return true;
        } else {
            cs.sendMessage(String.format("%sYou must run this command as a player.", ChatColor.RED));
            return true;
        }
    }
}
