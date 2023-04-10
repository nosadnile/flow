package net.nosadnile.flow.purpur.team;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.nosadnile.flow.purpur.arena.GeneratorData;
import net.nosadnile.flow.purpur.arena.ShopData;

public class Team {
    public Location bedLocation;
    public List<Player> players;
    public List<GeneratorData> generators;
    public List<ShopData> shops;
}
