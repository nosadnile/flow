package net.nosadnile.flow.api.player;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {
    private Map<String, NSNPlayer> players = new HashMap<>();

    public PlayerManager() {}

    public void addPlayer() {

    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        if(!players.containsKey(e.getPlayer().getName())) {
            players.put(e.getPlayer().getName(), new NSNPlayer(e.getPlayer()));
        }
    }
}
