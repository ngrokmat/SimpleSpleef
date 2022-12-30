package io.thadow.simplespleef.menu.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            lore_format.add("&7Players: &a%current%&7/&a%max%");
            lore_format.add("&7");
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

            configuration.addDefault("Menus.Party Menus.Main Menu.Title", "&8Party");
            configuration.addDefault("Menus.Party Menus.Main Menu.Create Party.Material", "BEACON");
            configuration.addDefault("Menus.Party Menus.Main Menu.Create Party.Name", "&aCreate");
            configuration.addDefault("Menus.Party Menus.Main Menu.Create Party.Lore", Collections.singletonList("&7Click to create a new party."));
            configuration.addDefault("Menus.Party Menus.Main Menu.Join Public Parties.Material", "CHEST");
            configuration.addDefault("Menus.Party Menus.Main Menu.Join Public Parties.Name", "&cJoin");
            configuration.addDefault("Menus.Party Menus.Main Menu.Join Public Parties.Lore", Collections.singletonList("&7Click to view the list of public parties."));

            configuration.addDefault("Menus.Party Menus.Options Menu.Title", "&8Party Options");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Members Item.Material", "PAPER");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Members Item.Name", "&dMembers &f%current%/%max%");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Members.Item.Leader Color", "&6");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Members.Item.Member Color", "&f");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Members Item.Lore Format", "&7- %color%%member_name%");

            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Material", "ENDER_PEARL");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Name", "&dPrivacy.");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Lore", Collections.singletonList("&cOnly invite."));

            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Material", "EYE_OF_ENDER");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Name", "&aPrivacy.");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Lore", Collections.singletonList("&aPublic."));

            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Invite Item.Material", "ENDER_CHEST");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Invite Item.Name", "&bInvite.");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Invite Item.Lore", "&7Click to invite a player.");

            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Leave Item.Material", "TNT");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Leave Item.Name", "&cLeave.");
            configuration.addDefault("Menus.Party Menus.Options Menu.Items.Leave Item.Lore", "&7Click to leave the party.");

            configuration.addDefault("Menus.Party Menus.Members Menu.Title", "&8Members");
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Name", Collections.singletonList("&aNext page"));
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Lore", Collections.singletonList("&aClick to view the next page."));
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Back Button.Material", "REDSTONE");
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Back Button.Name", "&cBack");
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Back Button.Lore", Collections.singletonList("&aClick to go back to the options menu."));
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Name", Collections.singletonList("&aPrevious page"));
            configuration.addDefault("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Lore", Collections.singletonList("&aClick to view the previous page."));
            configuration.addDefault("Menus.Party Menus.Members Menu.Format.Material", "PAPER");
            configuration.addDefault("Menus.Party Menus.Members Menu.Format.Leader Color", "&6");
            configuration.addDefault("Menus.Party Menus.Members Menu.Format.Member Color", "&f");
            configuration.addDefault("Menus.Party Menus.Members Menu.Format.Name", "%color%%member_name%");
            configuration.addDefault("Menus.Party Menus.Members Menu.Format.Lore", Arrays.asList("&aRight click to make this player the leader of the party", "&cLeft click to kick this player."));

            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Title", "&8PPublic parties");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Name", Collections.singletonList("&aNext page"));
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Lore", Collections.singletonList("&aClick to view the next page."));
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Material", "REDSTONE");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Name", "&cBack");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Lore", Collections.singletonList("&aClick to go back to the main menu."));
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Material", "ARROW");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Name", Collections.singletonList("&aPrevious page"));
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Lore", Collections.singletonList("&aClick to view the previous page."));
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Format.Material", "BOOK");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Format.Name", "&6Party of %leader%");
            configuration.addDefault("Menus.Party Menus.Join Public Parties Menu.Format.Lore", Arrays.asList("&7Privacy: &aPublic.", "&dMembers: &f%current%/%max%", "&7", "&eClick to join."));

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
