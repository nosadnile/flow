package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.player.NSNPlayer;

import java.util.Collections;

public class Respond extends Command implements TabExecutor {
    public Respond() {
        super("respond", "net.nosadnile.flow.b_respond", "r", "rsp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You must specify a message!"));
            return;
        }
        String message = String.join(" ", args);
        NSNPlayer sender_ = FlowWaterfall.players.getPlayer(sender.getName());
        if (sender_ == null || !sender_.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f An error occured."));
            return;
        }
        NSNPlayer playerTo = sender_.getLastMessaged();
        if (playerTo == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f You have not messaged anyone recently!"));
            return;
        }
        if (!playerTo.isOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That player is not online!"));
            return;
        }
        if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)).toLowerCase().contains("${jndi:ldap")) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cSECURITY&4] &b" + sender.getName() + " &chas attempted to perform the log4j RCE exploit. Message blocked."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cSECURITY&4] &cYou have attempted to perform the log4j RCE exploit. This is not allowed. The admins have been informed and your message has been blocked."));
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
        return Collections.EMPTY_LIST;
    }
}
