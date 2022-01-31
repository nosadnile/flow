package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.player.NSNPlayer;

import java.util.ArrayList;
import java.util.List;

public class Message extends Command implements TabExecutor {
    public Message() {
        super("msg", "net.nosadnile.flow.b_msg");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a player and a message!"));
            return;
        }
        String to_ = args[0];
        List<String> message_ = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            message_.add(args[i]);
        }
        String message = String.join(" ", message_);
        NSNPlayer playerTo = FlowWaterfall.players.getPlayer(to_);
        if (playerTo == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a player!"));
            return;
        }
        if (!playerTo.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That player is not online!"));
            return;
        }
        NSNPlayer sender_ = FlowWaterfall.players.getPlayer(sender.getName());
        if (sender_ == null || !sender_.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f An error occured."));
            return;
        }
        playerTo.get().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[" + sender.getName() + " -> You] &f" + message));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[You -> " + playerTo.get().getName() + "] &f" + message));
        playerTo.setLastMessaged(sender_);
        sender_.setLastMessaged(playerTo);
        FlowWaterfall.players.replacePlayer(playerTo.getName(), playerTo);
        FlowWaterfall.players.replacePlayer(sender_.getName(), sender_);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.getName().contains(args[0])) {
                    results.add(p.getName());
                }
            }
        } else {
            results.add("[Message]");
        }
        return results;
    }
}
