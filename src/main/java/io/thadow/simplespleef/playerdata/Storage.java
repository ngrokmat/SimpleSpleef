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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Storage {
    @Getter @Setter
    private StorageType storageType;
    @Getter
    private static final Storage storage = new Storage();
    @Getter @Setter
    private static boolean setupFinished = false;
    @Getter @Setter
    private static boolean storageError = false;

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
            setStorageType(StorageType.LOCAL);
            LocalStorage.setup();
        } else if (storageType == StorageType.MYSQL) {
            String host = Main.getConfiguration().getString("Configuration.Storage.MySQL.Host");
            int port = Main.getConfiguration().getInt("Configuration.Storage.MySQL.Port");
            String database = Main.getConfiguration().getString("Configuration.Storage.MySQL.Database");
            String username = Main.getConfiguration().getString("Configuration.Storage.MySQL.Username");
            String password = Main.getConfiguration().getString("Configuration.Storage.MySQL.Password");
            boolean ssl = Main.getConfiguration().getBoolean("Configuration.Storage.MySQL.SSL");
            (new MySQLConnection()).setup(host, port, database, username, password, ssl);
            setStorageType(StorageType.MYSQL);
        }
    }

    public void startSaveTask() {
        if (storageType == StorageType.MYSQL) {
            return;
        }
        int delay = Main.getConfiguration().getInt("Configuration.Storage.Local.Save Every");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            save();
            Main.getInstance().getLogger().log(Level.INFO, "Data has been saved.");
        }, 0L, 20L * 60 * delay);
    }

    public void save() {
        if (storageType == StorageType.LOCAL) {
            LocalStorage.savePlayers();
        }
    }
}
