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

import org.bukkit.command.PluginCommand;

import static com.drazisil.mailit.Mailit.plugin;

public class CommandManager {

    public static void registerCommands() {
        PluginCommand cmdSend = plugin.getCommand("send");
        if (cmdSend != null) cmdSend.setExecutor(new CommandSend());

//        PluginCommand cmdMailList = plugin.getCommand("mail list");
//        if (cmdMailList != null) cmdMailList.setExecutor(new CommandMailList());

        PluginCommand cmdMail = plugin.getCommand("mail");
        if (cmdMail != null) cmdMail.setExecutor(new CommandMail());

        PluginCommand cmdOpen = plugin.getCommand("open");
        if (cmdOpen != null) cmdOpen.setExecutor(new CommandOpen());
    }
}
