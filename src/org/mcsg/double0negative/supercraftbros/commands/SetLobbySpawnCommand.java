package org.mcsg.double0negative.supercraftbros.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mcsg.double0negative.supercraftbros.SettingsManager;

public class SetLobbySpawnCommand implements SubCommand{

	@Override
	public String help(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(player.isOp()){
			SettingsManager.getInstance().setLobbySpawn(player.getLocation());
			player.sendMessage(ChatColor.AQUA + "Lobby Created!");
		}
		return true;
	}

}
