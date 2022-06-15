package me.combatborn.skills.enums;

import java.util.List;

public enum SkillData {

    //normal skills

    // combat rank
    HEALTH("Healthpoints", "HP", SkillType.COMBAT, false),
    SPEED("Speed", "SPE", SkillType.COMBAT, false),
    MELEE("Melee", "MEL", SkillType.COMBAT, false),
    STRENGTH("Strength", "STR", SkillType.COMBAT, false),
    ARCHERY("Archery", "ARCH", SkillType.COMBAT, false),
    PRECISION("Precision", "PRE", SkillType.COMBAT, false),
    MAGIC("Magic", "MAG", SkillType.COMBAT, false),
    FOCUS("Focus", "FOC", SkillType.COMBAT, false),

    // gathering rank
    THIEVING("Thieving", "THI", SkillType.GATHERING, false),
    TAMING("Taming", "TAM", SkillType.GATHERING, false),
    HUNTING("Hunting", "HUNT", SkillType.GATHERING, false),
    FISHING("Fishing", "FISH", SkillType.GATHERING, false),
    MINING("Mining", "MIN", SkillType.GATHERING, false),
    LUMBERJACKING("Lumberjacking", "LUM", SkillType.GATHERING, false),
    FARMING("Farming", "FARM", SkillType.GATHERING, false),
    ENCHANTING("Enchanting", "ENCH", SkillType.GATHERING, false),

    // crafting rank
    FORGING("Forging", "FOR", SkillType.CRAFTING, false),
    LEATHERWORKING("Leatherworking", "LEAW", SkillType.CRAFTING, false),
    WOODWORKING("Woodworking", "WOOW", SkillType.CRAFTING, false),
    WEAVING("Weaving", "WEA", SkillType.CRAFTING, false),
    COOKING("Cooking", "COO", SkillType.CRAFTING, false),
    FIRECREATION("Firecreation", "FIRC", SkillType.CRAFTING, false),
    GLASSBLOWING("Glassblowing", "GLAB", SkillType.CRAFTING, false),
    CRYSTALREADING("Crystalreading", "CRYR", SkillType.CRAFTING, false),
    BUILDING("Building", "BUI", SkillType.CRAFTING, false),

    //elite skills

    // crafting rank
    ALCHEMY("Alchemy", "ALC", SkillType.CRAFTING, true, List.of(SkillData.COOKING)),
    DIVINECREATION("Divinecreation", "DIVC", SkillType.CRAFTING, true, List.of(SkillData.PRECISION, SkillData.WEAVING, SkillData.LEATHERWORKING)),
    INFERNALFORGING("Infernalforging", "INFF", SkillType.CRAFTING, true, List.of(SkillData.STRENGTH, SkillData.FIRECREATION, SkillData.FORGING)),
    SOULCRAFTING("Soulcrafting", "SOCR", SkillType.CRAFTING, true, List.of(SkillData.FOCUS, SkillData.CRYSTALREADING, SkillData.GLASSBLOWING)),

    // gathering rank
    DEEPFISHING("Deepfishing", "DEPF", SkillType.GATHERING, true, List.of(SkillData.FISHING, SkillData.HEALTH)),
    BREEDING("Breeding", "BRE", SkillType.GATHERING, true, List.of(SkillData.ARCHERY, SkillData.TAMING)),
    SOULCAPTURING("Soulcapturing", "SOCA", SkillType.GATHERING, true, List.of(SkillData.MAGIC, SkillData.HUNTING, SkillData.THIEVING)),

    // combat rank
    CLOAKING("Cloaking", "CLO", SkillType.COMBAT, true, List.of(SkillData.SPEED, SkillData.THIEVING)),
    TRANSCRIPTING("Transcripting", "TRA", SkillType.COMBAT, true, List.of(SkillData.PRECISION, SkillData.FOCUS)),
    DARKMAGIC("Darkmagic", "DMAG", SkillType.COMBAT, true, List.of(SkillData.MAGIC, SkillData.ENCHANTING, SkillData.SPEED)),
    SUMMONING("Summoning", "SUM", SkillType.COMBAT, true, List.of(SkillData.DARKMAGIC, SkillData.SOULCAPTURING, SkillData.SOULCRAFTING));

    private String name, acronym;
    private boolean elite;
    private SkillType skillType;
    private List<SkillData> requiredSkills[];

    SkillData(String name, String acronym, SkillType skillType, boolean elite, List<SkillData>... requriedSkills) {
        this.name = name;
        this.acronym = acronym;
        this.skillType = skillType;
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

    public SkillType getRankType() {
        return skillType;
    }

    public List<SkillData>[] getRequiredSkills(){
        return this.requiredSkills;
    }
}
