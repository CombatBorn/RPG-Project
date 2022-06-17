package me.combatborn.skills;

import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.RankType;

public class Rank {

    private PlayerData playerData;

    private int rank;
    private RankType rankType;
    private int experience;
    private int points;

    public Rank(PlayerData playerData, RankType rankType, int experience, int points) {
        this.playerData = playerData;
        this.rankType = rankType;
        this.experience = experience;
        this.points = points;
        this.rank = calculateRank();
    }

    private int calculateRank() {
        return this.experience / 1000;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public double getRank() {
        return rank;
    }

    public int getExperience() {
        return experience;
    }

    public int getPoints() {
        return points;
    }

    public void removeRankPoints(boolean elite) {
        if (elite) {
            this.points -= 2;
        } else {
            this.points -= 1;
        }
    }
}