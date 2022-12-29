package io.thadow.simplespleef;

import io.thadow.simplespleef.api.server.VersionHandler;
import io.thadow.simplespleef.api.storage.StorageType;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.commands.*;
import io.thadow.simplespleef.listeners.ArenaListener;
import io.thadow.simplespleef.listeners.PlayerListener;
import io.thadow.simplespleef.listeners.SignsListener;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.managers.ScoreboardManager;
import io.thadow.simplespleef.managers.SignsManager;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.utils.configuration.MainConfiguration;
import io.thadow.simplespleef.utils.configuration.MenusConfiguration;
import io.thadow.simplespleef.utils.configuration.ScoreboardsConfiguration;
import io.thadow.simplespleef.utils.configuration.SignsConfiguration;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
    @Getter
    private static MenusConfiguration menusConfiguration;

    @SuppressWarnings("unchecked")
    @Override
    public void onLoad() {
        super.onLoad();
        Class clazz;

        try {
            clazz = Class.forName("io.thadow.simplespleef.support." + version);
        } catch (ClassNotFoundException exception) {
            supported = false;
            return;
        }
        try {
            VERSION_HANDLER = (VersionHandler) clazz.getConstructor(Class.forName("org.bukkit.plugin.Plugin"), String.class).newInstance(this, version);
            supported = true;
        } catch (InstantiationException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (!supported) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        configuration = new MainConfiguration("configuration", getDataFolder().toString());
        signsConfiguration = new SignsConfiguration("signs", getDataFolder().toString());
        scoreboardsConfiguration = new ScoreboardsConfiguration("scoreboards", getDataFolder().toString());
        menusConfiguration = new MenusConfiguration("menus", getDataFolder().toString());
        StorageType storageType = StorageType.valueOf(configuration.getString("Configuration.Storage.Type"));
        Storage.getStorage().setupStorage(storageType);
        PlayerDataManager.getManager().loadPlayers();

        registerCommand(new MainCommand(), "simplespleef");
        registerCommand(new JoinCommand(), "join");
        registerCommand(new LeaveCommand(), "leave");
        registerCommand(new BuildCommand(), "build");
        registerCommand(new PartyCommand(), "party");
        registerListeners(new SignsListener(), new PlayerListener(), new ArenaListener());
        ArenaManager.getManager().loadArenas();
        ScoreboardManager.getManager().run();
        Storage.getStorage().startSaveTask();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (Arena arena : ArenaManager.getManager().getArenas()) {
            arena.end(true);
        }
        Storage.getStorage().save();
    }

    private void registerCommand(CommandExecutor commandExecutor, String key) {
        getCommand(key).setExecutor(commandExecutor);
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getInstance().getServer().getPluginManager().registerEvents(listener, instance));
    }
}
