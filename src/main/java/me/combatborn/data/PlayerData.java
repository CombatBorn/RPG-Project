package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.Rank;
import me.combatborn.skills.Skill;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerData {

    private final boolean dataLoaded;

    private final Player PLAYER;
    private final UUID UUID;

    private final double level;
    private Rank combatRank;
    private Rank gatheringRank;
    private Rank craftingRank;

    private Date firstLogin;
    private int playTime;
    private int monsterKills;
    private int bossKills;

    // needed to find out the time spent online
    private int loginTime;

    private final HashMap<SkillType, Skill> skills = new HashMap<>();

    public PlayerData(Player player) {
        RPGProject.playerData.put(player.getUniqueId(), this);
        this.PLAYER = player;
        this.UUID = player.getUniqueId();
        this.loginTime = (int) (System.currentTimeMillis() / 1000);

        // retrieve the player's SQL data and load it to server memory
        this.dataLoaded = loadPlayerData(PlayerDataManager.retrieveSQLData(this));

        this.level = PlayerDataManager.calculatePlayerLevel(this);
    }

    private boolean loadPlayerData(ResultSet results) {

        // the server failed to retrieve the data
        if (results == null) {
            return false;
        }

        // define member variables based on data retrieved from the SQL
        try {
            this.firstLogin = results.getDate("first_login");
            this.playTime = results.getInt("play_time");
            this.monsterKills = results.getInt("monster_kills");
            this.bossKills = results.getInt("boss_kills");
            this.combatRank = new Rank(this, RankType.COMBAT, results.getInt("combat_experience"), results.getInt("combat_points"));
            this.gatheringRank = new Rank(this, RankType.GATHERING, results.getInt("gathering_experience"), results.getInt("gathering_points"));
            this.craftingRank = new Rank(this, RankType.CRAFTING, results.getInt("crafting_experience"), results.getInt("crafting_points"));

            // stores an instance of each skill as an object in a hashmap
            // easily access the instance through using the proper SkillType enum
            for (SkillType skillType : SkillType.values()) {
                skills.put(skillType, new Skill(skillType, this, results.getInt(skillType.getName().toLowerCase(Locale.ROOT))));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void displayAllSkillLevels() {
        ArrayList<String> levels = new ArrayList<>();
        for (SkillType skillType : SkillType.values()) {
            levels.add(skillType.getName() + "[" + skills.get(skillType).getSkillLevel() + "]");
        }
        this.PLAYER.sendMessage(this.PLAYER.getDisplayName() + "'s Levels: " + levels);
    }

    // how long the player has been online in seconds
    public int getSessionLength() {
        return (int) (System.currentTimeMillis() / 1000 - this.loginTime);
    }

    public int getRealPlayTime() {
        return this.playTime + getSessionLength();
    }

    public void updatePlayTime() {
        this.playTime += getSessionLength();
    }

    public Player getPLAYER() {
        return PLAYER;
    }

    public UUID getUUID() {
        return UUID;
    }

    public double getLevel() {
        return level;
    }

    public Rank getCombatRank() {
        return combatRank;
    }

    public Rank getGatheringRank() {
        return gatheringRank;
    }

    public Rank getCraftingRank() {
        return craftingRank;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public int getMonsterKills() {
        return monsterKills;
    }

    public int getBossKills() {
        return bossKills;
    }

    public Date getFirstLogin() {
        return firstLogin;
    }

    public double getPlayTime() {
        return playTime;
    }

    public double getLoginTime() {
        return loginTime;
    }

    public Skill getSkill(SkillType skillType) {
        return this.skills.get(skillType);
    }
}
