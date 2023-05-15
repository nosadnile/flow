package net.nosadnile.flow.velocity.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.nosadnile.flow.velocity.FlowVelocity;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class RankArgumentType implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("a_rank", "another_rank");

    public static RankArgumentType rank() {
        return new RankArgumentType();
    }

    @Override
    public String parse(final StringReader reader) {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (String rank : FlowVelocity.rankManager.getNames()) {
            if (rank.toLowerCase().startsWith(builder.getRemaining())) {
                builder.suggest(rank);
            }
        }

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
