package org.mcsg.double0negative.supercraftbros.classes;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ClassType {
    ENDERMAN(ChatColor.DARK_PURPLE, Material.ENDER_PEARL), CACTUS(ChatColor.DARK_GREEN, Material.CACTUS), CREEPER(ChatColor.GREEN, Material.TNT), SKELETON(ChatColor.DARK_GRAY, Material.BOW), SPIDER(ChatColor.BLACK, Material.SPIDER_EYE), WITHER(ChatColor.LIGHT_PURPLE, Material.OBSIDIAN), ZOMBIE(ChatColor.DARK_AQUA, Material.ROTTEN_FLESH), UNKNOWN(ChatColor.AQUA, Material.PISTON_MOVING_PIECE), GHAST(ChatColor.DARK_GRAY, Material.GHAST_TEAR), ENDERDRAGON(ChatColor.DARK_BLUE, Material.DRAGON_EGG), BLAZE(
            ChatColor.GOLD, Material.BLAZE_ROD), WITCH(ChatColor.RED, Material.GLASS_BOTTLE);
    
    final ChatColor color;
    final Material material;
    
    ClassType(ChatColor color, Material material) {
        this.color = color;
        this.material = material;
    }
    
    private ItemStack createStack(ClassType c, Material m, int b) {
        ItemStack item = new ItemStack(m, 1, (short) b);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + localeCaps(c.toString()));
        meta.setLore(Arrays.asList(ChatColor.YELLOW + "Click here to choose the " + c.getColor() + localeCaps(c.toString()) + ChatColor.YELLOW + " class."));
        item.setItemMeta(meta);
        return item;
    }
    
    public ChatColor getColor() {
        return color;
    }
    
    public ItemStack getItemStack() {
        return createStack(this, material, 0).clone();
    }
    
    public String localeCaps(String in) {
        if (in.length() <= 1) return in.toUpperCase();
        String t = in.toLowerCase();
        char c = t.charAt(0);
        String s = String.valueOf(c).toUpperCase();
        return s + t.substring(1);
        
    }
}
