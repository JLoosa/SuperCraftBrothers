package org.mcsg.double0negative.supercraftbros;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.mcsg.double0negative.supercraftbros.event.BlockEvents;
import org.mcsg.double0negative.supercraftbros.event.DamageEvents;
import org.mcsg.double0negative.supercraftbros.event.InventoryEvents;
import org.mcsg.double0negative.supercraftbros.event.PlayerClassEvents;
import org.mcsg.double0negative.supercraftbros.event.PlayerTeleportEvents;
import org.mcsg.double0negative.supercraftbros.event.SignEvents;

import com.gmail.Jacob6816.scb.event.InventoryClassEvent;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class SuperCraftBros extends JavaPlugin {
    public WorldEditPlugin getWorldEdit() {
	Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
	if (worldEdit instanceof WorldEditPlugin) {
	    return (WorldEditPlugin) worldEdit;
	} else {
	    return null;
	}
    }

    @Override
    public void onDisable() {
	for (Game g : GameManager.getInstance().getGames()) {
	    g.disable();
	}
	HandlerList.unregisterAll(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
	SettingsManager.getInstance().setup(this);
	GameManager.getInstance().setup(this);
	this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);
	this.getServer().getPluginManager().registerEvents(new SignEvents(), this);
	this.getServer().getPluginManager().registerEvents(new PlayerClassEvents(), this);
	this.getServer().getPluginManager().registerEvents(new DamageEvents(), this);
	this.getServer().getPluginManager().registerEvents(new PlayerTeleportEvents(), this);
	this.getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
	this.getServer().getPluginManager().registerEvents(new InventoryClassEvent(), this);
	this.getCommand("scb").setExecutor(new CommandHandler(this));
	for (Player p : Bukkit.getOnlinePlayers()) {
	    p.teleport(SettingsManager.getInstance().getLobbySpawn());
	    p.getInventory().clear();
	    p.getInventory().setArmorContents(new ItemStack[4]);
	    p.updateInventory();
	    for (PotionEffectType e : PotionEffectType.values()) {
		if (e != null && p.hasPotionEffect(e))
		    p.removePotionEffect(e);
	    }
	}
    }
}
