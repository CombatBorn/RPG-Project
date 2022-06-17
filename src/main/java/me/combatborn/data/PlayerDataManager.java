package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataManager {

    // player level is the average of their Combat, Gathering, and Crafting ranks.
    protected static double calculatePlayerLevel(PlayerData data) {

        // an error will occur if the data is not loaded
        if (!data.isDataLoaded()) {
            return -1.0;
        }

        return (data.getCombatRank().getRank() + data.getGatheringRank().getRank() + data.getCraftingRank().getRank()) / 3;
    }

    //Attempts to store all player's data to the SQL
    public static void storePlayerData(PlayerData data) {

        // do nothing if data is not loaded
        if (!data.isDataLoaded()) {
            return;
        }

        // if server reboot, store all of the data to local files instead
        // next server startup needs to update the SQL with this data
        if (RPGProject.reboot) {

            Bukkit.getLogger().info(data.getPLAYER().getDisplayName() + "'s data is being written to local files.");

            // otherwise manually store into SQL
        } else {

            // temporary SQL query,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            try {

                RPGProject.SQL.getConnection().prepareStatement("UPDATE `player_data` SET " +
                        "`play_time`=" + data.getPlayTime() + ",`monster_kills`=" + data.getMonsterKills() + ",`boss_kills`=" + data.getBossKills() + "," +
                        "`combat_experience`=" + data.getCombatRank().getExperience() + ",`combat_points`=" + data.getCombatRank().getPoints() + "," +
                        "`gathering_experience`=" + data.getGatheringRank().getExperience() + ",`gathering_points`=" + data.getCombatRank().getPoints() + "," +
                        "`crafting_experience`=" + data.getCraftingRank().getExperience() + ",`crafting_points`=" + data.getCombatRank().getPoints() + "," +
                        "`health`=" + data.getSkill(SkillType.HEALTH).getSkillLevel() + ",`speed`=" + data.getSkill(SkillType.SPEED).getSkillLevel() + "," +
                        "`melee`=" + data.getSkill(SkillType.MELEE).getSkillLevel() + ",`strength`=" + data.getSkill(SkillType.STRENGTH).getSkillLevel() + "," +
                        "`archery`=" + data.getSkill(SkillType.ARCHERY).getSkillLevel() + ",`precision`=" + data.getSkill(SkillType.PRECISION).getSkillLevel() + "," +
                        "`magic`=" + data.getSkill(SkillType.MAGIC).getSkillLevel() + ",`focus`=" + data.getSkill(SkillType.FOCUS).getSkillLevel() + "," +
                        "`cloaking`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`transcripting`=" + data.getSkill(SkillType.TRANSCRIPTING).getSkillLevel() + "," +
                        "`darkmagic`=" + data.getSkill(SkillType.DARKMAGIC).getSkillLevel() + ",`summoning`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`thieving`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`taming`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`hunting`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`fishing`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`mining`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`lumberjacking`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`farming`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`enchanting`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`deepfishing`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`breeding`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`soulcapturing`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`forging`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`leatherworking`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`woodworking`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`weaving`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`cooking`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`firecreation`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`glassblowing`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`crystalreading`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`building`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`alchemy`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`divinecreation`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + "," +
                        "`infernalforging`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + ",`soulcrafting`=" + data.getSkill(SkillType.CLOAKING).getSkillLevel() + " " +
                        "WHERE uuid=\"" + data.getUUID().toString() + "\"").executeUpdate();
                Bukkit.broadcastMessage(data.getPLAYER().getDisplayName() + "'s data successfully stored to the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        // data was successfully stored, clear data from server memory
        RPGProject.playerData.remove(data.getUUID());

    }


    // attempts to load all player's data from the SQL
    protected static ResultSet retrieveSQLData(PlayerData data) {

        // there has already been a check to ensure the player's data isn't loaded yet

        ResultSet results;
        try {

            // temporary SQL queries,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUUID() + "\"").executeQuery();
            if (!results.next()) {
                RPGProject.SQL.getConnection().prepareStatement("INSERT INTO player_data (uuid) values (\"" + data.getUUID() + "\")").executeUpdate();
                results = RPGProject.SQL.getConnection().prepareStatement("Select * from player_data where uuid=\"" + data.getUUID() + "\"").executeQuery();
                results.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // data was successfully loaded
        Bukkit.broadcastMessage(data.getPLAYER().getDisplayName() + "'s data successfully loaded.");
        return results;

    }

}