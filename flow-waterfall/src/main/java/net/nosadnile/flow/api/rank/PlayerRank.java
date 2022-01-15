package net.nosadnile.flow.api.rank;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.md_5.bungee.api.ChatColor;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.player.NSNPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerRank {
    private String name;
    private String prefix;
    private String color;
    private String[] permissions;
    private String[] disabledPermissions;
    private List<NSNPlayer> players;

    public PlayerRank(String name, String prefix, String color, String... permissions) {
        this.name = name;
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.color = color;
        this.permissions = permissions;
        this.players = new ArrayList<>();
    }

    public void disablePermissions(String... permissions) {
        disabledPermissions = permissions;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public DBObject toDatabaseObject() {
        return new BasicDBObject("_id", name)
                         .append("prefix", prefix)
                         .append("color", color)
                         .append("permissions", permissions);
    }

    public void addPlayer(NSNPlayer player) {
        players.add(player);
        player.addRank(this);
        FlowWaterfall.players.replacePlayer(player.getName(), player);
        for(int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            player.get().setPermission(permission, true);
        }
        if(disabledPermissions != null) {
            for(int i = 0; i < disabledPermissions.length; i++) {
                String permission = disabledPermissions[i];
                player.get().setPermission(permission, false);
            }
        }
    }

    public void addPlayer(NSNPlayer player, String name) {
        players.add(player);
        player.addRank(this);
        if(FlowWaterfall.players == null) return;
        FlowWaterfall.players.replacePlayer(name, player);
        if(player.get() == null) return;
        for(int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            player.get().setPermission(permission, true);
        }
        if(disabledPermissions != null) {
            for(int i = 0; i < disabledPermissions.length; i++) {
                String permission = disabledPermissions[i];
                player.get().setPermission(permission, false);
            }
        }
    }

    public void removePlayer(NSNPlayer player) {
        players.remove(player);
        player.removeRank(this);
        FlowWaterfall.players.replacePlayer(player.getName(), player);
        for(int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            player.get().setPermission(permission, false);
        }
    }
}
