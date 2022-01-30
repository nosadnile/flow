package net.nosadnile.flow.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nosadnile.flow.FlowWaterfall;

public class PlayerLoginEvent implements Listener {
    @EventHandler
    public void onLogin(PostLoginEvent e) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a" + e.getPlayer().getDisplayName() + "&f has joined!"));
        }
        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a" + e.getPlayer().getDisplayName() + "&f has joined!"));
        FlowWaterfall.players.onLogin(e.getPlayer());
    }
}
