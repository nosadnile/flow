package net.nosadnile.flow.events;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nosadnile.flow.FlowWaterfall;

public class PlayerLoginEvent implements Listener {
    @EventHandler
    public void onLogin(PostLoginEvent e) {
        FlowWaterfall.players.onLogin(e.getPlayer());
    }
}
