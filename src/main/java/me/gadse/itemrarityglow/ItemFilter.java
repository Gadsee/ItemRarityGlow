package me.gadse.itemrarityglow;

import me.gadse.itemrarityglow.teams.GlowColorTeam;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFilter {

    private final String filterName;
    private final String displayNameFilter;
    private final String loreFilter;
    private final GlowColorTeam glowColorTeam;

    public ItemFilter(ItemRarityGlow plugin, String configKey) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("filters." + configKey);
        if (configurationSection == null)
            throw new IllegalArgumentException("The config key " + configKey + " is not specified under filters.");

        String displayNameFilter = configurationSection.getString("displayName");
        if (displayNameFilter != null)
            displayNameFilter = ChatColor.translateAlternateColorCodes('&', displayNameFilter);
        this.displayNameFilter = displayNameFilter;

        String loreFilter = configurationSection.getString("lore");
        if (loreFilter != null)
            loreFilter = ChatColor.translateAlternateColorCodes('&', loreFilter);
        this.loreFilter = loreFilter;

        String filterName = configurationSection.getString("name");
        if (filterName != null)
            filterName = ChatColor.translateAlternateColorCodes('&', filterName);
        this.filterName = filterName == null ? loreFilter : filterName;

        this.glowColorTeam = GlowColorTeam.valueOf(configurationSection.getString("color"));
    }

    public boolean matchesItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return false;

        if (displayNameFilter != null && !itemMeta.getDisplayName().contains(displayNameFilter))
            return false;

        if (loreFilter == null ^ itemMeta.getLore() == null)
            return false;

        if (loreFilter != null && itemMeta.getLore() != null) {
            for (String line : itemMeta.getLore()) {
                if (line.contains(loreFilter))
                    return true;
            }
            return false;
        }

        return true;
    }

    public String getFilterName() {
        return filterName;
    }

    public GlowColorTeam getGlowColorTeam() {
        return glowColorTeam;
    }
}
