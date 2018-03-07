package org.mcsg.double0negative.supercraftbros.event;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.GameManager;

public class SignEvents implements Listener {
    @EventHandler
    public void clickHandler(PlayerInteractEvent e) {
	if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.hasBlock())
	    return;
	Block clickedBlock = e.getClickedBlock();
	if (clickedBlock.getState() instanceof Sign == false)
	    return;
	Sign clickedSign = (Sign) clickedBlock.getState();
	String[] lines = clickedSign.getLines();
	if (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[class]")) {
	    String cl = lines[1];
	    Game g = GameManager.getInstance().getGame(GameManager.getInstance().getPlayerGameId(e.getPlayer()));
	    if (g != null) {
		g.setPlayerClass(e.getPlayer(), GameManager.getInstance().classList.get(cl.toLowerCase()).newInstance(e.getPlayer()));
		g.getPlayerClassBase(e.getPlayer()).PlayerSpawn();
		if (g.getBoard() != null)
		    g.getBoard().setup(true);
	    }
	} else if (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[join]")) {
	    int game = Integer.parseInt(lines[1]);
	    Game g = GameManager.getInstance().getGame(game);
	    g.addPlayer(e.getPlayer());
	} else if (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[leave]")) {
	    Player p = e.getPlayer();
	    p.performCommand("scb leave");
	}
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
	if (e.getLine(0).equalsIgnoreCase("[join]")) {
	    int game = Integer.parseInt(e.getLine(1));
	    Game g = GameManager.getInstance().getGame(game);
	    e.setLine(0, "§3[Join]");
	    e.setLine(2, "§eClick to join");
	    e.setLine(3, "§b" + g.getActivePlayers() + " /4");
	} else if (e.getLine(0).equalsIgnoreCase("[class]")) {
	    e.setLine(0, "§2[Class]");
	    e.setLine(2, "§bClick to pick");
	    e.setLine(3, "§bA class");
	} else if (e.getLine(0).equalsIgnoreCase("[leave]")) {
	    e.setLine(0, "§2[Leave]");
	    e.setLine(2, "§bClick to");
	    e.setLine(3, "§bleave");
	} else if (e.getLine(0).equalsIgnoreCase("[open]")) {
	    e.setLine(0, "§1[Open]");
	    e.setLine(2, "§aChoose a");
	    e.setLine(3, "§bClasss!");
	}
    }

    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    Plugin pl;
    Set<Location> locations = new HashSet<Location>();

    public void addSignListElement(String key, String... element) {
	List<String> list = getSignConfig().getStringList(key);
	list.addAll(Arrays.asList(element));
	getSignConfig().set(key, list);
	saveSignConfig();
    }

    public Set<Location> getLocs(String game) {
	locations.clear();
	for (Object locObj : getSignConfig().getList(game)) {
	    String locString = locObj.toString();
	    String[] loc = locString.split("\\,");
	    World w = Bukkit.getWorld(loc[0]);
	    Double x = Double.parseDouble(loc[1]);
	    Double y = Double.parseDouble(loc[2]);
	    Double z = Double.parseDouble(loc[3]);
	    float yaw = Float.parseFloat(loc[4]);
	    float pitch = Float.parseFloat(loc[5]);
	    Location location = new Location(w, x, y, z, yaw, pitch);
	    locations.add(location);
	}
	return locations;
    }

    public FileConfiguration getSignConfig() {
	if (customConfig == null) {
	    reloadSignConfig();
	}
	return customConfig;
    }

    public void reloadSignConfig() {
	pl.saveResource("signs.yml", false);
    }

    public void saveLoc(String game, Location loc) {
	String location = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
	addSignListElement(game, location);
	saveSignConfig();
    }

    public void saveSignConfig() {
	if (customConfig == null || customConfigFile == null) {
	    return;
	}
	try {
	    getSignConfig().save(customConfigFile);
	} catch (IOException ex) {
	    pl.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	}
    }

    public void updateSigns(String game) {
	for (Location loc : getLocs(game)) {
	    Sign sign = (Sign) loc.getBlock();
	    sign.setLine(3, "PLAYERS/MAX");
	}
    }
}
