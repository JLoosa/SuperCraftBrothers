package com.gmail.Jacob6816.scb.beta;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jacob
 *
 */
public class CustomClass {
    private String name;
    private String displayName;
    private ItemStack[] inventory;
    private ItemStack[] armor;

    public CustomClass(String name) {
	setName(name);
	setDisplayName(String.format("[%s]", name));
	setInventory(new ItemStack[0]);
	setArmor(new ItemStack[0]);
    }

    @SuppressWarnings("unchecked")
    public static CustomClass readFromConfigurationFile(FileConfiguration configuration, String name) {
	CustomClass custom = new CustomClass(name);
	ConfigurationSection section = configuration.getConfigurationSection(name);
	if (section == null)
	    return custom;
	custom.setDisplayName(section.getString("DisplayName"));
	ConfigurationSection armorSection = section.createSection("Armor");
	List<ItemStack> currentList = new LinkedList<ItemStack>();
	Object currentMap;
	for (String key : armorSection.getKeys(false)) {
	    currentMap = armorSection.get(key);
	    if (currentMap instanceof Map<?, ?> == false)
		continue;
	    currentList.add(ItemStack.deserialize((Map<String, Object>) currentMap));
	}
	custom.setArmor(currentList.toArray(new ItemStack[0]));
	currentList.clear();
	ConfigurationSection inventorySection = section.createSection("Inventory");
	for (String key : inventorySection.getKeys(false)) {
	    currentMap = inventorySection.get(key);
	    if (currentMap instanceof Map<?, ?> == false)
		continue;
	    currentList.add(ItemStack.deserialize((Map<String, Object>) currentMap));
	}
	custom.setInventory(currentList.toArray(new ItemStack[0]));
	return custom;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name.replaceAll("^[a-zA-z]", "");
    }

    public String getDisplayName() {
	return this.displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    public ItemStack[] getInventory() {
	return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
	this.inventory = inventory;
    }

    public ItemStack[] getArmor() {
	return armor;
    }

    public void setArmor(ItemStack[] armor) {
	this.armor = armor;
    }

    public void applyInventoriesToPlayer(Player player) {
	player.getInventory().setContents(inventory);
	player.getInventory().setArmorContents(armor);
    }

    public void writeToConfigurationFile(FileConfiguration configuration) {
	ConfigurationSection section = configuration.createSection(name);
	section.set("DisplayName", displayName);
	ConfigurationSection armorSection = section.createSection("Armor");
	int index = 0;
	while (index < armor.length) {
	    armorSection.set(Integer.toString(index), armor[index].serialize());
	}
	ConfigurationSection inventorySection = section.createSection("Inventory");
	index = 0;
	while (index < inventory.length) {
	    inventorySection.set(Integer.toString(index), inventory[index].serialize());
	}
    }
}
