package net.nosadnile.flow.purpur.commands.party;

import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.nosadnile.flow.purpur.FlowPurpur.getParty;
import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class AcceptCommand extends SubCommand {

    public AcceptCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof Player p)) return;
        if (args.length < 1) {
            return;
        }
        if (getParty().hasParty(p.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY));
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null || !Bukkit.getPlayer(args[0]).isOnline()) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("{player}", args[0]));
            return;
        }
        if (!PartyCommand.getPartySessionRequest().containsKey(Bukkit.getPlayer(args[0]).getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
            return;
        }
        if (PartyCommand.getPartySessionRequest().get(Bukkit.getPlayer(args[0]).getUniqueId()).toString().equalsIgnoreCase(p.getUniqueId().toString())) {
            PartyCommand.getPartySessionRequest().remove(Bukkit.getPlayer(args[0]).getUniqueId());
            if (getParty().hasParty(Bukkit.getPlayer(args[0]).getUniqueId())) {
                getParty().addMember(Bukkit.getPlayer(args[0]).getUniqueId(), p);
                Player pl;
                for (UUID on : getParty().getMembers(Bukkit.getPlayer(args[0]).getUniqueId())) {
                    pl = Bukkit.getPlayer(on);
                    if (pl == null) continue;
                    pl.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("{player}", p.getName()));
                }
            } else {
                getParty().createParty(Bukkit.getPlayer(args[0]), p);
                Player pl;
                for (UUID on : getParty().getMembers(Bukkit.getPlayer(args[0]).getUniqueId())) {
                    pl = Bukkit.getPlayer(on);
                    if (pl == null) continue;
                    pl.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("{player}", p.getName()));
                }
            }
        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
        }
    }
}
