package net.nosadnile.flow.velocity.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;

import java.util.Collections;
import java.util.List;

public class ListCommand implements SimpleCommand {
    public static CommandMeta getMeta() {
        return FlowVelocity.commandManager.metaBuilder("list")
                .aliases("ls")
                .build();
    }

    @Override
    public void execute(Invocation invocation) {
        String message = "&6[&f!&6]&f There are &a" + FlowVelocity.server.getPlayerCount() + "&f players online.";

        for (Player player : FlowVelocity.server.getAllPlayers()) {
            message += "\n  &b- " + player.getUsername();
        }

        invocation.source().sendMessage(ColorUtil.translateColorCodes('&', message));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("flow.admin");
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return Collections.emptyList();
    }
}
