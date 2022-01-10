package me.gadse.itemrarityglow.listeners;

import me.gadse.itemrarityglow.ItemRarityGlow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.scoreboard.Team;

public class ItemSpawn implements Listener {

    private final ItemRarityGlow plugin;

    public ItemSpawn(ItemRarityGlow plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        Team team = plugin.getTeamForItemStack(event.getEntity().getItemStack());
        if (team == null)
            return;

        team.addEntry(event.getEntity().getUniqueId().toString());
        event.getEntity().setGlowing(true);
    }
}
