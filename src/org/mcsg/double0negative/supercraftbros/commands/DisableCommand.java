package org.mcsg.double0negative.supercraftbros.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.GameManager;

public class DisableCommand implements SubCommand{

	@Override
	public String help(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(player.isOp()){
			
			if(args.length == 1){
				int i = Integer.parseInt(args[0]);
				GameManager.getInstance().getGame(i).disable();
				player.sendMessage(ChatColor.AQUA + "Arena " + i + " Disabled!");
			}
			else if(args.length == 0){
				for(Game g: GameManager.getInstance().getGames()){
					g.disable();
				}
			}
			
			
			
			
			
		}
		return true;
	}

}
