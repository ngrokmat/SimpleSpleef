package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.api.event.PlayerDeathInArenaEvent;
import io.thadow.simplespleef.api.event.PlayerJoinArenaEvent;
import io.thadow.simplespleef.api.event.PlayerLeaveArenaEvent;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.SignsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaEventsListener implements Listener {

    @EventHandler
    public void onPlayerJoinArenaEvent(PlayerJoinArenaEvent event) {
        Arena arena = event.getArena();
        SignsManager.getManager().updateSigns(arena);
    }

    @EventHandler
    public void onPlayerLeaveArenaEvent(PlayerLeaveArenaEvent event) {
        Arena arena = event.getArena();
        SignsManager.getManager().updateSigns(arena);
    }

    @EventHandler
    public void onPlayerDeathInArenaEvent(PlayerDeathInArenaEvent event) {
        Arena arena = event.getArena();
        SignsManager.getManager().updateSigns(arena);
    }
}
