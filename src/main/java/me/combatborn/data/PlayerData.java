package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.Rank;
import me.combatborn.skills.Skill;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillData;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

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

    private final HashMap<SkillData, Skill> skills = new HashMap<>();

    public PlayerData(Player player) {
        RPGProject.playerData.put(player.getUniqueId(), this);
        this.PLAYER = player;
        this.UUID = player.getUniqueId();

        // retrieve the player's SQL data and load it to server memory
        this.dataLoaded = loadPlayerData(PlayerDataManager.retrieveSQLData(this));

        this.level = PlayerDataManager.calculatePlayerLevel(this);
    }

    private boolean loadPlayerData(ResultSet results) {

        // the server failed to retrieve the data
        if (results == null) {
            return false;
        }
        try {
            this.firstLogin = results.getDate("first_login");
            this.monsterKills = results.getInt("monster_kills");
            this.bossKills = results.getInt("boss_kills");
            this.combatRank = new Rank(this, RankType.COMBAT, results.getInt("combat_experience"), results.getInt("combat_points"));
            this.gatheringRank = new Rank(this, RankType.GATHERING, results.getInt("gathering_experience"), results.getInt("gathering_points"));
            this.craftingRank = new Rank(this, RankType.CRAFTING, results.getInt("crafting_experience"), results.getInt("crafting_points"));

            for (SkillData skillData : SkillData.values()) {
                skills.put(skillData, new Skill(skillData, this, results.getInt(skillData.getName().toLowerCase(Locale.ROOT))));
                PLAYER.sendMessage("Loaded skill " + skillData.getName() + " level: " + skills.get(skillData).getSkillLevel());
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public Date getFirstLogin() {
        return firstLogin;
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

    public int getPlayTime() {
        return playTime;
    }

    public Skill getSkill(SkillData skillData){
        return this.skills.get(skillData);
    }
}
