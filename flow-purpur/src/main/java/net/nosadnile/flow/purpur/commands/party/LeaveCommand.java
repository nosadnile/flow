package net.nosadnile.flow.purpur.commands.party;

import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.nosadnile.flow.purpur.FlowPurpur.getParty;
import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class LeaveCommand extends SubCommand {

    public LeaveCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof Player p)) return;
        if (!getParty().hasParty(p.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
            return;
        }
        if (getParty().isOwner(p.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND));
            return;
        }
        getParty().removeFromParty(p.getUniqueId());
    }
}
