package net.nosadnile.flow.velocity.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ColorUtil {
    public static Component translateColorCodes(char code, String text) {
        return Component.text(
                text.replaceAll(code + "0", NamedTextColor.BLACK.asHexString())
                        .replaceAll(code + "1", NamedTextColor.DARK_BLUE.asHexString())
                        .replaceAll(code + "2", NamedTextColor.DARK_GREEN.asHexString())
                        .replaceAll(code + "3", NamedTextColor.DARK_AQUA.asHexString())
                        .replaceAll(code + "4", NamedTextColor.DARK_RED.asHexString())
                        .replaceAll(code + "5", NamedTextColor.DARK_PURPLE.asHexString())
                        .replaceAll(code + "6", NamedTextColor.GOLD.asHexString())
                        .replaceAll(code + "7", NamedTextColor.GRAY.asHexString())
                        .replaceAll(code + "8", NamedTextColor.DARK_GRAY.asHexString())
                        .replaceAll(code + "9", NamedTextColor.BLUE.asHexString())
                        .replaceAll(code + "a", NamedTextColor.GREEN.asHexString())
                        .replaceAll(code + "b", NamedTextColor.AQUA.asHexString())
                        .replaceAll(code + "c", NamedTextColor.RED.asHexString())
                        .replaceAll(code + "d", NamedTextColor.LIGHT_PURPLE.asHexString())
                        .replaceAll(code + "e", NamedTextColor.YELLOW.asHexString())
                        .replaceAll(code + "f", NamedTextColor.WHITE.asHexString())
                        .replaceAll(code + "k", TextDecoration.OBFUSCATED.toString())
                        .replaceAll(code + "l", TextDecoration.BOLD.toString())
                        .replaceAll(code + "m", TextDecoration.STRIKETHROUGH.toString())
                        .replaceAll(code + "n", TextDecoration.UNDERLINED.toString())
                        .replaceAll(code + "o", TextDecoration.ITALIC.toString())
                        .replaceAll(code + "r", NamedTextColor.BLACK.asHexString())
        );
    }
}
