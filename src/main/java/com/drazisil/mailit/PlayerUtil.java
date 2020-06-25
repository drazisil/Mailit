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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.drazisil.mailit.Mailit.plugin;

public class PlayerUtil {

    public static boolean isOfflinePlayer(CommandSender sender) {

        @NotNull OfflinePlayer[] offlinePlayers = plugin.getServer().getOfflinePlayers();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (Objects.equals(offlinePlayer.getName(), sender.getName())) return true;
        }
        return false;
    }

    public static boolean isPlayer(CommandSender sender) {
        return (sender instanceof Player || isOfflinePlayer(sender));
    }

    @Nullable
    public static OfflinePlayer getOfflinePlayerByName(String playerName) {
        @NotNull OfflinePlayer[] offlinePlayers = plugin.getServer().getOfflinePlayers();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (offlinePlayer.getName() != null) {
                if (offlinePlayer.getName().equals(playerName)) {
                    return offlinePlayer;
                }
            }
        }
        return null;
    }

    public static Player getPlayerByName(String playerName) {
        return plugin.getServer().getPlayer(playerName);
    }

}
