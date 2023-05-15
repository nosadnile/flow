package net.nosadnile.flow.velocity.commands.rank.edit;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;

public class ModifyPropertyCommand {
    public static int setValue(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "property": Property ID
        // - "value": Value

        return Command.SINGLE_SUCCESS;
    }

    public static int getValue(CommandContext<CommandSource> ctx) {
        // Props:
        // - "name": Rank ID
        // - "property": Property ID

        return Command.SINGLE_SUCCESS;
    }
}
