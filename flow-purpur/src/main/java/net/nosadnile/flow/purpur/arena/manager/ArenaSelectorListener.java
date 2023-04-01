package net.nosadnile.flow.purpur.arena.manager;

import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.api.bedwars.ArenaStatus;
import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.api.events.ArenaCacheCreateEvent;
import net.nosadnile.flow.purpur.api.events.ArenaCacheRemoveEvent;
import net.nosadnile.flow.purpur.api.events.ArenaCacheUpdateEvent;
import net.nosadnile.flow.purpur.config.SoundsConfig;
import net.nosadnile.flow.purpur.lang.LanguageHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaSelectorListener implements Listener {

    @EventHandler
    public void onArenaSelectorClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof ArenaGUI.SelectorHolder) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack i = e.getCurrentItem();

            if (i == null) return;
            if (i.getType() == Material.AIR) return;

            if (!FlowPurpur.getItemAdapter().hasTag(i, "server")) return;
            if (!FlowPurpur.getItemAdapter().hasTag(i, "world_identifier")) return;
            String server = FlowPurpur.getItemAdapter().getTag(i, "server");
            String identifier = FlowPurpur.getItemAdapter().getTag(i, "world_identifier");

            CachedArena a = ArenaManager.getInstance().getArena(server, identifier);
            if (a == null) return;

            if (e.getClick() == ClickType.LEFT) {
                if ((a.getStatus() == ArenaStatus.WAITING || a.getStatus() == ArenaStatus.STARTING) && a.addPlayer(p, null)) {
                    SoundsConfig.playSound("join-allowed", p);
                } else {
                    SoundsConfig.playSound("join-denied", p);
                    p.sendMessage(LanguageHelper.getMsg(p, Messages.ARENA_JOIN_DENIED_SELECTOR));
                }
            } else if (e.getClick() == ClickType.RIGHT) {
                if (a.getStatus() == ArenaStatus.PLAYING && a.addSpectator(p, null)) {
                    SoundsConfig.playSound("spectate-allowed", p);
                } else {
                    p.sendMessage(LanguageHelper.getMsg(p, Messages.ARENA_SPECTATE_DENIED_SELECTOR));
                    SoundsConfig.playSound("spectate-denied", p);
                }
            }
            p.closeInventory();
        }
    }

    @EventHandler
    public void onArenaSelectorClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        ArenaGUI.getRefresh().remove(e.getPlayer());
    }

    @EventHandler
    public void onCacheCreate(ArenaCacheCreateEvent e) {
        for (Player p : ArenaGUI.getRefresh().keySet()) {
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onCacheUpdate(ArenaCacheUpdateEvent e) {
        for (Player p : ArenaGUI.getRefresh().keySet()) {
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onCacheDelete(ArenaCacheRemoveEvent e) {
        for (Player p : ArenaGUI.getRefresh().keySet()) {
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }
}
