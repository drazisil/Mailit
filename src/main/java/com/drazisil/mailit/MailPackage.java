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

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;
import static com.drazisil.mailit.PlayerUtil.getOfflinePlayerByName;
import static java.lang.String.format;
import static org.bukkit.Bukkit.createInventory;

public class MailPackage implements InventoryHolder {

    private final OfflinePlayer from;
    private final OfflinePlayer to;
    private final int packageSize = 27;
    private final Inventory contents;
    private final UUID id;
    private boolean isOpen = false;

    public MailPackage(@NotNull Player playerFrom, @NotNull OfflinePlayer playerTo) {
        this.id = UUID.randomUUID();
        this.from = playerFrom;
        this.to = playerTo;
        this.contents = createInventory(
                this,
                packageSize,
                format("Package to %s",
                        to.getName()));
    }

    public MailPackage(String id, String fromName, String toName, String contents) {
        this.id = UUID.fromString(id);

        OfflinePlayer from = getOfflinePlayerByName(fromName);
        if (from == null) logger.severe(format("No player found with the name %s",
                fromName));
        this.from = from;

        OfflinePlayer to = getOfflinePlayerByName(toName);
        if (to == null) logger.severe(format("No player found with the name %s",
                fromName));
        this.to = to;

        ItemStack[] items = ItemStackUtil.deserialize(contents);

        this.contents = createInventory(
                this,
                packageSize,
                format("Package to %s",
                        to.getName()));

        this.contents.setStorageContents(items);
    }

    public OfflinePlayer getFrom() {
        return from;
    }

    public OfflinePlayer getTo() {
        return to;
    }

    @Override
    @NotNull
    public Inventory getInventory() {
        return contents;
    }

    public UUID getId() {
        return this.id;
    }

    public void open(Player player) {
        if (isOpen) {
            player.sendMessage("That package is currently being looked at, try again later.");
            return;
        }
        setOpen(true);
        player.openInventory(getInventory());
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void close() throws SQLException {

        save();

        setOpen(false);
    }

    public void save() throws SQLException {

        if (isEmpty()) {
            delete();
            return;
        }

        try {
            plugin.getDatabaseManager().update(
                    id,
                    from.getName(),
                    to.getName(),
                    contents.getStorageContents());
            logger.info(format("Package with id %s was saved.", getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private @NotNull ItemStack[] contentsToArray() {
        return getInventory().getStorageContents();
    }

    private void delete() throws SQLException {
        plugin.getMailboxManager().removePackageById(this);
        try {
            plugin.getDatabaseManager().delete(this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.info(format("Package with id %s was deleted.", getId()));
    }

    private boolean isEmpty() {

        for (int i = 0; i < contents.getSize(); i++) {
            if (!(contents.getItem(i) == null)) return false;
        }
        return true;
    }

    public String serialize() {
        return new SerializedPackage(this).toString();
    }

    public String toString() {
        return serialize();
    }

    public static class SerializedPackage {
        public final String pkgId;
        public final String fromPlayerName;
        public final String toPlayerName;
        public final String contents;

        public SerializedPackage(MailPackage pkg) {
            this.pkgId = String.valueOf(pkg.id);
            this.fromPlayerName = pkg.from.getName();
            this.toPlayerName = pkg.to.getName();

            this.contents = Arrays.toString(pkg.contents.getStorageContents());
        }

        public String toString() {
            return format("%s|%s|%s|%s",
                    this.pkgId,
                    this.fromPlayerName,
                    this.toPlayerName,
                    this.contents);
        }
    }
}
