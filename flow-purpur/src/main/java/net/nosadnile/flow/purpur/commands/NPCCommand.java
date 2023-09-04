package net.nosadnile.flow.purpur.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.nosadnile.flow.purpur.npc.NPCHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand {
    public static void register() {
        new CommandAPICommand("createnpc")
                .withArguments(new StringArgument("skin"))
                .withArguments(new GreedyStringArgument("name"))
                .executes(NPCCommand::execute).register();
    }

    public static void execute(CommandSender sender, CommandArguments args) {
        String name = (String) args.get("name");
        String skin = (String) args.get("skin");

        if (name == null) return;

        NPCHelper npc = new NPCHelper(skin, name, ((Player) sender).getLocation());

        npc.spawn();
    }
}
