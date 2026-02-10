package com.arqobackpack.commands;

import com.arqobackpack.ArqoBackpack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BPCommand implements CommandExecutor {
    private final ArqoBackpack plugin;

    public BPCommand(ArqoBackpack plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            if (!player.hasPermission("arqobackpack.use")) {
                player.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("no-permission"));
                return true;
            }
            plugin.getListener().open(player, player);
        } else {
            if (!player.hasPermission("arqobackpack.open.others")) {
                player.sendMessage(plugin.getMsg("prefix") + plugin.getMsg("no-permission"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                plugin.getListener().open(player, target);
            } else {
                player.sendMessage("§cPemain tidak online.");
            }
        }
        return true;
    }
}

