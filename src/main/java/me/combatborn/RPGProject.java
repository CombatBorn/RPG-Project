package me.combatborn;

import me.combatborn.commands.Test;
import me.combatborn.data.LoginListener;
import me.combatborn.data.PlayerData;
import me.combatborn.data.PlayerDataManager;
import me.combatborn.database.MySQL;
import me.combatborn.items.RPGItem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class RPGProject extends JavaPlugin {

    public static RPGProject MAIN_CLASS;
    public static MySQL SQL;

    public static boolean reboot;

    public static HashMap<Integer, RPGItem> rpgItems = new HashMap<>();
    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    @Override
    public void onEnable() {
        reboot = false;
        MAIN_CLASS = this;
        SQL = new MySQL("local");

        //update SQL to all data found in the local files

//        getCommand("RandomTeleport").setExecutor(new RandomTeleport());

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getCommand("Test").setExecutor(new Test());

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

    //loads all items from the database to server memory (rpgItems HashMap)
    private void loadRPGItems() {

    }

    public static PlayerData getPlayerData(Player player) {
        return RPGProject.playerData.getOrDefault(player.getUniqueId(), null);
    }

    public static boolean hasPlayerData(Player player){
        return RPGProject.playerData.containsKey(player.getUniqueId());
    }

}
