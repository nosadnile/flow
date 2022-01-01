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
    private boolean hasConfiguration = false;

    public DatabaseProvider(Plugin plugin) {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
            hasConfiguration = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + config.getString("database.host") + ":" + config.getString("database.port")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public DatabaseProvider(String host, String port) {
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + host + ":" + port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public DatabaseProvider() {
        try {
            db = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void disable() {
        db.close();
        if(!hasConfiguration) return;
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MongoClient getDB() {
        return db;
    }

    public static @Nullable DatabaseProvider get() {
        return FlowWaterfall.db;
    }
}
