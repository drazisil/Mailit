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

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Mailit extends JavaPlugin {

    public static Mailit plugin;
    public static Logger logger;

    private MailboxManager mailboxManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        logger = plugin.getLogger();
        mailboxManager = new MailboxManager();

        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);

        PluginCommand cmdSend = plugin.getCommand("send");
        if (cmdSend != null) cmdSend.setExecutor(new CommandSend());

        PluginCommand cmdMail = plugin.getCommand("mail");
        if (cmdMail != null) cmdMail.setExecutor(new CommandMail());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MailboxManager getMailboxManager() {
        return this.mailboxManager;
    }
}
