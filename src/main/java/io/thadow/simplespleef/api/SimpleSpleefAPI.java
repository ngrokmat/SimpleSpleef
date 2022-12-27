package io.thadow.simplespleef.api;

import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SimpleSpleefAPI {

    public static int getPlayerWins(String player_name) {
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player_name);
        return playerData != null ? playerData.getWins() : 0;
    }

    public static int getPlayerLosses(String player_name) {
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player_name);
        return playerData != null ? playerData.getLosses() : 0;
    }

    public static Arena getPlayerArena(Player player) {
        return ArenaManager.getManager().getPlayerArena(player) != null ? ArenaManager.getManager().getPlayerArena(player) : null;
    }

    public static List<Player> getArenaPlayers(Arena arena) {
        ArrayList<Player> players = new ArrayList<>();
        for (SpleefPlayer player : arena.getPlayers()) {
            players.add(player.getPlayer());
        }
        return players;
    }

    public static List<SpleefPlayer> getArenaSpleefPlayers(Arena arena) {
        return arena != null ? arena.getPlayers() : null;
    }
}
