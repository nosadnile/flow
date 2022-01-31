package net.nosadnile.flow.api.player;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.nosadnile.flow.api.chat.ElementWithChatGroup;
import net.nosadnile.flow.api.rank.PlayerRank;
import net.nosadnile.flow.api.utils.ElementWithId;
import net.nosadnile.flow.api.utils.ElementWithName;

import java.util.ArrayList;
import java.util.List;

public class NSNPlayer extends ElementWithChatGroup implements ElementWithName<String>, ElementWithId<String> {
    private ProxiedPlayer player;
    private NSNPlayer lastMessaged;
    private List<PlayerRank> ranks;
    private int level;
    private int xp;
    private String id;
    private String name;

    public NSNPlayer(ProxiedPlayer player) {
        this.player = player;
        id = player.getUniqueId().toString();
        name = player.getName();
        ranks = new ArrayList<>();
    }

    public NSNPlayer(String uuid, String name) {
        id = uuid;
        this.name = name;
        ranks = new ArrayList<>();
    }

    public ProxiedPlayer get() {
        return player;
    }

    public List<PlayerRank> getRanks() {
        return ranks;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void addRank(PlayerRank rank) {
        if(ranks.contains(rank)) return;
        ranks.add(0, rank);
    }

    public void removeRank(PlayerRank rank) {
        ranks.remove(ranks.indexOf(rank));
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlayer(ProxiedPlayer p) {
        this.player = p;
    }

    public boolean isOnline() {
        return this.player != null;
    }

    public NSNPlayer getLastMessaged() {
        return lastMessaged;
    }

    public void setLastMessaged(NSNPlayer lastMessaged) {
        this.lastMessaged = lastMessaged;
    }

    public DBObject toDatabaseObject() {
        String[] ranks_ = new String[ranks.size()];
        for (int i = 0; i < ranks.size(); i++) {
            PlayerRank rank = ranks.get(i);
            ranks_[i] = rank.getName();
        }
        return new BasicDBObject("_id", id)
                .append("level", level)
                .append("xp", xp)
                         .append("ranks", ranks_)
                         .append("name", player.getName());
    }
}
