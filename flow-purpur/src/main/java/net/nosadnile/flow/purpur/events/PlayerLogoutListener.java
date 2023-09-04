package net.nosadnile.flow.purpur.events;

import net.nosadnile.flow.purpur.sidebar.SidebarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutListener implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onLogout(PlayerQuitEvent e) {
        e.setQuitMessage("");

        SidebarManager.SIDEBAR.removeViewer(e.getPlayer());
    }
}
