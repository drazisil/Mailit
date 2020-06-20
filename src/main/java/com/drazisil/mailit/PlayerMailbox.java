package com.drazisil.mailit;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

public class PlayerMailbox {

    private final Player owner;
    private final ArrayList<MailPackage> packages = new ArrayList<>();
    private boolean isInUse = false;

    public PlayerMailbox(Player owner) {
        this.owner = owner;

  }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<MailPackage> getPackages() {
        return packages;
    }

    public int getPackageCount() {
        return packages.size();
    }

    @Nullable
    public MailPackage getPackage(int index) {

        return packages.get(index);
    }

    public MailPackage newPackage(Player receiver) {
        // TODO: Fill gaps
        MailPackage newPackage = new MailPackage(owner, receiver);
        packages.add(newPackage);
        return newPackage;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }

    @Nullable
    public MailPackage getPackage(UUID packageId) {
        for (MailPackage thisPackage: packages) {
            if (thisPackage.getId().equals(packageId)) return thisPackage;
        }
        return null;
    }
}
