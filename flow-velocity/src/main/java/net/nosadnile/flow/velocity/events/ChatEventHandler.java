package net.nosadnile.flow.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.util.ColorUtil;

public class ChatEventHandler {
    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!message.startsWith("/")) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            String rawServerName = player.getCurrentServer().get().getServerInfo().getName();
            String serverName = rawServerName.toUpperCase();
            String rank = "&7[&fDEFAULT&7]&f ";

            if (message.toLowerCase().contains("${jndi:ldap")) {
                FlowVelocity.server.getConsoleCommandSource().sendMessage(
                        ColorUtil.translateColorCodes('&',
                                "&4[&cSECURITY&4] &b" + player.getUsername() +
                                        " &chas attempted to perform the log4j RCE exploit. Message blocked."
                        ));

                player.sendMessage(ColorUtil.translateColorCodes('&',
                        "&4[&cSECURITY&4] &cYou have attempted to perform the log4j RCE exploit." +
                                "This is not allowed. The admins have been informed and your message has been blocked."
                ));

                return;
            }

            FlowVelocity.server.getConsoleCommandSource().sendMessage(
                    ColorUtil.translateColorCodes('&',
                            "&6[&eCHAT&6] &6[&e" + serverName + "&6] " + rank +
                                    player.getUsername() + ":&f " + message
                    ));

            for (Player target : FlowVelocity.server.getAllPlayers()) {
                target.sendMessage(ColorUtil.translateColorCodes('&',
                        "&6[&e" + serverName + "&6] " + rank +
                                player.getUsername() + ":&f " + message));
            }
        }
    }
}
