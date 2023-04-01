package net.nosadnile.flow.purpur.api.versionsupport.material;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import javax.annotation.Nullable;

public interface MaterialSupport {
    boolean isMaterial(String paramString);

    @Nullable
    Material getMaterial(String paramString);

    Material getMaterialOr(String paramString, Material paramMaterial);

    boolean isWool(Material paramMaterial);

    boolean isBed(Material paramMaterial);

    boolean isGlass(Material paramMaterial);

    boolean isGlassPane(Material paramMaterial);

    boolean isTerracotta(Material paramMaterial);

    boolean isConcrete(Material paramMaterial);

    boolean isConcretePowder(Material paramMaterial);

    @Nullable
    Material getForCurrent(String paramString1, String paramString2, String paramString3);

    boolean isCake(Material paramMaterial);

    boolean isSoil(Material paramMaterial);

    Material getSoil();

    class SupportBuilder {
        @Nullable
        public static MaterialSupport load() {
            Class<?> c;
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            try {
                c = Class.forName("com.andrei1058.spigot.versionsupport.material_" + version);
            } catch (ClassNotFoundException e) {
                return null;
            }
            try {
                return (MaterialSupport) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
    }
}