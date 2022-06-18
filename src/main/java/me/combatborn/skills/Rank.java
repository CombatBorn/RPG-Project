package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;

public class Rank {

    private PlayerData playerData;

    private RankType type;

    private int level;
    private int experience;
    private int points;

    public Rank(PlayerData playerData, RankType type, int experience, int points) {
        this.playerData = playerData;
        this.type = type;
        this.experience = experience;
        this.points = points;
        this.level = calculateRank();
    }

    private int calculateRank() {
        return calculateRank(this.experience);
    }

    private int calculateRank(int experience) {

        //an algorithm will go here to determine the Rank Level
        return experience / 1000;
    }

    public void addExperience(int gainedExperience) {
        int newLevel = calculateRank(this.experience + gainedExperience);
        if (level < newLevel) {
            playerData.getPLAYER().sendMessage("You have advanced from " + this.type.getName() + " Rank Level " + this.level + " to " + newLevel + "!");
            this.points += newLevel - this.level;
            this.level = newLevel;
        }else{
            playerData.getPLAYER().sendMessage("You earned "+gainedExperience+" experience toward your next "+ this.type.getName() +" level.");
        }
        this.experience += gainedExperience;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getPoints() {
        return points;
    }

    public RankType getType() {
        return type;
    }

    public void removeRankPoints(boolean elite, int amount) {
        if (elite) {
            this.points -= 2 * amount;
        } else {
            this.points -= amount;
        }
    }
}