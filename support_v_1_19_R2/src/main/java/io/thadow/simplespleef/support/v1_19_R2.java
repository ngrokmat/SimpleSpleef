package io.thadow.simplespleef.support;

import io.thadow.simplespleef.api.server.VersionHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class v1_19_R2 extends VersionHandler {

    public v1_19_R2(Plugin plugin, String versionName) {
        super(plugin, versionName);
    }

    @Override
    public void setBlockData(BlockState blockState, byte b) { }

    @Override
    public void setBackground(org.bukkit.block.BlockState b, org.bukkit.Material material) {
        b.getBlock().getRelative(((WallSign) b.getBlockData()).getFacing().getOppositeFace()).setType(material);
    }

    @Override
    public ItemStack addData(ItemStack i, String s) {
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.u();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.c(tag);
        }
        tag.a("ParkourRun", s);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public ItemStack setTag(ItemStack i, String s, String s1) {
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.u();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.c(tag);
        }
        tag.a(s, s1);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public String getTag(ItemStack i, String s) {
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.u();
        return (tag == null) ? null : (tag.e(s) ? tag.l(s) : null);
    }

    @Override
    public boolean isCustomItem(ItemStack i) {
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.u();
        if (tag == null)
            return false;
        return tag.e("ParkourRun");
    }

    @Override
    public String getData(ItemStack i) {
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.u();
        if (tag == null)
            return "";
        return tag.l("ParkourRun");
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
