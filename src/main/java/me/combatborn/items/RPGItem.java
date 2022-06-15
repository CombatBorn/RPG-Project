package me.combatborn.items;

import me.combatborn.skills.Skill;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RPGItem {

    private int id;
    private String name;
    private String description;
    private ItemStack minecraftItem;
    private int customModelData;
    private ArrayList<Skill> requiredUseLevels;
    private ArrayList<Skill> requiredEquipLevels;
    private ArrayList<Skill> requiredConsumeLevels;
    private boolean canTrade;

    public RPGItem(int id, String name, String description, ItemStack minecraftItem,
                   int customModelData, ArrayList<Skill> requiredUseLevels,
                   ArrayList<Skill> requiredEquipLevels, ArrayList<Skill> requiredConsumeLevels,
                   boolean canTrade) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minecraftItem = minecraftItem;
        this.customModelData = customModelData;
        this.requiredUseLevels = requiredUseLevels;
        this.requiredEquipLevels = requiredEquipLevels;
        this.requiredConsumeLevels = requiredConsumeLevels;
        this.canTrade = canTrade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getMinecraftItem() {
        return minecraftItem;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public ArrayList<Skill> getRequiredUseLevels() {
        return requiredUseLevels;
    }

    public ArrayList<Skill> getRequiredEquipLevels() {
        return requiredEquipLevels;
    }

    public ArrayList<Skill> getRequiredConsumeLevels() {
        return requiredConsumeLevels;
    }

    public boolean isCanTrade() {
        return canTrade;
    }
}
