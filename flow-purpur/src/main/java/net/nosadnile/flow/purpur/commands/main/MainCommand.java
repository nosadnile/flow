package net.nosadnile.flow.purpur.commands.main;

import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.commands.ParentCommand;
import net.nosadnile.flow.purpur.lang.LanguageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand extends ParentCommand {

    private static MainCommand instance;

    /**
     * Create a new Parent Command
     *
     * @param name
     */
    public MainCommand(String name) {
        super(name);
        instance = this;
        setAliases(List.of("bedwars"));
        addSubCommand(new SelectorCommand("gui", ""));
        addSubCommand(new LangCommand("lang", ""));
        addSubCommand(new LangCommand("language", ""));
        addSubCommand(new JoinCommand("join", ""));
        addSubCommand(new ReJoinCommand("rejoin", "bw.rejoin"));
        addSubCommand(new TpCommand("tp", "bw.tp"));
    }

    public static MainCommand getInstance() {
        return instance;
    }

    @Override
    public void sendDefaultMessage(CommandSender s) {
        if (s instanceof ConsoleCommandSender) return;
        Player p = (Player) s;

        s.sendMessage(" ");
        s.sendMessage("§8§l|-" + " §6" + FlowPurpur.getPlugin().getDescription().getName() + " v" + FlowPurpur.getPlugin().getDescription().getVersion() + " §7- §cCommands");
        s.sendMessage(" ");
        if (hasSubCommand("gui")) {
            p.spigot().sendMessage(FlowPurpur.createTC(LanguageHelper.getMsg(p, Messages.COMMAND_GUI_DISPLAY), "/bw gui", LanguageHelper.getMsg(p, Messages.COMMAND_GUI_HOVER)));
        }
        if (hasSubCommand("lang")) {
            p.spigot().sendMessage(FlowPurpur.createTC(LanguageHelper.getMsg(p, Messages.COMMAND_LANGUAGE_DISPLAY), "/bw lang", LanguageHelper.getMsg(p, Messages.COMMAND_LANGUAGE_HOVER)));
        }
        if (hasSubCommand("rejoin")) {
            p.spigot().sendMessage(FlowPurpur.createTC(LanguageHelper.getMsg(p, Messages.COMMAND_REJOIN_DISPLAY), "/bw rejoin", LanguageHelper.getMsg(p, Messages.COMMAND_REJOIN_HOVER)));
        }
        if (hasSubCommand("tp") && getSubCommand("tp").hasPermission(s)) {
            p.spigot().sendMessage(FlowPurpur.createTC(LanguageHelper.getMsg(p, Messages.COMMAND_TP_DISPLAY), "/bw tp", LanguageHelper.getMsg(p, Messages.COMMAND_TP_HOVER)));
        }
    }
}
