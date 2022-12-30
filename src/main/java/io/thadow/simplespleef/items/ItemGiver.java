package io.thadow.simplespleef.items;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemGiver {
    @Getter
    private static final ItemGiver giver = new ItemGiver();

    public void giveLobbyItems(Player player) {
        if (Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Arena Selector Item.Enabled")) {
            String arena_selector_material = Main.getConfiguration().getString("Configuration.Items.Lobby.Arena Selector Item.Material");
            int arena_selector_slot = Main.getConfiguration().getInt("Configuration.Items.Lobby.Arena Selector Item.Slot");
            String arena_selector_name = Main.getConfiguration().getString("Configuration.Items.Lobby.Arena Selector Item.Name");
            boolean arena_item_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Arena Selector Item.Unbreakable");
            int arena_selector_item_amount = Main.getConfiguration().getInt("Configuration.Items.Lobby.Arena Selector Item.Amount");
            List<String> arena_selector_lore = Main.getConfiguration().getStringList("Configuration.Items.Lobby.Arena Selector Item.Lore");
            ItemStack arena_selector_item = new ItemBuilder(Material.valueOf(arena_selector_material), arena_selector_item_amount)
                    .setDisplayName(arena_selector_name)
                    .setLore(arena_selector_lore)
                    .setUnbreakable(arena_item_unbreakable)
                    .build();
            arena_selector_item = Main.VERSION_HANDLER.addData(arena_selector_item, "ArenaSelectorItem");
            player.getInventory().setItem((arena_selector_slot - 1), arena_selector_item);
        }

        if (Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Party Item.Enabled")) {
            String party_material = Main.getConfiguration().getString("Configuration.Items.Lobby.Party Item.Material");
            int party_slot = Main.getConfiguration().getInt("Configuration.Items.Lobby.Party Item.Slot");
            String party_name = Main.getConfiguration().getString("Configuration.Items.Lobby.Party Item.Name");
            boolean party_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Party Item.Unbreakable");
            int party_item_amount = Main.getConfiguration().getInt("Configuration.Items.Lobby.Party Item.Amount");
            List<String> party_lore = Main.getConfiguration().getStringList("Configuration.Items.Lobby.Party Item.Lore");
            ItemStack party_item = new ItemBuilder(Material.valueOf(party_material), party_item_amount)
                    .setDisplayName(party_name)
                    .setLore(party_lore)
                    .setUnbreakable(party_unbreakable)
                    .build();
            party_item = Main.VERSION_HANDLER.addData(party_item, "PartyItem");
            player.getInventory().setItem((party_slot - 1), party_item);
        }
        if (Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Leave Item.Enabled")) {
            String leave_material = Main.getConfiguration().getString("Configuration.Items.Lobby.Leave Item.Material");
            int leave_slot = Main.getConfiguration().getInt("Configuration.Items.Lobby.Leave Item.Slot");
            String leave_name = Main.getConfiguration().getString("Configuration.Items.Lobby.Leave Item.Name");
            boolean leave_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Lobby.Leave Item.Unbreakable");
            int leave_item_amount = Main.getConfiguration().getInt("Configuration.Items.Lobby.Leave Item.Amount");
            List<String> leave_lore = Main.getConfiguration().getStringList("Configuration.Items.Lobby.Leave Item.Lore");
            ItemStack leave_item = new ItemBuilder(Material.valueOf(leave_material), leave_item_amount)
                    .setDisplayName(leave_name)
                    .setLore(leave_lore)
                    .setUnbreakable(leave_unbreakable)
                    .build();
            leave_item = Main.VERSION_HANDLER.addData(leave_item, "LeaveToHub");
            player.getInventory().setItem((leave_slot - 1), leave_item);
        }
    }

    public void giveArenaWaitingItems(Player player) {
        if (Main.getConfiguration().getBoolean("Configuration.Items.Waiting Items.Leave Item.Enabled")) {
            String leave_material = Main.getConfiguration().getString("Configuration.Items.Waiting Items.Leave Item.Material");
            int leave_slot = Main.getConfiguration().getInt("Configuration.Items.Waiting Items.Leave Item.Slot");
            String leave_name = Main.getConfiguration().getString("Configuration.Items.Waiting Items.Leave Item.Name");
            boolean leave_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Waiting Items.Leave Item.Unbreakable");
            int leave_item_amount = Main.getConfiguration().getInt("Configuration.Items.Waiting Items.Leave Item.Amount");
            List<String> leave_lore = Main.getConfiguration().getStringList("Configuration.Items.Waiting Items.Leave Item.Lore");
            ItemStack leave_item = new ItemBuilder(Material.valueOf(leave_material), leave_item_amount)
                    .setDisplayName(leave_name)
                    .setLore(leave_lore)
                    .setUnbreakable(leave_unbreakable)
                    .build();
            leave_item = Main.VERSION_HANDLER.addData(leave_item, "Leave");
            player.getInventory().setItem((leave_slot - 1), leave_item);
        }
    }

    public void giveArenaPlayingItems(Player player) {
        if (Main.getConfiguration().getBoolean("Configuration.Items.Playing Items.Leave Item.Enabled")) {
            String leave_material = Main.getConfiguration().getString("Configuration.Items.Playing Items.Leave Item.Material");
            int leave_slot = Main.getConfiguration().getInt("Configuration.Items.Playing Items.Leave Item.Slot");
            String leave_name = Main.getConfiguration().getString("Configuration.Items.Playing Items.Leave Item.Name");
            boolean leave_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Playing Items.Leave Item.Unbreakable");
            int leave_item_amount = Main.getConfiguration().getInt("Configuration.Items.Playing Items.Leave Item.Amount");
            List<String> leave_lore = Main.getConfiguration().getStringList("Configuration.Items.Playing Items.Leave Item.Lore");
            ItemStack leave_item = new ItemBuilder(Material.valueOf(leave_material), leave_item_amount)
                    .setDisplayName(leave_name)
                    .setLore(leave_lore)
                    .setUnbreakable(leave_unbreakable)
                    .build();
            leave_item = Main.VERSION_HANDLER.addData(leave_item, "Leave");
            player.getInventory().setItem((leave_slot - 1), leave_item);
        }
    }

    public void giveSpectatorItems(Player player) {
        if (Main.getConfiguration().getBoolean("Configuration.Items.Spectator Items.Play Again Item.Enabled")) {
            String play_again_material = Main.getConfiguration().getString("Configuration.Items.Spectator Items.Play Again Item.Material");
            int play_again_slot = Main.getConfiguration().getInt("Configuration.Items.Spectator Items.Play Again Item.Slot");
            String play_again_name = Main.getConfiguration().getString("Configuration.Items.Spectator Items.Play Again Item.Name");
            boolean play_again_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Spectator Items.Play Again Item.Unbreakable");
            int play_again_amount = Main.getConfiguration().getInt("Configuration.Items.Spectator Items.Play Again Item.Amount");
            List<String> play_again_lore = Main.getConfiguration().getStringList("Configuration.Items.Spectator Items.Play Again Item.Lore");
            ItemStack play_again_item = new ItemBuilder(Material.valueOf(play_again_material), play_again_amount)
                    .setDisplayName(play_again_name)
                    .setLore(play_again_lore)
                    .setUnbreakable(play_again_unbreakable)
                    .build();
            play_again_item = Main.VERSION_HANDLER.addData(play_again_item, "PlayAgain");
            player.getInventory().setItem((play_again_slot - 1), play_again_item);
        }
        if (Main.getConfiguration().getBoolean("Configuration.Items.Spectator Items.Leave Item.Enabled")) {
            String leave_material = Main.getConfiguration().getString("Configuration.Items.Spectator Items.Leave Item.Material");
            int leave_slot = Main.getConfiguration().getInt("Configuration.Items.Spectator Items.Leave Item.Slot");
            String leave_name = Main.getConfiguration().getString("Configuration.Items.Spectator Items.Leave Item.Name");
            boolean leave_unbreakable = Main.getConfiguration().getBoolean("Configuration.Items.Spectator Items.Leave Item.Unbreakable");
            int leave_item_amount = Main.getConfiguration().getInt("Configuration.Items.Spectator Items.Leave Item.Amount");
            List<String> leave_lore = Main.getConfiguration().getStringList("Configuration.Items.Spectator Items.Leave Item.Lore");
            ItemStack leave_item = new ItemBuilder(Material.valueOf(leave_material), leave_item_amount)
                    .setDisplayName(leave_name)
                    .setLore(leave_lore)
                    .setUnbreakable(leave_unbreakable)
                    .build();
            leave_item = Main.VERSION_HANDLER.addData(leave_item, "Leave");
            player.getInventory().setItem((leave_slot - 1), leave_item);
        }
    }
}
