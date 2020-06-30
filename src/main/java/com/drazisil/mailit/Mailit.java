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

import org.bukkit.inventory.ItemFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static com.drazisil.mailit.commands.CommandManager.registerCommands;
import static com.drazisil.mailit.listeners.EventManager.registerEventListeners;

public final class Mailit extends JavaPlugin {

    public static Mailit plugin;
    public static Logger logger;

    private DatabaseManager databaseManager;
    private MailboxManager mailboxManager;
    private static ItemFactory itemFactory;
    private ItemFactory metaItemFactory;

    public static ItemFactory getItemFactory() {
        return itemFactory;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public MailboxManager getMailboxManager() {
        return this.mailboxManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        logger = plugin.getLogger();
        this.metaItemFactory = getServer().getItemFactory();
        mailboxManager = new MailboxManager();
        databaseManager = new DatabaseManager();
        itemFactory = getServer().getItemFactory();

        registerEventListeners();

        registerCommands();

        plugin.saveDefaultConfig();

        databaseManager.connect();
    }

    public ItemFactory getMetaFactory() {
        return this.metaItemFactory;
    }
}
