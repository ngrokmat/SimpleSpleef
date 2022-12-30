package io.thadow.simplespleef.api.event;

import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinArenaEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final Arena arena;
    @Getter
    private final Player player;
    @Getter
    private final SpleefPlayer spleefPlayer;

    public PlayerJoinArenaEvent(Arena arena, Player player, SpleefPlayer spleefPlayer) {
        this.arena = arena;
        this.player = player;
        this.spleefPlayer = spleefPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
