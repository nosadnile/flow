package net.nosadnile.flow.purpur.npc;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.Position;
import net.minecraft.server.level.EntityPlayer;

public class NPC extends EntityPlayer {
    public Location position;
    public String skinUuid;
    public String name;

    public NPC(String skinUuid, String name, Location position) {
        super(NMSHandler.getServer(), NMSHandler.getWorld(position.getWorld()),
                new GameProfile(UUID.fromString(skinUuid), name));

        this.name = name;
        this.skinUuid = skinUuid;
        this.position = position;

        this.teleportTo(NMSHandler.getWorld(position.getWorld()),
                new Position(position.getX(), position.getY(), position.getZ()));
    }

    public void spawn() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.spawnForPlayer(player);
        }
    }

    public void spawnForPlayer(Player player) {
        PacketContainer playerInfoPacket = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        PacketContainer spawnPacket = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        PacketContainer rotationPacket = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        PacketContainer tabListPacket = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);

        PlayerInfoData info = new PlayerInfoData(
                new WrappedGameProfile(UUID.fromString(skinUuid), name),
                0,
                NativeGameMode.NOT_SET,
                WrappedChatComponent.fromText(name));

        playerInfoPacket.getPlayerInfoAction().write(0, PlayerInfoAction.ADD_PLAYER);
        playerInfoPacket.getPlayerInfoDataLists().write(0, Collections.singletonList(info));

        spawnPacket.getPlayerInfoDataLists().write(0, Collections.singletonList(info));

        rotationPacket.getPlayerInfoDataLists().write(0, Collections.singletonList(info));
        rotationPacket.getBytes().write(0, (byte) ((position.getYaw() * 256f) / 360f));

        tabListPacket.getPlayerInfoAction().write(0, PlayerInfoAction.REMOVE_PLAYER);
        tabListPacket.getPlayerInfoDataLists().write(0, Collections.singletonList(info));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, playerInfoPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, spawnPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, rotationPacket);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, tabListPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
