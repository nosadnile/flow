package net.nosadnile.flow.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchServerEvent implements Listener {
    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a" + e.getPlayer().getDisplayName() + "&f has switched to server &b" + e.getPlayer().getServer().getInfo().getName().toUpperCase() + "&f!"));
        }
    }
}
