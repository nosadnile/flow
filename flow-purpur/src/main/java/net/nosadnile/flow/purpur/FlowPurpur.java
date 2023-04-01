package net.nosadnile.flow.purpur;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.nosadnile.flow.purpur.api.bedwars.BedWars;
import net.nosadnile.flow.purpur.api.versionsupport.block.BlockSupport;
import net.nosadnile.flow.purpur.api.versionsupport.itemstack.ItemStackSupport;
import net.nosadnile.flow.purpur.api.versionsupport.material.MaterialSupport;
import net.nosadnile.flow.purpur.api.versionsupport.sound.SoundSupport;
import net.nosadnile.flow.purpur.arena.manager.ArenaManager;
import net.nosadnile.flow.purpur.arena.manager.ArenaSelectorListener;
import net.nosadnile.flow.purpur.arena.sign.SignManager;
import net.nosadnile.flow.purpur.commands.RejoinCommand;
import net.nosadnile.flow.purpur.commands.main.MainCommand;
import net.nosadnile.flow.purpur.commands.party.PartyCommand;
import net.nosadnile.flow.purpur.config.BedWarsConfig;
import net.nosadnile.flow.purpur.config.ConfigPath;
import net.nosadnile.flow.purpur.config.SoundsConfig;
import net.nosadnile.flow.purpur.database.*;
import net.nosadnile.flow.purpur.events.PlayerLoginListener;
import net.nosadnile.flow.purpur.events.PlayerLogoutListener;
import net.nosadnile.flow.purpur.events.PlayerMessageListener;
import net.nosadnile.flow.purpur.lang.LangListeners;
import net.nosadnile.flow.purpur.lang.LanguageManager;
import net.nosadnile.flow.purpur.levels.Level;
import net.nosadnile.flow.purpur.levels.internal.InternalLevel;
import net.nosadnile.flow.purpur.levels.internal.LevelListeners;
import net.nosadnile.flow.purpur.party.*;
import net.nosadnile.flow.purpur.socket.ServerSocketTask;
import net.nosadnile.flow.purpur.socket.TimeOutTask;
import net.nosadnile.flow.purpur.support.SupportPAPI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Random;

public class FlowPurpur extends JavaPlugin implements BedWars {
    public static Random random;
    public static FlowPurpur plugin;
    public static BedWarsConfig config;
    private static Database remoteDatabase = null;
    private static StatsCache statsCache;

    private static SoundSupport soundAdapter;
    private static MaterialSupport materialAdapter;
    private static BlockSupport blockAdapter;
    private static ItemStackSupport itemAdapter;

    private static Party party;
    private static Level levelManager;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Database getRemoteDatabase() {
        return remoteDatabase;
    }

    public static void setRemoteDatabase(Database remoteDatabase) {
        FlowPurpur.remoteDatabase = remoteDatabase;
    }

    public static StatsCache getStatsCache() {
        return statsCache;
    }

    public static MaterialSupport getMaterialAdapter() {
        return materialAdapter;
    }

    @SuppressWarnings("unused")
    public static BlockSupport getBlockAdapter() {
        return blockAdapter;
    }

    public static ItemStackSupport getItemAdapter() {
        return itemAdapter;
    }

    public static SoundSupport getSoundAdapter() {
        return soundAdapter;
    }

    private static void registerListeners(@NotNull Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, getPlugin());
        }
    }

    public static Party getParty() {
        return party;
    }

    public static Level getLevelManager() {
        return levelManager;
    }

    /**
     * Create a text component.
     */
    @NotNull
    public static TextComponent createTC(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    public static BedWars getAPI() {
        return FlowPurpur.plugin;
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        soundAdapter = SoundSupport.SupportBuilder.load();
        materialAdapter = MaterialSupport.SupportBuilder.load();
        blockAdapter = BlockSupport.SupportBuilder.load();
        itemAdapter = ItemStackSupport.SupportBuilder.load();

        LanguageManager.init();

        config = new BedWarsConfig();

        if (config.getBoolean("database.enable")) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> remoteDatabase = new MySQL());
        } else {
            remoteDatabase = new NoDatabase();
        }

        statsCache = new StatsCache();

        if (!ServerSocketTask.init(config.getInt(ConfigPath.GENERAL_CONFIGURATION_PORT))) {
            getLogger().severe("Could not register port: " + config.getInt(ConfigPath.GENERAL_CONFIGURATION_PORT));
            getLogger().severe("Please change it in config! Port already in use!");
        }

        getLogger().info("Listening for BedWars1058 arenas on port: " + config.getInt(ConfigPath.GENERAL_CONFIGURATION_PORT));

        registerListeners(new LangListeners(), new ArenaSelectorListener(), new CacheListener());

        // noinspection InstantiationOfUtilityClass
        new SoundsConfig();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Bukkit.getScheduler().runTaskTimer(this, new TimeOutTask(), 20L, 10L);

        // Party support
        if (config.getYml().getBoolean(ConfigPath.GENERAL_CONFIGURATION_ALLOW_PARTIES)) {
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("Parties")) {
                getLogger().info("Hook into Parties (by AlessioDP) support!");
                party = new Parties();
            } else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Spigot-Party-API-PAF")) {
                getLogger().info("Hook into Party and Friends Extended Edition for BungeeCord (by Simonsator) support!");
                party = new PAFBungeeCordParty();
            } else if (Bukkit.getServer().getPluginManager().isPluginEnabled("PartyAndFriends")) {
                getLogger().info("Hook into Party and Friends for Spigot (by Simonsator) support!");
                party = new PAF();
            }
        }

        if (party == null) {
            party = new Internal();

            getLogger().info("Loading internal Party system. /party");
        }

        levelManager = new InternalLevel();

        Bukkit.getPluginManager().registerEvents(new LevelListeners(), this);

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("bw", new MainCommand("bw"));
            commandMap.register("rejoin", new RejoinCommand("rejoin"));

            if (config.getBoolean(ConfigPath.GENERAL_ENABLE_PARTY_CMD)) {
                commandMap.register("party", new PartyCommand("party"));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // PlaceholderAPI Support
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Hook into PlaceholderAPI support!");
            new SupportPAPI().register();
        }

        Metrics metrics = new Metrics(this, 6036);

        metrics.addCustomChart(new SimplePie("default_language", () -> LanguageManager.get().getDefaultLanguage().getIso()));
        metrics.addCustomChart(new SimplePie("party_adapter", () -> getParty().getClass().getName()));
        metrics.addCustomChart(new SimplePie("level_adapter", () -> getLevelManager().getClass().getName()));

        SignManager.init();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLoginEvent&f!"));

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLogoutListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerLogoutEvent&f!"));

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMessageListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f Registered event &aPlayerMessageEvent&f!"));

        FlowPurpur.random = new Random();
    }

    @Override
    public void onDisable() {
        ServerSocketTask.stopTasks();
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public LanguageUtil getLanguageUtil() {
        return LanguageManager.get();
    }

    @Override
    public ArenaUtil getArenaUtil() {
        return ArenaManager.getInstance();
    }
}
