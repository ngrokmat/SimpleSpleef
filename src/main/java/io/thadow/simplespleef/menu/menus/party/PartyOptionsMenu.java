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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartyOptionsMenu {

    public static void open(Player player) {
        Party party = PartyManager.getManager().getPlayerParty(player);
        if (party == null) {
            return;
        }
        Inventory inventory = load(party);
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.PARTY_MENU_OPTIONS, 1, 27));
    }

    protected static Inventory load(Party party) {
        String title = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, 27, Utils.format(title));

        String members_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Members Item.Material");
        String members_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Members Item.Name");
        members_item_name = members_item_name.replace("%current%", String.valueOf(party.getMembers().size()));
        members_item_name = members_item_name.replace("%max%", String.valueOf(party.getMaxSize()));
        String leader_color = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Members.Item.Leader Color");
        String member_color = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Members.Item.Member Color");
        String members_item_lore_format = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Members Item.Lore Format");
        List<String> members_item_lore = new ArrayList<>();
        for (Player member : party.getMembers()) {
            if (party.isLeader(member)) {
                members_item_lore.add(members_item_lore_format);
                members_item_lore = members_item_lore.stream().map(line -> line.replace("%color%", leader_color)).collect(Collectors.toList());
            } else if (!party.isLeader(member)) {
                members_item_lore.add(members_item_lore_format);
                members_item_lore = members_item_lore.stream().map(line ->  line.replace("%color%", member_color)).collect(Collectors.toList());
            }
            members_item_lore = members_item_lore.stream().map(line -> line.replace("%member_name%", member.getName())).collect(Collectors.toList());
        }
        ItemStack members_item = new ItemBuilder(Material.valueOf(members_material), 1)
                .setDisplayName(members_item_name)
                .setLore(members_item_lore)
                .build();
        members_item = Main.VERSION_HANDLER.addData(members_item, "MembersItem");
        inventory.setItem(10, members_item);

        String privacy_item_material;
        String privacy_item_name;
        List<String> privacy_item_lore;
        String privacy;

        switch (party.getPrivacy()) {
            case PRIVATE:
                privacy = "PRIVATE";
                privacy_item_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Material");
                privacy_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Name");
                privacy_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Lore");
                break;
            case PUBLIC:
                privacy = "PUBLIC";
                privacy_item_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Material");
                privacy_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Name");
                privacy_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Lore");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + party.getPrivacy());
        }

        ItemStack privacy_item = new ItemBuilder(Material.valueOf(privacy_item_material), 1)
                .setDisplayName(privacy_item_name)
                .setLore(privacy_item_lore)
                .build();
        privacy_item = Main.VERSION_HANDLER.addData(privacy_item, "Privacy=" + privacy);
        inventory.setItem(12, privacy_item);

        String invite_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Invite Item.Material");
        String invite_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Invite Item.Name");
        List<String> invite_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Invite Item.Lore");
        ItemStack invite_item = new ItemBuilder(Material.valueOf(invite_material), 1)
                .setDisplayName(invite_item_name)
                .setLore(invite_item_lore)
                .build();
        invite_item = Main.VERSION_HANDLER.addData(invite_item, "InviteItem");
        inventory.setItem(14, invite_item);

        String leave_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Leave Item.Material");
        String leave_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Leave Item.Name");
        List<String> leave_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Leave Item.Lore");
        ItemStack leave_item = new ItemBuilder(Material.valueOf(leave_material), 1)
                .setDisplayName(leave_item_name)
                .setLore(leave_item_lore)
                .build();
        leave_item = Main.VERSION_HANDLER.addData(leave_item, "LeaveItem");
        inventory.setItem(16, leave_item);

        return inventory;
    }
}
