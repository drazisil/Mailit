package com.drazisil.mailit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.drazisil.mailit.Mailit.plugin;

public class CommandMail implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be ran as a player.");
            return true;
        }

        Player sendingPlayer = (Player) sender;

        // Get the mailbox for the player.
        PlayerMailbox mailbox = plugin.getMailboxManager().getMailbox(sendingPlayer);

        sendingPlayer.sendMessage(String.format("You have %d packages in your mailbox.", mailbox.getPackageCount()));

        return true;
    }
}
