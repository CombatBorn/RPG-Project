package me.combatborn.data;

import me.combatborn.RPGProject;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Locale;
import java.util.UUID;

public class LoginListener implements Listener {

    // temporary, move later
    @EventHandler
    public void reworkReload(ServerCommandEvent event){
        if (event.getCommand().toLowerCase(Locale.ROOT).equals("rl")) {
            event.setCommand("reload confirm");
        }
    }


    //retrieve data from the SQL and store to the PlayerData object
    @EventHandler
    public void login(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        // if a player's data is found, it is not unloaded yet
        if (RPGProject.hasPlayerData(player)) {
            // kick player
            player.kickPlayer("Log in attempt too quick, try again shortly!");
            return;
        }
        //store the instance of the player's data within the playerData hashmap
        RPGProject.playerData.put(uuid, new PlayerData(player));
    }

    // store data from the PlayerData object into the SQL
    @EventHandler
    public void logout(PlayerQuitEvent event) {

        // the storePlayerData() method is automatically called for any PlayerData objects
        // found in the Main Class's HashMap
        if (RPGProject.reboot) {
            return;
        }

        // if player's data is not found, there should be no data to save
        Player player = event.getPlayer();
        if (!RPGProject.hasPlayerData(player)) {
            return;
        }
        PlayerData playerData = RPGProject.getPlayerData(player);

        //store data to SQL
        PlayerDataManager.storePlayerData(playerData);

    }
}
