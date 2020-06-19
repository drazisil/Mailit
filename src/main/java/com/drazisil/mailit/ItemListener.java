package com.drazisil.mailit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemUsed = event.getItem();

        if (itemUsed == null) return;

        Material itemType = event.getItem().getType();

        if (itemType == Material.MAP) {
            logger.info(String.format("%s used a map.", player.getName()));
        }

//        player.openInventory(plugin.getMailboxManager().getMailbox(player).getInventory());
    }
}
