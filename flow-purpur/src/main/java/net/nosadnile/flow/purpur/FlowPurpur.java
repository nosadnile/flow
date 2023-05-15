package net.nosadnile.flow.purpur;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.nosadnile.flow.purpur.commands.NPCCommand;
import net.nosadnile.flow.purpur.events.PlayerLoginListener;
import net.nosadnile.flow.purpur.events.PlayerLogoutListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

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
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(false));
        CommandAPI.onEnable();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));

        FlowPurpur.random = new Random();

        NPCCommand.register();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
