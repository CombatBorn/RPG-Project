package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.enums.SkillData;
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


            // otherwise manually store into SQL
        } else {

            // temporary SQL queries,
            // will add to separate thread with a queue system to prevent
            // strain on the main thread

            try {
                RPGProject.SQL.getConnection().prepareStatement("UPDATE `player_data` SET " +
                        "`play_time`=" + data.getPlayTime() + ",`monster_kills`=" + data.getMonsterKills() + ",`boss_kills`=" + data.getBossKills() + "," +
                        "`combat_experience`=" + data.getCombatRank().getExperience() + ",`combat_points`=" + data.getCombatRank().getPoints() + "," +
                        "`gathering_experience`=" + data.getGatheringRank().getExperience() + ",`gathering_points`=" + data.getCombatRank().getPoints() + "," +
                        "`crafting_experience`=" + data.getCraftingRank().getExperience() + ",`crafting_points`=" + data.getCombatRank().getPoints() + "," +
                        "`health`=" + data.getSkill(SkillData.HEALTH).getSkillLevel() + ",`speed`=" + data.getSkill(SkillData.SPEED).getSkillLevel() + "," +
                        "`melee`=" + data.getSkill(SkillData.MELEE).getSkillLevel() + ",`strength`=" + data.getSkill(SkillData.STRENGTH).getSkillLevel() + "," +
                        "`archery`=" + data.getSkill(SkillData.ARCHERY).getSkillLevel() + ",`precision`=" + data.getSkill(SkillData.PRECISION).getSkillLevel() + "," +
                        "`magic`=" + data.getSkill(SkillData.MAGIC).getSkillLevel() + ",`focus`=" + data.getSkill(SkillData.FOCUS).getSkillLevel() + "," +
                        "`cloaking`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`transcripting`=" + data.getSkill(SkillData.TRANSCRIPTING).getSkillLevel() + "," +
                        "`darkmagic`=" + data.getSkill(SkillData.DARKMAGIC).getSkillLevel() + ",`summoning`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`thieving`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`taming`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`hunting`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`fishing`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`mining`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`lumberjacking`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`farming`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`enchanting`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`deepfishing`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`breeding`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`soulcapturing`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`forging`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`leatherworking`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`woodworking`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`weaving`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`cooking`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`firecreation`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`glassblowing`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`crystalreading`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`building`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`alchemy`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`divinecreation`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + "," +
                        "`infernalforging`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + ",`soulcrafting`=" + data.getSkill(SkillData.CLOAKING).getSkillLevel() + " " +
                        "WHERE uuid=`\"" + data.getUUID().toString() + "\"`");
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