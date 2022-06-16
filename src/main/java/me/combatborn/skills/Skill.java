package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.SkillType;
import me.combatborn.skills.enums.SkillData;

import java.util.List;


public class Skill {

    private SkillData skillData;
    private PlayerData playerData;

    private int level;
    private int experience;


    public Skill(SkillData skillData, PlayerData playerData){
        this.skillData = skillData;
        this.playerData = playerData;
        calculateRankLevel();
    }

    public boolean levelUp() {
        if (this.level == 100) {
            return false;
        }
        this.level++;
        return true;
    }

    private int calculateRankLevel(){
        if (this.getSkillType().equals(SkillType.COMBAT)){
            this.experience = this.playerData.getCombatRank().getExperience();
        }else if (this.getSkillType().equals(SkillType.GATHERING)){

        }else if (this.getSkillType().equals(SkillType.CRAFTING)){

        }
        return -1;
    }

    public String getSkillName(){
        return this.skillData.getName();
    }

    public String getSkillAcronym(){
        return this.skillData.getAcronym();
    }

    public boolean isElite(){
        return this.skillData.isElite();
    }

    public SkillType getSkillType(){
        return this.skillData.getRankType();
    }

    public List<SkillData>[] getRequiredSkills(){
        return this.skillData.getRequiredSkills();
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
}
