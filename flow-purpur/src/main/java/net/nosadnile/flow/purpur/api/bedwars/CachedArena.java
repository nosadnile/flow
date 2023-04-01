package net.nosadnile.flow.purpur.api.bedwars;

import org.bukkit.entity.Player;

public interface CachedArena {

    String getRemoteIdentifier();

    String getArenaName();

    void setArenaName(String newName);

    String getServer();

    String getDisplayName(Language lang);

    String getArenaGroup();

    void setArenaGroup(String group);

    String getDisplayGroup(Language lang);

    ArenaStatus getStatus();

    void setStatus(ArenaStatus arenaStatus);

    String getDisplayStatus(Language lang);

    int getMaxPlayers();

    void setMaxPlayers(int players);

    int getCurrentPlayers();

    void setCurrentPlayers(int players);

    long getLastUpdate();

    void setLastUpdate(long time);

    int getMaxInTeam();

    void setMaxInTeam(int max);

    boolean addSpectator(Player player, String targetPlayer);

    // NULL if not party
    boolean addPlayer(Player player, String partyOwnerName);

    boolean reJoin(RemoteReJoin player);

    boolean equals(CachedArena arena);
}
