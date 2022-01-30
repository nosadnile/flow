package net.nosadnile.flow.api.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
    public static ChatColor darkVariant(ChatColor colorIn) {
        EnumColor color = toEnumColor(colorIn);
        switch (color) {
            case AQUA:
                return ChatColor.DARK_AQUA;
            case BLUE:
                return ChatColor.DARK_BLUE;
            case GREEN:
                return ChatColor.DARK_GREEN;
            case RED:
                return ChatColor.DARK_RED;
            case GRAY:
                return ChatColor.DARK_GRAY;
            case LIGHT_PURPLE:
                return ChatColor.DARK_PURPLE;
            case YELLOW:
                return ChatColor.GOLD;
            default:
                return colorIn;
        }
    }

    public static ChatColor normalVariant(ChatColor colorIn) {
        EnumColor color = toEnumColor(colorIn);
        switch (color) {
            case DARK_AQUA:
                return ChatColor.AQUA;
            case DARK_BLUE:
                return ChatColor.BLUE;
            case DARK_GREEN:
                return ChatColor.GREEN;
            case DARK_RED:
                return ChatColor.RED;
            case DARK_GRAY:
                return ChatColor.GRAY;
            case DARK_PURPLE:
                return ChatColor.LIGHT_PURPLE;
            case GOLD:
                return ChatColor.YELLOW;
            default:
                return colorIn;
        }
    }

    public static EnumColor toEnumColor(ChatColor colorIn) {
        String color = colorIn.toString().toUpperCase();
        return EnumColor.valueOf(color);
    }
}
