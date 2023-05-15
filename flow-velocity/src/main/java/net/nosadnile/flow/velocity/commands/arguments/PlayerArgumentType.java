package net.nosadnile.flow.velocity.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerArgumentType implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("a_player", "another_player");

    private final ProxyServer proxy;

    private PlayerArgumentType(ProxyServer proxy) {
        this.proxy = proxy;
    }

    public static PlayerArgumentType players(ProxyServer server) {
        return new PlayerArgumentType(server);
    }

    @Override
    public String parse(final StringReader reader) {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (Player player : this.proxy.getAllPlayers()) {
            if (player.getUsername().toLowerCase().startsWith(builder.getRemaining())) {
                builder.suggest(player.getUsername());
            }
        }

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
