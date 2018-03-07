package com.gmail.Jacob6816.scb.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCreator {
	public HeadCreator() {
	}

	public ItemStack getMobhead(SkullType type, String display) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) type.ordinal());
		ItemMeta meta = skull.getItemMeta();
		meta.setDisplayName(display);
		skull.setItemMeta(meta);
		return skull;
	}

	public ItemStack getPlayerhead(String name, String display) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(name)));
		meta.setDisplayName(display);
		skull.setItemMeta(meta);
		return skull;
	}
}
