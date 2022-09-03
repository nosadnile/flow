package net.nosadnile.flow.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.util.ColorUtil;

public class LoginEventHandler {
    @Subscribe
    public void onLogin(PostLoginEvent event) {
        for (Player player : FlowVelocity.server.getAllPlayers()) {
            player.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f &a" + event.getPlayer().getUsername() + "&f has joined!"));
        }

        event.getPlayer().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f &a" + event.getPlayer().getUsername() + "&f has joined!"));
    }
}
