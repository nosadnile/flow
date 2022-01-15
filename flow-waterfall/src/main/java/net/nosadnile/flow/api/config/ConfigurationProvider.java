package net.nosadnile.flow.api.config;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class ConfigurationProvider {
    private String fileName;
    private Plugin plugin;
    private File file;
    private Configuration config;

    public ConfigurationProvider(Plugin plugin) {
        fileName = "config.yml";
        this.plugin = plugin;
        saveDefault();
        file = new File(plugin.getDataFolder(), fileName);
        try {
            config = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationProvider(String file, Plugin plugin) {
        fileName = file;
        this.plugin = plugin;
        saveDefault();
        this.file = new File(plugin.getDataFolder(), file);
        try {
            config = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefault() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File configFile = new File(plugin.getDataFolder(), fileName);
        URL u = plugin.getClass().getResource(fileName);
        if(u != null) {
            InputStream in = plugin.getResourceAsStream(fileName);
            if(!configFile.exists()) {
                try {
                    Files.copy(in, configFile.toPath());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(!configFile.exists()) {
                try {
                    configFile.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public Configuration get() {
        return config;
    }

    public ConfigurationProvider reloadConfiguration() {
        try {
            config = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ConfigurationProvider loadConfiguration(String file) {
        this.file = new File(plugin.getDataFolder(), file);
        try {
            config = net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ConfigurationProvider saveConfiguration() {
        try {
            net.md_5.bungee.config.ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ConfigurationProvider reloadConfig() {
        return reloadConfiguration();
    }

    public ConfigurationProvider loadConfig(String file) {
        return loadConfiguration(file);
    }

    public ConfigurationProvider saveConfig() {
        return saveConfiguration();
    }
}
