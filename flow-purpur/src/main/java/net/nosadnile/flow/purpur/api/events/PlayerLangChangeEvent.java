package net.nosadnile.flow.purpur.api.events;

import net.nosadnile.flow.purpur.api.bedwars.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerLangChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Language oldLang, newLang;
    private boolean cancelled = false;

    /**
     * Called when a Player changes his language.
     *
     * @param p       target player.
     * @param oldLang old language.
     * @param newLang new language.
     */
    public PlayerLangChangeEvent(Player p, Language oldLang, Language newLang) {
        this.player = p;
        this.oldLang = oldLang;
        this.newLang = newLang;
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
     * Check if event is cancelled.
     *
     * @return true if canceled.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Change value.
     *
     * @param cancelled cancel event.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get Player.
     *
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get old Language.
     *
     * @return old language.
     */
    public Language getOldLang() {
        return oldLang;
    }

    /**
     * Get new Language.
     *
     * @return new language.
     */
    public Language getNewLang() {
        return newLang;
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
