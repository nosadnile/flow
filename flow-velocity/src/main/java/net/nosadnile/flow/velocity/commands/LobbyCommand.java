package net.nosadnile.flow.velocity.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LobbyCommand implements SimpleCommand {
    public static CommandMeta getMeta() {
        return FlowVelocity.commandManager.metaBuilder("lobby")
                .aliases("hub")
                .build();
    }

    @Override
    public void execute(Invocation invocation) {
        Optional<RegisteredServer> server = FlowVelocity.server.getServer("lobby");
        CommandSource source = invocation.source();

        if (invocation.arguments().length == 0) {
            if (source instanceof Player player) {
                if (server.isPresent()) {
                    player.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Sending you to the lobby..."));

                    player.createConnectionRequest(server.get()).fireAndForget();
                } else {
                    player.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c An unknown error occurred."));
                }
            } else {
                source.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c You must be a player to use this command!"));
            }
        } else if (invocation.arguments().length >= 1 && source.hasPermission("flow.admin")) {
            Player origin = (Player) source;
            Optional<Player> target = FlowVelocity.server.getPlayer(invocation.arguments()[0]);

            if (server.isPresent()) {
                if (target.isPresent()) {
                    origin.sendMessage(ColorUtil.translateColorCodes('&',
                            "&6[&f!&6]&f Sending " + target.get().getUsername() + " to the lobby..."));

                    target.get().sendMessage(ColorUtil.translateColorCodes('&',
                            "&6[&f!&6]&c " + origin.getUsername() + "&f is sending you to the lobby..."));

                    target.get().createConnectionRequest(server.get()).fireAndForget();
                } else {
                    source.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c That player could not be found!"));
                }
            } else {
                source.sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&c An unknown error occurred."));
            }
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        if (invocation.arguments().length >= 1) {
            return invocation.source().hasPermission("flow.admin");
        } else {
            return true;
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();

        if (invocation.arguments().length == 1 && invocation.source().hasPermission("flow.admin")) {
            for (Player player : FlowVelocity.server.getAllPlayers()) {
                String username = player.getUsername();
                if (username.toLowerCase().contains(invocation.arguments()[0].toLowerCase())) {
                    suggestions.add(username);
                }
            }
        }

        return suggestions;
    }
}
