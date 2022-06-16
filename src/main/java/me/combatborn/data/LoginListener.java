package me.combatborn.data;

import me.combatborn.RPGProject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LoginListener implements Listener {

    //retrieve data from the SQL and store to the PlayerData object
    @EventHandler
    public void login(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerData playerData = RPGProject.getPlayerData(player);
        if (playerData != null && !playerData.isDataLoaded()) {
            //kick the player, data is already loaded
            //consider adding a check to see if data is currently being unloaded
            return;
        }
        //store the instance of the player's data within the playerData hashmap
        RPGProject.playerData.put(uuid, new PlayerData(player));
    }

    //store data from the PlayerData object into the SQL
    @EventHandler
    public void logout(PlayerQuitEvent event) {

        //the storePlayerData() method is automatically called for all PlayerData objects upon server reboot
        if (RPGProject.reboot) {
            return;
        }

        Player player = event.getPlayer();
        PlayerData playerData = RPGProject.getPlayerData(player);
        if (playerData == null) {
            return;
        }

        //store data to SQL
        PlayerDataManager.storePlayerData(playerData);

    }
}
