package io.thadow.simplespleef.playerdata.storages.mysql;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.api.storage.mysql.Callback;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.playerdata.Storage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLStorage {

    public static void createTable() {
        try {
            PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ssp_data (`UUID` varchar(200), `PLAYER_NAME` varchar(50), `WINS` INT(5), `LOSSES` INT(5))");
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<PlayerData> getPlayers() {
        ArrayList<PlayerData> playerData = new ArrayList<>();
        try {
            PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("SELECT * FROM ssp_data");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("UUID");
                String player_name = resultSet.getString("PLAYER_NAME");
                int wins = resultSet.getInt("WINS");
                int losses = resultSet.getInt("LOSSES");
                playerData.add(new PlayerData(player_name, uuid, wins, losses));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        Storage.setSetupFinished(true);
        return playerData;
    }

    public static void getPlayer(String uuid, Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            int wins = 0;
            int losses = 0;
            String name = "";
            boolean found = false;
            try {
                PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("SELECT * FROM ssp_data WHERE uuid=?");
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    name = resultSet.getString("PLAYER_NAME");
                    wins = resultSet.getInt("WINS");
                    losses = resultSet.getInt("LOSSES");
                    found = true;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            if (found) {
                PlayerData playerData = new PlayerData(name, uuid, wins, losses);
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> callback.onEnd(playerData));
            } else {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> callback.onEnd(null));
            }
        });
    }

    public static boolean containsPlayer(Player player) {
        try {
            PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("SELECT * FROM ssp_data WHERE (uuid=?)");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static void createPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("INSERT INTO ssp_data (UUID,PLAYER_NAME,WINS,LOSSES) VALUE (?,?,?,?)");
                statement.setString(1, player.getUniqueId().toString());
                statement.setString(2, player.getName());
                statement.setInt(3,0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void updatePlayer(PlayerData playerData) {
        String uuid = playerData.getUuid();
        String player_name = playerData.getPlayerName();
        int wins = playerData.getWins();
        int losses = playerData.getLosses();

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement statement = MySQLConnection.getConnection().prepareStatement("UPDATE ssp_data SET player_name=?, wins=?, losses=? WHERE (uuid=?)");
                statement.setString(1, player_name);
                statement.setInt(2, wins);
                statement.setInt(3, losses);
                statement.setString(4, uuid);
                statement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void addWin(Player player) {
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player.getName());
        playerData.addWin();
        updatePlayer(playerData);
    }

    public static void addLose(Player player) {
        PlayerData playerData = PlayerDataManager.getManager().getPlayerData(player.getName());
        playerData.addLose();
        updatePlayer(playerData);
    }
}
