package org.mcsg.double0negative.supercraftbros.event;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class SignUpdateEvent implements Listener {
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

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.SIGN_POST || e.getBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getBlock();
			if (sign.getLine(0).equalsIgnoreCase("[join]")) { // also check if game exists
				saveLoc(sign.getLine(1), sign.getLocation());
			}
		}
	}

	public void reloadSignConfig() {
		pl.saveResource("signs.yml", false);
	}

	public void saveLoc(String game, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + ","
				+ loc.getYaw() + "," + loc.getPitch();
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