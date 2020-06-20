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
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static org.bukkit.Bukkit.createInventory;

public class MailPackage implements InventoryHolder {

    private final Player from;
    private final Player to;
    private final Inventory contents;
    private final int mailboxSize = 27;
    private final int lastSlotIndex = mailboxSize - 1;
    private final UUID id;
    private boolean isOpen = false;

    public MailPackage(Player playerFrom, Player playerTo) {
        this.id = UUID.randomUUID();
        this.from = playerFrom;
        this.to = playerTo;
        this.contents = createInventory(
                null,
                mailboxSize,
                String.format("Package from %s to %s", from.getName(), to.getName()));
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

    public void close() {
        setOpen(false);
    }
}
