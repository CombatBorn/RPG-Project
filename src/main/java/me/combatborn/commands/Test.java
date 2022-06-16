package me.combatborn.commands;

import me.combatborn.RPGProject;
import me.combatborn.data.PlayerData;
import me.combatborn.skills.enums.SkillData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("This command can only be used by a player.");
            return false;
        }
        Player player = (Player) sender;
        PlayerData playerData = RPGProject.getPlayerData(player);
        player.sendMessage(player.getDisplayName()+ "'s Stats:");
        player.sendMessage("  First Join: " + playerData.getFirstLogin().toString());
        player.sendMessage("  Combat Level: " + playerData.getCombatRank().getRank() + " ("+ playerData.getCombatRank().getExperience() +")");
        player.sendMessage("  Gathering Level: " + playerData.getCombatRank().getRank() + " ("+ playerData.getGatheringRank().getExperience() +")");
        player.sendMessage("  Crafting Level: " + playerData.getCombatRank().getRank() + " ("+ playerData.getCraftingRank().getExperience() +")");
        player.sendMessage("  Monster Kills: " + playerData.getMonsterKills());
        player.sendMessage("  Boss Kills: " + playerData.getBossKills());

        playerData.getSkill(SkillData.MAGIC).levelUp();



        return true;
    }
}
