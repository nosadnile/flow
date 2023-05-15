package net.nosadnile.flow.velocity.ranks;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import net.nosadnile.flow.api.database.FlowDatabaseAPI;
import net.nosadnile.flow.velocity.FlowVelocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RankManager {
    public List<Rank> ranks;

    public RankManager() {
        this.ranks = new ArrayList<>();
    }

    public void load() {
        this.ranks.clear();

        FlowDatabaseAPI api = FlowVelocity.database;
        MongoClient mongo = api.getClient();

        if (mongo == null) return;

        DB db = mongo.getDB(api.getDatabase());
        DBCollection ranks = db.getCollection("ranks");

        List<DBObject> objects = ranks.find().toArray();

        for (DBObject object : objects) {
            Rank rank = Rank.load(object);

            this.ranks.add(rank);
        }
    }

    public void save() {
        for (Rank rank : this.ranks) {
            rank.push();
        }
    }

    public Optional<Rank> getRank(String name) {
        for (Rank rank : this.ranks) {
            if (rank.getName().equalsIgnoreCase(name))
                return Optional.of(rank);
        }

        return Optional.empty();
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();

        for (Rank rank : this.ranks) {
            names.add(rank.getName());
        }

        return names;
    }
}
