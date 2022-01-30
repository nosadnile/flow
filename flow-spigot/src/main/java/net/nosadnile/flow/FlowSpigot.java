package net.nosadnile.flow;

import net.md_5.bungee.api.ChatColor;
import net.nosadnile.flow.events.PlayerLoginEvent;
import net.nosadnile.flow.events.PlayerLogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FlowSpigot extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutEvent(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));
    }

    @Override
    public void onDisable() {

    }
}
