package io.thadow.simplespleef.support;

import io.thadow.simplespleef.api.server.VersionHandler;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class v1_9_R2 extends VersionHandler {

    public v1_9_R2(Plugin plugin, String versionName) {
        super(plugin, versionName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBlockData(BlockState block, byte data) {
        block.getBlock().getRelative(((org.bukkit.material.Sign) block.getData()).getAttachedFace()).setData(data, true);
    }

    @Override
    public void setBackground(BlockState b, org.bukkit.Material material) {
        b.getLocation().getBlock().getRelative(((org.bukkit.material.Sign) b.getData()).getAttachedFace()).setType(material);
    }

    @Override
    public ItemStack addData(ItemStack i, String s) {
        net.minecraft.server.v1_9_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
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
        net.minecraft.server.v1_9_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
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
        net.minecraft.server.v1_9_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        return (tag == null) ? null : (tag.hasKey(s) ? tag.getString(s) : null);
    }

    @Override
    public boolean isCustomItem(ItemStack i) {
        net.minecraft.server.v1_9_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null)
            return false;
        return tag.hasKey("SimpleSpleef");
    }

    @Override
    public String getData(ItemStack i) {
        net.minecraft.server.v1_9_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null)
            return "";
        return tag.getString("SimpleSpleef");
    }

    @Override
    public ItemStack createItemStack(String material, int amount, short data) {
        return new ItemStack(Material.valueOf(material), amount, data);
    }

    @Override
    public ItemStack makeUnbreakable(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
