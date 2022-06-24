package net.nosadnile.flow;

import com.github.juliarn.npc.NPCPool;
import net.md_5.bungee.api.ChatColor;
import net.nosadnile.flow.command.SpawnNPC;
import net.nosadnile.flow.events.NPCEvents;
import net.nosadnile.flow.events.PlayerLoginEvent;
import net.nosadnile.flow.events.PlayerLogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class FlowSpigot extends JavaPlugin {
    public static NPCPool npcPool;
    public static Random random;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));

        Bukkit.getPluginManager().registerEvents(new NPCEvents(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered events &aNPCEvents&f!"));
        Objects.requireNonNull(this.getCommand("npc")).setExecutor(new SpawnNPC());
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered command &aSpawnNPC&f!"));

        FlowSpigot.npcPool = NPCPool.builder(this)
                .spawnDistance(60)
                .actionDistance(30)
                .tabListRemoveTicks(20)
                .build();

        FlowSpigot.random = new Random();
    }

    @Override
    public void onDisable() {

    }
}
