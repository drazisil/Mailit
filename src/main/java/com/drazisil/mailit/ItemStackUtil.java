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

import org.bukkit.inventory.ItemStack;

import static com.drazisil.mailit.Mailit.logger;

public class ItemStackUtil {
    public static String serializeFromSet(ItemStack[] storageContents) {

        StringBuilder serializedSet = new StringBuilder();

        for (ItemStack itemStack : storageContents) {
            if (itemStack == null) {
                serializedSet.append("null");
                break;
            }

            StringBuilder toString = new StringBuilder("ItemStack{").append(itemStack.getType().name()).append(" x ").append(itemStack.getAmount());
            if (itemStack.hasItemMeta()) {
                toString.append(", ").append(itemStack.getItemMeta());
            }
            serializedSet.append(toString.append('}').toString());

        }

        // TODO: This returns null :(

        logger.warning(serializedSet.toString());
        return serializedSet.toString();


    }
}
