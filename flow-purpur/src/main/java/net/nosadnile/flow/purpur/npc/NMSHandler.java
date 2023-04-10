package net.nosadnile.flow.purpur.npc;

import org.bukkit.Bukkit;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;

public class NMSHandler {
    public static MinecraftServer getServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static WorldServer getWorld(World world) {
        return ((CraftWorld) world).getHandle();
    }

    public static PlayerConnection getConnection(Player player) {
        return ((CraftPlayer) player).getHandle().b;
    }
}
