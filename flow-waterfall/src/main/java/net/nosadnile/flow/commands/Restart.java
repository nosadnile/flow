package net.nosadnile.flow.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nosadnile.flow.FlowWaterfall;

import java.util.Collections;

public class Restart extends Command implements TabExecutor {
    public Restart() {
        super("restart", "net.nosadnile.flow.admin.b_restart", "rs");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            p.disconnect("Proxy server restarting...\nDO NOT REJOIN UNTIL IT IS RESTARTED OR YOUR DATA WILL NOT SAVE!");
        }
        FlowWaterfall.players.saveToDatabase();
        ProxyServer.getInstance().stop();
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
