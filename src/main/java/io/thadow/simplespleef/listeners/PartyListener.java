package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.managers.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PartyListener implements Listener {

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player target = (Player) event.getRightClicked();
            if (player.isSneaking()) {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.dispatchCommand(player, "party invite " + target.getName()));
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PartyManager.getManager().getInviting().contains(player)) {
            PartyManager.getManager().getInviting().remove(player);
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.dispatchCommand(player, "party invite " + event.getMessage()));
        }
    }
}
