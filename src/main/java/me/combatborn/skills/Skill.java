package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillData;
import org.bukkit.entity.Player;

import java.util.List;


public class Skill {

    private SkillData skillData;
    private PlayerData playerData;
    private Rank rank;
    private Player player;
    private String skillName;
    private boolean eliteSkill;

    private int skillLevel;

    public Skill(SkillData skillData, PlayerData playerData, int skillLevel) {
        this.skillData = skillData;
        this.skillName = this.skillData.getName();
        this.eliteSkill = this.skillData.isElite();
        this.skillLevel = skillLevel;
        this.playerData = playerData;
        this.rank = determineRank();
        this.player = playerData.getPLAYER();
    }

    public boolean levelUp() {
        if (this.skillLevel == 100) {
            return false;
        }

        // if the player doesn't have enough points
//        if ((this.eliteSkill && this.rank.getPoints() < 2) || this.rank.getPoints() < 1) {
//            player.sendMessage("You don't have enough points to level up the " + this.skillName + " skill");
//            return false;
//        }
        this.skillLevel++;
        this.rank.removeRankPoint(this.eliteSkill);
        player.sendMessage("Level up! Reached " + this.skillName + " skill level " + this.skillLevel);
        return true;
    }

    private Rank determineRank() {
        if (this.skillData.getRankType() == RankType.COMBAT) {
            return this.playerData.getCombatRank();
        } else if (this.skillData.getRankType() == RankType.GATHERING) {
            return this.playerData.getGatheringRank();
        } else if (this.skillData.getRankType() == RankType.CRAFTING) {
            return this.playerData.getCraftingRank();
        }
        return null;
    }

    public String getSkillName() {
        return this.skillData.getName();
    }

    public String getSkillAcronym() {
        return this.skillData.getAcronym();
    }

    public boolean isElite() {
        return this.skillData.isElite();
    }

    public RankType getSkillType() {
        return this.skillData.getRankType();
    }

    public List<SkillData>[] getRequiredSkills() {
        return this.skillData.getRequiredSkills();
    }

    public int getSkillLevel() {
        return skillLevel;
    }
}
