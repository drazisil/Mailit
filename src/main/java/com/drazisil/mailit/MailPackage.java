package com.drazisil.mailit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static org.bukkit.Bukkit.createInventory;

public class MailPackage {

    private final Player from;
    private final Player to;
    private final Inventory contents;

    public MailPackage(Player playerFrom, Player playerTo) {
        this.from = playerFrom;
        this.to = playerTo;
        this.contents = createInventory(
                null,
                27,
                String.format("Package from %s to %s", from.getName(), to.getName()));
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public Inventory getContents() {
        return contents;
    }
}
