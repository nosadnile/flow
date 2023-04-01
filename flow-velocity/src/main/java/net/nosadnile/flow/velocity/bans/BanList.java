package net.nosadnile.flow.velocity.bans;

import net.nosadnile.flow.api.time.TimeUnit;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanList {
    public List<String> banned;
    public Map<String, Pair<TimeUnit, Integer>> tempBanned;

    public BanList(List<String> banned, Map<String, Pair<TimeUnit, Integer>> tempBanned) {
        this.banned = banned;
        this.tempBanned = tempBanned;
    }

    public static Map<String, Pair<TimeUnit, Integer>> deserializeTempBans(List<String> tempBans) throws Exception {
        Map<String, Pair<TimeUnit, Integer>> parsed = new HashMap<>();

        for (String tempBan : tempBans) {
            String[] split = tempBan.split("::");

            String player = split[0];
            String pairString = split[1];

            String[] splitPair = pairString.split("==");

            TimeUnit unit = TimeUnit.deserialize(splitPair[0]);
            int time = Integer.parseInt(splitPair[1]);

            Pair<TimeUnit, Integer> pair = new Pair<>(unit, time);

            parsed.put(player, pair);
        }

        return parsed;
    }

    public void add(String player) {
        this.banned.add(player);
    }

    public void addTemp(String player, Pair<TimeUnit, Integer> time) {
        this.tempBanned.put(player, time);
    }

    public List<String> serializeTempBans() {
        List<String> serialized = new ArrayList<>();

        for (Map.Entry<String, Pair<TimeUnit, Integer>> entry : this.tempBanned.entrySet()) {
            String player = entry.getKey();
            Pair<TimeUnit, Integer> pair = entry.getValue();

            String unitString = pair.getValue0().serialize();
            String pairString = unitString + "==" + pair.getValue1();

            String serializedString = player + "::" + pairString;

            serialized.add(serializedString);
        }

        return serialized;
    }
}
