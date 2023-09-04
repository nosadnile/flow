package net.nosadnile.flow.purpur.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class NPCHelper {
    public Location position;
    public String skin;
    public String name;

    public NPCHelper(String skin, String name, Location position) {
        this.name = name;
        this.skin = skin;
        this.position = position;
    }

    public void spawn() {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);

        npc.spawn(position);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(skin);
    }
}
