package net.nosadnile.flow.api.player;

import com.mongodb.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.nosadnile.flow.FlowWaterfall;
import net.nosadnile.flow.api.database.DatabaseProvider;

import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private Map<String, NSNPlayer> players;

    public PlayerManager() {
        players = new HashMap<>();
        refreshFromDatabase();
    }

    public void addPlayer(ProxiedPlayer player) {
        players.put(player.getName(), new NSNPlayer(player));
    }

    public void addPlayer(String name, NSNPlayer player) {
        players.put(name, player);
    }

    public void replacePlayer(String name, NSNPlayer replaceWith) {
        players.replace(name, replaceWith);
    }

    public NSNPlayer getPlayer(String name) {
        return players.get(name);
    }

    public NSNPlayer getPlayerByUUID(String uuid) {
        for(NSNPlayer p : players.values()) {
            if(p.getId() == uuid) {
                return p;
            }
        }
        return null;
    }

    public void removePlayer(ProxiedPlayer player) throws NameNotFoundException {
        if(players.containsKey(player.getName())) {
            players.remove(player.getName());
        } else {
            throw new NameNotFoundException(player.getName() + " was not found in the database!");
        }
    }

    public void removePlayer(String playerName) throws NameNotFoundException {
        if(players.containsKey(playerName)) {
            players.remove(playerName);
        } else {
            throw new NameNotFoundException(playerName + " was not found in the database!");
        }
    }

    public boolean isPlayerInDatabase(ProxiedPlayer player) {
        return players.containsKey(player.getName());
    }

    public boolean isPlayerInDatabase(String playerName) {
        return players.containsKey(playerName);
    }

    public boolean isPlayerInDatabaseUUID(String uuid) {
        for(NSNPlayer p : players.values()) {
            if(p.getId() == uuid) {
                return true;
            }
        }
        return false;
    }

    public void onLogin(ProxiedPlayer p) {
        if(!players.containsKey(p.getName())) {
            addPlayer(p);
            System.out.println(players);
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Registered new player &a" + p.getName() + "&f!"));
        }
        NSNPlayer np = getPlayer(p.getName());
        np.setPlayer(p);
        replacePlayer(p.getName(), np);
    }

    public void refreshFromDatabase() {
        DatabaseProvider db_ = FlowWaterfall.db;
        MongoClient db = db_.get();
        DB flowDb = db.getDB("_nsn_flow-pre-alpha-testing");
        DBCollection players_ = flowDb.getCollection("players");
        List<DBObject> players = players_.find().toArray();
        ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Trying to refresh players..."));
        for(DBObject player : players) {
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Found tentative player " + player.get("_id") + "..."));
            Object[] ranks_ = ((BasicDBList) player.get("ranks")).toArray();
            String[] ranks = new String[ranks_.length];
            for(int i = 0; i < ranks_.length; i++) {
                ranks[i] = ranks_[i].toString();
            }
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Found tentative player " + player.get("_id") + "'s ranks..."));
            if(player.get("name") == null) {
                ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Tentative player " + player.get("_id") + " is null."));
                continue;
            }
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Found player &a" + player.get("name") + "&f in the database!"));
            addPlayer(player.get("name").toString(), new NSNPlayer(player.get("_id").toString(), player.get("name").toString()));
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Setting stats for player &a" + player.get("name") + "&f..."));
            NSNPlayer p = getPlayer(player.get("name").toString());
            for(String r : ranks) {
                if(FlowWaterfall.rankManager.getByName(r) == null) continue;
                FlowWaterfall.rankManager.getByName(r).addPlayer(p, player.get("name").toString());
                p.addRank(FlowWaterfall.rankManager.getByName(r));
                ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Given player &a" + player.get("name") + "&f the &a" + r + "&f rank!"));
            }
            p.setXp(Integer.parseInt(player.get("xp").toString()));
            p.setLevel(Integer.parseInt(player.get("level").toString()));
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Stats set for player &a" + player.get("name") + "&f!"));
            replacePlayer(player.get("name").toString(), p);
            ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&f!&6]&f &a[PLAYERS]&f Registered player &a" + player.get("name") + "&f!"));
        }
    }

    public void saveToDatabase() {
        DatabaseProvider db_ = FlowWaterfall.db;
        MongoClient db = db_.get();
        DB flowDb = db.getDB("_nsn_flow-pre-alpha-testing");
        DBCollection players_ = flowDb.getCollection("players");
        for(NSNPlayer player : players.values()) {
            if(player.get() == null) continue;
            DBCursor cur = players_.find(new BasicDBObject("_id", player.get().getUniqueId().toString()));
            if(cur.toArray().size() == 0 || cur.toArray().get(0) == null) {
                players_.insert(player.toDatabaseObject());
            } else {
                players_.findAndRemove(new BasicDBObject("_id", player.get().getUniqueId().toString()));
                players_.insert(player.toDatabaseObject());
            }
        }
    }
}
