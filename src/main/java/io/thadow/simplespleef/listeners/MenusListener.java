package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.party.PartyPrivacy;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.menu.Menu;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenusListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Menu.getPlayersInMenus().remove((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onArenaSelectorClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getPlayersInMenus().containsKey(player)) {
                MenuInfo info = Menu.getPlayersInMenus().get(player);
                if (info.getType() == MenuType.ARENA_SELECTOR_MENU) {
                    event.setCancelled(true);
                    int page = info.getPage();
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                        return;
                    }
                    if (Main.VERSION_HANDLER.isCustomItem(clickedItem)) {
                        String data = Main.VERSION_HANDLER.getData(clickedItem);
                        if (data.equalsIgnoreCase("NextPage")) {
                            Menu.openMenu(player, MenuType.ARENA_SELECTOR_MENU, (page + 1));
                            return;
                        }
                        if (data.equalsIgnoreCase("CloseItem")) {
                            player.closeInventory();
                            return;
                        }
                        if (data.equalsIgnoreCase("PreviousPage")) {
                            Menu.openMenu(player, MenuType.ARENA_SELECTOR_MENU, (page - 1));
                            return;
                        }
                        if (data.startsWith("arenaID=")) {
                            String[] data_split = data.split("=");
                            String arenaID = data_split[1];
                            Arena arena = ArenaManager.getManager().getArenaByID(arenaID);
                            if (arena == null) {
                                return;
                            }
                            ArenaManager.getManager().handleJoin(player, arena, false);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPartyMainMenuClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getPlayersInMenus().containsKey(player)) {
                MenuInfo info = Menu.getPlayersInMenus().get(player);
                if (info.getType() == MenuType.PARTY_MENU_MAIN) {
                    event.setCancelled(true);
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                        return;
                    }
                    if (Main.VERSION_HANDLER.isCustomItem(clickedItem)) {
                        String data = Main.VERSION_HANDLER.getData(clickedItem);
                        if (data.equalsIgnoreCase("CreateParty")) {
                            Bukkit.dispatchCommand(player, "party create");
                            Menu.openMenu(player, MenuType.PARTY_MENU_OPTIONS, 1);
                            return;
                        }
                        if (data.equalsIgnoreCase("JoinPublic")) {
                            Menu.openMenu(player, MenuType.PUBLIC_PARTY_MENU, 1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPartyOptionsMenuClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getPlayersInMenus().containsKey(player)) {
                MenuInfo info = Menu.getPlayersInMenus().get(player);
                if (info.getType() == MenuType.PARTY_MENU_OPTIONS) {
                    event.setCancelled(true);
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                        return;
                    }
                    if (Main.VERSION_HANDLER.isCustomItem(clickedItem)) {
                        String data = Main.VERSION_HANDLER.getData(clickedItem);
                        Party party = PartyManager.getManager().getPlayerParty(player);
                        if (party == null) {
                            player.closeInventory();
                            String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                            player.sendMessage(message);
                            return;
                        }
                        if (data.equalsIgnoreCase("MembersItem")) {
                            if (!party.isLeader(player)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                                player.sendMessage(message);
                                return;
                            }
                            Menu.openMenu(player, MenuType.PARTY_MENU_MEMBERS, 1);
                            return;
                        }
                        if (data.startsWith("Privacy=")) {
                            if (!party.isLeader(player)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                                player.sendMessage(message);
                                return;
                            }
                            String privacy = data.split("=")[1];
                            if (privacy.equalsIgnoreCase("PRIVATE")) {
                                String privacy_item_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Material");
                                String privacy_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Name");
                                List<String> privacy_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Privacy Item.Public.Lore");
                                ItemStack privacy_item = new ItemBuilder(Material.valueOf(privacy_item_material), 1)
                                        .setDisplayName(privacy_item_name)
                                        .setLore(privacy_item_lore)
                                        .build();
                                Bukkit.dispatchCommand(player, "party public");
                                privacy_item = Main.VERSION_HANDLER.addData(privacy_item, "Privacy=PUBLIC");
                                event.getInventory().setItem(12, privacy_item);
                                player.updateInventory();
                                return;
                            }
                            if (privacy.equalsIgnoreCase("PUBLIC")) {
                                String privacy_item_material = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Material");
                                String privacy_item_name = Menu.getConfiguration().getString("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Name");
                                List<String> privacy_item_lore = Menu.getConfiguration().getStringList("Menus.Party Menus.Options Menu.Items.Privacy Item.Private.Lore");
                                ItemStack privacy_item = new ItemBuilder(Material.valueOf(privacy_item_material), 1)
                                        .setDisplayName(privacy_item_name)
                                        .setLore(privacy_item_lore)
                                        .build();
                                Bukkit.dispatchCommand(player, "party private");
                                privacy_item = Main.VERSION_HANDLER.addData(privacy_item, "Privacy=PRIVATE");
                                event.getInventory().setItem(12, privacy_item);
                                player.updateInventory();
                                return;
                            }
                        }
                        if (data.equalsIgnoreCase("InviteItem")) {
                            if (!party.isLeader(player)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                                player.sendMessage(message);
                                return;
                            }
                            PartyManager.getManager().getInviting().add(player);
                            String message = Utils.getMessage("Messages.Commands.Party Command.Inviting Someone");
                            player.sendMessage(message);
                            player.closeInventory();
                            return;
                        }
                        if (data.equalsIgnoreCase("LeaveItem")) {
                            Bukkit.dispatchCommand(player, "party leave");
                            player.closeInventory();
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPublicPartiesMenuClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getPlayersInMenus().containsKey(player)) {
                MenuInfo info = Menu.getPlayersInMenus().get(player);
                if (info.getType() == MenuType.PUBLIC_PARTY_MENU) {
                    event.setCancelled(true);
                    int page = info.getPage();
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                        return;
                    }
                    if (Main.VERSION_HANDLER.isCustomItem(clickedItem)) {
                        String data = Main.VERSION_HANDLER.getData(clickedItem);
                        if (data.equalsIgnoreCase("NextPage")) {
                            Menu.openMenu(player, MenuType.PUBLIC_PARTY_MENU, (page + 1));
                            return;
                        }
                        if (data.equalsIgnoreCase("BackItem")) {
                            Menu.openMenu(player, MenuType.PARTY_MENU_MAIN, 1);
                            return;
                        }
                        if (data.equalsIgnoreCase("PreviousPage")) {
                            Menu.openMenu(player, MenuType.PUBLIC_PARTY_MENU, (page - 1));
                            return;
                        }
                        if (data.startsWith("PartyOf=")) {
                            Player leader = Bukkit.getPlayerExact(data.split("=")[1]);
                            if (leader == null) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Player Offline");
                                message = message.replace("%target%", data.split("=")[1]);
                                player.sendMessage(message);
                                return;
                            }
                            Party party = PartyManager.getManager().getPlayerParty(leader);
                            if (party == null) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.No Party Invited");
                                message = message.replace("%target%", leader.getName());
                                player.sendMessage(message);
                                return;
                            }
                            if (PartyManager.getManager().hasParty(player)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Already In Party");
                                player.sendMessage(message);
                                return;
                            }
                            if (!party.isLeader(leader)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Not Leader");
                                message = message.replace("%target%", leader.getName());
                                player.sendMessage(message);
                                return;
                            }
                            if (party.getPrivacy() == PartyPrivacy.PRIVATE) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Party Is Private");
                                player.sendMessage(message);
                                return;
                            }
                            if (party.getMembers().size() >= PartyManager.getMaxSize()) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Party Full");
                                player.sendMessage(message);
                                return;
                            }
                            for (Player member : party.getMembers()) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Player Joined");
                                message = message.replace("%target%", player.getName());
                                member.sendMessage(message);
                            }
                            party.addMember(player);
                            String message = Utils.getMessage("Messages.Commands.Party Command.Joined Public");
                            message = message.replace("%leader%", leader.getName());
                            player.sendMessage(message);
                            player.closeInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPartyMembersMenuClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getPlayersInMenus().containsKey(player)) {
                MenuInfo info = Menu.getPlayersInMenus().get(player);
                if (info.getType() == MenuType.PARTY_MENU_MEMBERS) {
                    event.setCancelled(true);
                    int page = info.getPage();
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                        return;
                    }
                    if (Main.VERSION_HANDLER.isCustomItem(clickedItem)) {
                        String data = Main.VERSION_HANDLER.getData(clickedItem);
                        if (data.equalsIgnoreCase("NextPage")) {
                            Menu.openMenu(player, MenuType.PARTY_MENU_MEMBERS, (page + 1));
                            return;
                        }
                        if (data.equalsIgnoreCase("BackItem")) {
                            Menu.openMenu(player, MenuType.PARTY_MENU_OPTIONS, 1);
                            return;
                        }
                        if (data.equalsIgnoreCase("PreviousPage")) {
                            Menu.openMenu(player, MenuType.PARTY_MENU_MEMBERS, (page - 1));
                            return;
                        }
                        if (event.getClick() == ClickType.RIGHT && data.startsWith("MemberName=")) {
                            String member_name = data.split("=")[1];
                            Bukkit.dispatchCommand(player, "party leader " + member_name);
                            player.closeInventory();
                            return;
                        }
                        if (event.getClick() == ClickType.LEFT && data.startsWith("MemberName=")) {
                            String member_name = data.split("=")[1];
                            Bukkit.dispatchCommand(player, "party kick " + member_name);
                            player.closeInventory();
                        }
                    }
                }
            }
        }
    }
}
