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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static com.drazisil.mailit.Mailit.plugin;

public class MailboxManager {

    //    private final ArrayList<PlayerMailbox> mailboxes = new ArrayList<>();
    private final ArrayList<MailPackage> pkgList = new ArrayList<>();

    public ArrayList<MailPackage> getPackagesByReceiver(Player player) throws SQLException {

        ArrayList<MailPackage> playerPackagesRaw = plugin.getDatabaseManager().retrieveByReceiver(player.getName());

        ArrayList<MailPackage> playerPackages = new ArrayList<>();

        for (MailPackage pkg : pkgList) {
            if (pkg.getTo() == player) playerPackages.add(pkg);
        }
        return playerPackages;
    }

    public int getPackageCountByReceiver(Player player) throws SQLException {
        ArrayList<MailPackage> playerPackages = getPackagesByReceiver(player);
        return playerPackages.size();
    }

    public MailPackage newPackage(Player sendingPlayer, Player receivingPlayer) {
        MailPackage newPkg = new MailPackage(sendingPlayer, receivingPlayer);
        pkgList.add(newPkg);
        return newPkg;
    }

    @Nullable
    public MailPackage getPackageById(UUID packageId) {
        for (MailPackage pkg : pkgList) {
            if (pkg.getId().equals(packageId)) return pkg;
        }
        return null;
    }

    public void removePackageById(MailPackage mailPackage) {
        pkgList.remove(mailPackage);
    }
}
