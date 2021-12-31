package net.nosadnile.flow.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onChat(ChatEvent e) {
        Connection sender = e.getSender();
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            String message = e.getMessage();
            if(message.startsWith("/")) return;
            e.setCancelled(true);
            for(ProxiedPlayer p : player.getServer().getInfo().getPlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&fDEFAULT&7]&f " + player.getDisplayName() + "&f: " + message));
            }
        }
    }
}
