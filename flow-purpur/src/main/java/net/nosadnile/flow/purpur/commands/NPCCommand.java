package net.nosadnile.flow.purpur.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.nosadnile.flow.purpur.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class NPCCommand {
    public static void register() {
        new CommandAPICommand("npc")
                .withArguments(new GreedyStringArgument("name"))
                .executes(NPCCommand::execute).register();
    }

    public static void execute(CommandSender sender, CommandArguments args) {
        String name = (String) args.get("name");

        if (name == null) return;

        String uuid = Objects.requireNonNull(Bukkit.getPlayer(name)).getUniqueId().toString();

        NPC npc = new NPC(uuid, name, ((Player) sender).getLocation());

        npc.spawn();
    }
}
