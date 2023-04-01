package net.nosadnile.flow.purpur.commands.party;

import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.ParentCommand;
import net.nosadnile.flow.purpur.lang.LanguageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PartyCommand extends ParentCommand {

    //owner, target
    private static final HashMap<UUID, UUID> partySessionRequest = new HashMap<>();

    public PartyCommand(String name) {
        super(name);
        addSubCommand(new InviteCommand("invite", ""));
        addSubCommand(new AcceptCommand("accept", ""));
        addSubCommand(new LeaveCommand("leave", ""));
        addSubCommand(new DisbandCommand("disband", ""));
        addSubCommand(new RemoveCommand("remove", ""));
    }

    /**
     * Get list of requests.
     */
    public static HashMap<UUID, UUID> getPartySessionRequest() {
        return partySessionRequest;
    }

    @Override
    public void sendDefaultMessage(CommandSender s) {
        if (s instanceof ConsoleCommandSender) return;
        Player p = (Player) s;
        for (String st : LanguageManager.get().getList(p, Messages.COMMAND_PARTY_HELP)) {
            p.sendMessage(st);
        }
    }
}
