package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server extends Command implements TabExecutor {
    public Server() {
        super("server", "net.nosadnile.flow.b_server", "switch");
    }

    @Override
    @SuppressWarnings("ALL")
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer) && args.length < 2) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f This command can only be executed by a player!"));
            return;
        }
        if(args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a server to switch to!"));
            return;
        }
        if(args.length == 2) {
            if(!sender.hasPermission("net.nosadnile.flow.admin.b_teleport_other")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You don't have permission to execute this command!"));
                return;
            }
            String playerName = args[1];
            if(playerName == null || playerName == "") {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a valid player to teleport!"));
                return;
            }
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
            if(player == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a valid player to teleport!"));
                return;
            }
            String serverName = args[0];
            if(serverName == null || serverName == "") {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a server to switch to!"));
                return;
            }
            ServerInfo server = ProxyServer.getInstance().getServerInfo(serverName);
            if(serverName == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a valid server to switch to!"));
                return;
            }
            if(player.getServer().getInfo().getName() == server.getName()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f " + playerName + " is already connected to &b" + serverName + "&f!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Sending " + playerName + " to &b" + serverName + "&f..."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&c " + sender.getName() + "&f is sending you to &b" + serverName + "&f..."));
                player.connect(server);
            }
            return;
        }
        String serverName = args[0];
        if(serverName == null || serverName == "") {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a server to switch to!"));
            return;
        }
        ServerInfo server = ProxyServer.getInstance().getServerInfo(serverName);
        if(serverName == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a valid server to switch to!"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(player.getServer().getInfo().getName() == server.getName()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You are already connected to &b" + serverName + "&f!"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Sending you to &b" + serverName + "&f..."));
            player.connect(server);
        }
    }

    @Override
    @SuppressWarnings("ALL")
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> servers = new ArrayList<>();
        List<String> players = new ArrayList<>();
        for(String s : ProxyServer.getInstance().getServers().keySet()) {
            if (s.contains(args[0])) {
                servers.add(s);
            }
        }
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.getName().contains(args[1])) {
                players.add(p.getName());
            }
        }
        if(args.length == 1) {
            return servers;
        } else if(args.length == 2) {
            return players;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
