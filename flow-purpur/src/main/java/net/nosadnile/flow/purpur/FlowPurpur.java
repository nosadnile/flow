package net.nosadnile.flow.purpur;

import net.nosadnile.flow.purpur.events.PlayerLoginEvent;
import net.nosadnile.flow.purpur.events.PlayerLogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class FlowPurpur extends JavaPlugin {
    public static Random random;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));

        FlowPurpur.random = new Random();
    }

    @Override
    public void onDisable() {
    }
}
