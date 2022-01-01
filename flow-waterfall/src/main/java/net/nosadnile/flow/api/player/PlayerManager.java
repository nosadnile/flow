package net.nosadnile.flow.api.player;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NameNotFoundException;

public class PlayerManager implements Listener {
    private Map<String, NSNPlayer> players = new HashMap<>();

    public PlayerManager() {}

    public void addPlayer(ProxiedPlayer player) {
        players.put(player.getName(), new NSNPlayer(player));
    }

    public void removePlayer(ProxiedPlayer player) throws NameNotFoundException {
        if(players.containsKey(player.getName())) {
            players.remove(player.getName());
        } else {
            throw new NameNotFoundException(player.getName() + " was not found in the database!");
        }
    }

    public void removePlayer(String playerName) throws NameNotFoundException {
        if(players.containsKey(playerName)) {
            players.remove(playerName);
        } else {
            throw new NameNotFoundException(playerName + " was not found in the database!");
        }
    }

    public boolean isPlayerInDatabase(ProxiedPlayer player) {
        return players.containsKey(player.getName());
    }

    public boolean isPlayerInDatabase(String playerName) {
        return players.containsKey(playerName);
    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        if(!players.containsKey(e.getPlayer().getName())) {
            addPlayer(e.getPlayer());
        }
    }
}
