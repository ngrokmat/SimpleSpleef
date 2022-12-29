package io.thadow.simplespleef.api.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class MenuInfo {

    @Getter
    Player player;
    @Getter @Setter
    MenuType type;
    @Getter
    int page;
    @Getter
    int menuSize;

    public MenuInfo(Player player, MenuType type, int page, int menuSize) {
        this.player = player;
        this.type = type;
        this.page = page;
        this.menuSize = menuSize;
    }
}
