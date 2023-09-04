package net.nosadnile.flow.velocity.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.commands.arguments.EnumArgumentType;
import net.nosadnile.flow.velocity.commands.arguments.PlayerArgumentType;
import net.nosadnile.flow.velocity.commands.arguments.RankArgumentType;
import net.nosadnile.flow.velocity.commands.rank.CreateRankCommand;
import net.nosadnile.flow.velocity.commands.rank.DeleteRankCommand;
import net.nosadnile.flow.velocity.commands.rank.RankHelpCommand;
import net.nosadnile.flow.velocity.commands.rank.RankPlayerCommand;
import net.nosadnile.flow.velocity.commands.rank.edit.ModifyListCommand;
import net.nosadnile.flow.velocity.commands.rank.edit.ModifyPropertyCommand;

public class RankCommand {
    public static BrigadierCommand create(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> command = LiteralArgumentBuilder.<CommandSource>literal("rank")
                .requires(source -> source.hasPermission("flow.admin"))
                .executes(RankHelpCommand::showHelp)
                .then(
                        LiteralArgumentBuilder.<CommandSource>literal("create")
                                .then(
                                        RequiredArgumentBuilder.<CommandSource, String>argument("name", RankArgumentType.rank())
                                                .then(
                                                        RequiredArgumentBuilder.<CommandSource, String>argument("color", EnumArgumentType.literals(ColorUtil.names()))
                                                                .then(
                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("prefix", StringArgumentType.greedyString())
                                                                                .executes(CreateRankCommand::createRank)
                                                                )
                                                )
                                )
                                .build()
                )
                .then(
                        LiteralArgumentBuilder.<CommandSource>literal("delete")
                                .then(
                                        RequiredArgumentBuilder.<CommandSource, String>argument("name", RankArgumentType.rank())
                                                .executes(DeleteRankCommand::deleteRank)
                                )
                                .build()
                )
                .then(
                        LiteralArgumentBuilder.<CommandSource>literal("edit")
                                .then(
                                        RequiredArgumentBuilder.<CommandSource, String>argument("name", RankArgumentType.rank())
                                                .then(
                                                        LiteralArgumentBuilder.<CommandSource>literal("add")
                                                                .then(
                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("list", StringArgumentType.word())
                                                                                .then(
                                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("value", StringArgumentType.greedyString())
                                                                                                .executes(ModifyListCommand::addListValue)
                                                                                )
                                                                )
                                                                .build()
                                                )
                                                .then(
                                                        LiteralArgumentBuilder.<CommandSource>literal("remove")
                                                                .then(
                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("list", StringArgumentType.word())
                                                                                .then(
                                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("value", StringArgumentType.greedyString())
                                                                                                .executes(ModifyListCommand::removeListValue)
                                                                                )
                                                                )
                                                                .build()
                                                )
                                                .then(
                                                        LiteralArgumentBuilder.<CommandSource>literal("set")
                                                                .then(
                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("property", StringArgumentType.word())
                                                                                .then(
                                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("value", StringArgumentType.greedyString())
                                                                                                .executes(ModifyPropertyCommand::setValue)
                                                                                )
                                                                )
                                                                .build()
                                                )
                                                .then(
                                                        LiteralArgumentBuilder.<CommandSource>literal("get")
                                                                .then(
                                                                        RequiredArgumentBuilder.<CommandSource, String>argument("property", StringArgumentType.word())
                                                                                .executes(ModifyPropertyCommand::getValue)
                                                                )
                                                                .build()
                                                )
                                )
                                .build()
                )
                .then(
                        LiteralArgumentBuilder.<CommandSource>literal("give")
                                .then(
                                        RequiredArgumentBuilder.<CommandSource, String>argument("name", RankArgumentType.rank())
                                                .then(
                                                        RequiredArgumentBuilder.<CommandSource, String>argument("player", PlayerArgumentType.players(proxy))
                                                                .executes(RankPlayerCommand::giveRank)
                                                )
                                )
                                .build()
                )
                .then(
                        LiteralArgumentBuilder.<CommandSource>literal("revoke")
                                .then(
                                        RequiredArgumentBuilder.<CommandSource, String>argument("name", RankArgumentType.rank())
                                                .then(
                                                        RequiredArgumentBuilder.<CommandSource, String>argument("player", PlayerArgumentType.players(proxy))
                                                                .executes(RankPlayerCommand::revokeRank)
                                                )
                                )
                                .build()
                )
                .build();

        return new BrigadierCommand(command);
    }

    public static CommandMeta getMeta() {
        return FlowVelocity.commandManager.metaBuilder("rank").plugin(FlowVelocity.INSTANCE).build();
    }

    // Command: /rank create [id] [color] [prefix...]
    // Command: /rank delete [id]
    // Command: /rank edit [id] add [list_property] [value...]
    // Command: /rank edit [id] remove [list_property] [value...]
    // Command: /rank edit [id] set [property] [value...]
    // Command: /rank edit [id] get [property]
    // Command: /rank give [id] [player]
    // Command: /rank revoke [id] [player]
}
