package me.combatborn.skills;

import me.combatborn.data.PlayerData;

public class Rank {

    private PlayerData playerData;

    private double rank;
    private int experience;
    private int points;

    public Rank(PlayerData playerData, int experience, int points) {
        this.playerData = playerData;
        this.experience = experience;
        this.points = points;
        calculateRank();
    }

    private void calculateRank(){
        this.rank = this.experience / 1000;
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
}
