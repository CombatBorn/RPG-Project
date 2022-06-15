package me.combatborn.skills;

public class Magic extends Skill {

    public Magic(int experience, int level){
        super();
        super.setExperience(experience);
        super.setLevel(level);
        super.setRankType(RankType.COMBAT);
    }

}
