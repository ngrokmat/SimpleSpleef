package io.thadow.simplespleef.utils.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MenusConfiguration extends ConfigurationFile {


    public MenusConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Menus.Arena Selector Menu.Title", "&bArenas");

            configuration.addDefault("Menus.Arena Selector Menu.Waiting.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Waiting.Data", 5);

            configuration.addDefault("Menus.Arena Selector Menu.Starting.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Starting.Data", 1);

            configuration.addDefault("Menus.Arena Selector Menu.Playing.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Playing.Data", 14);

            configuration.addDefault("Menus.Arena Selector Menu.Ending.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Ending.Data", 15);

            configuration.addDefault("Menus.Arena Selector Menu.Restarting.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Restarting.Data", 15);

            configuration.addDefault("Menus.Arena Selector Menu.Disabled.Material", "STAINED_GLASS");
            configuration.addDefault("Menus.Arena Selector Menu.Disabled.Data", 8);

            configuration.addDefault("Menus.Arena Selector.Options.Show Disabled Arenas", false);
            configuration.addDefault("Menus.Arena Selector.Options.Show Restarting Arenas", false);
            configuration.addDefault("Menus.Arena Selector.Options.Item Name", "&8> &a%arenaName%");

            List<String> lore_format = new ArrayList<>();
            lore_format.add("&7");
            lore_format.add("&7Status: %status%");
            lore_format.add("&7Players: &a%current%&7&a%max%");
            lore_format.add("&aClick to join!");
            lore_format.add("&7");

            configuration.addDefault("Menus.Arena Selector.Options.Lore Format", lore_format);

            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Next Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Next Page Button.Item Name", "&aNext page.");
            List<String> next_page_lore = new ArrayList<>();
            next_page_lore.add("&eClick to view the next page");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Next Page Button.Lore", next_page_lore);

            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Close Menu Button.Material", "REDSTONE");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Close Menu Button.Item Name", "&cClose menu");
            List<String> close_menu_lore = new ArrayList<>();
            close_menu_lore.add("&eClick to close this menu.");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Close Menu Button.Lore", close_menu_lore);

            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Previous Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Previous Page Button.Item Name", "&aBack page.");
            List<String> previous_page_lore = new ArrayList<>();
            previous_page_lore.add("&eClick to view the previous page.");
            configuration.addDefault("Menus.Arena Selector Menu.Navigation.Previous Page Button.Lore", previous_page_lore);

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
