package me.combatborn.skills.enums;

import me.combatborn.skills.Skill;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SkillType {

    // normal skills

    // combat rank
    HEALTH("Health", "HP", RankType.COMBAT, false),
    SPEED("Speed", "SPE", RankType.COMBAT, false),
    MELEE("Melee", "MEL", RankType.COMBAT, false),
    STRENGTH("Strength", "STR", RankType.COMBAT, false),
    ARCHERY("Archery", "ARCH", RankType.COMBAT, false),
    PRECISION("Precision", "PRE", RankType.COMBAT, false),
    MAGIC("Magic", "MAG", RankType.COMBAT, false),
    FOCUS("Focus", "FOC", RankType.COMBAT, false),

    // gathering rank
    THIEVING("Thieving", "THI", RankType.GATHERING, false),
    TAMING("Taming", "TAM", RankType.GATHERING, false),
    HUNTING("Hunting", "HUNT", RankType.GATHERING, false),
    FISHING("Fishing", "FISH", RankType.GATHERING, false),
    MINING("Mining", "MIN", RankType.GATHERING, false),
    LUMBERJACKING("Lumberjacking", "LUM", RankType.GATHERING, false),
    FARMING("Farming", "FARM", RankType.GATHERING, false),
    ENCHANTING("Enchanting", "ENCH", RankType.GATHERING, false),

    // crafting rank
    FORGING("Forging", "FOR", RankType.CRAFTING, false),
    LEATHERWORKING("Leatherworking", "LEAW", RankType.CRAFTING, false),
    WOODWORKING("Woodworking", "WOOW", RankType.CRAFTING, false),
    WEAVING("Weaving", "WEA", RankType.CRAFTING, false),
    COOKING("Cooking", "COO", RankType.CRAFTING, false),
    FIRECREATION("Firecreation", "FIRC", RankType.CRAFTING, false),
    GLASSBLOWING("Glassblowing", "GLAB", RankType.CRAFTING, false),
    CRYSTALREADING("Crystalreading", "CRYR", RankType.CRAFTING, false),
    BUILDING("Building", "BUI", RankType.CRAFTING, false),

    // elite skills

    // crafting rank
    ALCHEMY("Alchemy", "ALC", RankType.CRAFTING, true, SkillType.COOKING),
    DIVINECREATION("Divinecreation", "DIVC", RankType.CRAFTING, true, SkillType.PRECISION, SkillType.WEAVING, SkillType.LEATHERWORKING),
    INFERNALFORGING("Infernalforging", "INFF", RankType.CRAFTING, true, SkillType.STRENGTH, SkillType.FIRECREATION, SkillType.FORGING),
    SOULCRAFTING("Soulcrafting", "SOCR", RankType.CRAFTING, true, SkillType.FOCUS, SkillType.CRYSTALREADING, SkillType.GLASSBLOWING),

    // gathering rank
    DEEPFISHING("Deepfishing", "DEPF", RankType.GATHERING, true, SkillType.FISHING, SkillType.HEALTH),
    BREEDING("Breeding", "BRE", RankType.GATHERING, true, SkillType.ARCHERY, SkillType.TAMING),
    SOULCAPTURING("Soulcapturing", "SOCA", RankType.GATHERING, true, SkillType.MAGIC, SkillType.HUNTING, SkillType.THIEVING),

    // combat rank
    CLOAKING("Cloaking", "CLO", RankType.COMBAT, true, SkillType.SPEED, SkillType.THIEVING),
    TRANSCRIPTING("Transcripting", "TRA", RankType.COMBAT, true, SkillType.PRECISION, SkillType.FOCUS),
    DARKMAGIC("Darkmagic", "DMAG", RankType.COMBAT, true, SkillType.MAGIC, SkillType.ENCHANTING, SkillType.SPEED),
    SUMMONING("Summoning", "SUM", RankType.COMBAT, true, SkillType.DARKMAGIC, SkillType.SOULCAPTURING, SkillType.SOULCRAFTING);

    private final String name;
    private final String acronym;
    private final boolean elite;
    private final RankType rankType;
    private final SkillType[] requiredSkills;

    SkillType(String name, String acronym, RankType rankType, boolean elite, SkillType...requriedSkills) {
        this.name = name;
        this.acronym = acronym;
        this.rankType = rankType;
        this.elite = elite;
        this.requiredSkills = requriedSkills;
    }

    public String getName() {
        return name;
    }

    public String getAcronym() {
        return acronym;
    }

    public boolean isElite() {
        return elite;
    }

    public RankType getRankType() {
        return rankType;
    }

    public SkillType[] getRequiredSkills() {
        return this.requiredSkills;
    }
}
