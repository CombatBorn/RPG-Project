package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;

public class Rank {

    private PlayerData playerData;

    private RankType type;

    private int rankLevel;
    private int experience;
    private int points;

    public Rank(PlayerData playerData, RankType type, int experience, int points) {
        this.playerData = playerData;
        this.type = type;
        this.experience = experience;
        this.points = points;
        this.rankLevel = calculateRank();
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
        if (rankLevel < newLevel) {
            playerData.getPLAYER().sendMessage("You have advanced from " + this.type.getName() + " Rank Level " + this.rankLevel + " to " + newLevel + "!");
            this.points += newLevel - this.rankLevel;
            this.rankLevel = newLevel;
        }else{
            playerData.getPLAYER().sendMessage("You earned "+gainedExperience+" experience toward your next "+ this.type.getName() +" level.");
        }
        this.experience += gainedExperience;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public int getRankLevel() {
        return rankLevel;
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

    public void removeRankPoints(boolean elite) {
        if (elite) {
            this.points -= 2;
        } else {
            this.points -= 1;
        }
    }
}