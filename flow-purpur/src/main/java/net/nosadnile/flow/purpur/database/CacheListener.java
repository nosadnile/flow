package net.nosadnile.flow.purpur.database;

import net.nosadnile.flow.purpur.FlowPurpur;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class CacheListener implements Listener {

    @EventHandler
    // Create cache for player if does not exist yet.
    public void onLogin(PlayerLoginEvent e) {
        if (e == null) return;
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(FlowPurpur.getPlugin(), () -> {
            //create cache row for player
            FlowPurpur.getStatsCache().createStatsCache(p);
            //update local cache for player
            FlowPurpur.getRemoteDatabase().updateLocalCache(p.getUniqueId());
        });
    }
}
