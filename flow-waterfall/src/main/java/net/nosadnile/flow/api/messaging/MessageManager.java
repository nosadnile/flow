package net.nosadnile.flow.api.messaging;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    private Map<String, MessageHandler> handlers;

    public MessageManager(Plugin plugin) {
        handlers = new HashMap<>();
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, new MessageListener());
    }

    public MessageHandler getHandler(String channel) {
        return handlers.get(channel);
    }

    public boolean isRegistered(String channel) {
        return handlers.containsKey(channel);
    }

    public void unregisterHandler(String channel) {
        handlers.remove(channel);
    }

    public void registerHandler(String channel, MessageHandler handler) {
        handlers.put(channel, handler);
    }
}
