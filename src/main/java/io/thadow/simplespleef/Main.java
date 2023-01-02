package io.thadow.simplespleef;

import io.thadow.simplespleef.api.server.VersionHandler;
import io.thadow.simplespleef.api.storage.StorageType;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.commands.*;
import io.thadow.simplespleef.lib.scoreboard.Scoreboard;
import io.thadow.simplespleef.listeners.*;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.managers.ScoreboardManager;
import io.thadow.simplespleef.menu.Menu;
import io.thadow.simplespleef.menu.configuration.MenusConfiguration;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.utils.Utils;
import io.thadow.simplespleef.utils.configuration.MainConfiguration;
import io.thadow.simplespleef.utils.configuration.ScoreboardsConfiguration;
import io.thadow.simplespleef.utils.configuration.SignsConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    public static VersionHandler VERSION_HANDLER;
    private static boolean supported;
    private static final String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
    @Getter
    private static MainConfiguration configuration;
    @Getter
    private static SignsConfiguration signsConfiguration;
    @Getter
    private static ScoreboardsConfiguration scoreboardsConfiguration;
    @Getter @Setter
    private static Location lobbyLocation;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void onLoad() {
        super.onLoad();
        Class clazz;

        try {
            clazz = Class.forName("io.thadow.simplespleef.support." + version);
        } catch (ClassNotFoundException exception) {
            getLogger().log(Level.INFO, "Unsupported minecraft version: " + version);
            supported = false;
            return;
        }
        try {
            getLogger().log(Level.INFO, "Loading support for: " + version);
            VERSION_HANDLER = (VersionHandler) clazz.getConstructor(Class.forName("org.bukkit.plugin.Plugin"), String.class).newInstance(this, version);
            supported = true;
        } catch (InstantiationException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException exception) {
            supported = false;
            getLogger().log(Level.INFO, "An error occurred while loading the support of your version: " + version);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(Utils.format("&cSimpleSpleef has been reloaded"));
        }

        if (!supported) {
            getLogger().log(Level.SEVERE, "Unsupported minecraft version: " + version);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;

        configuration = new MainConfiguration("configuration", getDataFolder().toString());
        signsConfiguration = new SignsConfiguration("signs", getDataFolder().toString());
        scoreboardsConfiguration = new ScoreboardsConfiguration("scoreboards", getDataFolder().toString());
        Menu.configuration = new MenusConfiguration("menus", getDataFolder().toString());
        lobbyLocation = Utils.getLocationFromConfig(configuration, "Configuration.Lobby.Location");
        StorageType storageType = StorageType.valueOf(configuration.getString("Configuration.Storage.Type"));
        Storage.getStorage().setupStorage(storageType);

        if (Storage.isStorageError()) {
            return;
        }

        registerCommand(new MainCommand(), "simplespleef");
        registerCommand(new JoinCommand(), "join");
        registerCommand(new LeaveCommand(), "leave");
        registerCommand(new BuildCommand(), "build");
        registerCommand(new PartyCommand(), "party");
        registerListeners(
                new SignsListener(),
                new PlayerListener(),
                new ArenaListener(),
                new MenusListener(),
                new ArenaEventsListener(),
                new PartyListener());
        PlayerDataManager.getManager().loadPlayers();
        ArenaManager.getManager().loadArenas();
        ScoreboardManager.getManager().run();
        Storage.getStorage().startSaveTask();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (Arena arena : ArenaManager.getManager().getArenas()) {
            arena.end(true, false);
        }
        Storage.getStorage().save();
        Bukkit.getScheduler().cancelTasks(this);
        Scoreboard.scoreboards.clear();
    }

    private void registerCommand(CommandExecutor commandExecutor, String key) {
        getCommand(key).setExecutor(commandExecutor);
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getInstance().getServer().getPluginManager().registerEvents(listener, instance));
    }
}
