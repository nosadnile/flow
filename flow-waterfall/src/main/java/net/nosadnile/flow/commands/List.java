package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;

public class List extends Command implements TabExecutor {
    public List() {
        super("list", "net.nosadnile.flow.b_list", "ls");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String msg = "&6[&f!&6]&f There are &a" + ProxyServer.getInstance().getPlayers().size() + "&f players online.";
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            msg += "\n  &b- " + p.getName();
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
