package me.gadse.itemrarityglow.listeners;

import me.gadse.itemrarityglow.ItemFilter;
import me.gadse.itemrarityglow.ItemRarityGlow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

public class EntityPickupItem implements Listener {

    private final ItemRarityGlow plugin;

    public EntityPickupItem(ItemRarityGlow plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        int currentFilterId = event.getEntity().getPersistentDataContainer()
                .getOrDefault(plugin.getFilterKey(), PersistentDataType.INTEGER, 0);

        for (ItemFilter itemFilter : plugin.getItemFiltersById(currentFilterId)) {
            if (itemFilter.matchesItemStack(event.getItem().getItemStack()))
                return;
        }

        event.setCancelled(true);
    }
}
