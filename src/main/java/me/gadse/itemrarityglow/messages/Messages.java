package me.gadse.itemrarityglow.messages;

import me.gadse.itemrarityglow.ItemRarityGlow;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Messages {
    PREFIX (false),
    RELOAD (true),
    PICKUP_FILTER (true);

    private final boolean showPrefix;
    private String message;

    Messages(boolean showPrefix) {
        this.showPrefix = showPrefix;
    }

    public void loadMessage(ItemRarityGlow plugin) {
        String message = plugin.getConfig().getString("messages." + name().toLowerCase());
        if (message == null)
            return;

        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    @SafeVarargs
    public final void send(CommandSender sender, Pair<RegexUtil, Object>... placeholders) {
        String tempMessage = message;

        for (Pair<RegexUtil, Object> placeholder : placeholders)
            tempMessage = placeholder.getKey().replaceAll(tempMessage, placeholder.getValue().toString());

        sender.sendMessage((showPrefix ? PREFIX.getMessage() : "") + tempMessage);
    }

    public String getMessage() {
        return message;
    }
}
