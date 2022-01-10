package me.gadse.itemrarityglow.commands;

import me.gadse.itemrarityglow.ItemRarityGlow;
import me.gadse.itemrarityglow.messages.Messages;
import me.gadse.itemrarityglow.messages.Pair;
import me.gadse.itemrarityglow.messages.RegexUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ItemRarityGlowCommand implements CommandExecutor {

    private final ItemRarityGlow plugin;

    public ItemRarityGlowCommand(ItemRarityGlow plugin) {
        this.plugin = plugin;

        PluginCommand pluginCommand = plugin.getCommand("itemrarityglow");
        if (pluginCommand != null)
            pluginCommand.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1)
            return false;

        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("itemrarityglow.reload")) {
            plugin.reloadConfig();
            Messages.RELOAD.send(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("togglepickup") && sender.hasPermission("itemrarityglow.togglepickup")) {
            if (!(sender instanceof Player))
                return true;

            Player player = (Player) sender;

            int id = player.getPersistentDataContainer().getOrDefault(plugin.getFilterKey(), PersistentDataType.INTEGER, 0);
            int nextId = plugin.getNextId(id);
            player.getPersistentDataContainer().set(plugin.getFilterKey(), PersistentDataType.INTEGER, nextId);

            Messages.PICKUP_FILTER.send(sender,
                    new Pair<>(RegexUtil.FILTER_NAME, plugin.getFilterById(nextId).getFilterName()));
            return true;
        }

        return true;
    }
}
