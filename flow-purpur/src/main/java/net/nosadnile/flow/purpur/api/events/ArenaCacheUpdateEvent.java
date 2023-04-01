package net.nosadnile.flow.purpur.api.events;

import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArenaCacheUpdateEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final CachedArena arena;

    /**
     * Called when an arena is enabled successfully. It's called after a restart as well.
     */
    public ArenaCacheUpdateEvent(CachedArena a) {
        this.arena = a;
    }

    /**
     * Bukkit stuff.
     *
     * @return handlers.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get the arena.
     *
     * @return arena.
     */
    public CachedArena getArena() {
        return arena;
    }

    /**
     * Bukkit stuff.
     *
     * @return handlers.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
