package com.drazisil.mailit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

import static org.bukkit.Bukkit.createInventory;

public class PlayerMailbox {

    private final Player owner;
    private final ArrayList<MailPackage> packages = new ArrayList<>();

    public PlayerMailbox(Player owner) {
        this.owner = owner;

  }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<MailPackage> getPackages() {
        return packages;
    }
}
