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
import static java.lang.String.format;
import static org.bukkit.Bukkit.createInventory;

public class MailPackage implements InventoryHolder {

    private final Player from;
    private final Player to;
    private final int packageSize = 27;
    private Inventory contents = null;
    private final UUID id;
    private boolean isOpen = false;

    public MailPackage(Player playerFrom, Player playerTo) {
        this.id = UUID.randomUUID();
        this.from = playerFrom;
        this.to = playerTo;
        this.contents = createInventory(
                this,
                packageSize,
                format("Package from %s to %s",
                        from.getName(),
                        to.getName()));
    }

    public MailPackage(String id, String fromName, String toName, String contents) throws SQLException {
        this.id = UUID.fromString(id);

        Player from = PlayerUtil.getPlayerByName(fromName);
        if (from == null) logger.severe(format("No player found with the name %s",
                fromName));
        this.from = from;

        Player to = PlayerUtil.getPlayerByName(toName);
        if (to == null) logger.severe(format("No player found with the name %s",
                fromName));
        this.to = to;

        ItemStack[] items = ItemStackUtil.deserialize(contents);

        this.contents = createInventory(
                this,
                packageSize,
                format("Package from %s to %s",
                        from.getName(),
                        to.getName()));

        this.contents.setStorageContents(items);
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
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
        from.openInventory(getInventory());
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public MailPackage close() throws SQLException {

        save();

        setOpen(false);
        return this;
    }

    public void save() throws SQLException {

        if (isEmpty()) {
            delete();
            return;
        }

        try {
            plugin.getDatabaseManager().update(this);
            logger.info(format("Package with id %s was saved.", getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
