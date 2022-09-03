package net.nosadnile.flow.velocity.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ColorUtil {
    public static Component translateColorCodes(char code, String text) {
        // This webpage saved me from insanity. https://docs.adventure.kyori.net/migration/bungeecord-chat-api.html
        return LegacyComponentSerializer.legacy(code).deserialize(text);
    }

    public static String stripColors(String text) {
        return PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(text));
    }
}
