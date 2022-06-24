package net.nosadnile.flow.events;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.event.PlayerNPCHideEvent;
import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.AnimationModifier;
import com.github.juliarn.npc.modifier.MetadataModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCEvents implements Listener {
    @EventHandler
    public void handleNPCShow(PlayerNPCShowEvent e) {
        NPC npc = e.getNPC();

        e.send(
                npc.animation().queue(AnimationModifier.EntityAnimation.SWING_MAIN_ARM),
                npc.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true)
        );
    }

    @EventHandler
    public void handleNPCHide(PlayerNPCHideEvent e) {
        Player player = e.getPlayer();
        NPC npc = e.getNPC();

        if (e.getReason() == PlayerNPCHideEvent.Reason.EXCLUDED) {
            npc.removeExcludedPlayer(player);
        }
    }

    @EventHandler
    public void handleNPCInteract(PlayerNPCInteractEvent e) {
        Player player = e.getPlayer();
        NPC npc = e.getNPC();

        if (e.getUseAction() == PlayerNPCInteractEvent.EntityUseAction.ATTACK) {
            player.sendMessage("Why are you hitting me? :(");
            npc.rotation().queueLookAt(player.getEyeLocation()).send(player);
        }
    }
}
