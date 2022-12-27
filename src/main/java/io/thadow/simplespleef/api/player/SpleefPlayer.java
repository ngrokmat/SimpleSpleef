package io.thadow.simplespleef.api.player;

import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpleefPlayer {

    @Getter
    Player player;
    @Getter @Setter
    boolean spectating;
    @Getter @Setter
    PlayerData playerData;
    @Getter @Setter
    Arena arena = null;

    public SpleefPlayer(Player player, PlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
    }

    public void teleport(Location location) {
        player.teleport(location);
    }

    public void sendMessage(String message) {
        player.sendMessage(Utils.format(message));
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public String getName() {
        return player.getName();
    }
}
