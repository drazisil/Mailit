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
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;
import static java.lang.String.format;

public class ItemStackUtil {
    public static ItemStack[] deserialize(String serialisedString) {

        // Remove end caps
        serialisedString = serialisedString.substring(1, serialisedString.length() - 1);

        String[] parts = serialisedString.split(", ");

        ArrayList<String> partsArr = Lists.newArrayList(parts);

        ArrayList<ItemStack> items = new ArrayList<>();

        for (String item : partsArr) {
            items.add(parseItemStackString(item));
        }

        return items.toArray(new ItemStack[0]);


    }

    private static ItemStack parseItemStackString(String item) {

        if (item.equals("null")) return null;

        String[] parts = item.split(" x ");

        String m = parts[0].substring(10);
        String c = parts[1].substring(0, parts[1].length() - 1);

        Material material = Material.valueOf(m);

        // TODO: Handle metadata
        // ItemStack{FILLED_MAP x 1, MAP_META:{meta-type=MAP, map-id=21}}

        int count = Integer.parseInt(c);

        return new ItemStack(material, count);
    }

    public static String toString(ItemStack itemStack) {
        String itemMeta = "";

        StringBuilder itemString = new StringBuilder("ItemStack{").append(itemStack.getType().name()).append(" x ").append(itemStack.getAmount());
        if (itemStack.hasItemMeta()) {
            itemMeta = String.valueOf(itemStack.getItemMeta());
        }
        itemString.append('}');

        if (!itemMeta.equals("")) itemString.append("|").append(itemMeta);

        logger.warning(itemString.toString());

        return itemString.toString();
    }

    public static ItemStack getItemStack(String itemString) {
        // Example string: ItemStack{FILLED_MAP x 1}|MAP_META:{meta-type=MAP, map-id=7}

        if (itemString.contains("|")) {
            // There is meta in this string, split it.
            String[] parts = itemString.split("\\|");
            String item = parts[0];
            String meta = parts[1];

            ItemStack itemStack = parseItemStackString(item);
            logger.warning("1.1: " + itemStack);

            ItemMeta itemMeta = parseItemMetaString(itemStack, meta);
            logger.warning("1.2: " + itemMeta);
            return itemStack;

        }
        ItemStack itemStack = parseItemStackString(itemString);
        logger.warning("2: " + itemStack);
        return itemStack;

    }

    @Nullable
    private static <T extends ItemMeta> T parseItemMetaString(ItemStack itemStack, String itemMeta) {
        // First, get the itemMeta type
        @Nullable T defaultMeta = (T) plugin.getMetaFactory().getItemMeta(itemStack.getType());

        if (defaultMeta == null) {
            logger.severe(format("Unable to locate a meta type for material %s, why did you request meta for it?", itemStack.getType()));
            return null;
        }

        logger.warning(format("Located %s for %s, we can continue.", defaultMeta, itemStack.getType()));

        return defaultMeta;
    }


//    public static ItemStack fromString(String item, String meta) {
//        boolean itemHasMeta = false;
//
//        if (!item.equals("")) itemHasMeta = true;
//
//    }
}
