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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

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
