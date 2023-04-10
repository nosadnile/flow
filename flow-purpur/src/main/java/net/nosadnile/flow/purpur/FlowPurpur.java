package net.nosadnile.flow.purpur;

import net.nosadnile.flow.purpur.events.PlayerLoginListener;
import net.nosadnile.flow.purpur.events.PlayerLogoutListener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class FlowPurpur extends JavaPlugin {
    public static Random random;
    public static FlowPurpur plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));

        FlowPurpur.random = new Random();
    }

    @Override
    public void onDisable() {
        
    }
}
