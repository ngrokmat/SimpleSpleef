package io.thadow.simplespleef.playerdata.storages.local;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.Storage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;

public class LocalStorage {
    private static FileConfiguration configuration = null;
    private static File configurationFile = null;

    public static FileConfiguration get() {
        if (configuration == null) {
            reload();
        }
        return configuration;
    }

    public static void reload() {
        if (configuration == null)
            configurationFile = new File(Main.getInstance().getDataFolder(), "playersdata.yml");
        configuration = YamlConfiguration.loadConfiguration(configurationFile);
        Reader reader = new InputStreamReader(Main.getInstance().getResource("playersdata.yml"));
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(reader);
        configuration.setDefaults(yamlConfiguration);
    }

    public static void setup() {
        File configuration = new File(Main.getInstance().getDataFolder(), "playersdata.yml");
        if (!configuration.exists()) {
            Main.getInstance().saveResource("playersdata.yml", false);
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            try {
                yamlConfiguration.load(configuration);
            } catch (InvalidConfigurationException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void save() {
        try {
            configuration.save(configurationFile);
        }catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public static void createPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        get().set("Data." + uuid + ".Name", player.getName());
        get().set("Data." + uuid + ".Wins", 0);
        get().set("Data." + uuid + ".Losses", 0);
        save();
    }

    public static void removePlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        get().set("Data." + uuid + ".Name", null);
        get().set("Data." + uuid + ".Wins", null);
        get().set("Data." + uuid + ".Losses", null);
        get().set("Data." + uuid, null);
        save();
    }

    public static boolean containsPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        return get().contains("Data." + uuid + ".Name");
    }

    public static Integer getWins(Player player) {
        if (containsPlayer(player)) {
            String uuid = player.getUniqueId().toString();
            return get().getInt("Data." + uuid + ".Wins");
        }
        return 0;
    }

    public static Integer getLosses(Player player) {
        if (containsPlayer(player)) {
            String uuid = player.getUniqueId().toString();
            return get().getInt("Data." + uuid + ".Losses");
        }
        return 0;
    }

    public static void addWin(Player player) {
        String uuid = player.getUniqueId().toString();
        Integer currentWins = getWins(player);
        get().set("Data." + uuid + ".Wins", currentWins + 1);
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player.getName());
        playerData.addWin();
    }

    public static void addLose(Player player) {
        String uuid = player.getUniqueId().toString();
        Integer currentLosses = getLosses(player);
        get().set("Data." + uuid + ".Losses", currentLosses + 1);
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player.getName());
        playerData.addLose();
    }

    public static ArrayList<PlayerData> getPlayers() {
        ArrayList<PlayerData> players = new ArrayList<>();
        if (get().getConfigurationSection("Data") == null) {
            Storage.setSetupFinished(true);
            Main.getInstance().getLogger().log(Level.INFO, "Data has been fully loaded. Type: LOCAL");
            return players;
        }
        for (String uuid : get().getConfigurationSection("Data").getKeys(false)) {
            String playerName = get().getString("Data." + uuid + ".Name");
            int wins = get().getInt("Data." + uuid + ".Wins");
            int losses = get().getInt("Data." + uuid + ".Losses");
            players.add(new PlayerData(playerName, uuid, wins, losses));
        }
        Storage.setSetupFinished(true);
        Main.getInstance().getLogger().log(Level.INFO, "Data has been fully loaded. Type: LOCAL");
        return players;
    }

    public static void savePlayers() {
        if (PlayerDataManager.getManager().getPlayers() == null) {
            return;
        }
        for (PlayerData playerData : PlayerDataManager.getManager().getPlayers()) {
            String uuid = playerData.getUuid();
            String playerName = playerData.getPlayerName();
            int wins = playerData.getWins();
            int losses = playerData.getLosses();
            get().set("Data." + uuid + ".Name", playerName);
            get().set("Data." + uuid + ".Wins", wins);
            get().set("Data." + uuid + ".Losses", losses);
        }
        save();
    }
}
