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

public class PublicPartiesMenu {

    public static void open(Player player, int page) {
        if (PartyManager.getManager().getPublicParties().size() == 0) {
            player.closeInventory();
            String message = Utils.getMessage("Messages.Commands.Party Command.No Public Parties");
            player.sendMessage(message);
            return;
        }
        Inventory inventory = load(page);
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.PUBLIC_PARTY_MENU, page, 54));
    }

    protected static Inventory load(int page) {
        String title = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.format(title));

        int slot = 0;

        for (int i = 0; i < PartyManager.getManager().getPublicParties().size(); i++) {
            Party party = PartyManager.getManager().getPublicParties().get(i);
            if (party != null) {
                String material = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Format.Material");
                String name = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Format.Name");
                name = name.replace("%current%", String.valueOf(party.getSize()));
                name = name.replace("%max%", String.valueOf(party.getMaxSize()));
                name = name.replace("%leader%", party.getLeader().getName());
                List<String> lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Join Public Parties Menu.Format.Lore");
                lore = lore.stream().map(line -> line.replace("%current%", String.valueOf(party.getMembers().size()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%max%", String.valueOf(party.getMaxSize()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%leader%", party.getLeader().getName())).collect(Collectors.toList());
                ItemStack item = new ItemBuilder(Material.valueOf(material), 1)
                        .setDisplayName(name)
                        .setLore(lore)
                        .build();
                item = Main.VERSION_HANDLER.addData(item, "PartyOf=" + party.getLeader().getName());

                inventory.setItem(slot, item);
                slot++;

                if (slot > 44) {
                    break;
                }
            }
        }


        if (getTotalPages() > page) {
            String material = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Party Menus.Join Public Parties Menu.Navigation.Next Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page + 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "NextPage");
            inventory.setItem(53, item);
        }

        String back_material = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Material");
        String back_name = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Name");
        List<String> back_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Join Public Parties Menu.Navigation.Back Button.Lore");
        ItemStack back_item = new ItemBuilder(Material.valueOf(back_material), 1).setDisplayName(back_name).setLore(back_lore).build();
        back_item = Main.VERSION_HANDLER.addData(back_item, "BackItem");
        inventory.setItem(49, back_item);


        if (page > 1) {
            String material = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Party Menus.Join Public Parties Menu.Navigation.Previous Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page - 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "PreviousPage");
            inventory.setItem(45, item);
        }
        return inventory;
    }

    protected static int getTotalPages() {
        if (PartyManager.getManager().getPublicParties().size() % 45 == 0) {
            return (PartyManager.getManager().getPublicParties().size() / 45);
        } else {
            return (PartyManager.getManager().getPublicParties().size() / 45) + 1;
        }
    }
}
