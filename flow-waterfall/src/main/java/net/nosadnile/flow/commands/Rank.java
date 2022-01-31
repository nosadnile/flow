package net.nosadnile.flow.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.player.NSNPlayer;
import net.nosadnile.flow.api.rank.PlayerRank;

import java.util.ArrayList;
import java.util.List;

public class Rank extends Command implements TabExecutor {
    public Rank() {
        super("rank", "net.nosadnile.flow.admin.b_rank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Please use a sub-command!"));
            return;
        }
        String sComm = args[0];
        if(sComm.equalsIgnoreCase("give")) {
            if(args.length < 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Please specify the rank and a player!"));
                return;
            }
            String rN = args[1];
            String pN = args[2];
            PlayerRank r = FlowWaterfall.rankManager.getByName(rN);
            if(r == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a rank!"));
                return;
            }
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(pN);
            if(p == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a player!"));
                return;
            }
            NSNPlayer n = FlowWaterfall.players.getPlayer(p.getName());
            if(n == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f An internal error occured."));
                return;
            }
            r.addPlayer(n);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Given &a" + n.getName() + "&f the &a" + rN + "&f rank."));
            n.get().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a" + sender.getName() + "&f has given you the &a" + rN + "&f rank."));
        } else if(sComm.equalsIgnoreCase("revoke")) {
            if(args.length < 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Please specify the rank and a player!"));
                return;
            }
            String rN = args[1];
            String pN = args[2];
            PlayerRank r = FlowWaterfall.rankManager.getByName(rN);
            if(r == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a rank!"));
                return;
            }
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(pN);
            if(p == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a player!"));
                return;
            }
            NSNPlayer n = FlowWaterfall.players.getPlayer(p.getName());
            if(n == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f An internal error occured."));
                return;
            }
            r.removePlayer(n);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Removed &a" + n.getName() + "&f's &a" + rN + "&f rank."));
            n.get().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a" + sender.getName() + "&f removed your &a" + rN + "&f rank."));
        } else if(sComm.equalsIgnoreCase("create")) {
            if(args.length < 4) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Please specify the name, the color, and the prefix!"));
                return;
            }
            String rN = args[1];
            ChatColor rC = ChatColor.of(args[2]);
            List<String> rP_ = new ArrayList<>();
            for(int i = 3; i < args.length; i++) {
                rP_.add(args[i]);
            }
            String rP = ChatColor.translateAlternateColorCodes('&', String.join(" ", rP_));
            PlayerRank r = new PlayerRank(rN, rP, rC.getName());
            if(FlowWaterfall.rankManager.isRegistered(rN)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That rank already exists!"));
                return;
            }
            FlowWaterfall.rankManager.registerRank(rN, r);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Created rank &f" + rP + "&f!"));
            return;
        } else if(sComm.equalsIgnoreCase("delete")) {
            if(args.length < 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Please specify the name, the color, and the prefix!"));
                return;
            }
            String rN = args[1];
            if(!FlowWaterfall.rankManager.isRegistered(rN)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a rank!"));
                return;
            }
            PlayerRank r = FlowWaterfall.rankManager.getByName(rN);
            FlowWaterfall.rankManager.unregisterRank(rN);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Created rank &f" + r.getPrefix() + "&f!"));
            return;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f That is not a sub-command!"));
            return;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if(args.length == 1) {
            results.add("create");
            results.add("modify");
            results.add("delete");
            results.add("give");
            results.add("revoke");
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("create")) {
                for(PlayerRank r : FlowWaterfall.rankManager.getRanks().values()) {
                    if (r.getName().contains(args[1])) {
                        results.add(r.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("delete")) {
                for(PlayerRank r : FlowWaterfall.rankManager.getRanks().values()) {
                    if (r.getName().contains(args[1])) {
                        results.add(r.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("modify")) {
                for(PlayerRank r : FlowWaterfall.rankManager.getRanks().values()) {
                    if (r.getName().contains(args[1])) {
                        results.add(r.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("give")) {
                for(PlayerRank r : FlowWaterfall.rankManager.getRanks().values()) {
                    if (r.getName().contains(args[1])) {
                        results.add(r.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("revoke")) {
                for(PlayerRank r : FlowWaterfall.rankManager.getRanks().values()) {
                    if (r.getName().contains(args[1])) {
                        results.add(r.getName());
                    }
                }
            }
        } else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("create")) {
                for(ChatColor c : ChatColor.values()) {
                    if (c.getName().contains(args[2])) {
                        results.add(c.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("modify")) {
                results.add("permissions");
                results.add("prefix");
                results.add("color");
            } else if(args[0].equalsIgnoreCase("give")) {
                for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.getName().contains(args[2])) {
                        results.add(p.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("revoke")) {
                for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.getName().contains(args[2])) {
                        results.add(p.getName());
                    }
                }
            }
        } else if(args.length == 4) {
            if(args[0].equalsIgnoreCase("create")) {
                results.add("[Rank Prefix]");
            } else if(args[0].equalsIgnoreCase("modify")) {
                if(args[1].equalsIgnoreCase("color")) {
                    for(ChatColor c : ChatColor.values()) {
                        if (c.getName().contains(args[3])) {
                            results.add(c.getName());
                        }
                    }
                } else if(args[1].equalsIgnoreCase("permissions")) {
                    results.add("[Permission Name]");
                } else if(args[1].equalsIgnoreCase("prefix")) {
                    results.add("[Rank Prefix]");
                }
            }
        } else if(args.length == 5) {
            if(args[0].equalsIgnoreCase("modify")) {
                if(args[1].equalsIgnoreCase("permissions")) {
                    results.add("true");
                    results.add("false");
                } else if(args[1].equalsIgnoreCase("prefix")) {
                    results.add("[Rank Prefix]");
                }
            } else if(args[0].equalsIgnoreCase("create")) {
                results.add("[Rank Prefix]");
            }
        } else if(args.length > 5) {
            if(args[0].equalsIgnoreCase("create")) {
                results.add("[Rank Prefix]");
            } else if(args[0].equalsIgnoreCase("modify")) {
                if(args[1].equalsIgnoreCase("prefix")) {
                    results.add("[Rank Prefix]");
                }
            }
        }
        return results;
    }
}
