package io.thadow.simplespleef.items;

import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import lombok.Getter;
import org.bukkit.entity.Player;

public class ItemGiver {
    @Getter
    private static ItemGiver giver = new ItemGiver();

    public void giveLobbyItems(Player player) {

    }

    public void giveArenaWaitingItems(Player player) {

    }

    public void giveSpectatorItems(SpleefPlayer spleefPlayer) {
        Player player = spleefPlayer.getPlayer();
    }
}
