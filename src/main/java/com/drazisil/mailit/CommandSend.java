package com.drazisil.mailit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;

public class CommandSend implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be ran as a player.");
            return true;
        }

        Player sendingPlayer = (Player) sender;

        if (args.length != 1) return false;

        // Check if the argument is a player
        String receivingPlayerName = args[0];
        Player receivingPlayer = sender.getServer().getPlayer(receivingPlayerName);
        if (receivingPlayer == null) {
            sender.sendMessage("Unable to locate that player, please check the spelling.");
            return true;
        }

        logger.info(String.format("%s used the send command for %s", sender.getName(), receivingPlayerName));

        // Get the mailbox for the player.
        PlayerMailbox mailbox = plugin.getMailboxManager().getMailbox(sendingPlayer);

        if (mailbox == null) {
            // We should never hit this, log a warning
            logger.warning(String.format("Unable to locate mailbox for %s!", sendingPlayer.getName()));
            return true;
        }

        // Is the mailbox in use?
        if (mailbox.isInUse()) return true;
        //TODO: Come back and fix this
        mailbox.setInUse(true);

        MailPackage newPackage = mailbox.newPackage(receivingPlayer);

        sendingPlayer.openInventory(newPackage.getInventory());

        return true;
    }
}
