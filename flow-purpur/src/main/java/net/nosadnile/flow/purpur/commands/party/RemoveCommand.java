package net.nosadnile.flow.purpur.commands.party;

import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.nosadnile.flow.purpur.FlowPurpur.getParty;
import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class RemoveCommand extends SubCommand {

    public RemoveCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof Player p)) return;

        if (args.length == 0) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_USAGE));
            return;
        }
        if (getParty().hasParty(p.getUniqueId()) && !getParty().isOwner(p.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("{player}", args[0]));
            return;
        }
        if (!getParty().isMember(p.getUniqueId(), target.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("{player}", args[0]));
            return;
        }
        getParty().removePlayer(p.getUniqueId(), target.getUniqueId());
    }
}
