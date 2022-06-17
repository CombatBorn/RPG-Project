package me.combatborn.skills.enums;

import java.util.HashMap;
import java.util.List;

public enum RankType {
    COMBAT("Combat", SkillType.HEALTH, SkillType.SPEED, SkillType.MELEE,
            SkillType.STRENGTH, SkillType.ARCHERY, SkillType.PRECISION, SkillType.MAGIC,
            SkillType.FOCUS, SkillType.CLOAKING, SkillType.TRANSCRIPTING, SkillType.DARKMAGIC,
            SkillType.SUMMONING),
    GATHERING("Gathering", SkillType.THIEVING, SkillType.TAMING, SkillType.HUNTING,
            SkillType.FISHING, SkillType.MINING, SkillType.LUMBERJACKING, SkillType.FARMING,
            SkillType.ENCHANTING, SkillType.DEEPFISHING, SkillType.BREEDING, SkillType.SOULCAPTURING),
    CRAFTING("Crafting", SkillType.FORGING, SkillType.LEATHERWORKING,
            SkillType.WOODWORKING, SkillType.WEAVING, SkillType.COOKING, SkillType.FIRECREATION,
            SkillType.GLASSBLOWING, SkillType.CRYSTALREADING, SkillType.BUILDING, SkillType.ALCHEMY,
            SkillType.DIVINECREATION, SkillType.INFERNALFORGING, SkillType.SOULCRAFTING);

    String typeName;
    SkillType[] skillTypes;

    RankType(String typeName, SkillType...allSkills) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public SkillType[] getSkillTypes() {
        return skillTypes;
    }
}
