package io.thadow.simplespleef.arena.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;

public class ArenaConfiguration extends ConfigurationFile {
    public ArenaConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Enabled", false);
            configuration.addDefault("Arena Name", configName);
            configuration.addDefault("Max Players", 12);
            configuration.addDefault("Min Players", 1);
            configuration.addDefault("Max Time", 120);
            configuration.addDefault("Ending Time", 5);
            configuration.addDefault("Wait To Start Time", 15);
            configuration.addDefault("Re-Enable Time", 10);

            configuration.addDefault("Death Mode.Mode", "Y_LEVEL");
            configuration.addDefault("Death Mode.Touch Block Mode.Blocks", Collections.singletonList("OAK_LOG"));
            configuration.addDefault("Death Mode.Y Level Mode.Y Level", 20);

            configuration.addDefault("Teleport Death Mode.Mode", "SAME_LOCATION");

            configuration.addDefault("Spleef Mode.Mode", "SNOW");
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Snow.Enabled", true);
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Snow.Amount", 3);
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Egg.Enabled", false);
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Egg.Amount", 3);
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Bow.Enabled", false);
            configuration.addDefault("Spleef Mode.Snow Mode.Special To Give.Bow.Arrow Amount", 5);

            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Snow.Enabled", false);
            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Snow.Amount", false);
            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Egg.Enabled", true);
            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Egg.Amount", 3);
            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Bow.Enabled", false);
            configuration.addDefault("Spleef Mode.Wood Mode.Special To Give.Bow.Arrow Amount", 5);

            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Snow.Enabled", false);
            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Snow.Amount", 3);
            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Egg.Enabled", false);
            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Egg.Amount", 3);
            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Bow.Enabled", true);
            configuration.addDefault("Spleef Mode.Solid Mode.Special To Give.Bow.Arrow Amount", 5);

            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Snow.Enabled", false);
            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Snow.Amount", 3);
            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Egg.Enabled", false);
            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Egg.Amount", 3);
            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Bow.Enabled", true);
            configuration.addDefault("Spleef Mode.Other Mode.Special To Give.Bow.Arrow Amount", 5);

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
