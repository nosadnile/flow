package net.nosadnile.flow.purpur.arena;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import net.nosadnile.flow.purpur.team.Team;

@SuppressWarnings("deprecation")
public class Arena {
    public List<GeneratorData> generators;
    public List<Team> teams;
    public List<Location> preBuilt;

    public void spawn() {
        for (Team team : teams) {
            for (ShopData shop : team.shops) {
                this.spawnShopNPC(shop);
            }
        }
    }

    public void spawnShopNPC(ShopData data) {
        switch (data.type) {
            case ITEM_SHOP:
                // TODO: Spawn NPC
                return;

            case TEAM_SHOP:
                // TODO: Spawn NPC
                return;
        }
    }

    public void spawnGenerators() {
        for (GeneratorData data : generators) {
            switch (data.type) {
                case DIAMOND:
                    Entity entity = data.location.getWorld().spawnEntity(data.location, EntityType.ARMOR_STAND);
                    ArmorStand armorStand = (ArmorStand) entity;

                    armorStand.setCustomName(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            "&bDiamond\n&eTier &cI\n&eSpawns in &c1 &esecond!&r"
                        )
                    );

                    armorStand.setVisible(false);
                    armorStand.setCustomNameVisible(true);

                    armorStand.teleport(
                        new Location(
                            data.location.getWorld(),
                            data.location.getX(),
                            data.location.getY() + 3.0,
                            data.location.getZ()
                        )
                    );

                    return;
                
                case EMERALD:

                    return;
                
                default:
                    return;
            }
        }
    }
}
