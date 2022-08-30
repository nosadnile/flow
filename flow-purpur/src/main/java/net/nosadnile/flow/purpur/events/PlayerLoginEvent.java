package net.nosadnile.flow.purpur.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginEvent implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        e.setJoinMessage("");
    }
}
