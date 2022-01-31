package net.nosadnile.flow.api.chat;

import net.md_5.bungee.api.ChatColor;
import net.nosadnile.flow.api.group.PlayerGroup;
import net.nosadnile.flow.api.player.NSNPlayer;
import net.nosadnile.flow.api.utils.ColorUtil;

import java.util.UUID;

public class ChatGroup extends PlayerGroup {
    // Static groups
    public static GlobalChatGroup GLOBAL = new GlobalChatGroup();

    private String name;
    private String uuid;
    private ChatColor color;
    private boolean useCustomPrefix;
    private String prefix;

    public ChatGroup(String name, ChatColor color, String customPrefix) {
        super();
        this.name = name.toUpperCase();
        this.color = color;
        this.uuid = UUID.randomUUID().toString();
        this.useCustomPrefix = true;
        this.prefix = customPrefix;
    }

    public ChatGroup(String name, ChatColor color) {
        super();
        this.name = name.toUpperCase();
        this.color = color;
        this.uuid = UUID.randomUUID().toString();
        this.useCustomPrefix = false;
    }

    public ChatGroup(String name) {
        super();
        this.name = name.toUpperCase();
        this.color = ChatColor.AQUA;
        this.uuid = UUID.randomUUID().toString();
        this.useCustomPrefix = false;
    }

    private void init() {

    }

    private void sendMessage_(String message) {
        for (NSNPlayer player : players) {
            if (player.isOnline()) {
                player.get().sendMessage(message);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Deprecated
    public void sendRawMessage(String message) {
        sendMessage_(message);
    }

    @Deprecated
    public void sendNonAnnotatedMessage(String message) {
        sendMessage_(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendMessage(String senderName, String messageIn) {
        String message = ColorUtil.darkVariant(color) + "[" + color + name + ColorUtil.darkVariant(color) + "] " + color + senderName + ": &f" + messageIn;
        if (useCustomPrefix && prefix != null) {
            message = prefix + " " + senderName + ": &f" + messageIn;
        }
        sendNonAnnotatedMessage(message);
    }

    public void sendMessage(String senderName, String messageIn, String variablePrefix) {
        String message = ColorUtil.darkVariant(color) + "[" + color + name + ColorUtil.darkVariant(color) + "] " + color + senderName + ": &f" + messageIn;
        if (variablePrefix != null) {
            message = variablePrefix + " " + senderName + ": &f" + messageIn;
        }
        sendNonAnnotatedMessage(message);
    }
}
