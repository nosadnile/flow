package net.nosadnile.flow.app;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import net.nosadnile.flow.api.database.DatabaseProvider;

public class AppMain {
    public static void main(String[] args) {
        DatabaseProvider db_ = new DatabaseProvider("localhost", "27017");
        MongoClient db = db_.getDB();
        DB flowDb = db.getDB("_nsn_flow-pre-alpha-testing");
        List<DBObject> rw08ranks = new ArrayList<>();
        rw08ranks.add(new BasicDBObject("_id", 0)
        .append("name", "admin")
        .append("prefix", "[Admin]")
        .append("color", "Red")
        .append("permissions", "net.nosadnile.flow.admin.*"));
        DBObject player = new BasicDBObject("_id", "111dff66-087b-4948-bbd4-f47ed46af6ac")
                                    .append("username", "RedstoneWizard08")
                                    .append("level", 1)
                                    .append("xp", 0)
                                    .append("ranks", rw08ranks);
        DBCollection players = flowDb.getCollection("players");
        players.insert(player);
        db_.disable();
    }
}
