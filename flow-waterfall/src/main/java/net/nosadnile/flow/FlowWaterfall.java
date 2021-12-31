package net.nosadnile.flow;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.nosadnile.flow.api.database.DatabaseProvider;
import net.nosadnile.flow.commands.Nick;
import net.nosadnile.flow.commands.Server;
import net.nosadnile.flow.events.PlayerChatEvent;

public class FlowWaterfall extends Plugin {
    public static LuckPerms lp;
    public static DatabaseProvider db;

    @Override
    public void onEnable() {
        lp = LuckPermsProvider.get();
        db = new DatabaseProvider(this);
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Server());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Nick());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerChatEvent());
    }

    @Override
    public void onDisable() {
        db.disable();
    }
}
