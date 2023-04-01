package net.nosadnile.flow.purpur.api.bedwars;

import java.util.UUID;

public interface RemoteReJoin {

    /**
     * Remove re-join data.
     */
    void destroy();

    /**
     * Get user uuid.
     *
     * @return user uuid.
     */
    UUID getUUID();

    /**
     * Get arena.
     *
     * @return arena.
     **/
    CachedArena getArena();
}
