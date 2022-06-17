package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;


public class Skill {

    private SkillType skillType;
    private PlayerData playerData;
    private Rank rank;
    private Player player;
    private String skillName;
    private boolean eliteSkill;

    private int skillLevel;

    public Skill(SkillType skillType, PlayerData playerData, int skillLevel) {
        this.skillType = skillType;
        this.skillName = this.skillType.getName();
        this.eliteSkill = this.skillType.isElite();
        this.skillLevel = skillLevel;
        this.playerData = playerData;
        this.rank = determineRank();
        this.player = playerData.getPLAYER();
    }

    public boolean applyPoints() {
        if (this.skillLevel == 100) {
            return false;
        }

        if (!hasRequirementsToLevelUp()) {
            player.sendMessage("You haven't unlocked the " + this.skillName + " skill yet.");
            return false;
        }

        // checks if player has enough points to level the skill
        if ((this.eliteSkill && this.rank.getPoints() < 2) || this.rank.getPoints() < 1) {
            player.sendMessage("You don't have enough points to level up the " + this.skillName + " skill.");
            return false;
        }
        this.skillLevel++;
        this.rank.removeRankPoints(this.eliteSkill);
        player.sendMessage("Level up! Reached " + this.skillName + " skill level " + this.skillLevel);
        return true;
    }

    // elite skills require other skills to be level 100 before leveling up
    private boolean hasRequirementsToLevelUp() {
        if (!eliteSkill) {
            return true;
        }

        for (SkillType skillType : this.skillType.getRequiredSkills()) {

            // this will check whether or not they meet the requirement of the skill
            if (this.playerData.getSkill(skillType).getSkillLevel() < 100) {
                return false;
            }
        }

        return true;
    }

    private Rank determineRank() {
        Bukkit.getLogger().info("The current Skill is: " + this.skillType.getName());
        Bukkit.getLogger().info("Rank type for the " + this.skillName + " skill is: " + this.skillType.getRankType());
        if (this.skillType.getRankType() == RankType.COMBAT) {
            return this.playerData.getCombatRank();
        } else if (this.skillType.getRankType() == RankType.GATHERING) {
            return this.playerData.getGatheringRank();
        } else if (this.skillType.getRankType() == RankType.CRAFTING) {
            return this.playerData.getCraftingRank();
        }
        return null;
    }

    public String getSkillName() {
        return this.skillType.getName();
    }

    public String getSkillAcronym() {
        return this.skillType.getAcronym();
    }

    public boolean isElite() {
        return this.skillType.isElite();
    }

    public RankType getSkillType() {
        return this.skillType.getRankType();
    }

    public SkillType[] getRequiredSkills() {
        return this.skillType.getRequiredSkills();
    }

    public int getSkillLevel() {
        return skillLevel;
    }
}
