package net.nosadnile.flow.purpur.team;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.util.Tuple;
import net.nosadnile.flow.purpur.arena.GeneratorData;
import net.nosadnile.flow.purpur.arena.ShopData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.List;

@SuppressWarnings("deprecation")
public class Team {
    public ChatColor color;
    public Tuple<Location, BlockFace> bedLocation;

    public List<Player> players;
    public List<GeneratorData> generators;
    public List<ShopData> shops;

    public void spawnBed() {
        Block start = this.bedLocation.a().getBlock();
        BlockFace facing = this.bedLocation.b();
        Material material;

        if (this.color == ChatColor.WHITE) {
            material = Material.WHITE_BED;
        } else if (this.color == ChatColor.GOLD) {
            material = Material.ORANGE_BED;
        } else if (this.color == ChatColor.DARK_PURPLE) {
            material = Material.MAGENTA_BED;
        } else if (this.color == ChatColor.YELLOW) {
            material = Material.YELLOW_BED;
        } else if (this.color == ChatColor.GREEN) {
            material = Material.LIME_BED;
        } else if (this.color == ChatColor.GRAY) {
            material = Material.GRAY_BED;
        } else if (this.color == ChatColor.AQUA) {
            material = Material.CYAN_BED;
        } else if (this.color == ChatColor.LIGHT_PURPLE) {
            material = Material.PURPLE_BED;
        } else if (this.color == ChatColor.BLUE) {
            material = Material.BLUE_BED;
        } else if (this.color == ChatColor.RED) {
            material = Material.RED_BED;
        } else if (this.color == ChatColor.BLACK) {
            material = Material.BLACK_BED;
        } else {
            throw new InvalidParameterException("Unknown bed color!");
        }

        this.createBed(start, facing, material);
    }

    public void createBed(Block start, BlockFace facing, Material material) {
        for (Bed.Part part : Bed.Part.values()) {
            start.setType(material);

            final Bed bedState = (Bed) start.getBlockData();

            bedState.setPart(part);
            bedState.setFacing(facing);

            start.setBlockData(bedState);
            start = start.getRelative(facing.getOppositeFace());
        }
    }

    public boolean isBed(Location location) {
        Block start = this.bedLocation.a().getBlock();
        Block end = start.getRelative(this.bedLocation.b().getOppositeFace());

        Location pos1 = start.getLocation();
        Location pos2 = end.getLocation();

        return location.equals(pos1) || location.equals(pos2);
    }

    public void eliminate() {
        World world = this.bedLocation.a().getWorld();
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            player.playSound(Sound.sound(Key.key("minecraft:entity/ender_dragon/growl"), Sound.Source.AMBIENT, 1, 1));
        }
    }
}
