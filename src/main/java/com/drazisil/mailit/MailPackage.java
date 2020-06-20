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
}
