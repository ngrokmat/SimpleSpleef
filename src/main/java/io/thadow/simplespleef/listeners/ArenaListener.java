package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.api.arena.DeathMode;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.PlayerDataManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class ArenaListener implements Listener {

    @EventHandler
    public void checkTouchBlockDeathMode(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (spleefPlayer.isSpectating()) {
            return;
        }
        Arena arena = spleefPlayer.getArena();
        if (arena != null) {
            if (arena.getDeathMode().equals(DeathMode.TOUCH_BLOCK) && arena.getStatus() == Status.PLAYING) {
                List<String> blocks = arena.getConfiguration().getStringList("Death Mode.Touch Block Mode.Blocks");
                Location blockLocation = event.getTo().clone().subtract(0, 1, 0);
                Block block = blockLocation.getBlock();
                for (String type : blocks) {
                    if (block.getType().toString().equalsIgnoreCase(type)) {
                        arena.killPlayer(spleefPlayer);
                    }
                }
            }
        }
    }

    @EventHandler
    public void checkYLevelDeathMode(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (spleefPlayer.isSpectating()) {
            return;
        }
        Arena arena = spleefPlayer.getArena();
        if (arena != null) {
            if (arena.getDeathMode().equals(DeathMode.Y_LEVEL) && arena.getStatus() == Status.PLAYING) {
                int yLevel = arena.getYLevelMode();
                if (event.getTo().getY() <= yLevel) {
                    arena.killPlayer(spleefPlayer);
                }
            }
        }
    }

    @EventHandler
    public void checkDamageTakenDeathMode(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
            if (spleefPlayer.isSpectating()) {
                return;
            }
            Arena arena = spleefPlayer.getArena();
            if (arena != null) {
                if (arena.getDeathMode().equals(DeathMode.DAMAGE_TAKEN) && arena.getStatus() == Status.PLAYING) {
                    arena.killPlayer(spleefPlayer);
                }
            }
        }
    }

    @EventHandler
    public void preventPlayerDamageToOthers(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            SpleefPlayer spleefDamager = PlayerDataManager.getManager().getSpleefPlayer(damager);
            if (spleefDamager.isSpectating() && spleefDamager.getArena() != null) {
                event.setCancelled(true);
            }
            Player victim = (Player) event.getEntity();
            SpleefPlayer spleefVictim = PlayerDataManager.getManager().getSpleefPlayer(victim);
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && spleefVictim.getArena() != null) {
                event.setCancelled(true);
            }
        }
    }
}
