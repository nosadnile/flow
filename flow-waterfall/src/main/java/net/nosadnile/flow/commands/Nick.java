package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class Nick extends Command implements TabExecutor {
    public Nick() {
        super("nick", "net.nosadnile.flow.b_nick");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f This command can only be executed by a player!"));
            return;
        }
        if(args.length == 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Setting your nickname to: " + args[0]));
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', args[0])).toLowerCase().contains("${jndi:ldap")) {
                ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cSECURITY&4] &b" + sender.getName() + " &chas attempted to perform the log4j RCE exploit. Message blocked."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cSECURITY&4] &cYou have attempted to perform the log4j RCE exploit. This is not allowed. The admins have been informed and your message has been blocked."));
                return;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                player.setDisplayName(player.getName());
            } else {
                player.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[0]));
            }
            return;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a nickname!"));
            return;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        java.util.List<String> results = new ArrayList<>();
        if (args.length == 1) {
            results.add("reset");
            results.add("[Custom Nickname]");
        }
        return results;
    }
}
