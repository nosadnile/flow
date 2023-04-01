package net.nosadnile.flow.purpur.commands.main;

import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.api.bedwars.ArenaStatus;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.arena.manager.TpRequest;
import net.nosadnile.flow.purpur.commands.SubCommand;
import net.nosadnile.flow.purpur.lang.LanguageHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TpCommand extends SubCommand {


    public TpCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("This command is for players!");
            return;
        }

        Player p = (Player) s;

        if (!p.hasPermission("bw.tp")) {
            p.sendMessage(LanguageHelper.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            return;
        }

        if (args.length != 1) {
            p.sendMessage(LanguageHelper.getMsg((Player) s, Messages.COMMAND_TP_USAGE));
            return;
        }

        TpRequest tr = TpRequest.getTpRequest(((Player) s).getUniqueId());
        if (tr == null) {
            tr = new TpRequest(((Player) s).getUniqueId(), args[0]);
        } else return;

        TpRequest finalTr = tr;
        Player pl = (Player) s;
        Bukkit.getScheduler().runTaskLater(FlowPurpur.getPlugin(), () -> {
            if (pl.isOnline()) {
                if (finalTr.getArena() == null) {
                    pl.sendMessage(LanguageHelper.getMsg(pl, Messages.COMMAND_TP_NOT_FOUND).replace("{player}", finalTr.getTarget()));
                } else {
                    if (finalTr.getArena().getStatus() != ArenaStatus.PLAYING) {
                        pl.sendMessage(LanguageHelper.getMsg(pl, Messages.COMMAND_TP_FAIL2).replace("{player}",
                                finalTr.getTarget()).replace("{server}", finalTr.getArena().getServer()));
                    } else {
                        finalTr.getArena().addSpectator(pl, finalTr.getTarget());
                    }
                }
            }
        }, 45L);
    }
}
