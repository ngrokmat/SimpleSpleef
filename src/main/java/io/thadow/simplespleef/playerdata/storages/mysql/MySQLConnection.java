package io.thadow.simplespleef.playerdata.storages.mysql;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.playerdata.Storage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class MySQLConnection {
    @Getter
    @Setter
    private static Connection connection;
    String host;
    int port;
    String database;
    String username;
    String password;
    boolean ssl;

    public void setup(String host, int port, String database, String username, String password, boolean ssl) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.ssl = ssl;
        connect();
    }

    public void connect() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                String url = "jdbc:mysql://" + host + "/" + database + "?useSSL=" + ssl + "&autoReconnect=true";
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection(url, username, password));
                Main.getInstance().getLogger().log(Level.INFO, "Database connected successfully.");
                MySQLStorage.createTable();
            }
        } catch (SQLException | ClassNotFoundException exception) {
            Storage.setStorageError(true);
            Main.getInstance().getLogger().log(Level.SEVERE, "An error occurred while connecting to the database: " + exception.getMessage());
            Main.getInstance().getLogger().log(Level.INFO, "Disabling to prevent errors...");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }
}
