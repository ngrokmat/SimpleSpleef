package io.thadow.simplespleef.utils.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;

public class SignsConfiguration extends ConfigurationFile {
    public SignsConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Format", Arrays.asList("&a[arena]", "&2[current]&9/&2[max]", "[status]"));
            configuration.addDefault("Status.Disabled.Material", "STAINED_GLASS");
            configuration.addDefault("Status.Disabled.Data", 8);
            configuration.addDefault("Status.Waiting.Material", "STAINED_GLASS");
            configuration.addDefault("Status.Waiting.Data", 5);
            configuration.addDefault("Status.Starting.Material", "STAINED_GLASS");
            configuration.addDefault("Status.Starting.Data", 1);
            configuration.addDefault("Status.Playing.Material", "STAINED_GLASS");
            configuration.addDefault("Status.Playing.Data", 14);
            configuration.addDefault("Status.Ending.Material","STAINED_GLASS");
            configuration.addDefault("Status.Ending.Data", 15);
            configuration.addDefault("Status.Restarting.Material","STAINED_GLASS");
            configuration.addDefault("Status.Restarting.Data", 15);

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
