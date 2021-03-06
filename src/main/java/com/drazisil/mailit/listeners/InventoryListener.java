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

package com.drazisil.mailit.listeners;

import com.drazisil.mailit.MailPackage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.sql.SQLException;
import java.util.UUID;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;
import static java.lang.String.format;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) throws SQLException {

        if (!(event.getInventory().getHolder() instanceof MailPackage)) return;

        MailPackage inventoryHolder = (MailPackage) event.getInventory().getHolder();

        UUID packageId = inventoryHolder.getId();

        MailPackage mailPackage = plugin.getMailboxManager()
                .getPendingPackageById(packageId);

        if (mailPackage == null) {
            logger.severe(format("We received a close event for a package with id %s, but no such package exists!", packageId));
            return;
        }
        mailPackage.close();

    }
}
