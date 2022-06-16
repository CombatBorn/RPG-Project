package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.Rank;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataManager {

    //player level is the average of their Combat, Gathering, and Crafting ranks.
    protected static double calculatePlayerLevel(PlayerData data) {
        //An error will occur if the data is not loaded
        if (!data.isDataLoaded()) {
            return -1.0;
        }
        return (data.getCombatRank().getRank() + data.getGatheringRank().getRank() + data.getCraftingRank().getRank()) / 3;
    }

    //Attempts to store all player's data to the SQL
    public static void storePlayerData(PlayerData data) {

        //do nothing if data is not loaded
        if (!data.isDataLoaded()) {
            return;
        }

        //if server reboot, store all of the data to local files instead
        //next server startup needs to update the SQL with this data
        if (RPGProject.reboot) {


        //Otherwise manually store into SQL
        } else {


        }

        //data was successfully stored, clear data from memory
        RPGProject.playerData.remove(data.getUuid());

    }


    //Attempts to load all player's data from the SQL
    protected static boolean loadPlayerData(PlayerData data) {

        if (data.isDataLoaded()) {
            //kick the player, they must wait for their data to unload before logging in
            return false;
        }

        ResultSet results;
        try {

            // temporary SQL queries,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUuid() + "\"").executeQuery();
            if (!results.next()) {
                RPGProject.SQL.getConnection().prepareStatement("INSERT INTO player_data (uuid) values (\"" + data.getUuid() + "\")").executeUpdate();
                results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUuid() + "\"").executeQuery();
            }
            data.setCombatRank(new Rank(data, results.getInt("combat_experience"), results.getInt("combat_points")));
            data.setGatheringRank(new Rank(data, results.getInt("gathering_experience"), results.getInt("gathering_points")));
            data.setCraftingRank(new Rank(data, results.getInt("crafting_experience"), results.getInt("crafting_points")));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        // data was successfully loaded
        Bukkit.broadcastMessage(data.getPlayer().getDisplayName()+ "'s data successfully loaded.");
        return true;

    }

}
