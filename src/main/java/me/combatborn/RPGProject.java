package me.combatborn;

import me.combatborn.commands.LevelUp;
import me.combatborn.commands.Levels;
import me.combatborn.commands.Stats;
import me.combatborn.data.LoginListener;
import me.combatborn.data.PlayerData;
import me.combatborn.data.PlayerDataManager;
import me.combatborn.database.MySQL;
import me.combatborn.items.RPGItem;
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

    public static boolean reboot;

    public static HashMap<Integer, RPGItem> rpgItems = new HashMap<>();
    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static final HashMap<String, SkillType> allSkills = new HashMap<>();

    @Override
    public void onEnable() {
        reboot = false;
        MAIN_CLASS = this;
        SQL = new MySQL("local");
        PLUGINS_FOLDER_PATH = getDataFolder();

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getCommand("Stats").setExecutor(new Stats());
        getCommand("Levels").setExecutor(new Levels());
        getCommand("LevelUp").setExecutor(new LevelUp());

        // store data from all files found within the local files
        retrieveRebootData();

        // load all online player's data into memory
        for (Player player : Bukkit.getOnlinePlayers()){
            LoginListener.login(player);
        }

        // define all skills into a static HashMap
        for (SkillType skillType : SkillType.values()){
            RPGProject.allSkills.put(skillType.getName().toLowerCase(Locale.ROOT),skillType);
        }

    }

    @Override
    public void onDisable() {

        // this enables all playerData files to be stored on local files
        // instead of the SQL to prevent loss of data
        reboot = true;

        //store player data to server local files
        for (UUID uuid : RPGProject.playerData.keySet()) {
            PlayerDataManager.storePlayerData(RPGProject.playerData.get(uuid));
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

        if (!RPGProject.playerData.containsKey(player.getUniqueId())) {

            // fatal error, kick the player to prevent further issues
            player.kickPlayer("Your data failed to load, try logging in again or contact an administrator.");
            return null;
        }

        return RPGProject.playerData.get(player.getUniqueId());
    }

    public static boolean hasPlayerData(Player player) {
        return RPGProject.playerData.containsKey(player.getUniqueId());
    }

}
