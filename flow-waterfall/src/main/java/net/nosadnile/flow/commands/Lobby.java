package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;

public class Lobby extends Command implements TabExecutor {
    public Lobby() {
        super("lobby", "net.nosadnile.flow.b_lobby", "hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ServerInfo server = ProxyServer.getInstance().getServerInfo("lobby");
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(player.getServer().getInfo().getName() == server.getName()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You are already connected to the lobby!"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Sending you to the lobby..."));
            player.connect(server);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
