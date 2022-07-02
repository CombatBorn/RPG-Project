package me.combatborn;

import me.combatborn.commands.admin.CMD;
import me.combatborn.commands.player.LevelUp;
import me.combatborn.commands.player.Skills;
import me.combatborn.commands.player.Stats;
import me.combatborn.data.LoginListener;
import me.combatborn.data.PlayerData;
import me.combatborn.data.PlayerDataManager;
import me.combatborn.database.MySQL;
import me.combatborn.items.RPGItem;
import me.combatborn.skills.enums.RankType;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

public final class RPGProject extends JavaPlugin {

    public static RPGProject MAIN_CLASS;
    public static MySQL SQL;
    public static File PLUGINS_FOLDER_PATH;

    public static boolean REBOOT;

    public static HashMap<Integer, RPGItem> RPG_ITEMS = new HashMap<>();
    public static HashMap<UUID, PlayerData> PLAYER_DATA = new HashMap<>();

    public static final HashMap<String, SkillType> SKILLS = new HashMap<>();

    public static final HashMap<RankType, SkillType[]> RANK_SKILLS = new HashMap<>();

    @Override

    public void onEnable() {
        REBOOT = false;
        MAIN_CLASS = this;
        SQL = new MySQL("local");
        PLUGINS_FOLDER_PATH = getDataFolder();

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new LevelUp(), this);
        getCommand("Stats").setExecutor(new Stats());
        getCommand("Skills").setExecutor(new Skills());
        getCommand("LevelUp").setExecutor(new LevelUp());
        getCommand("CMD").setExecutor(new CMD());

        // store data from all files found within the local files
        retrieveRebootData();

        // load all online player's data into memory
        for (Player player : Bukkit.getOnlinePlayers()) {
            LoginListener.login(player);
        }

        // define all skills into a static HashMap
        for (SkillType skillType : SkillType.values()) {
            RPGProject.SKILLS.put(skillType.getName().toLowerCase(Locale.ROOT), skillType);
        }

        // define all skills for a rank into a static HashMap
        RANK_SKILLS.put(RankType.COMBAT, new SkillType[]{SkillType.HEALTH, SkillType.SPEED,
                SkillType.MELEE, SkillType.STRENGTH, SkillType.ARCHERY, SkillType.PRECISION,
                SkillType.MAGIC, SkillType.FOCUS, SkillType.CLOAKING, SkillType.TRANSCRIPTING,
                SkillType.DARKMAGIC, SkillType.SUMMONING});
        RANK_SKILLS.put(RankType.GATHERING, new SkillType[]{SkillType.THIEVING, SkillType.TAMING, SkillType.HUNTING,
                SkillType.FISHING, SkillType.MINING, SkillType.LUMBERJACKING, SkillType.FARMING,
                SkillType.ENCHANTING, SkillType.DEEPFISHING, SkillType.BREEDING, SkillType.SOULCAPTURING});
        RANK_SKILLS.put(RankType.CRAFTING, new SkillType[]{SkillType.FORGING, SkillType.LEATHERWORKING,
                SkillType.WOODWORKING, SkillType.WEAVING, SkillType.COOKING, SkillType.FIRECREATION,
                SkillType.GLASSBLOWING, SkillType.CRYSTALREADING, SkillType.BUILDING, SkillType.ALCHEMY,
                SkillType.DIVINECREATION, SkillType.INFERNALFORGING, SkillType.SOULCRAFTING});

    }

    @Override
    public void onDisable() {

        // this enables all playerData files to be stored on local files
        // instead of the SQL to prevent loss of data
        REBOOT = true;

        //store player data to server local files
        for (UUID uuid : RPGProject.PLAYER_DATA.keySet()) {
            PlayerDataManager.storePlayerData(RPGProject.PLAYER_DATA.get(uuid));
        }

    }

    public void retrieveRebootData() {

        // get all files found in the
        File[] rebootDataFiles = new File(PLUGINS_FOLDER_PATH.getPath()).listFiles();

        if (rebootDataFiles == null) {
            Bukkit.getLogger().info("[RPGProject] No reboot data was found");
            return;
        }

        Scanner scanner = null;
        String sqlData;
        int totalRetrieved = 0;
        ArrayList<File> filesToDelete = new ArrayList<>();

        // make a SQL query for the contents of each file then delete the file if successful
        for (File rebootDataFile : rebootDataFiles) {

            try {

                // the Scanner allows the file contents to be stored to the sqlData String
                scanner = new Scanner(rebootDataFile);
                sqlData = scanner.nextLine();
                scanner.close();

            } catch (FileNotFoundException e) {

                // no data was found within the file or the file was invalid
                Bukkit.getLogger().info("[RPGProject] There was an error reading file: " + rebootDataFile.getName());
                continue;

            }

            try {

                // temporary SQL query,
                // will add to separate thread with a queue system to prevent
                // strain on the main thread
                SQL.getConnection().prepareStatement(sqlData).executeUpdate();

                // data is no longer need to
                rebootDataFile.delete();

                totalRetrieved++;


            } catch (SQLException e) {
                Bukkit.getLogger().info("[RPGProject] There was an error uploading file contents to the database: " + rebootDataFile.getName());
            }
        }

        if (totalRetrieved > 0) {
            Bukkit.getLogger().info("[RPGProject] A total of " + totalRetrieved + " player's data was updated to the SQL");
        }

    }

    //loads all items from the database to server memory (rpgItems HashMap)
    private void loadRPGItems() {

    }

    public static PlayerData getPlayerData(Player player) {

        if (!RPGProject.PLAYER_DATA.containsKey(player.getUniqueId())) {

            // fatal error, kick the player to prevent further issues
            player.kickPlayer("Your data failed to load, try logging in again or contact an administrator.");
            return null;
        }

        return RPGProject.PLAYER_DATA.get(player.getUniqueId());
    }

    public static boolean hasPlayerData(Player player) {
        return RPGProject.PLAYER_DATA.containsKey(player.getUniqueId());
    }

}
