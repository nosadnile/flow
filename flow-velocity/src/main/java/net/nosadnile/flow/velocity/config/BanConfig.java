package net.nosadnile.flow.velocity.config;

import net.nosadnile.flow.api.time.TimeUnit;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.bans.BanList;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BanConfig {
    public static BanList getBanList() throws Exception {
        List<String> banned = Arrays.stream(FlowVelocity.configManager.getStringArray("bans.permanent")).toList();
        List<String> tempBannedArray = Arrays.stream(FlowVelocity.configManager.getStringArray("bans.temporary")).toList();

        Map<String, Pair<TimeUnit, Integer>> tempBanned = BanList.deserializeTempBans(tempBannedArray);

        return new BanList(banned, tempBanned);
    }

    public static void updateBanList(BanList list) {
        FlowVelocity.configManager.setStringArray("bans.permanent", list.banned.toArray(String[]::new));
        FlowVelocity.configManager.setStringArray("bans.temporary", list.serializeTempBans().toArray(String[]::new));
    }
}
