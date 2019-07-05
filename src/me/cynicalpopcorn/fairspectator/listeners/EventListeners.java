/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.fairspectator.listeners;

import me.cynicalpopcorn.fairspectator.Main;
import me.cynicalpopcorn.fairspectator.models.PlayerLocationYML;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Nicole
 */
public class EventListeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {       
        //Get joining player
        Player joinedPlayer = event.getPlayer();
        
        //Make them to ensure their file exists
        Main.getInstance().createPlayerLocation(joinedPlayer.getUniqueId().toString(), joinedPlayer.getName(), joinedPlayer.getLocation().getX(), joinedPlayer.getLocation().getY(), joinedPlayer.getLocation().getZ(), joinedPlayer.getLocation().getWorld().getUID().toString(), joinedPlayer.getLocation().getPitch(), joinedPlayer.getLocation().getYaw());
    }
}
