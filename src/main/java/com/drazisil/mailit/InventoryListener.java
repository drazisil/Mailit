package com.drazisil.mailit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof MailPackage)) return;

        Player player = (Player) event.getPlayer();
        MailPackage inventoryHolder = (MailPackage) event.getInventory().getHolder();

        UUID packageId = inventoryHolder.getId();

        PlayerMailbox mailbox = plugin.getMailboxManager().getMailbox(player);

        MailPackage mailPackage = mailbox.getPackage(packageId);

        if (mailPackage == null) {
            logger.warning(String.format("We received a close event for a package with id %s, but no such package exists!", packageId));
        }
        mailbox.setInUse(false);
    }
}
