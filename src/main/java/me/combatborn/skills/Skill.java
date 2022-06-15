package me.combatborn.skills;

public abstract class Skill {

    private String name;

    private int level;
    private int experience;

    private boolean elite;
    private RankType rankType;


    public void setRankType(RankType rankType) {
        this.rankType = rankType;
    }

    public boolean levelUp() {
        if (this.level == 100) {
            return false;
        }
        this.level++;
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }
}
