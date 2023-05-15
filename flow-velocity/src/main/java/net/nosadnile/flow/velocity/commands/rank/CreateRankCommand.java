package net.nosadnile.flow.velocity.commands.rank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.nosadnile.flow.velocity.ranks.Rank;
import net.nosadnile.flow.velocity.util.ColorUtil;

public class CreateRankCommand {
    public static int createRank(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "color": Rank Color
        // - "prefix": Rank Prefix

        String name = ctx.getArgument("name", String.class);
        String color = ctx.getArgument("color", String.class);
        String prefix = ctx.getArgument("prefix", String.class);

        Rank rank = new Rank(name, prefix, color);

        rank.push();

        String messageText = "Created rank \"" + name + "&r\" with color " + ColorUtil.toTextColor(color) + color + "&r and prefix \"" + prefix + "&r\"!";
        Component message = ColorUtil.createServerMessage(messageText);

        ctx.getSource().sendMessage(message);

        return Command.SINGLE_SUCCESS;
    }
}
