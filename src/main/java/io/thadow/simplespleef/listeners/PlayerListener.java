package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.lib.scoreboard.Scoreboard;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.menu.Menu;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (!Storage.isSetupFinished()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Utils.format("&c[SimpleSpleef] \n &cData is not fully loaded, please wait. \n &cIf you keep getting this error for too long \n &ccontact an administrator."));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Storage.getStorage().createPlayer(event.getPlayer());
        PlayerDataManager.getManager().addSpleefPlayer(event.getPlayer());

        Player player = event.getPlayer();
        Utils.teleportToLobby(PlayerDataManager.getManager().getSpleefPlayer(player));
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
            if (arena.getStatus() == Status.PLAYING) {
                arena.killPlayer(spleefPlayer);
            }
            arena.removePlayer(spleefPlayer, spleefPlayer.isSpectating());
        }
        PlayerDataManager.getManager().removeSpleefPlayer(spleefPlayer);
        PartyManager.getManager().getInviting().remove(player);
    }

    @EventHandler
    public void onPlayerFoodLevel(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!Main.getConfiguration().getBoolean("Configuration.Global.No Hunger")) {
                return;
            }
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
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand.getType() == null || itemInHand.getType() == Material.AIR || event.getAction().equals(Action.PHYSICAL)) {
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Main.VERSION_HANDLER.isCustomItem(itemInHand)) {
                String data = Main.VERSION_HANDLER.getData(itemInHand);
                if (data.equalsIgnoreCase("ArenaSelectorItem")) {
                    event.setCancelled(true);
                    Menu.openMenu(player, MenuType.ARENA_SELECTOR_MENU, 1);
                    return;
                }
                if (data.equalsIgnoreCase("PartyItem")) {
                    event.setCancelled(true);
                    if (PartyManager.getManager().hasParty(player)) {
                        Menu.openMenu(player, MenuType.PARTY_MENU_OPTIONS, 1);
                        return;
                    }
                    Menu.openMenu(player, MenuType.PARTY_MENU_MAIN, 1);
                    return;
                }
                if (data.equalsIgnoreCase("Leave")) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(player, "leave");
                    return;
                }
                if (data.equalsIgnoreCase("PlayAgain")) {
                    event.setCancelled(true);
                    if (!ArenaManager.getManager().joinRandom(player, true)) {
                        String message = Utils.getMessage("Messages.Arenas.Play Again.No Arenas Available");
                        player.sendMessage(message);
                    }
                    return;
                }
                if (data.equalsIgnoreCase("LeaveToHub")) {
                    String server = Main.getConfiguration().getString("Configuration.Items.Lobby.Leave Item.Server To Connect");
                    Utils.sendPlayerTo(player, server);
                }
            }
        }
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
                if (!Main.getConfiguration().getBoolean("Configuration.Lobby.Cancel Damage")) {
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PartyManager.getManager().getInviting().contains(player)) {
            PartyManager.getManager().getInviting().remove(player);
            event.setCancelled(true);
            String toInvite = event.getMessage();
            Bukkit.dispatchCommand(player, "party invite " + toInvite);
        }
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) {
            return;
        }
        if (Main.getConfiguration().getBoolean("Configuration.Arenas.Per Arena Chat")) {
            Arena arena = ArenaManager.getManager().getPlayerArena(player);
            if (arena != null) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    Arena arenap = ArenaManager.getManager().getPlayerArena(players);
                    if (arenap == null) {
                        event.getRecipients().remove(players);
                    } else {
                        if (!arenap.getArenaID().equals(arena.getArenaID())) {
                            event.getRecipients().remove(players);
                        }
                    }
                }
            } else {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ArenaManager.getManager().getPlayerArena(players) != null) {
                        event.getRecipients().remove(players);
                    }
                }
            }
        }
    }
}
