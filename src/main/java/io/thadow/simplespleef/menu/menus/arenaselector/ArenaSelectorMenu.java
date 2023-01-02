package io.thadow.simplespleef.menu.menus.arenaselector;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.menu.MenuInfo;
import io.thadow.simplespleef.api.menu.MenuType;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.managers.ArenaManager;
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

public class ArenaSelectorMenu {

    public static void open(Player player, int page) {
        Inventory inventory = load(page);
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.ARENA_SELECTOR_MENU, page, 54));
    }

    protected static Inventory load(int page) {
        String title = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.format(title));

        int slot = 0;

        for (int i = 45 * (page - 1); i < ArenaManager.getManager().getArenas().size(); i++) {
            Arena arena = ArenaManager.getManager().getArenas().get(i);
            if (arena != null) {
                ItemStack item = null;
                String name = Menu.getConfiguration().getString("Menus.Arena Selector.Options.Item Name");
                name = name.replace("%arenaName%", arena.getArenaName());
                List<String> lore = Menu.getConfiguration().getStringList("Menus.Arena Selector.Options.Lore Format");
                lore = Utils.format(lore);
                lore = lore.stream().map(line -> line.replace("%status%", Utils.getFormattedStatus(arena))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%current%", String.valueOf(arena.getTotalPlayersSize()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%max%", String.valueOf(arena.getMaxPlayers()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%arenaID%", arena.getArenaID())).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%arenaName%", arena.getArenaName())).collect(Collectors.toList());
                if (arena.getStatus() == Status.DISABLED) {
                    String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Disabled.Material");
                    int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Disabled.Data");
                    if (Menu.getConfiguration().getBoolean("Menus.Arena Selector.Options.Show Disabled Arenas")) {
                        item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                    }
                }
                if (arena.getStatus() == Status.WAITING) {
                    String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Waiting.Material");
                    int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Waiting.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                }
                if (arena.getStatus() == Status.STARTING) {
                    String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Starting.Material");
                    int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Starting.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                }
                if (arena.getStatus() == Status.PLAYING) {
                    String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Playing.Material");
                    int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Playing.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                }
                if (arena.getStatus() == Status.ENDING) {
                    String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Ending.Material");
                    int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Ending.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                }
                if (item != null) {
                    if (arena.getStatus() == Status.RESTARTING) {
                        if (Menu.getConfiguration().getBoolean("Menus.Arena Selector.Options.Show Restarting Arenas")) {
                            String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Restarting.Material");
                            int data = Menu.getConfiguration().getInt("Menus.Arena Selector Menu.Restarting.Data");
                            item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                        }
                    }
                    item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                    inventory.setItem(slot, item);
                    slot++;
                }
                if (slot > 44) {
                    break;
                }
            }
        }

        if (getTotalPages() > page) {
            String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Next Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Next Page Button.Item Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Next Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page + 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "NextPage");
            inventory.setItem(53, item);
        }

        String close_material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Close Menu Button.Material");
        String close_name = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Close Menu Button.Item Name");
        List<String> close_lore = Menu.getConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Close Menu Button.Lore");
        ItemStack close_item = new ItemBuilder(Material.valueOf(close_material), 1).setDisplayName(close_name).setLore(close_lore).build();
        close_item = Main.VERSION_HANDLER.addData(close_item, "CloseItem");
        inventory.setItem(49, close_item);


        if (page > 1) {
            String material = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Previous Page Button.Material");
            String name = Menu.getConfiguration().getString("Menus.Arena Selector Menu.Navigation.Previous Page Button.Item Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Previous Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page - 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            item = Main.VERSION_HANDLER.addData(item, "PreviousPage");
            inventory.setItem(45, item);
        }
        return inventory;
    }

    protected static int getTotalPages() {
        List<Arena> arenas = ArenaManager.getManager().getArenas();
        List<Arena> arenasToShow = new ArrayList<>();
        for (Arena arena : arenas) {
            if (arena.getStatus() == Status.DISABLED) {
                if (Menu.getConfiguration().getBoolean("Menus.Arena Selector.Options.Show Disabled Arenas")) {
                    arenasToShow.add(arena);
                }
            }
            if (arena.getStatus() == Status.RESTARTING) {
                if (Menu.getConfiguration().getBoolean("Menus.Arena Selector.Options.Show Restarting Arenas")) {
                    arenasToShow.add(arena);
                }
            }
            if (arena.getStatus() == Status.WAITING || arena.getStatus() == Status.STARTING || arena.getStatus() == Status.ENDING || arena.getStatus() == Status.PLAYING) {
                arenasToShow.add(arena);
            }
        }
        if (arenasToShow.size() % 45 == 0) {
            return (arenasToShow.size() / 45);
        } else {
            return (arenasToShow.size() / 45) + 1;
        }
    }
}
