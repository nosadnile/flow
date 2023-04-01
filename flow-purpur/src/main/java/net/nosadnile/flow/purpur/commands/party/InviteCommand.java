package net.nosadnile.flow.purpur.commands.party;

import com.google.common.collect.ImmutableList;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static net.nosadnile.flow.purpur.FlowPurpur.getParty;
import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class InviteCommand extends SubCommand {

    public InviteCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof Player p)) return;
        if (args.length == 0) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_USAGE));
            return;
        }
        if (getParty().hasParty(p.getUniqueId()) && !getParty().isOwner(p.getUniqueId())) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return;
        }
        if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
            if (p == Bukkit.getPlayer(args[0])) {
                p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF));
                return;
            }
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_SENT).replace("{player}", args[0]));
            TextComponent tc = new TextComponent(getMsg(p, Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG).replace("{player}", p.getName()));
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + p.getName()));
            Bukkit.getPlayer(args[0]).spigot().sendMessage(tc);
            if (PartyCommand.getPartySessionRequest().containsKey(p.getUniqueId())) {
                PartyCommand.getPartySessionRequest().replace(p.getUniqueId(), Bukkit.getPlayer(args[0]).getUniqueId());
            } else {
                PartyCommand.getPartySessionRequest().put(p.getUniqueId(), Bukkit.getPlayer(args[0]).getUniqueId());
            }
        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("{player}", args[0]));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) {
        if (args.length == 0) {
            return ImmutableList.of();
        } else {
            String lastWord = args[args.length - 1];
            Player senderPlayer = s instanceof Player ? (Player) s : null;
            ArrayList<String> matchedPlayers = new ArrayList();
            Iterator var8 = s.getServer().getOnlinePlayers().iterator();

            while (true) {
                Player player;
                String name;
                do {
                    if (!var8.hasNext()) {
                        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
                        return matchedPlayers;
                    }

                    player = (Player) var8.next();
                    name = player.getName();
                } while (senderPlayer != null && !senderPlayer.canSee(player));

                if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                    matchedPlayers.add(name);
                }
            }
        }
    }
}
