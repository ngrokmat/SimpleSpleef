package io.thadow.simplespleef.api.playerdata;

import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.Storage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData {
    @Getter
    String playerName;
    @Getter
    String uuid;
    @Getter
    int wins;
    @Getter
    int losses;

    public PlayerData(String playerName, String uuid, int wins, int losses) {
        this.playerName = playerName;
        this.uuid = uuid;
        this.wins = wins;
        this.losses = losses;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLose() {
        this.losses++;
    }
}
