package me.combatborn.skills.enums;

public enum RankType {
    COMBAT("Combat"),
    CRAFTING("Crafting"),
    GATHERING("Gathering");

    String name;

    RankType(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return name;
    }
}
