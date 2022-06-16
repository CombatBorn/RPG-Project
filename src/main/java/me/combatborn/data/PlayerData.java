package me.combatborn.data;

import me.combatborn.RPGProject;
import me.combatborn.skills.Rank;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {

    private boolean dataLoaded = false;

    private Player player;
    private UUID uuid;

    private double level;
    private Rank combatRank;
    private Rank gatheringRank;
    private Rank craftingRank;

    public PlayerData(Player player) {
        RPGProject.playerData.put(player.getUniqueId(), this);
        this.dataLoaded = PlayerDataManager.loadPlayerData(this);
        this.level = PlayerDataManager.calculatePlayerLevel(this);
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getLevel() {
        return level;
    }

    public Rank getCombatRank() {
        return combatRank;
    }

    public void setCombatRank(Rank combatRank) {
        this.combatRank = combatRank;
    }

    public Rank getGatheringRank() {
        return gatheringRank;
    }

    public void setGatheringRank(Rank gatheringRank) {
        this.gatheringRank = gatheringRank;
    }

    public Rank getCraftingRank() {
        return craftingRank;
    }

    public void setCraftingRank(Rank craftingRank) {
        this.craftingRank = craftingRank;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }
}
