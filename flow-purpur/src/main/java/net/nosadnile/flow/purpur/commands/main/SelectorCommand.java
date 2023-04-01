package net.nosadnile.flow.purpur.commands.main;

import net.nosadnile.flow.purpur.arena.manager.ArenaGUI;
import net.nosadnile.flow.purpur.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SelectorCommand extends SubCommand {
    /**
     * Create a new sub-command.
     * Do not forget to add it to a parent.
     *
     * @param name       sub-command name.
     * @param permission sub-command permission, leave empty if no permission is required.
     */
    public SelectorCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (s instanceof ConsoleCommandSender) return;
        Player p = (Player) s;

        String group = "default";

        if (args.length == 1) {
            group = args[0];
        }

        ArenaGUI.openGui(p, group);
    }
}
