package me.combatborn.data;

import me.combatborn.RPGProject;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {

    private boolean dataLoaded = false;

    private Player player;
    private UUID uuid;

    private int unspentCombatPoints;
    private int unspentCraftingPoints;
    private int unspentGatheringPoints;

    public PlayerData(Player player) {
        RPGProject.playerData.put(player.getUniqueId(), this);
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    //Attempts to load all player's data from the SQL
    public void loadPlayerData() {

        if (this.dataLoaded){
            //kick the player, they must wait for their data to unload before logging in
            return;
        }



        //data was successfully loaded
        this.dataLoaded = true;

    }

    //Attempts to store all player's data to the SQL
    public void storePlayerData() {

        //do nothing if data is not loaded
        if (!this.dataLoaded) {
            return;
        }

        //if server reboot, store all of the data to local files instead
        //next server startup needs to update the SQL with this data
        if (RPGProject.reboot) {


        //Otherwise manually store into SQL
        } else {


        }

        //unload complete, clear data from memory if there's no reboot
        if (!RPGProject.reboot){
            RPGProject.playerData.remove(this.uuid);
        }

        //data was successfully stored
        this.dataLoaded = false;

    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }
}
