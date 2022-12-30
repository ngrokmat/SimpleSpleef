package io.thadow.simplespleef.menu.menus.party;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.menu.Menu;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PartyMainMenu {

    public static void open(Player player) {
        Inventory inventory = load();
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.PARTY_MENU_MAIN, 1, 27));
    }

    protected static Inventory load() {
        String title = Menu.getConfiguration().getString("Menus.Party Menus.Main Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, 27, Utils.format(title));

        String create_material = Menu.getConfiguration().getString("Menus.Party Menus.Main Menu.Create Party.Material");
        String create_name = Menu.getConfiguration().getString("Menus.Party Menus.Main Menu.Create Party.Name");
        List<String> create_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Main Menu.Create Party.Lore");
        ItemStack create_item = new ItemBuilder(Material.valueOf(create_material), 1).setDisplayName(create_name).setLore(create_lore).build();
        create_item = Main.VERSION_HANDLER.addData(create_item, "CreateParty");
        inventory.setItem(11, create_item);

        String join_material = Menu.getConfiguration().getString("Menus.Party Menus.Main Menu.Join Public Parties.Material");
        String join_name = Menu.getConfiguration().getString("Menus.Party Menus.Main Menu.Join Public Parties.Name");
        List<String> join_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Main Menu.Join Public Parties.Lore");
        ItemStack join_item = new ItemBuilder(Material.valueOf(join_material), 1).setDisplayName(join_name).setLore(join_lore).build();
        join_item = Main.VERSION_HANDLER.addData(join_item, "JoinPublic");
        inventory.setItem(15, join_item);

        return inventory;
    }
}
