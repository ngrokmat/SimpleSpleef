package io.thadow.simplespleef.menu.menus;

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

import java.util.List;
import java.util.stream.Collectors;

public class ArenaSelectorMenu {

    public static void open(Player player, int page) {
        Inventory inventory = load(player, page);
        player.openInventory(inventory);
        Menu.getPlayersInMenus().put(player, new MenuInfo(player, MenuType.ARENA_SELECTOR_MENU, 1, 54));
    }

    protected static Inventory load(Player player, int page) {
        String title = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Title");

        MenuInfo info = Menu.getPlayersInMenus().get(player);

        Inventory inventory = Bukkit.createInventory(null, 54, Utils.format(title));

        int slot = 0;

        for (int i = 45 * (page - 1); i < ArenaManager.getManager().getArenas().size(); i++) {
            Arena arena = ArenaManager.getManager().getArenas().get(i);
            if (arena != null) {
                ItemStack item = null;
                String name = Main.getMenusConfiguration().getString("Menus.Arena Selector.Options.Item Name");
                List<String> lore = Main.getMenusConfiguration().getStringList("Menus.Arena Selector.Options.Lore Format");
                lore = Utils.format(lore);
                lore = lore.stream().map(line -> line.replace("%status%", Utils.getFormattedStatus(arena))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%current%", String.valueOf(arena.getTotalPlayersSize()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%max%", String.valueOf(arena.getMaxPlayers()))).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%arenaID%", arena.getArenaID())).collect(Collectors.toList());
                lore = lore.stream().map(line -> line.replace("%arenaName%", arena.getArenaName())).collect(Collectors.toList());
                if (arena.getStatus() == Status.DISABLED) {
                    String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Disabled.Material");
                    int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Disabled.Data");
                    if (Main.getMenusConfiguration().getBoolean("Menus.Arena Selector.Options.Show Disabled Arenas")) {
                        item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                        item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                    }
                }
                if (arena.getStatus() == Status.WAITING) {
                    String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Waiting.Material");
                    int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Waiting.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                    item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                }
                if (arena.getStatus() == Status.STARTING) {
                    String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Starting.Material");
                    int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Starting.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                    item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                }
                if (arena.getStatus() == Status.PLAYING) {
                    String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Playing.Material");
                    int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Playing.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                    item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                }
                if (arena.getStatus() == Status.ENDING) {
                    String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Ending.Material");
                    int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Ending.Data");
                    item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                    item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                }
                if (arena.getStatus() == Status.RESTARTING) {
                    if (Main.getMenusConfiguration().getBoolean("Menus.Arena Selector.Options.Show Restarting Arenas")) {
                        String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Restarting.Material");
                        int data = Main.getMenusConfiguration().getInt("Menus.Arena Selector Menu.Restarting.Data");
                        item = new ItemBuilder(Material.valueOf(material), 1, (short) data).setDisplayName(name).setLore(lore).build();
                        item = Main.VERSION_HANDLER.addData(item, "arenaID=" + arena.getArenaID());
                    }
                }
                if (item != null) {
                    inventory.setItem(slot, item);
                    slot++;
                }
                if (slot > 45) {
                    break;
                }
            }
        }

        if (getTotalPages() < page) {
            String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Next Page Button.Material");
            String name = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Next Page Button.Item Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Next Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page + 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            inventory.setItem(54, item);
        }

        String close_material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Close Menu Button.Material");
        String close_name = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Close Menu Button.Item Name");
        List<String> close_lore = Main.getMenusConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Close Menu Button.Lore");
        ItemStack close_item = new ItemBuilder(Material.valueOf(close_material), 1).setDisplayName(close_name).setLore(close_lore).build();
        inventory.setItem(49, close_item);


        if (page < 1) {
            String material = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Previous Page Button.Material");
            String name = Main.getMenusConfiguration().getString("Menus.Arena Selector Menu.Navigation.Previous Page Button.Item Name");
            List<String> lore = Main.getConfiguration().getStringList("Menus.Arena Selector Menu.Navigation.Previous Page Button.Lore");
            lore = lore.stream().map(line -> line.replace("%page%", String.valueOf(page - 1))).collect(Collectors.toList());
            ItemStack item = new ItemBuilder(Material.valueOf(material), 1).setDisplayName(name).setLore(lore).build();
            inventory.setItem(45, item);
        }
        return inventory;
    }

    protected static int getTotalPages() {
        if (ArenaManager.getManager().getArenas().size() % 45 == 0) {
            return (ArenaManager.getManager().getArenas().size() / 45);
        } else {
            return (ArenaManager.getManager().getArenas().size() / 45) + 1;
        }
    }
}
