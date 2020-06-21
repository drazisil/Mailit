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

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackUtil {
    public static ItemStack[] deserialize(String serialisedString) {

        String[] parts = serialisedString.split(", ");

        ArrayList<String> partsArr = Lists.newArrayList(parts);

        ArrayList<ItemStack> items = new ArrayList<>();

        for (String item : partsArr) {
            items.add(parseItemStackString(item));
        }

        return (ItemStack[]) items.toArray();


    }

    private static ItemStack parseItemStackString(String item) {

        if (item.equals("null")) return null;

        String[] parts = item.split(" x ");

        Material material = Material.valueOf(parts[0]);
        int count = Integer.parseInt(parts[1]);

        return new ItemStack(material, count);
    }
}
