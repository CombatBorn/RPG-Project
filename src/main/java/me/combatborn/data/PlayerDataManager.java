package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataManager {

    // player level is the average of their Combat, Gathering, and Crafting ranks.
    protected static double calculatePlayerLevel(PlayerData data) {

        // an error will occur if the data is not loaded
        if (!data.isDataLoaded()) {
            return -1.0;
        }

        return (data.getCombatRank().getLevel() + data.getGatheringRank().getLevel() + data.getCraftingRank().getLevel()) / 3;
    }

    //Attempts to store all player's data to the SQL
    public static void storePlayerData(PlayerData data) {

        // do nothing if data is not loaded
        if (!data.isDataLoaded()) {
            return;
        }

        // calculate total time spent online
        Bukkit.getLogger().info(data.getPLAYER().getDisplayName() + " was online for " + data.getSessionLength() + "s");
        data.updatePlayTime();

        // data that needs to be stored
        String sqlCommand = "UPDATE `player_data` SET " +
                "`play_time`=" + data.getPlayTime() + ",`monster_kills`=" + data.getMonsterKills() + ",`boss_kills`=" + data.getBossKills() + "," +
                "`combat_experience`=" + data.getCombatRank().getExperience() + ",`combat_points`=" + data.getCombatRank().getPoints() + "," +
                "`gathering_experience`=" + data.getGatheringRank().getExperience() + ",`gathering_points`=" + data.getGatheringRank().getPoints() + "," +
                "`crafting_experience`=" + data.getCraftingRank().getExperience() + ",`crafting_points`=" + data.getCraftingRank().getPoints() + "," +
                "`health`=" + data.getSkill(SkillType.HEALTH).getLevel() + ",`speed`=" + data.getSkill(SkillType.SPEED).getLevel() + "," +
                "`melee`=" + data.getSkill(SkillType.MELEE).getLevel() + ",`strength`=" + data.getSkill(SkillType.STRENGTH).getLevel() + "," +
                "`archery`=" + data.getSkill(SkillType.ARCHERY).getLevel() + ",`precision`=" + data.getSkill(SkillType.PRECISION).getLevel() + "," +
                "`magic`=" + data.getSkill(SkillType.MAGIC).getLevel() + ",`focus`=" + data.getSkill(SkillType.FOCUS).getLevel() + "," +
                "`cloaking`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`transcripting`=" + data.getSkill(SkillType.TRANSCRIPTING).getLevel() + "," +
                "`darkmagic`=" + data.getSkill(SkillType.DARKMAGIC).getLevel() + ",`summoning`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`thieving`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`taming`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`hunting`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`fishing`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`mining`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`lumberjacking`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`farming`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`enchanting`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`deepfishing`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`breeding`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`soulcapturing`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`forging`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`leatherworking`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`woodworking`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`weaving`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`cooking`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`firecreation`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`glassblowing`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`crystalreading`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`building`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`alchemy`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`divinecreation`=" + data.getSkill(SkillType.CLOAKING).getLevel() + "," +
                "`infernalforging`=" + data.getSkill(SkillType.CLOAKING).getLevel() + ",`soulcrafting`=" + data.getSkill(SkillType.CLOAKING).getLevel() + " " +
                "WHERE uuid=\"" + data.getUUID().toString() + "\"";


        // if server reboot, store all of the data to local files instead
        // next server startup needs to update the SQL with this data
        if (RPGProject.REBOOT) {

            File rebootData = new File(RPGProject.PLUGINS_FOLDER_PATH.getPath() + "/"+ data.getUUID() +".txt");

            Bukkit.getLogger().info(rebootData.getPath());

            try {
                rebootData.createNewFile();
                FileWriter fileWriter = new FileWriter(rebootData);
                fileWriter.write(sqlCommand);
                fileWriter.close();
                Bukkit.getLogger().info(data.getPLAYER().getDisplayName() + "'s data was successfully written to local files");
            } catch (IOException e) {
                Bukkit.getLogger().info(data.getPLAYER().getDisplayName() + "'s data failed to write to local files");
                e.printStackTrace();
            }


            // otherwise manually store into SQL
        } else {

            // temporary SQL query,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            try {

                // sends the updated data to the SQL
                RPGProject.SQL.getConnection().prepareStatement(sqlCommand).executeUpdate();
                Bukkit.broadcastMessage(data.getPLAYER().getDisplayName() + "'s data successfully stored to the database.");

                // data was successfully stored, clear data from server memory
                RPGProject.PLAYER_DATA.remove(data.getUUID());

            } catch (SQLException e) {
                Bukkit.broadcastMessage(data.getPLAYER().getDisplayName() + "'s data failed to store to the database.");
                e.printStackTrace();
            }

        }

    }


    // attempts to load all player's data from the SQL
    protected static ResultSet retrieveSQLData(PlayerData data) {

        // there has already been a check to ensure the player's data isn't loaded yet

        ResultSet results;
        try {

            // temporary SQL queries,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            // retrieves the player's data from player_data in the database
            results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUUID() + "\"").executeQuery();

            // check if the data is found
            if (!results.next()) {

                // data not found, this is the player's first login!
                // store the player's uuid to the database, this automatically generates
                // their login date, stats, and skills on the database (level 0)
                RPGProject.SQL.getConnection().prepareStatement("INSERT INTO player_data (uuid) values (\"" + data.getUUID() + "\")").executeUpdate();

                // retrieves the generated data from the database
                results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUUID() + "\"").executeQuery();

                // point the ResultSet to the only result found
                results.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // persistent database data was successfully queried or generated and is ready to be shipped around :)
        Bukkit.broadcastMessage(data.getPLAYER().getDisplayName() + "'s data successfully loaded.");
        return results;

    }

}