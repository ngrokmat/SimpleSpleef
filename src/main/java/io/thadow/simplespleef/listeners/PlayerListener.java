package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.lib.scoreboard.Scoreboard;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.Storage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Storage.getStorage().createPlayer(event.getPlayer());
        PlayerDataManager.getManager().addSpleefPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
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
}
