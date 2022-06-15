package me.combatborn.skills;

import me.combatborn.skills.enums.SkillType;
import me.combatborn.skills.enums.SkillData;

import java.util.List;


public class Skill {

    private SkillData skillData;

    private int level;
    private int experience;


    public Skill(SkillData skillData, int level, int experience){
        this.skillData = skillData;
        this.level = level;
        this.experience = experience;
    }

    public boolean levelUp() {
        if (this.level == 100) {
            return false;
        }
        this.level++;
        return true;
    }

    public String getSkillName(){
        return this.skillData.getName();
    }

    public String getSkillAcronym(){
        return this.skillData.getAcronym();
    }

    public boolean isBoolean(){
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
