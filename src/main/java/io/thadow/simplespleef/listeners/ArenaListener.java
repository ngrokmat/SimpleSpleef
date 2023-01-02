package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.api.arena.DeathMode;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class ArenaListener implements Listener {

    @EventHandler
    public void checkArenaZone(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (spleefPlayer.getArena() != null) {
            Arena arena = spleefPlayer.getArena();
            if (arena.getCorner1() == null || arena.getCorner2() == null) {
                return;
            }
            Location toLocation = event.getTo();
            if (arena.getStatus() == Status.PLAYING || arena.getStatus() == Status.ENDING) {
                Utils.Region region = new Utils.Region(
                        new Vector(arena.getCorner1().getBlockX(), arena.getCorner1().getBlockY(), arena.getCorner1().getBlockZ()),
                        new Vector(arena.getCorner2().getBlockX(), arena.getCorner2().getBlockY(), arena.getCorner2().getBlockZ()));
                if (!region.isInside(toLocation)) {
                    player.teleport(arena.getSpawnLocation());
                }
            }
        }
    }

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
    public void preventDamageInStatus(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
            Arena arena = spleefPlayer.getArena();
            if (arena == null) {
                return;
            }
            if (arena.getStatus() == Status.WAITING
            || arena.getStatus() == Status.ENDING
            || arena.getStatus() == Status.STARTING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void preventDamagePlaying(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
            Arena arena = spleefPlayer.getArena();
            if (arena == null) {
                return;
            }
            if (arena.getStatus() == Status.PLAYING) {
                EntityDamageEvent.DamageCause cause = event.getCause();
                if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    event.setCancelled(true);
                }
                if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                    event.setCancelled(true);
                }
                if (cause == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }
                if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void checkDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            SpleefPlayer spleefDamager = PlayerDataManager.getManager().getSpleefPlayer(damager);
            Player victim = (Player) event.getEntity();
            SpleefPlayer spleefVictim = PlayerDataManager.getManager().getSpleefPlayer(victim);
            if (damager.getName().equalsIgnoreCase(victim.getName())) {
                event.setCancelled(true);
                return;
            }
            if (spleefDamager.isSpectating() && spleefDamager.getArena() != null) {
                event.setCancelled(true);
                return;
            }
            if (spleefVictim.isSpectating() && spleefVictim.getArena() != null) {
                event.setCancelled(true);
            }
            if (spleefDamager.getArena() == spleefVictim.getArena()) {
                event.setCancelled(true);
            }
        }
        if (event.getDamager() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getDamager();
            if (snowball.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player shooter = (Player) snowball.getShooter();
                if (victim == shooter) {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setDamage(0.01D);
            return;
        }
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player shooter = (Player) arrow.getShooter();
                if (victim == shooter) {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setDamage(0.01D);
            return;
        }
        if (event.getDamager() instanceof Egg) {
            Egg egg = (Egg) event.getDamager();
            if (egg.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player shooter = (Player) egg.getShooter();
                if (victim == shooter) {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setDamage(0.01D);
        }
    }


    @EventHandler
    public void checkBrokenBlocks(BlockBreakEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        Arena arena = spleefPlayer.getArena();
        if (arena != null) {
            if (spleefPlayer.isSpectating()) {
                event.setCancelled(true);
                return;
            }
            if (arena.getStatus() != Status.PLAYING) {
                event.setCancelled(true);
                return;
            }
            List<String> allowedBlocks = arena.getAllowedBreakableBlocks();
            Block block = event.getBlock();
            if (allowedBlocks.contains(block.getType().toString())) {
                if (arena.isSnowSpecialEnabled()) {
                    int amount = arena.getSnowSpecialAmount();
                    ItemStack snow = new ItemBuilder(Material.SNOW_BALL, amount).build();
                    player.getInventory().addItem(snow);
                }
                if (arena.isEggSpecialEnabled()) {
                    int amount = arena.getEggSpecialAmount();
                    ItemStack egg = new ItemBuilder(Material.EGG, amount).build();
                    player.getInventory().addItem(egg);
                }
                if (arena.isBowSpecialEnabled()) {
                    int amount = arena.getArrowSpecialAmount();
                    if (!player.getInventory().contains(Material.BOW)) {
                        ItemStack bow = new ItemBuilder(Material.BOW, 1).build();
                        player.getInventory().addItem(bow);
                    }
                    ItemStack arrows = new ItemBuilder(Material.ARROW, amount).build();
                    player.getInventory().addItem(arrows);
                }
                event.getBlock().getDrops().clear();
                arena.getBrokenBlocks().put(block.getLocation(), block.getType());
                event.getBlock().getLocation().getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
            }
            event.setCancelled(true);
        }
    }
}
