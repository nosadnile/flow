package net.nosadnile.flow.purpur.lang;

import net.nosadnile.flow.purpur.api.bedwars.Language;
import net.nosadnile.flow.purpur.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LanguageHelper extends PluginConfig implements Language {

    private final String iso;
    private String prefix = "";

    public LanguageHelper(Plugin plugin, String iso) {
        super(plugin, "messages_" + iso, "plugins/" + plugin.getName() + "/Languages");
        this.iso = iso;
        LanguageManager.get().addLanguage(this);
    }

    public static String getMsg(Player p, String m) {
        return LanguageManager.get().getMsg(p, m);
    }

    public String getLangName() {
        return getYml().getString("name");
    }

    public boolean exists(String path) {
        return getYml().get(path) != null;
    }

    public String getMsg(String path) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getYml().getString(path)).replace("{prefix}", prefix));
    }

    public List<String> getList(String path) {
        return getYml().getStringList(path).stream().map(s -> s = ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getIso() {
        return iso;
    }
}
