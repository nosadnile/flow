package net.nosadnile.flow.purpur.commands.main;

import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.api.bedwars.Language;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.SubCommand;
import net.nosadnile.flow.purpur.lang.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class LangCommand extends SubCommand {

    /**
     * Create a new sub-command.
     * Do not forget to add it to a parent.
     *
     * @param name       sub-command name.
     * @param permission sub-command permission, leave empty if no permission is required.
     */
    public LangCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (s instanceof ConsoleCommandSender) return;
        Player p = (Player) s;
        if (!hasPermission(p)) {
            p.sendMessage(getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            return;
        }

        if (args.length == 0) {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_HEADER));
            for (Language l : LanguageManager.get().getLanguages()) {
                p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_FORMAT).replace("{iso}", l.getIso()).replace("{name}", l.getLangName()));
            }
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE));
        } else if (LanguageManager.get().isLanguageExist(args[0])) {
            LanguageManager.get().setPlayerLanguage(p, args[0], false);
            Bukkit.getScheduler().runTaskLater(FlowPurpur.getPlugin(), () -> p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY)), 10L);
        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_NOT_EXIST));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) {
        List<String> tab = new ArrayList<>();
        for (Language lang : LanguageManager.get().getLanguages()) {
            tab.add(lang.getIso());
        }
        return tab;
    }
}
