package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.api.storage.StorageType;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.playerdata.storages.local.LocalStorage;
import io.thadow.simplespleef.playerdata.storages.mysql.MySQLConnection;
import io.thadow.simplespleef.playerdata.storages.mysql.MySQLStorage;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDataManager {
    @Getter
    private static PlayerDataManager manager = new PlayerDataManager();
    @Getter
    private ArrayList<PlayerData> players;
    @Getter
    private final HashMap<Player, SpleefPlayer> spleefPlayers = new HashMap<>();

    public void loadPlayers() {
        if (Storage.getStorage().getStorageType() == StorageType.LOCAL) {
            players = LocalStorage.getPlayers();
        } else if (Storage.getStorage().getStorageType() == StorageType.MYSQL) {
            players = MySQLStorage.getPlayers();
        }
    }

    public void savePlayers() {
        if (Storage.getStorage().getStorageType() == StorageType.LOCAL) {
            LocalStorage.savePlayers();
        }
    }

    public PlayerData getPlayerData(String name) {
        for (PlayerData data : players) {
            if (data.getPlayerName().equalsIgnoreCase(name)) {
                return data;
            }
        }
        return null;
    }

    public SpleefPlayer getSpleefPlayer(Player player) {
        for (SpleefPlayer spleefPlayer : spleefPlayers.values()) {
            if (spleefPlayer.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                return spleefPlayer;
            }
        }
        return null;
    }

    public void addSpleefPlayer(Player player) {
        if (getSpleefPlayer(player) == null) {
            spleefPlayers.put(player, new SpleefPlayer(player, getPlayerData(player.getName())));
            return;
        }
        spleefPlayers.put(player, getSpleefPlayer(player));
    }

    public void removeSpleefPlayer(SpleefPlayer player) {
        spleefPlayers.remove(player.getPlayer());
    }

    public void addPlayerData(PlayerData data) {
        players.add(data);
    }

    public void addPlayerWin(Player player) {
        Storage.getStorage().addWin(player);
        addSpleefPlayer(player);
    }

    public void addPlayerLose(Player player) {
        Storage.getStorage().addLose(player);
        addSpleefPlayer(player);
    }

    public int getPlayerWins(Player player) {
        return getPlayerData(player.getName()).getWins();
    }

    public int getPlayerLosses(Player player) {
        return getPlayerData(player.getName()).getLosses();
    }
}
