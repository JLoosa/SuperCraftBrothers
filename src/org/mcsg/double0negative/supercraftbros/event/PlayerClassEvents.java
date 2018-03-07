package org.mcsg.double0negative.supercraftbros.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcsg.double0negative.supercraftbros.Game;
import org.mcsg.double0negative.supercraftbros.Game.State;
import org.mcsg.double0negative.supercraftbros.GameManager;
import org.mcsg.double0negative.supercraftbros.SettingsManager;

import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;

public class PlayerClassEvents implements Listener {
    private PacketPlayInClientCommand respawn = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
    GameManager gm;
    
    public PlayerClassEvents() {
        gm = GameManager.getInstance();
    }
    
    @EventHandler
    public void blockFire(BlockIgniteEvent e) {
        System.out.println("lighing");
        final Block b = e.getBlock();
        Bukkit.getScheduler().scheduleSyncDelayedTask(GameManager.getInstance().getPlugin(), new Runnable() {
            @Override
			@SuppressWarnings("deprecation")
            public void run() {
                b.setTypeId(0);
                b.getState().update();
            }
        }, 60);
    }
    
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent e) {
        try {
            Player victim = null;
            Player attacker = null;
            if (e.getEntity() instanceof Player) {
                victim = (Player) e.getEntity();
            }
            if (e.getDamager() instanceof Player) {
                attacker = (Player) e.getDamager();
            }
            if (victim != null && attacker != null) {
                if (gm.getPlayerGameId(victim) != -1 && gm.getPlayerGameId(attacker) != -1) {
                    gm.getPlayerClass(victim).PlayerDamaged();
                    gm.getPlayerClass(attacker).PlayerAttack(victim);
                }
            }
        }
        catch (Exception et) {}
        
    }
    
    @EventHandler
    public void onEntityDamaged(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            int game = GameManager.getInstance().getPlayerGameId(p);
            if (game != -1) {
                Game g = gm.getGame(game);
                if (g.getState() == Game.State.INGAME) {
                    
                    g.getPlayerClassBase(p).PlayerDamaged();
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            int game = GameManager.getInstance().getPlayerGameId(p);
            if (game != -1) {
                gm.getGame(game).getPlayerClassBase(p).PlayerShootArrow(e.getProjectile());
            }
            
        }
    }
    
    @EventHandler
    public void onEntityDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = e.getEntity();
            
            int id = gm.getPlayerGameId(p);
            if (id != -1) {
                gm.getPlayerClass(p).PlayerDeath();
                gm.getGame(id).killPlayer(p, e.getDeathMessage());
                e.setDeathMessage(null);
                new BukkitRunnable() {
                    @Override
					public void run() {
                        ((CraftPlayer) p).getHandle().playerConnection.a(respawn);
                    }
                }.runTaskLater(gm.getPlugin(), 3);
            }
        }
    }
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        
        e.blockList().clear();
        Location l = e.getLocation();
        if (e.getEntity() instanceof Fireball) {
            System.out.println("cancel");
            e.setCancelled(true);
            l.getWorld().createExplosion(l, 4);
        }
    }
    
    @EventHandler
    public void onEntityRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(GameManager.getInstance().getPlugin(), new Runnable() {
            @Override
			public void run() {
                int id = gm.getPlayerGameId(p);
                if (id != -1) {
                    gm.getGame(id).spawnPlayer(p);
                    gm.getPlayerClass(p).PlayerSpawn();
                }
                else {
                    p.teleport(SettingsManager.getInstance().getLobbySpawn());
                }
            }
        }, 1);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        int id = gm.getPlayerGameId(p);
        if (id != -1) {
            Game g = gm.getGame(id);
            if (g.getState() == Game.State.INGAME) {
                if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE) {
                    g.getPlayerClassBase(p).Smash();
                }
                else {
                    g.getPlayerClassBase(p).PlayerInteract(e.getAction());
                }
            }
            // e.setCancelled(true);
        }
        
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        int id = gm.getPlayerGameId(p);
        if (id != -1) {
            Game g = gm.getGame(id);
            if (g.getState() == Game.State.INGAME) g.getPlayerClassBase(p).PlayerMove();
        }
    }
    
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e) {
        int id = gm.getPlayerGameId(e.getPlayer());
        if (id != -1) {
            if (gm.getGame(id).getState() == State.INGAME) gm.getPlayerClass(e.getPlayer()).PlayerPlaceBlock(e.getBlock());
        }
    }
    
}
