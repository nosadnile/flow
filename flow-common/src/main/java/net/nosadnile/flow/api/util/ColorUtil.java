package net.nosadnile.flow.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorUtil {
    public static Component translateColorCodes(char code, String text) {
        // This webpage saved me from insanity. https://docs.adventure.kyori.net/migration/bungeecord-chat-api.html
        return LegacyComponentSerializer.legacy(code).deserialize(text);
    }

    public static String translateRawColorCodes(char code, String text) {
        return LegacyComponentSerializer.legacy(code).deserialize(text).content();
    }

    public static String stripColors(String text) {
        return PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(text));
    }

    public static Component createServerMessage(String text) {
        return ColorUtil.translateColorCodes('&', "&6[&f!&6]&r " + text);
    }

    public static Collection<String> names() {
        return List.of("dark_red",
                "red",
                "gold",
                "yellow",
                "dark_green",
                "green",
                "aqua",
                "dark_aqua",
                "dark_blue",
                "blue",
                "light_purple",
                "dark_purple",
                "white",
                "gray",
                "dark_gray",
                "black"
        );
    }

    public static String toTextColor(String color) {
        Map<String, String> colors = new HashMap<>();

        colors.put("dark_red", "&4");
        colors.put("red", "&c");
        colors.put("gold", "&6");
        colors.put("yellow", "&e");
        colors.put("dark_green", "&2");
        colors.put("green", "&a");
        colors.put("aqua", "&b");
        colors.put("dark_aqua", "&3");
        colors.put("dark_blue", "&1");
        colors.put("blue", "&9");
        colors.put("light_purple", "&d");
        colors.put("dark_purple", "&5");
        colors.put("white", "&f");
        colors.put("gray", "&7");
        colors.put("dark_gray", "&8");
        colors.put("black", "&0");

        return colors.get(color);
    }
}
