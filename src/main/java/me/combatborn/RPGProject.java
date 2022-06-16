package me.combatborn;

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

    public static boolean reboot = false;

    public static HashMap<Integer, RPGItem> rpgItems;
    public static HashMap<UUID, PlayerData> playerData;

    @Override
    public void onEnable() {
        MAIN_CLASS = this;
        SQL = new MySQL("local");

        //update SQL to all data found in the local files

//        getCommand("RandomTeleport").setExecutor(new RandomTeleport());

        getServer().getPluginManager().registerEvents(new LoginListener(), this);

    }

    @Override
    public void onDisable() {
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

}
