package io.thadow.simplespleef.api.playerdata;

import lombok.Getter;

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
