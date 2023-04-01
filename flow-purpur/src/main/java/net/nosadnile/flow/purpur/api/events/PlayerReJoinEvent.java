package net.nosadnile.flow.purpur.api.events;

import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerReJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final CachedArena arena;

    /**
     * Called when a player rejoins an arena.
     * This is called before sending him to the server.
     *
     * @param player player.
     * @param arena  arena.
     */
    public PlayerReJoinEvent(Player player, CachedArena arena) {
        this.player = player;
        this.arena = arena;
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
     * Get player.
     *
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get arena.
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
