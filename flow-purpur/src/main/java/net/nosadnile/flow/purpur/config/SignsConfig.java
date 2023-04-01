package net.nosadnile.flow.purpur.config;

import net.nosadnile.flow.purpur.FlowPurpur;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class SignsConfig extends PluginConfig {

    public SignsConfig() {
        super(FlowPurpur.getPlugin(), "signs", FlowPurpur.getPlugin().getDataFolder().getPath());

        YamlConfiguration yml = getYml();

        yml.options().copyDefaults(true);

        yml.addDefault(ConfigPath.SIGNS_LIST_PATH, new ArrayList<>());
        yml.addDefault(ConfigPath.SIGNS_SETTINGS_STATIC_SHOW_PLAYING, false);

        save();
    }
}
