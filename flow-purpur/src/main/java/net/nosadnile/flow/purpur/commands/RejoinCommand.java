package net.nosadnile.flow.purpur.commands;

import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.api.bedwars.RemoteReJoin;
import net.nosadnile.flow.purpur.config.SoundsConfig;
import net.nosadnile.flow.purpur.lang.LanguageHelper;
import net.nosadnile.flow.purpur.lang.LanguageManager;
import net.nosadnile.flow.purpur.rejoin.RemoteReJoinManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class RejoinCommand extends BukkitCommand {

    public RejoinCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("This command is for players!");
            return true;
        }

        Player p = (Player) s;

        if (!p.hasPermission("bw.rejoin")) {
            p.sendMessage(LanguageHelper.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            return true;
        }

        RemoteReJoin rj = RemoteReJoinManager.getReJoin(p.getUniqueId());

        if (rj == null) {
            p.sendMessage(LanguageHelper.getMsg(p, Messages.REJOIN_NO_ARENA));
            SoundsConfig.playSound("rejoin-denied", p);
            return true;
        }

        if (!rj.getArena().reJoin(rj)) {
            p.sendMessage(LanguageHelper.getMsg(p, Messages.REJOIN_DENIED));
            SoundsConfig.playSound("rejoin-denied", p);
            return true;
        }

        p.sendMessage(LanguageHelper.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", rj.getArena().getDisplayName(LanguageManager.get().getPlayerLanguage(p))));
        SoundsConfig.playSound("rejoin-allowed", p);
        return true;
    }
}
