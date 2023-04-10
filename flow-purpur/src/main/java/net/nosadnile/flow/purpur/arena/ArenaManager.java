package net.nosadnile.flow.purpur.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import net.nosadnile.flow.purpur.team.Team;

public class ArenaManager {
    public static List<Arena> ACTIVE_ARENAS = new ArrayList<>();

    @Nullable
    public static Arena getArenaForPlayer(Player player) {
        for (Arena arena : ACTIVE_ARENAS) {
            for (Team team : arena.teams) {
                if (team.players.contains(player))
                    return arena;
            }
        }

        return null;
    }
}
