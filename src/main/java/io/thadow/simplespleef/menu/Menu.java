package io.thadow.simplespleef.menu;

import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.menu.menus.ArenaSelectorMenu;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;

public class Menu {
    @Getter
    private static final HashMap<Player, MenuInfo> playersInMenus = new HashMap<>();

    public void openMenu(Player player, String menu, int page) {
        switch (menu.toLowerCase()) {
            case "arena_selector":
                ArenaSelectorMenu.open(player, page);
        }
    }
}
