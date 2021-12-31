package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;

public class Nick extends Command implements TabExecutor {
    public Nick() {
        super("nick", "net.nosadnile.flow.nick");
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
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[0]));
            return;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a nickname!"));
            return;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
