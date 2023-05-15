package net.nosadnile.flow.velocity.commands.rank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import net.nosadnile.flow.velocity.util.ColorUtil;

public class RankHelpCommand {
    public static int showHelp(CommandContext<CommandSource> ctx) {
        CommandSource source = ctx.getSource();

        source.sendMessage(ColorUtil.createServerMessage("&cUsage:\n&a=> Not done yet!"));

        return Command.SINGLE_SUCCESS;
    }
}
