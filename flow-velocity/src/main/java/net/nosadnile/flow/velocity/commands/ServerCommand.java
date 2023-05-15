package net.nosadnile.flow.velocity.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerCommand implements SimpleCommand {
    public static CommandMeta getMeta() {
        return FlowVelocity.commandManager.metaBuilder("server")
                .aliases("switch")
                .build();
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();

        if (invocation.arguments().length == 0) {
            if (sender.hasPermission("flow.admin")) {
                sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c Usage: /server <server> [player]"));
            } else {
                sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c Usage: /server <server>"));
            }
        } else if (invocation.arguments().length == 1) {
            if (sender instanceof Player player) {
                Optional<RegisteredServer> server = FlowVelocity.server.getServer(invocation.arguments()[0]);

                if (server.isPresent()) {
                    if (server.get().getPlayersConnected().contains(player)) {
                        player.sendMessage(ColorUtil.translateColorCodes('&',
                                "&6[&f!&6]&f You are already connected to &b" + server.get().getServerInfo().getName() + "&f!"));
                    } else {
                        player.sendMessage(ColorUtil.translateColorCodes('&',
                                "&6[&f!&6]&f Sending you to &b" + server.get().getServerInfo().getName() + "&f..."));

                        player.createConnectionRequest(server.get()).fireAndForget();
                    }
                } else {
                    sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c That server could not found!"));
                }
            } else {
                sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c You must be a player to use this command!"));
            }
        } else if (invocation.arguments().length >= 2 && invocation.source().hasPermission("flow.admin")) {
            Player origin = (Player) sender;
            Optional<Player> target = FlowVelocity.server.getPlayer(invocation.arguments()[1]);

            Optional<RegisteredServer> server = FlowVelocity.server.getServer(invocation.arguments()[0]);

            if (server.isPresent()) {
                if (target.isPresent()) {
                    if (server.get().getPlayersConnected().contains(target)) {
                        origin.sendMessage(ColorUtil.translateColorCodes('&',
                                "&6[&f!&6]&f " + target.get().getUsername() + " is already connected to &b" +
                                        server.get().getServerInfo().getName() + "&f!"));
                    } else {
                        origin.sendMessage(ColorUtil.translateColorCodes('&',
                                "&6[&f!&6]&f Sending " + target.get().getUsername() + " to &b" +
                                        server.get().getServerInfo().getName() + "&f..."));

                        target.get().sendMessage(ColorUtil.translateColorCodes('&',
                                "&6[&f!&6]&c " + origin.getUsername() + "&f is sending you to &b" +
                                        server.get().getServerInfo().getName() + "&f..."));

                        target.get().createConnectionRequest(server.get()).fireAndForget();
                    }
                } else {
                    sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c That player could not found!"));
                }
            } else {
                sender.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c That server could not found!"));
            }
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        if (invocation.arguments().length >= 2) {
            return invocation.source().hasPermission("flow.admin");
        } else {
            return true;
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();

        if (invocation.arguments().length == 1) {
            for (RegisteredServer server : FlowVelocity.server.getAllServers()) {
                String serverName = server.getServerInfo().getName();
                if (serverName.toLowerCase().contains(invocation.arguments()[0].toLowerCase())) {
                    suggestions.add(serverName);
                }
            }
        }

        if (invocation.arguments().length == 2 && invocation.source().hasPermission("flow.admin")) {
            for (Player player : FlowVelocity.server.getAllPlayers()) {
                String username = player.getUsername();
                if (username.toLowerCase().contains(invocation.arguments()[1].toLowerCase())) {
                    suggestions.add(username);
                }
            }
        }

        return suggestions;
    }
}
