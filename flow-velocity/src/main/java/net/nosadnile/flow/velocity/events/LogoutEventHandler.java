package net.nosadnile.flow.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.util.TabListSystem;

public class LogoutEventHandler {
    @Subscribe
    public void onLogout(DisconnectEvent event) {
        for (Player player : FlowVelocity.server.getAllPlayers()) {
            player.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f &a" + event.getPlayer().getUsername() + "&f has left."));

            TabListSystem.setTabList(player);
        }
    }
}
