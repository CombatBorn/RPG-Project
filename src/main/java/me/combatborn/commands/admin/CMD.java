package me.combatborn.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class CMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can modify the custom model data of a held item.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendUsage(player);
            return false;
        }

        int customModelData;

        try {
            customModelData = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sendUsage(player);
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.GOLD + "You must hold an item in hand to run this command.");
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);

        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage("Usage: /cmd <custom model data>");
    }

}
