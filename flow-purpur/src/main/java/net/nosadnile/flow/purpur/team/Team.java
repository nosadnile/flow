package net.nosadnile.flow.purpur.team;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.util.Tuple;
import net.nosadnile.flow.purpur.arena.GeneratorData;
import net.nosadnile.flow.purpur.arena.ShopData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Team {
    public NamedTextColor color;
    public Tuple<Location, BlockFace> bedLocation;

    public List<Player> players;
    public List<GeneratorData> generators;
    public List<ShopData> shops;

    public Team(NamedTextColor color) {
        this.color = color;
        this.bedLocation = null;
        this.players = new ArrayList<>();
        this.generators = null;
        this.shops = null;
    }

    public void spawnBed() {
        Block start = this.bedLocation.a().getBlock();
        BlockFace facing = this.bedLocation.b();
        Material material;

        if (this.color == NamedTextColor.WHITE) {
            material = Material.WHITE_BED;
        } else if (this.color == NamedTextColor.GOLD) {
            material = Material.ORANGE_BED;
        } else if (this.color == NamedTextColor.DARK_PURPLE) {
            material = Material.MAGENTA_BED;
        } else if (this.color == NamedTextColor.YELLOW) {
            material = Material.YELLOW_BED;
        } else if (this.color == NamedTextColor.GREEN) {
            material = Material.LIME_BED;
        } else if (this.color == NamedTextColor.GRAY) {
            material = Material.GRAY_BED;
        } else if (this.color == NamedTextColor.AQUA) {
            material = Material.CYAN_BED;
        } else if (this.color == NamedTextColor.LIGHT_PURPLE) {
            material = Material.PURPLE_BED;
        } else if (this.color == NamedTextColor.BLUE) {
            material = Material.BLUE_BED;
        } else if (this.color == NamedTextColor.RED) {
            material = Material.RED_BED;
        } else if (this.color == NamedTextColor.BLACK) {
            material = Material.BLACK_BED;
        } else {
            throw new InvalidParameterException("Unknown bed color!");
        }

        this.createBed(start, facing, material);
    }

    public String getName() {
        if (this.color == NamedTextColor.WHITE) {
            return "white";
        } else if (this.color == NamedTextColor.GOLD) {
            return "orange";
        } else if (this.color == NamedTextColor.DARK_PURPLE) {
            return "magenta";
        } else if (this.color == NamedTextColor.YELLOW) {
            return "yellow";
        } else if (this.color == NamedTextColor.GREEN) {
            return "lime";
        } else if (this.color == NamedTextColor.GRAY) {
            return "gray";
        } else if (this.color == NamedTextColor.AQUA) {
            return "cyan";
        } else if (this.color == NamedTextColor.LIGHT_PURPLE) {
            return "purple";
        } else if (this.color == NamedTextColor.BLUE) {
            return "blue";
        } else if (this.color == NamedTextColor.RED) {
            return "red";
        } else if (this.color == NamedTextColor.BLACK) {
            return "black";
        } else {
            throw new InvalidParameterException("Unknown team color!");
        }
    }

    public String getNameCapitalized() {
        if (this.color == NamedTextColor.WHITE) {
            return "White";
        } else if (this.color == NamedTextColor.GOLD) {
            return "Orange";
        } else if (this.color == NamedTextColor.DARK_PURPLE) {
            return "Magenta";
        } else if (this.color == NamedTextColor.YELLOW) {
            return "Yellow";
        } else if (this.color == NamedTextColor.GREEN) {
            return "Lime";
        } else if (this.color == NamedTextColor.GRAY) {
            return "Gray";
        } else if (this.color == NamedTextColor.AQUA) {
            return "Cyan";
        } else if (this.color == NamedTextColor.LIGHT_PURPLE) {
            return "Purple";
        } else if (this.color == NamedTextColor.BLUE) {
            return "Blue";
        } else if (this.color == NamedTextColor.RED) {
            return "Red";
        } else if (this.color == NamedTextColor.BLACK) {
            return "Black";
        } else {
            throw new InvalidParameterException("Unknown team color!");
        }
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
