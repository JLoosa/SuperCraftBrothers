package org.mcsg.double0negative.supercraftbros.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.Game.State;
import org.mcsg.double0negative.supercraftbros.GameManager;

public class PlayerTeleportEvents implements Listener {
    @EventHandler
    public void teleportHandler(PlayerTeleportEvent e) {
	int game = GameManager.getInstance().getPlayerGameId(e.getPlayer());
	if (game != -1) {
	    Game g = GameManager.getInstance().getGame(game);
	    if (g.getState() == State.LOBBY) {
		e.setCancelled(true);
	    }
	}
    }
}
