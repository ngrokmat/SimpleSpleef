package io.thadow.simplespleef.utils.configuration;

import com.google.common.collect.Lists;
import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainConfiguration extends ConfigurationFile {

    public MainConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Configuration.Storage.Type", "LOCAL");
            configuration.addDefault("Configuration.Storage.MySQL.Host", "host");
            configuration.addDefault("Configuration.Storage.MySQL.Port", 1313);
            configuration.addDefault("Configuration.Storage.MySQL.Database", "database");
            configuration.addDefault("Configuration.Storage.MySQL.Username", "username");
            configuration.addDefault("Configuration.Storage.MySQL.Password", "password");
            configuration.addDefault("Configuration.Storage.MySQL.SSL", false);

            configuration.addDefault("Configuration.Arenas.Status.Waiting", "&aWAITING");
            configuration.addDefault("Configuration.Arenas.Status.Starting", "&6STARTING");
            configuration.addDefault("Configuration.Arenas.Status.Playing", "&cPLAYING");
            configuration.addDefault("Configuration.Arenas.Status.Ending", "&2ENDING");
            configuration.addDefault("Configuration.Arenas.Status.Restarting", "&1RESTARTING");
            configuration.addDefault("Configuration.Arenas.Status.Disabled", "&1DISABLED");

            configuration.addDefault("Configuration.Arenas.Nobody", "Nobody");

            List<String> mainUsage = new ArrayList<>();
            mainUsage.add("/sp createArena (id)");
            mainUsage.add("/sp deleteArena (id)");
            configuration.addDefault("Messages.Commands.Main Command.Usage", mainUsage);

            configuration.addDefault("Messages.Signs.Sign Added", "Sign added: %arenaID%");
            configuration.addDefault("Messages.Signs.Sign Removed", "Sign removed: %arenaID%");

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
