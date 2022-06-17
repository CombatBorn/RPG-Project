package me.combatborn.commands;

import me.combatborn.RPGProject;
import me.combatborn.data.PlayerData;
import me.combatborn.skills.Skill;
import me.combatborn.skills.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;

public class LevelUp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("This command can only be used by a player.");
            return false;
        }

        // command requires 1 argument
        if (args.length != 1) {
            return false;
        }

        Player player = (Player) sender;

        // player will be kicked if server fails to retrieve their playerData
        PlayerData playerData = RPGProject.getPlayerData(player);

        // player's data was not found
        if (playerData == null) {
            return false;
        }

        // check if the skill entered is a valid skill
        String skillName = args[0].toLowerCase(Locale.ROOT);
        if (!RPGProject.allSkills.containsKey(skillName.toLowerCase(Locale.ROOT))) {
            player.sendMessage(skillName + " is not a valid skill.");
            player.sendMessage("Valid Skills: " + Arrays.toString(SkillType.values()));
            return false;
        }

        // attempt to level up a skill of your choice
        playerData.getSkill(RPGProject.allSkills.get(skillName)).applyPoints();
        return true;
    }
}
