package net.nosadnile.flow.api.chat;

import net.md_5.bungee.api.ChatColor;
import net.nosadnile.flow.api.group.PlayerGroup;
import net.nosadnile.flow.api.player.NSNPlayer;
import net.nosadnile.flow.api.utils.ColorUtil;

import java.util.UUID;

public class ChatGroup extends PlayerGroup {
    private String name;
    private String uuid;
    private ChatColor color;

    public ChatGroup(String name, ChatColor color) {
        super();
        this.name = name.toUpperCase();
        this.color = color;
        this.uuid = UUID.randomUUID().toString();
    }

    public ChatGroup(String name) {
        super();
        this.name = name.toUpperCase();
        this.color = ChatColor.AQUA;
        this.uuid = UUID.randomUUID().toString();
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
        sendNonAnnotatedMessage(message);
    }
}
