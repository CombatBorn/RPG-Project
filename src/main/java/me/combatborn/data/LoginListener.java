package me.combatborn.data;

import me.combatborn.RPGProject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Locale;
import java.util.UUID;

public class LoginListener implements Listener {

    // temporary listener, move later
    @EventHandler
    public void reworkReload(ServerCommandEvent event){
        if (event.getCommand().toLowerCase(Locale.ROOT).equals("rl")) {
            event.setCommand("reload confirm");
        }
    }

    @EventHandler
    public void damageMonster(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)){
            return;
        }

        Player player = (Player) event.getDamager();

        if (event.getEntity().isDead()){
            player.sendMessage("You kilt the mob");
        }

        // player will be kicked if server fails to retrieve their playerData
        PlayerData playerData = RPGProject.getPlayerData(player);

        // player's data was not found
        if (playerData == null){
            return;
        }

        playerData.getCombatRank().addExperience((int)(Math.random() * 1000));

    }

    // retrieve data from the SQL and store to the PlayerData object
    @EventHandler
    public void login(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        login(player);
    }

    public static void login(Player player){
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

        Player player = event.getPlayer();

        // player will be kicked if server fails to retrieve their playerData
        PlayerData playerData = RPGProject.getPlayerData(player);

        // if player's data is not found, there should be no data to save
        if (playerData == null){
            return;
        }

        // store data to SQL
        PlayerDataManager.storePlayerData(playerData);

    }
}
