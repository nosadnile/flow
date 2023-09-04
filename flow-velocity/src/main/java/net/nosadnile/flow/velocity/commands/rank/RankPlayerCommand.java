package net.nosadnile.flow.velocity.commands.rank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.ranks.Rank;
import net.nosadnile.flow.velocity.ranks.RankManager;

import java.util.Optional;

public class RankPlayerCommand {
    public static int giveRank(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "player": Player Name

        String name = ctx.getArgument("name", String.class);
        String playerName = ctx.getArgument("player", String.class);

        Optional<Player> playerOption = FlowVelocity.server.getPlayer(playerName);

        if (playerOption.isEmpty()) {
            String messageText = "&cA player with that name could not be found!";
            Component message = ColorUtil.createServerMessage(messageText);

            ctx.getSource().sendMessage(message);

            return 0;
        }

        RankManager rankManager = FlowVelocity.rankManager;
        Optional<Rank> rankOption = rankManager.getRank(name);

        if (rankOption.isEmpty()) {
            String messageText = "&cA rank with that name could not be found!";
            Component message = ColorUtil.createServerMessage(messageText);

            ctx.getSource().sendMessage(message);

            return 0;
        }

        Player player = playerOption.get();
        Rank rank = rankOption.get();

        rank.addPlayer(player);

        String messageText = "Given rank &a" + name + "&r to player &b" + playerName + "&r!";
        Component message = ColorUtil.createServerMessage(messageText);

        ctx.getSource().sendMessage(message);

        return Command.SINGLE_SUCCESS;
    }

    public static int revokeRank(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "player": Player Name

        String name = ctx.getArgument("name", String.class);
        String playerName = ctx.getArgument("player", String.class);

        Optional<Player> playerOption = FlowVelocity.server.getPlayer(playerName);

        if (playerOption.isEmpty()) {
            String messageText = "&cA player with that name could not be found!";
            Component message = ColorUtil.createServerMessage(messageText);

            ctx.getSource().sendMessage(message);

            return 0;
        }

        RankManager rankManager = FlowVelocity.rankManager;
        Optional<Rank> rankOption = rankManager.getRank(name);

        if (rankOption.isEmpty()) {
            String messageText = "&cA rank with that name could not be found!";
            Component message = ColorUtil.createServerMessage(messageText);

            ctx.getSource().sendMessage(message);

            return 0;
        }

        Player player = playerOption.get();
        Rank rank = rankOption.get();

        rank.removePlayer(player);

        String messageText = "Removed rank &a" + name + "&r from player &b" + playerName + "&r!";
        Component message = ColorUtil.createServerMessage(messageText);

        ctx.getSource().sendMessage(message);

        return Command.SINGLE_SUCCESS;
    }
}
