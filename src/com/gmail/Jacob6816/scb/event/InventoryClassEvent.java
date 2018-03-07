package com.gmail.Jacob6816.scb.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.GameManager;
import org.mcsg.double0negative.supercraftbros.classes.ClassType;

public class InventoryClassEvent implements Listener {
    private Inventory inv;

    public InventoryClassEvent() {
	int size = ClassType.values().length;
	while (size % 9 != 0)
	    size++;
	inv = Bukkit.createInventory(null, size, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "         Scb Classes!");
	for (ClassType c : ClassType.values()) {
	    inv.addItem(c.getItemStack());
	}
    }

    @EventHandler
    public void clickHandler(PlayerInteractEvent e) {
	final Player p = e.getPlayer();
	if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
	    return;
	Block clickedBlock = e.getClickedBlock();
	if (!(clickedBlock.getState() instanceof Sign))
	    return;
	Sign sign = (Sign) clickedBlock.getState();
	String[] lines = sign.getLines();
	if (!ChatColor.stripColor(lines[0]).equalsIgnoreCase("[open]"))
	    return;
	int g = GameManager.getInstance().getPlayerGameId(p);
	if (g >= 0)
	    p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
	if (!(event.getWhoClicked() instanceof Player))
	    return;
	final Player p = (Player) event.getWhoClicked();
	int id = GameManager.getInstance().getPlayerGameId(p);
	if (id < 0)
	    return;
	event.setCancelled(true);
	if (!event.getInventory().getName().equals(inv.getName()))
	    return;
	ItemStack c = event.getCurrentItem();
	if (c == null || c.getType() == Material.AIR)
	    c = event.getCursor();
	if (c == null || c.getType() == Material.AIR)
	    return;
	if (!c.hasItemMeta())
	    return;
	if (!c.getItemMeta().hasDisplayName())
	    return;
	for (ClassType t : ClassType.values()) {
	    if (t.localeCaps(t.toString()).equals(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
		Game g = GameManager.getInstance().getGamePlayer(p);
		g.setPlayerClass(p, GameManager.getInstance().classList.get(t.toString().toLowerCase()).newInstance(p));
		g.getPlayerClassBase(p).PlayerSpawn();
		return;
	    }
	}
    }
}
