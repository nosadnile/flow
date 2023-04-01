package net.nosadnile.flow.purpur.rejoin;

import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import net.nosadnile.flow.purpur.api.bedwars.RemoteReJoin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteReJoinManager implements RemoteReJoin {

    private static final ConcurrentHashMap<UUID, RemoteReJoin> rejoinByUUID = new ConcurrentHashMap<>();
    private final CachedArena arena;
    private final UUID uuid;

    public RemoteReJoinManager(UUID player, CachedArena arena) {
        this.uuid = player;
        this.arena = arena;
        rejoinByUUID.put(uuid, this);
    }

    public static RemoteReJoin getReJoin(UUID player) {
        return rejoinByUUID.getOrDefault(player, null);
    }

    public static ConcurrentHashMap<UUID, RemoteReJoin> getRejoinByUUID() {
        return rejoinByUUID;
    }

    public CachedArena getArena() {
        return arena;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void destroy() {
        rejoinByUUID.remove(uuid);
    }
}
