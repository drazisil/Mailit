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

package com.drazisil.mailit.commands;

import com.drazisil.mailit.MailPackage;
import com.drazisil.mailit.MailboxManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;
import static com.drazisil.mailit.PlayerUtil.getPlayerByName;
import static com.drazisil.mailit.PlayerUtil.isPlayer;
import static java.lang.String.format;

public class CommandSend implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label, String[] args) {

        if (!(isPlayer(sender))) {
            sender.sendMessage("This command can only be ran as a player.");
            return true;
        }

        if (args.length != 1) return false;

        Player sendingPlayer = (Player) sender;

        // Check if the argument is a player
        String receivingPlayerName = args[0];
        Player receivingPlayer = getPlayerByName(receivingPlayerName);
        if (receivingPlayer == null) {
            sender.sendMessage("Unable to locate that player, please check the spelling.");
            return true;
        }

        logger.info(format("%s used the send command for %s", sender.getName(), receivingPlayerName));

        MailboxManager mailboxManager = plugin.getMailboxManager();

        MailPackage mailPackage = mailboxManager.newPackage(sendingPlayer, receivingPlayer);
        mailboxManager.open(mailPackage, sendingPlayer);
        return true;
    }

}
