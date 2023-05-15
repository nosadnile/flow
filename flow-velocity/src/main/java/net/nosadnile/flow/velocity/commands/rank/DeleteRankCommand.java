package net.nosadnile.flow.velocity.commands.rank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;

public class DeleteRankCommand {
    public static int deleteRank(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID

        return Command.SINGLE_SUCCESS;
    }
}
