package com.drazisil.mailit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;

public class PLayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MailboxManager mailboxManager = plugin.getMailboxManager();

        if (!(mailboxManager.hasMailbox(player))) {
            PlayerMailbox mailbox = new PlayerMailbox(player);

            mailboxManager.addMailboxes(mailbox);

            logger.info(String.format("New mailbox registered for %s", player.getName()));

        }
    }
}
