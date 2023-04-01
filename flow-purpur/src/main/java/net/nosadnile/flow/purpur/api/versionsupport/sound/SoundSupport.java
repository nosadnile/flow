package net.nosadnile.flow.purpur.api.versionsupport.sound;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import javax.annotation.Nullable;

public interface SoundSupport {
    boolean isSound(String paramString);

    @Nullable
    Sound getSound(String paramString);

    Sound getSoundOr(String paramString, Sound paramSound);

    @Nullable
    Sound getForCurrentVersion(String paramString1, String paramString2, String paramString3);

    class SupportBuilder {
        @Nullable
        public static SoundSupport load() {
            Class<?> c;
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            try {
                c = Class.forName("com.andrei1058.spigot.versionsupport.sound_" + version);
            } catch (ClassNotFoundException e) {
                return null;
            }
            try {
                return (SoundSupport) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
    }
}