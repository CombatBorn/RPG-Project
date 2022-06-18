package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.entity.Player;


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

    public boolean applyPoints() {
        if (this.level == 100) {
            return false;
        }

        if (!hasRequirementsToLevelUp()) {
            player.sendMessage("You haven't unlocked the " + this.name + " skill yet.");
            return false;
        }

        // checks if player has enough points to level the skill
        if ((this.elite && this.rank.getPoints() < 2) || this.rank.getPoints() < 1) {
            player.sendMessage("You don't have enough " + this.rank.getType().getName() + " points to level up the " + this.name + " skill.");
            return false;
        }
        this.level++;
        this.rank.removeRankPoints(this.elite);
        player.sendMessage("Level up! Reached " + this.name + " skill level " + this.level);
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
