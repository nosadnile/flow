package net.nosadnile.flow.purpur.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InternalParty {

    List<UUID> members = new ArrayList<>();
    UUID owner;

    public InternalParty(UUID p) {
        owner = p;
        Internal.parties.add(this);
    }

    public UUID getOwner() {
        return owner;
    }

    void addMember(Player p) {
        members.add(p.getUniqueId());
    }
}
