package net.nosadnile.flow.velocity.commands.rank.edit;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;

public class ModifyListCommand {
    public static int addListValue(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "list": List ID
        // - "value": Value

        return Command.SINGLE_SUCCESS;
    }

    public static int removeListValue(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "list": List ID
        // - "value": Value

        return Command.SINGLE_SUCCESS;
    }
}
