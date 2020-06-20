/*
 * Mailit is a Minecraft Bukkit plugin to allow for player mailboxes
 * Copyright (C) 2020 Joe Becher (Drazisil)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
