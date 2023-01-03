package io.thadow.simplespleef.items;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, short data) {
        itemStack = new ItemStack(material, amount, data);
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.setDisplayName(Utils.format(displayName));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        lore = Utils.format(lore);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (unbreakable) {
            itemStack = Main.VERSION_HANDLER.makeUnbreakable(itemStack);
        }
        return this;
    }

    public ItemBuilder addEnchantments(List<String> enchantments) {
        for (String string : enchantments) {
            String[] split = string.split(":");
            Enchantment enchantment = Enchantment.getByName(split[0]);
            int level = Integer.parseInt(split[1]);
            itemStack.addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
