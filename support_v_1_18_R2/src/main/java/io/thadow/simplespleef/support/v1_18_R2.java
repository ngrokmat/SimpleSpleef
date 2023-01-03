package io.thadow.simplespleef.support;

import io.thadow.simplespleef.api.server.VersionHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class v1_18_R2 extends VersionHandler {

    public v1_18_R2(Plugin plugin, String versionName) {
        super(plugin, versionName);
    }

    @Override
    public void setBlockData(BlockState blockState, byte b) { }

    @Override
    public void setBackground(org.bukkit.block.BlockState b, org.bukkit.Material material) {
        if (b.getBlockData() instanceof WallSign) {
            b.getBlock().getRelative(((WallSign) b.getBlockData()).getFacing().getOppositeFace()).setType(material);
        }
    }

    @Override
    public org.bukkit.inventory.ItemStack addData(org.bukkit.inventory.ItemStack i, String data) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.t();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.c(tag);
        }

        tag.a("SimpleSpleef", data);
        return CraftItemStack.asBukkitCopy(itemStack);
    }


    @Override
    public org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack itemStack, String key, String value) {
        ItemStack is = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = is.t();
        if (tag == null) {
            tag = new NBTTagCompound();
            is.c(tag);
        }

        tag.a(key, value);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public String getTag(org.bukkit.inventory.ItemStack itemStack, String key) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.t();
        return tag == null ? null : tag.e(key) ? tag.l(key) : null;
    }

    @Override
    public boolean isCustomItem(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.t();
        if (tag == null) return false;
        return tag.e("SimpleSpleef");
    }

    @Override
    public String getData(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.t();
        if (tag == null) return "";
        return tag.l("SimpleSpleef");
    }

    @Override
    public org.bukkit.inventory.ItemStack createItemStack(String material, int amount, short data) {
        return new org.bukkit.inventory.ItemStack(org.bukkit.Material.valueOf(material), amount);
    }

    @Override
    public org.bukkit.inventory.ItemStack makeUnbreakable(org.bukkit.inventory.ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
