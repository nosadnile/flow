package net.nosadnile.flow.purpur.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.nosadnile.flow.purpur.arena.Arena;
import net.nosadnile.flow.purpur.arena.ArenaManager;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Arena arena = ArenaManager.getArenaForPlayer(player);

        if (arena == null) return;

        if (arena.preBuilt.contains(event.getBlock().getLocation()))
            event.setCancelled(true);
    }
}
