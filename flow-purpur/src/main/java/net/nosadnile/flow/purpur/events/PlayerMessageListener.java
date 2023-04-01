package net.nosadnile.flow.purpur.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMessageListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!event.message().toString().startsWith("/")) {
            event.setCancelled(true);
        }
    }
}
