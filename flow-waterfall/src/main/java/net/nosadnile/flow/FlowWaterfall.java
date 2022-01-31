package net.nosadnile.flow;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.nosadnile.flow.api.chat.ChatGroupManager;
import net.nosadnile.flow.api.config.ConfigurationProvider;
import net.nosadnile.flow.api.database.DatabaseProvider;
import net.nosadnile.flow.api.messaging.MessageManager;
import net.nosadnile.flow.api.player.PlayerManager;
import net.nosadnile.flow.api.rank.PlayerRank;
import net.nosadnile.flow.api.rank.RankManager;
import net.nosadnile.flow.commands.*;
import net.nosadnile.flow.events.PlayerChatEvent;
import net.nosadnile.flow.events.PlayerLoginEvent;
import net.nosadnile.flow.events.PlayerLogoutEvent;
import net.nosadnile.flow.events.PlayerSwitchServerEvent;

public class FlowWaterfall extends Plugin {
    public static LuckPerms lp;
    public static DatabaseProvider db;
    public static ConfigurationProvider config;
    public static MessageManager messageManager;
    public static RankManager rankManager;
    public static PlayerManager players;
    public static ChatGroupManager chatGroups;

    @Override
    public void onEnable() {
        lp = LuckPermsProvider.get();
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f LuckPerms connected!"));
        config = new ConfigurationProvider(this);
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Configuration loaded!"));
        db = DatabaseProvider.noPassword("mongo", "27017");
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Database connected!"));
        messageManager = new MessageManager(this);
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Message manager loaded!"));
        rankManager = new RankManager();
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Rank manager loaded!"));
        chatGroups = new ChatGroupManager();
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Rank manager loaded!"));
        players = new PlayerManager();
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Chat Group manager loaded!"));
        if (!rankManager.isRegistered("admin")) {
            rankManager.registerRank("admin", new PlayerRank("admin", "&4[&cADMIN&4]", "RED", "*"));
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[MAIN]&f Registered rank &aAdmin&f!"));
        }
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Server());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aServer&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Nick());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aNick&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadConfig());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aReloadConfig&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aLobby&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Rank());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aRank&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Message());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aMessage&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Respond());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aRespond&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new List());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aList&f!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Restart());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aRestart&f!"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerChatEvent());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerChatEvent&f!"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerLoginEvent());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerSwitchServerEvent());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerSwitchServerEvent&f!"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerLogoutEvent());
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Completed initialization!"));
    }

    @Override
    public void onDisable() {
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            p.disconnect(ChatColor.translateAlternateColorCodes('&', "&6Server is restarting."));
        }
        players.saveToDatabase();
        rankManager.saveToDatabase();
        db.disable();
    }
}
