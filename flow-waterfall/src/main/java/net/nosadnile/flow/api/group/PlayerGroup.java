package net.nosadnile.flow.api.group;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.nosadnile.flow.api.player.NSNPlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlayerGroup {
    public List<NSNPlayer> players;

    public PlayerGroup() {
        players = new ArrayList<>();
    }

    public List<NSNPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(NSNPlayer player) {
        players.add(player);
    }

    public void removePlayer(NSNPlayer player) {
        players.remove(player);
    }

    public boolean hasPlayer(NSNPlayer player) {
        return players.contains(player);
    }

    public @Nullable
    NSNPlayer getPlayer(String name) {
        for (NSNPlayer player : players) {
            if (player.getName() == name) {
                return player;
            }
        }
        return null;
    }

    public @Nullable
    NSNPlayer getPlayer(ProxiedPlayer proxiedPlayer) {
        String name = proxiedPlayer.getName();
        for (NSNPlayer player : players) {
            if (player.getName() == name) {
                return player;
            }
        }
        return null;
    }
}
