package io.thadow.simplespleef.menu.menus.party;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.menu.Menu;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class PartyMembersMenu {

    public static void open(Player player, int page) {
        Inventory inventory = load(player, page);
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.PARTY_MENU_MEMBERS, page, 54));
    }

    protected static Inventory load(Player player, int page) {
        String title = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.format(title));
        Party party = PartyManager.getManager().getPlayerParty(player);
        if (party == null) {
            return null;
        }

        int slot = 0;

        for (int i = 45 * (page - 1); i < party.getMembers().size(); i++) {
            Player member = party.getMembers().get(i);
            if (member != null) {
                ItemStack item;
                String material = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Format.Material");
                String leader_color = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Format.Leader Color");
                String member_color = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Format.Member Color");
                List<String> lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Members Menu.Format.Lore");
                String name = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Format.Name");
                if (party.isLeader(member)) {
                    name = name.replace("%color%", leader_color);
                } else if (!party.isLeader(member)) {
                    name = name.replace("%color%", member_color);
                }
                name = name.replace("%member_name%", member.getName());
                item = new ItemBuilder(Material.valueOf(material), 1)
                        .setDisplayName(name)
                        .setLore(lore)
                        .build();
                item = Main.VERSION_HANDLER.addData(item, "MemberName=" + member.getName());

                inventory.setItem(slot, item);
                slot++;
                if (slot > 44) {
                    break;
                }
            }
        }

        if (getTotalPages(party) > page) {
            String material = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Party Menus.Members Menu.Navigation.Next Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page + 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "NextPage");
            inventory.setItem(53, item);
        }

        String back_material = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Back Button.Material");
        String back_name = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Back Button.Name");
        List<String> back_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Members Menu.Navigation.Back Button.Lore");
        ItemStack back_item = new ItemBuilder(Material.valueOf(back_material), 1).setDisplayName(back_name).setLore(back_lore).build();
        back_item = Main.VERSION_HANDLER.addData(back_item, "BackItem");
        inventory.setItem(49, back_item);


        if (page > 1) {
            String material = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Party Menus.Members Menu.Navigation.Previous Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page - 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "PreviousPage");
            inventory.setItem(45, item);
        }
        return inventory;
    }

    protected static int getTotalPages(Party party) {
        if (party.getMembers().size() % 45 == 0) {
            return (party.getMembers().size() / 45);
        } else {
            return (party.getMembers().size() / 45) + 1;
        }
    }
}
