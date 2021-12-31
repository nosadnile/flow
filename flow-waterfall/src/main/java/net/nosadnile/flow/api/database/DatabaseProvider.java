package net.nosadnile.flow.api.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.nosadnile.flow.FlowWaterfall;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class DatabaseProvider {
    private Plugin plugin;
    private Configuration config;
    private MongoClient db;

    public DatabaseProvider(Plugin plugin) {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + config.getString("database.host") + ":" + config.getString("database.port")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void disable() {
        db.close();
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable DatabaseProvider get() {
        return FlowWaterfall.db;
    }
}
