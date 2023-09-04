package net.nosadnile.flow.purpur.events;

import net.nosadnile.flow.purpur.sidebar.SidebarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginListener implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onLogin(PlayerJoinEvent e) {
        e.setJoinMessage("");

        SidebarManager.SIDEBAR.addViewer(e.getPlayer());
    }
}
