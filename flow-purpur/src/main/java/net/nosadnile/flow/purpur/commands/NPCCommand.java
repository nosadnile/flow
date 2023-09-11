package net.nosadnile.flow.purpur.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.LocationArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

public class NPCCommand {
    public static void register() {
        new CommandAPICommand("createnpc")
                .withArguments(new LocationArgument("position"))
                .withArguments(new StringArgument("skin"))
                .withArguments(new GreedyStringArgument("name"))
                .withHelp("Creates a new NPC.", "/createnpc <x> <y> <z> <skin> <name>")
                .executes(NPCCommand::execute).register();
    }

    public static void execute(CommandSender sender, CommandArguments args) {
        String name = (String) args.get("name");
        String skin = (String) args.get("skin");
        Location pos = (Location) args.get("position");

        if (name == null) return;

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);

        npc.spawn(pos);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(skin);
    }
}
