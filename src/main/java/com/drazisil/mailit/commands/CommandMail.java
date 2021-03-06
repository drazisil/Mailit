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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.drazisil.mailit.Mailit.plugin;
import static com.drazisil.mailit.PlayerUtil.isPlayer;
import static java.lang.String.format;

public class CommandMail implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!isPlayer(sender)) {
            sender.sendMessage("This command can only be ran as a player.");
            return true;
        }

        Player player = (Player) sender;

        ArrayList<MailPackage> pkgs = null;
        try {
            pkgs = plugin.getMailboxManager()
                    .getPackagesByReceiver(player);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (pkgs == null || pkgs.size() == 0) {
            player.sendMessage("You have no packages in your mailbox.");
            return true;
        }

        player.sendMessage(format("You have %d packages in your mailbox.",
                pkgs.size()));

        // TODO: Handle this cleaner
        if (args.length == 1 && args[0].equals("list")) {
            player.sendMessage("Listing packages...");

            int pkgIdx = 0;
            for (MailPackage pkg : pkgs) {
                player.sendMessage(format("   %d: from %s",
                        pkgIdx,
                        pkg.getFrom().getName()));
                pkgIdx++;
            }

            player.sendMessage("\nType /open <package number> to view one.");
        }

        return true;
    }
}
