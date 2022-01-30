package net.nosadnile.flow.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutEvent implements Listener {
    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }
}
