package io.thadow.simplespleef.menu;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.menu.configuration.MenusConfiguration;
import io.thadow.simplespleef.menu.menus.arenaselector.ArenaSelectorMenu;
import io.thadow.simplespleef.menu.menus.party.PartyMainMenu;
import io.thadow.simplespleef.menu.menus.party.PartyMembersMenu;
import io.thadow.simplespleef.menu.menus.party.PartyOptionsMenu;
import io.thadow.simplespleef.menu.menus.party.PublicPartiesMenu;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Menu {
    @Getter
    private static final HashMap<Player, MenuInfo> playersInMenus = new HashMap<>();
    @Getter
    public static MenusConfiguration configuration;

    public static void openMenu(Player player, MenuType type, int page) {
        switch (type) {
            case ARENA_SELECTOR_MENU:
                ArenaSelectorMenu.open(player, page);
                break;
            case PARTY_MENU_MAIN:
                PartyMainMenu.open(player);
                break;
            case PARTY_MENU_OPTIONS:
                PartyOptionsMenu.open(player);
                break;
            case PARTY_MENU_MEMBERS:
                PartyMembersMenu.open(player, page);
                break;
            case PUBLIC_PARTY_MENU:
                PublicPartiesMenu.open(player, page);
                break;
        }
    }
}
