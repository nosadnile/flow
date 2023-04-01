package net.nosadnile.flow.purpur.arena.sign;

import net.nosadnile.flow.purpur.api.bedwars.CachedArena;

public interface ArenaSign {

    String getGroup();

    String getArena();

    CachedArena getAssignedArena();

    void refresh();

    void remove();

    boolean equals(String world, int x, int y, int z);

    SignStatus getStatus();

    void setStatus(SignStatus status);

    enum SignStatus {
        REFRESHING, NO_DATA, FOUND
    }
}
