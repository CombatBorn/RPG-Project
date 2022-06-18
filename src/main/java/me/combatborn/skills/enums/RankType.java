package me.combatborn.skills.enums;

import me.combatborn.RPGProject;

public enum RankType {
    COMBAT("Combat"),
    GATHERING("Gathering"),
    CRAFTING("Crafting");

    String name;
    SkillType[] skillTypes;

    RankType(String name) {
        this.name = name;
    }

    public SkillType[] getSkills(){
        if (this.name.equals("Combat")){
            return RPGProject.RANK_SKILLS.get(RankType.COMBAT);
        }else if (this.name.equals("Gathering")){
            return RPGProject.RANK_SKILLS.get(RankType.GATHERING);
        }else if (this.name.equals("Crafting")){
            return RPGProject.RANK_SKILLS.get(RankType.CRAFTING);
        }
        return null;
    }

    public String getName() {
        return name;
    }
}

