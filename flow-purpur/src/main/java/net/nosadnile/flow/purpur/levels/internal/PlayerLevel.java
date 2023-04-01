package net.nosadnile.flow.purpur.levels.internal;

import net.nosadnile.flow.purpur.FlowPurpur;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLevel {

    private static final HashMap<UUID, PlayerLevel> levelByPlayer = new HashMap<>();
    private final UUID uuid;
    private int level;
    private int nextLevelCost;
    private String levelName;
    private int currentXp;
    private String progressBar;
    private String requiredXp;
    private String formattedCurrentXp;

    /**
     * Cache a player level.
     */
    public PlayerLevel(UUID player) {
        level = 0;
        currentXp = 0;

        this.uuid = player;
        this.levelName = ChatColor.translateAlternateColorCodes('&', FlowPurpur.config.getString("levels-settings.default-name").replace("{number}", String.valueOf(level)));
        this.nextLevelCost = 1000;

        updateProgressBar();
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
        if (!levelByPlayer.containsKey(player)) levelByPlayer.put(player, this);
    }

    /**
     * Get PlayerLevel by player.
     */
    public static PlayerLevel getLevelByPlayer(UUID player) {
        return levelByPlayer.getOrDefault(player, new PlayerLevel(player));
    }

    /**
     * Update the player progress bar.
     */
    @SuppressWarnings("ConstantConditions")
    private void updateProgressBar() {
        double l1 = ((nextLevelCost - currentXp) / (double) (nextLevelCost)) * 10;
        int locked = (int) l1;
        int unlocked = 10 - locked;
        if (locked < 0 || unlocked < 0) {
            locked = 10;
            unlocked = 0;
        }

        progressBar = ChatColor.translateAlternateColorCodes('&', FlowPurpur.config.getYml().getString("levels-settings.progress-bar-format").replace("{progress}",
                FlowPurpur.config.getYml().getString("levels-settings.progress-bar-unlocked-color") + String.valueOf(new char[unlocked]).replace("\0", FlowPurpur.config.getYml().getString("levels-settings.progress-bar-symbol"))
                        + FlowPurpur.config.getYml().getString("levels-settings.progress-bar-locked-color") + String.valueOf(new char[locked]).replace("\0", FlowPurpur.config.getYml().getString("levels-settings.progress-bar-symbol"))));
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
    }

    /**
     * Get player current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set player level.
     */
    public void setLevel(int level) {
        this.level = level;
        this.levelName = ChatColor.translateAlternateColorCodes('&', levelName.replace("{number}", String.valueOf(level)));
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        updateProgressBar();
    }

    /**
     * Get the amount of xp required to level up.
     */
    public int getNextLevelCost() {
        return nextLevelCost;
    }

    /**
     * Get player uuid.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get player current level display name.
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Get player xp.
     */
    public int getCurrentXp() {
        return currentXp;
    }

    /**
     * Get progress bar for player.
     */
    public String getProgress() {
        return progressBar;
    }

    /**
     * Get target xp already formatted.
     * Like: 2000 is 2k
     */
    public String getFormattedRequiredXp() {
        return requiredXp;
    }

    /**
     * Set player xp.
     */
    public void setXp(int currentXp) {
        this.currentXp = currentXp;
        updateProgressBar();
    }

    /**
     * Get player xp already formatted.
     * Like: 1000 is 1k
     */
    public String getFormattedCurrentXp() {
        return formattedCurrentXp;
    }

    /**
     * Get player level as int.
     */
    public int getPlayerLevel() {
        return level;
    }

    /**
     * Destroy data.
     */
    public void destroy() {
        levelByPlayer.remove(uuid);
        //SpigotMain.remoteDatabase.setLevelData(uuid, level, currentXp);
    }

    public void lazyLoad(int level, int currentXp, @NotNull String levelName, int nextLevelCost) {
        this.levelName = ChatColor.translateAlternateColorCodes('&', levelName.replace("{number}", String.valueOf(level)));
        this.nextLevelCost = nextLevelCost;
        this.level = level;
        if (this.level < 0) this.level = 0;
        if (this.currentXp < 0) this.currentXp = 0;
        this.currentXp = currentXp;
        updateProgressBar();
    }
}
