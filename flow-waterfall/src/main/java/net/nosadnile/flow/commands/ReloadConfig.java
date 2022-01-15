package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nosadnile.flow.FlowWaterfall;

import java.util.Collections;

public class ReloadConfig extends Command implements TabExecutor {
    public ReloadConfig() {
        super("brc", "net.nosadnile.flow.admin.b_reload_config", "breloadconfig", "bungeereloadconfig");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Reloading configuration..."));
        FlowWaterfall.config.reloadConfig();
        ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Configuration reloaded!");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
