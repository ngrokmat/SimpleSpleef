package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.lib.scoreboard.Scoreboard;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Storage.getStorage().createPlayer(event.getPlayer());
        PlayerDataManager.getManager().addSpleefPlayer(event.getPlayer());

        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0D);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PartyManager.getManager().hasParty(player)) {
            Party party = PartyManager.getManager().getPlayerParty(player);
            if (party.isLeader(player)) {
                PartyManager.getManager().disbandParty(party);
                return;
            }
            party.removeMember(player);
            for (Player member : party.getMembers()) {
                String lmessage = Utils.getMessage("Messages.Commands.Party Command.Player Left");
                lmessage = lmessage.replace("%target%", player.getName());
                member.sendMessage(lmessage);
            }
        }
        if (Scoreboard.scoreboards.containsKey(event.getPlayer().getUniqueId())) {
            Scoreboard scoreboard = Scoreboard.scoreboards.get(event.getPlayer().getUniqueId());
            scoreboard.delete();
            Scoreboard.scoreboards.remove(event.getPlayer().getUniqueId());
        }
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(event.getPlayer());
        if (PlayerDataManager.getManager().getSpleefPlayer(event.getPlayer()).getArena() != null) {
            Arena arena = spleefPlayer.getArena();
            arena.removePlayer(spleefPlayer);
        }
        PlayerDataManager.getManager().removeSpleefPlayer(spleefPlayer);
    }

    @EventHandler
    public void onPlayerFoodLevel(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (!Utils.getBuilders().contains(player) && spleefPlayer.getArena() == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (!Utils.getBuilders().contains(player) && spleefPlayer.getArena() == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (!Utils.getBuilders().contains(player) && spleefPlayer.getArena() == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (!Utils.getBuilders().contains(player) && spleefPlayer.getArena() == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
        if (!Utils.getBuilders().contains(player) && spleefPlayer.getArena() == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            SpleefPlayer spleefPlayer = PlayerDataManager.getManager().getSpleefPlayer(player);
            Arena arena = spleefPlayer.getArena();
            if (arena == null) {
                event.setCancelled(true);
            }
        }
    }
}
