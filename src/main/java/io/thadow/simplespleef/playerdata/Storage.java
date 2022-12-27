package io.thadow.simplespleef.playerdata;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.api.storage.StorageType;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.storages.local.LocalStorage;
import io.thadow.simplespleef.playerdata.storages.mysql.MySQLConnection;
import io.thadow.simplespleef.playerdata.storages.mysql.MySQLStorage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class Storage {
    @Getter @Setter
    private StorageType storageType;
    @Getter
    private static final Storage storage = new Storage();
    @Getter @Setter
    private static boolean setupFinished = false;

    public void addWin(Player player) {
        if (getStorageType() == StorageType.LOCAL) {
            LocalStorage.addWin(player);
        } else if (getStorageType() == StorageType.MYSQL) {
            MySQLStorage.addWin(player);
        }
    }

    public void addLose(Player player) {
        if (getStorageType() == StorageType.LOCAL) {
            LocalStorage.addLose(player);
        } else if (getStorageType() == StorageType.MYSQL) {
            MySQLStorage.addLose(player);
        }
    }

    public void createPlayer(Player player) {
        if (getStorageType() == StorageType.LOCAL) {
            if (!LocalStorage.containsPlayer(player)) {
                LocalStorage.createPlayerData(player);
                PlayerDataManager.getManager().addPlayerData(new PlayerData(player.getName(), player.getUniqueId().toString(), 0, 0));
            }
        } else if (getStorageType() == StorageType.MYSQL) {
            if (!MySQLStorage.containsPlayer(player)) {
                MySQLStorage.createPlayerData(player);
                PlayerDataManager.getManager().addPlayerData(new PlayerData(player.getName(), player.getUniqueId().toString(), 0,0));
            }
        }
    }

    public void setupStorage(StorageType storageType) {
        if (storageType == StorageType.LOCAL) {
            LocalStorage.setup();
            setStorageType(StorageType.LOCAL);
        } else if (storageType == StorageType.MYSQL) {
            String host = Main.getConfiguration().getString("Configuration.Storage.MySQL.Host");
            int port = Main.getConfiguration().getInt("Configuration.Storage.MySQL.Port");
            String database = Main.getConfiguration().getString("Configuration.Storage.MySQL.Database");
            String username = Main.getConfiguration().getString("Configuration.Storage.MySQL.Username");
            String password = Main.getConfiguration().getString("Configuration.Storage.MySQL.Password");
            boolean ssl = Main.getConfiguration().getBoolean("Configuration.Storage.MySQL.SSL");
            (new MySQLConnection()).setup(host, port, database, username, password, ssl);
        }
    }

    public void save() {
        if (storageType == StorageType.LOCAL) {
            LocalStorage.savePlayers();
        }
    }
}