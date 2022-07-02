package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class Skill {

    private final SkillType skillType;
    private final PlayerData playerData;
    private final Rank rank;
    private final Player player;
    private final String name;
    private final boolean elite;

    private int level;

    public Skill(SkillType skillType, PlayerData playerData, int level) {
        this.skillType = skillType;
        this.name = this.skillType.getName();
        this.elite = this.skillType.isElite();
        this.level = level;
        this.playerData = playerData;
        this.rank = determineRank();
        this.player = playerData.getPLAYER();
    }

    public boolean applyPoints(int amount) {

        // elite skills require other skills to be level 100 before leveling up
        if (!hasRequirementsToLevelUp()) {
            player.sendMessage(this.name + " is an elite skill which you haven't unlocked yet. ", "Requires Level 100 in: " + ChatColor.GOLD + Arrays.toString(this.skillType.getRequiredSkills()));
            return false;
        }

        // check if the desired level would surpass 100
        if (this.level + amount > 100) {
            player.sendMessage("Unable to apply points which would exceed level 100 in this skill.");
            return false;
        }

        // checks if player has the amount of points specified
        if ((this.elite && this.rank.getPoints() < amount * 2) || (!this.elite && this.rank.getPoints() < amount)) {
            player.sendMessage("You have " + this.rank.getPoints() + " available " + this.rank.getType().getName() + " points. Unable to apply " + amount + " points to the " + this.name + " skill.");
            return false;
        }

        // all checks pass, apply points to the skill and reduce total points
        this.level += amount;
        this.rank.removePoints(this.elite, amount);
        player.sendMessage((amount > 1 ? "" + amount + " l" : "L") + "evel up" + (amount > 1 ? "s" : "") + "! Reached " + this.name + " skill level " + this.level);
        return true;
    }

    // elite skills require other skills to be level 100 before leveling up
    private boolean hasRequirementsToLevelUp() {
        if (!elite) {
            return true;
        }

        for (SkillType skillType : this.skillType.getRequiredSkills()) {

            // this will check whether or not they meet the requirement of the skill
            if (this.playerData.getSkill(skillType).getLevel() < 100) {
                return false;
            }
        }

        return true;
    }

    private Rank determineRank() {
        if (this.skillType.getRankType() == RankType.COMBAT) {
            return this.playerData.getCombatRank();
        } else if (this.skillType.getRankType() == RankType.GATHERING) {
            return this.playerData.getGatheringRank();
        } else if (this.skillType.getRankType() == RankType.CRAFTING) {
            return this.playerData.getCraftingRank();
        }
        return null;
    }

    public String getName() {
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

    public int getLevel() {
        return level;
    }
}
