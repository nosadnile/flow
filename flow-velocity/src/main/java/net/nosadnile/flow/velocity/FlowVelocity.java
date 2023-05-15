package net.nosadnile.flow.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.nosadnile.flow.api.database.FlowDatabaseAPI;
import net.nosadnile.flow.velocity.bans.BanManager;
import net.nosadnile.flow.velocity.commands.ListCommand;
import net.nosadnile.flow.velocity.commands.LobbyCommand;
import net.nosadnile.flow.velocity.commands.RankCommand;
import net.nosadnile.flow.velocity.commands.ServerCommand;
import net.nosadnile.flow.velocity.config.ConfigAccessor;
import net.nosadnile.flow.velocity.config.ConfigManager;
import net.nosadnile.flow.velocity.config.CredentialConfig;
import net.nosadnile.flow.velocity.events.ChatEventHandler;
import net.nosadnile.flow.velocity.events.LoginEventHandler;
import net.nosadnile.flow.velocity.events.LogoutEventHandler;
import net.nosadnile.flow.velocity.events.ServerSwitchEventHandler;
import net.nosadnile.flow.velocity.ranks.RankManager;
import net.nosadnile.flow.velocity.tasks.ServerTickTask;
import net.nosadnile.flow.velocity.util.ColorUtil;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "flow-velocity",
        name = "Flow",
        version = "1.3.0",
        description = "Powering the NoSadNile Network since 2022.",
        url = "https://www.nosadnile.net/",
        authors = {"RedstoneWizard08", "NoSadBeHappy", "KingGoldGolem", "EverestPlayer", "CloudWolf818"}
)
public class FlowVelocity {

    public static ProxyServer server;
    public static CommandManager commandManager;
    public static Logger logger;
    public static Path dataDirectory;
    public static LuckPerms luckPerms;
    public static ConfigManager configManager;
    public static FlowDatabaseAPI database;
    public static BanManager banManager;
    public static RankManager rankManager;
    public static FlowVelocity INSTANCE;

    @Inject
    public FlowVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        FlowVelocity.server = server;
        FlowVelocity.commandManager = server.getCommandManager();
        FlowVelocity.logger = logger;
        FlowVelocity.dataDirectory = dataDirectory.toAbsolutePath();
        FlowVelocity.banManager = new BanManager();
        FlowVelocity.rankManager = new RankManager();
        FlowVelocity.INSTANCE = this;

        this.doInit();
    }

    public void doInit() {
        // =========================== Begin initialization ===========================

        String defaultConfig = """
                database:
                  host: localhost
                  port: 27017
                  user: MyUsername
                  pass: MyPassword
                  name: flow-db
                  usePass: true
                  anon: false
                                
                bans:
                  permanent:
                  temporary:
                """;

        // =========================== Setup config dir ===========================

        boolean mkdirs = FlowVelocity.dataDirectory.toFile().mkdirs();

        if (!mkdirs) return;

        // =========================== Save default config ===========================

        Path configPath = FlowVelocity.dataDirectory.resolve("config.yml");

        if (!configPath.toFile().exists()) {
            try {
                if (!configPath.toFile().createNewFile()) return;

                FileOutputStream fos = new FileOutputStream(configPath.toFile());

                fos.write(defaultConfig.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // =========================== Check for config ===========================

        if (FlowVelocity.configManager == null)
            this.doInit();

        // =========================== Connect to LuckPermsHook ===========================

        luckPerms = LuckPermsProvider.get();
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f LuckPermsHook connected!"));

        // =========================== Load config ===========================

        try {
            configManager = ConfigAccessor.getConfig();
            server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Loaded config!"));
        } catch (IOException e) {
            server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Problem loading config: " + e.getMessage()));
        }

        // =========================== Connect to database ===========================

        database = new FlowDatabaseAPI();
        database.login(CredentialConfig.getCredentials());

        try {
            database.connect();

            server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Connected to database!"));
        } catch (Exception e) {
            server.getConsoleCommandSource().sendMessage(
                    ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Problem connecting to database: " + e.getMessage())
            );
        }

        // =========================== Load bans ===========================

//        try {
//            banManager.load();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // =========================== Load ranks ===========================

        rankManager.load();

        // =========================== Register events ===========================

        server.getEventManager().register(this, new ChatEventHandler());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered event: ChatEventHandler"));

        server.getEventManager().register(this, new LoginEventHandler());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered event: LoginEventHandler"));

        server.getEventManager().register(this, new LogoutEventHandler());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered event: LogoutEventHandler"));

        server.getEventManager().register(this, new ServerSwitchEventHandler());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered event: ServerSwitchEventHandler"));

        // =========================== Register commands ===========================

        commandManager.register(ListCommand.getMeta(), new ListCommand());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered command: ListCommand"));

        commandManager.register(ServerCommand.getMeta(), new ServerCommand());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered command: ServerCommand"));

        commandManager.register(LobbyCommand.getMeta(), new LobbyCommand());
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered command: LobbyCommand"));

        commandManager.register(RankCommand.getMeta(), RankCommand.create(FlowVelocity.server));
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered command: RankCommand"));

        // =========================== Register tasks ===========================

        server.getScheduler().buildTask(this, ServerTickTask::run).repeat(1, TimeUnit.SECONDS).schedule();
        server.getConsoleCommandSource().sendMessage(ColorUtil.translateColorCodes('&', "&6[&f!&6]&f Registered task: ServerTickTask"));
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        // =========================== Kick all players ===========================

        for (Player player : server.getAllPlayers()) {
            player.disconnect(ColorUtil.translateColorCodes('&', "&6Server restarting."));
        }

        // =========================== Save ranks ===========================

        rankManager.save();

        // =========================== Disconnect from database ===========================

        if (database.connected) {
            database.disconnect();
        }
    }
}
