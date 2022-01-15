package net.nosadnile.flow.api.rank;

import com.mongodb.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.database.DatabaseProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankManager {
    private Map<String, PlayerRank> ranks;

    public RankManager() {
        ranks = new HashMap<>();
        refreshFromDatabase();
    }

    public PlayerRank getByName(String name) {
        return ranks.get(name);
    }

    public boolean isRegistered(String name) {
        return ranks.containsKey(name);
    }

    public void registerRank(String name, PlayerRank rank) {
        ranks.put(name, rank);
    }

    public void unregisterRank(String name) {
        ranks.remove(name);
    }

    public Map<String, PlayerRank> getRanks() {
        return ranks;
    }

    public void refreshFromDatabase() {
        DatabaseProvider db_ = FlowWaterfall.db;
        MongoClient db = db_.get();
        DB flowDb = db.getDB("_nsn_flow-pre-alpha-testing");
        DBCollection ranks_ = flowDb.getCollection("ranks");
        List<DBObject> ranks = ranks_.find().toArray();
        for(DBObject rank : ranks) {
            Object[] perms_ = ((BasicDBList) rank.get("permissions")).toArray();
            String[] perms = new String[perms_.length];
            for(int i = 0; i < perms_.length; i++) {
                perms[i] = perms_[i].toString();
            }
            registerRank(rank.get("_id").toString(), new PlayerRank(rank.get("_id").toString(), rank.get("prefix").toString(), rank.get("color").toString(), perms));
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[RANKS]&f Registered rank &a" + rank.get("_id") + "&f!"));
        }
    }

    public void saveToDatabase() {
        DatabaseProvider db_ = FlowWaterfall.db;
        MongoClient db = db_.get();
        DB flowDb = db.getDB("_nsn_flow-pre-alpha-testing");
        DBCollection ranks_ = flowDb.getCollection("ranks");
        for(PlayerRank rank : ranks.values()) {
            DBCursor cur = ranks_.find(new BasicDBObject("_id", rank.getName()));
            if(cur.toArray().size() == 0 || cur.toArray().get(0) == null) {
                ranks_.insert(rank.toDatabaseObject());
            } else {
                ranks_.findAndRemove(new BasicDBObject("_id", rank.getName()));
                ranks_.insert(rank.toDatabaseObject());
            }
        }
    }
}
