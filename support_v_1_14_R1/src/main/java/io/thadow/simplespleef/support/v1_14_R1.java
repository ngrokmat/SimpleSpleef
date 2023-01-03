package io.thadow.simplespleef.support;

import io.thadow.simplespleef.api.server.VersionHandler;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class v1_14_R1 extends VersionHandler {

    public v1_14_R1(Plugin plugin, String versionName) {
        super(plugin, versionName);
    }

    @Override
    public void setBlockData(BlockState blockState, byte b) { }

    @Override
    public void setBackground(org.bukkit.block.BlockState b, org.bukkit.Material material) {
        b.getBlock().getRelative(((WallSign)b.getBlockData()).getFacing().getOppositeFace()).setType(material);
    }

    @Override
    public ItemStack addData(ItemStack i, String s) {
        net.minecraft.server.v1_14_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }
        tag.setString("SimpleSpleef", s);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public ItemStack setTag(ItemStack i, String s, String s1) {
        net.minecraft.server.v1_14_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }
        tag.setString(s, s1);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public String getTag(ItemStack i, String s) {
        net.minecraft.server.v1_14_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        return (tag == null) ? null : (tag.hasKey(s) ? tag.getString(s) : null);
    }

    @Override
    public boolean isCustomItem(ItemStack i) {
        net.minecraft.server.v1_14_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null)
            return false;
        return tag.hasKey("SimpleSpleef");
    }

    @Override
    public String getData(ItemStack i) {
        net.minecraft.server.v1_14_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null)
            return "";
        return tag.getString("SimpleSpleef");
    }

    @Override
    public ItemStack createItemStack(String material, int amount, short data) {
        return new ItemStack(Material.valueOf(material), amount);
    }

    @Override
    public ItemStack makeUnbreakable(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
