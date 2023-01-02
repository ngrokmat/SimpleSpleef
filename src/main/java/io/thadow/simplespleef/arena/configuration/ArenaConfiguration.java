package io.thadow.simplespleef.arena.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;

public class ArenaConfiguration extends ConfigurationFile {
    public ArenaConfiguration(String configName, String dir) {
        super(configName, dir);
        YamlConfiguration configuration = getConfiguration();

        configuration.addDefault("Enabled", false);
        configuration.addDefault("Arena Name", configName);
        configuration.addDefault("Max Players", 12);
        configuration.addDefault("Min Players", 1);
        configuration.addDefault("Max Time", 120);
        configuration.addDefault("Ending Time", 5);
        configuration.addDefault("Wait To Start Time", 60);
        configuration.addDefault("Full Short Time", 10);
        configuration.addDefault("Re-Enable Time", 10);

        configuration.addDefault("Death Mode.Mode", "Y_LEVEL");
        configuration.addDefault("Death Mode.Touch Block Mode.Blocks", Collections.singletonList("STONE"));
        configuration.addDefault("Death Mode.Y Level Mode.Y Level", 20);

        configuration.addDefault("Teleport Death Mode.Mode", "SAME_LOCATION");

        configuration.addDefault("Start Teleport Mode.Mode", "SAME_LOCATION");

        configuration.addDefault("Spleef Mode.Special To Give.Snow.Enabled", true);
        configuration.addDefault("Spleef Mode.Special To Give.Snow.Amount", 3);
        configuration.addDefault("Spleef Mode.Special To Give.Egg.Enabled", false);
        configuration.addDefault("Spleef Mode.Special To Give.Egg.Amount", 3);
        configuration.addDefault("Spleef Mode.Special To Give.Bow.Enabled", false);
        configuration.addDefault("Spleef Mode.Special To Give.Bow.Arrow Amount", 5);
        configuration.addDefault("Spleef Mode.Allowed Breakable Blocks", Collections.singletonList("SNOW"));

        configuration.addDefault("Inventory.Items.Slot 1.Material", "DIAMOND_SPADE");
        configuration.addDefault("Inventory.Items.Slot 1.Name", "&bDiamond Shovel");
        configuration.addDefault("Inventory.Items.Slot 1.Unbreakable", true);
        configuration.addDefault("Inventory.Items.Slot 1.Amount", 1);
        configuration.addDefault("Inventory.Items.Slot 1.Lore", Collections.singletonList(""));
        configuration.addDefault("Inventory.Items.Slot 1.Enchantments", Collections.singletonList("DIG_SPEED:5"));

        configuration.options().copyDefaults(true);
        save();
    }
}
