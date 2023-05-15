package net.nosadnile.flow.velocity.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class EnumArgumentType implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("something", "another_thing");

    private final Collection<String> values;

    private EnumArgumentType(Collection<String> values) {
        this.values = values;
    }

    public static EnumArgumentType literals(Collection<String> values) {
        return new EnumArgumentType(values);
    }

    @Override
    public String parse(final StringReader reader) {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (String value : this.values) {
            if (value.toLowerCase().startsWith(builder.getRemaining())) {
                builder.suggest(value);
            }
        }

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
