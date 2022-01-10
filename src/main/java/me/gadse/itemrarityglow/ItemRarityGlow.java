package me.gadse.itemrarityglow;

import me.gadse.itemrarityglow.commands.ItemRarityGlowCommand;
import me.gadse.itemrarityglow.listeners.EntityPickupItem;
import me.gadse.itemrarityglow.listeners.ItemSpawn;
import me.gadse.itemrarityglow.messages.Messages;
import me.gadse.itemrarityglow.teams.GlowColorTeam;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemRarityGlow extends JavaPlugin {

    private final List<ItemFilter> itemFilters = new ArrayList<>();
    private NamespacedKey filterKey;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists() && !getDataFolder().mkdirs())
            return;
        saveDefaultConfig();

        filterKey = new NamespacedKey(this, "irg-filter");

        for (GlowColorTeam colorTeam : GlowColorTeam.values())
            colorTeam.injectTeam(this);

        new ItemRarityGlowCommand(this);
        getServer().getPluginManager().registerEvents(new ItemSpawn(this), this);
        getServer().getPluginManager().registerEvents(new EntityPickupItem(this), this);

        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        itemFilters.clear();

        super.reloadConfig();

        for (Messages message : Messages.values())
            message.loadMessage(this);

        ConfigurationSection filterSection = getConfig().getConfigurationSection("filters");
        if (filterSection == null)
            return;

        filterSection.getKeys(false).forEach(filterKey -> {
            ItemFilter itemFilter = new ItemFilter(this, filterKey);
            itemFilters.add(itemFilter);
        });
    }

    @Override
    public void onDisable() {
        itemFilters.clear();
        for (GlowColorTeam colorTeam : GlowColorTeam.values())
            colorTeam.unregisterTeam();
    }

    @Nullable
    public Team getTeamForItemStack(ItemStack itemStack) {
        for (ItemFilter itemFilter : itemFilters) {
            if (itemFilter.matchesItemStack(itemStack))
                return itemFilter.getGlowColorTeam().getTeam();
        }
        return null;
    }

    public int getNextId(int id) {
        if (itemFilters.size() > id + 1)
            return id + 1;
        return 0;
    }

    public List<ItemFilter> getItemFiltersById(int id) {
        return itemFilters.subList(id, itemFilters.size());
    }

    public ItemFilter getFilterById(int id) {
        return itemFilters.get(id);
    }

    public NamespacedKey getFilterKey() {
        return filterKey;
    }
}
