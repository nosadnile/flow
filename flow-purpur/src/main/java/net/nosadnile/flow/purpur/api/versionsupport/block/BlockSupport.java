package net.nosadnile.flow.purpur.api.versionsupport.block;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import javax.annotation.Nullable;

public interface BlockSupport {
    void setBlockData(Block paramBlock, byte paramByte);

    void setDurability(String paramString1, String paramString2, float paramFloat);

    @Nullable
    Block getBlockBehindSign(Block paramBlock);

    class SupportBuilder {
        @Nullable
        public static BlockSupport load() {
            Class<?> c;
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            try {
                c = Class.forName("com.andrei1058.spigot.versionsupport.block_" + version);
            } catch (ClassNotFoundException e) {
                return null;
            }
            try {
                return (BlockSupport) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
    }
}
