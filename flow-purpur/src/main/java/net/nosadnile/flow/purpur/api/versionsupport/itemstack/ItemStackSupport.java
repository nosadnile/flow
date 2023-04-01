package net.nosadnile.flow.purpur.api.versionsupport.itemstack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface ItemStackSupport {
    @Nullable
    ItemStack getInHand(Player paramPlayer);

    @Nullable
    ItemStack getInOffHand(Player paramPlayer);

    @Nullable
    ItemStack createItem(String paramString, int paramInt, byte paramByte);

    ItemStack createItem(Material paramMaterial, int paramInt, byte paramByte);

    ItemStack addTag(ItemStack paramItemStack, String paramString1, String paramString2);

    boolean hasTag(ItemStack paramItemStack, String paramString);

    @Nullable
    String getTag(ItemStack paramItemStack, String paramString);

    ItemStack removeTag(ItemStack paramItemStack, String paramString);

    void setUnbreakable(ItemStack paramItemStack, boolean paramBoolean);

    void minusAmount(Player paramPlayer, ItemStack paramItemStack, int paramInt);

    double getDamage(ItemStack paramItemStack);

    boolean isArmor(ItemStack paramItemStack);

    boolean isTool(ItemStack paramItemStack);

    boolean isSword(ItemStack paramItemStack);

    boolean isAxe(ItemStack paramItemStack);

    boolean isBow(ItemStack paramItemStack);

    boolean isProjectile(ItemStack paramItemStack);

    boolean isPlayerHead(ItemStack paramItemStack);

    ItemStack applyPlayerSkinOnHead(Player paramPlayer, ItemStack paramItemStack);

    ItemStack applySkinTextureOnHead(String paramString, ItemStack paramItemStack);

    default byte getItemData(ItemStack itemStack) {
        return 0;
    }

    class SupportBuilder {
        @Nullable
        public static ItemStackSupport load() {
            Class<?> c;
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            try {
                c = Class.forName("com.andrei1058.spigot.versionsupport.itemstack_" + version);
            } catch (ClassNotFoundException e) {
                return null;
            }
            try {
                return (ItemStackSupport) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
    }
}