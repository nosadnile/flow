package net.nosadnile.flow.purpur.support;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import net.nosadnile.flow.purpur.api.bedwars.Messages;
import net.nosadnile.flow.purpur.arena.manager.ArenaManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import static net.nosadnile.flow.purpur.lang.LanguageHelper.getMsg;

public class SupportPAPI extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "bw1058";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "andrei1058";
    }

    @NotNull
    @Override
    public String getVersion() {
        return FlowPurpur.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {

        if (s.contains("group_count_")) {
            String group = s.replace("group_count_", "");
            int amount = ArenaManager.getArenas().stream().filter(a -> a.getArenaGroup().equalsIgnoreCase(group)).mapToInt(CachedArena::getCurrentPlayers).sum();
            return String.valueOf(amount);
        }

        String replay = "";
        switch (s) {
            case "current_online":
                replay = String.valueOf(ArenaManager.getArenas().stream().mapToInt(CachedArena::getCurrentPlayers).sum());
                break;
            case "current_arenas":
                replay = String.valueOf(ArenaManager.getArenas().size());
                break;
        }
        if (!replay.isEmpty()) return replay;
        if (p == null) return null;
        if (p.getUniqueId() == null) return null;
        switch (s) {
            case "stats_firstplay":
                replay = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(FlowPurpur.getStatsCache().getPlayerFirstPlay(p.getUniqueId()));
                break;
            case "stats_lastplay":
                replay = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(FlowPurpur.getStatsCache().getPlayerLastPlay(p.getUniqueId()));
                break;
            case "stats_kills":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerKills(p.getUniqueId()));
                break;
            case "stats_total_kills":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerKills(p.getUniqueId()) + FlowPurpur.getStatsCache().getPlayerFinalKills(p.getUniqueId()));
                break;
            case "stats_wins":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerWins(p.getUniqueId()));
                break;
            case "stats_finalkills":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerFinalKills(p.getUniqueId()));
                break;
            case "stats_deaths":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerDeaths(p.getUniqueId()));
                break;
            case "stats_losses":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerLoses(p.getUniqueId()));
                break;
            case "stats_finaldeaths":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerFinalDeaths(p.getUniqueId()));
                break;
            case "stats_bedsdestroyed":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerBedsDestroyed(p.getUniqueId()));
                break;
            case "stats_gamesplayed":
                replay = String.valueOf(FlowPurpur.getStatsCache().getPlayerGamesPlayed(p.getUniqueId()));
                break;
            case "player_level":
                replay = FlowPurpur.getLevelManager().getLevel(p);
                break;
            case "player_level_raw":
                replay = String.valueOf(FlowPurpur.getLevelManager().getPlayerLevel(p));
                break;
            case "player_progress":
                replay = FlowPurpur.getLevelManager().getProgressBar(p);
                break;
            case "player_xp_formatted":
                replay = FlowPurpur.getLevelManager().getCurrentXpFormatted(p);
                break;
            case "player_xp":
                replay = String.valueOf(FlowPurpur.getLevelManager().getCurrentXp(p));
                break;
            case "player_rerq_xp_formatted":
            case "player_req_xp_formatted":
                replay = FlowPurpur.getLevelManager().getRequiredXpFormatted(p);
                break;
            case "player_rerq_xp":
            case "player_req_xp":
                replay = String.valueOf(FlowPurpur.getLevelManager().getRequiredXp(p));
                break;
        }

        return replay;
    }
}
