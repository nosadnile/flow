package net.nosadnile.flow.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.player.NSNPlayer;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onChat(ChatEvent e) {
        Connection sender = e.getSender();
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            String message = e.getMessage();
            if(message.startsWith("/")) return;
            e.setCancelled(true);
            String sName_ = player.getServer().getInfo().getName();
//            String serverName = sName_.substring(0, 1).toUpperCase() + sName_.substring(1);
            String serverName = sName_.toUpperCase();
            String rank = "&7[&fDEFAULT&7]";
            ChatColor rankColor = ChatColor.WHITE;
            NSNPlayer p_ = FlowWaterfall.players.getPlayer(player.getName());
            if(p_.getRanks().size() > 0 && p_.getRanks().get(0) != null) {
                rank = p_.getRanks().get(0).getPrefix();
                rankColor = ChatColor.of(p_.getRanks().get(0).getColor());
            }
            if(message.contains("${jndi:ldap")) {
                ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cSECURITY&4] &b" + player.getDisplayName() + " &chas attempted to perform the log4j RCE exploit. Message blocked."));
                return;
            }
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&eCHAT&6] &6[&e" + serverName + "&6] " + rank + rankColor + " " + player.getDisplayName() + ":&f " + message));
            for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&e" + serverName + "&6] " + rank + rankColor + " " + player.getDisplayName() + ":&f " + message));
            }
        }
    }
}
