package net.nosadnile.flow.api.rank;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.nosadnile.flow.FlowWaterfall;

public class PlayerRank {
    private String name;
    private String prefix;
    private ChatColor color;
    private String[] permissions;

    public PlayerRank(String name, String prefix, ChatColor color, String... permissions) {
        this.name = name;
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.color = color;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getColor() {
        return color;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void addPlayer(ProxiedPlayer player) {

    }
}
