package net.nosadnile.flow.velocity.ranks;

import com.mongodb.*;
import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.NodeMap;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.nosadnile.flow.api.database.Entity;
import net.nosadnile.flow.api.database.FlowDatabaseAPI;
import net.nosadnile.flow.api.util.ColorUtil;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.hooks.LuckPermsHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Rank extends Entity {
    private final List<UUID> players;
    private final List<Player> _players;
    private String name;
    private String prefix;
    private String color;
    private List<String> permissions;
    private List<String> disabledPermissions;

    private Rank() {
        // NOTE: For internal use only! Doesn't initialize it fully!

        this.players = new ArrayList<>();
        this._players = new ArrayList<>();
    }

    public Rank(String name, String prefix, String color) {
        this.name = name;
        this.prefix = ColorUtil.translateRawColorCodes('&', prefix);
        this.color = color;

        this.permissions = new ArrayList<>();
        this.disabledPermissions = new ArrayList<>();
        this.players = new ArrayList<>();
        this._players = new ArrayList<>();
    }

    public Rank(String name, String prefix, String color, List<String> disabledPermissions, String... permissions) {
        this.name = name;
        this.prefix = ColorUtil.translateRawColorCodes('&', prefix);
        this.color = color;
        this.permissions = Arrays.stream(permissions).toList();
        this.disabledPermissions = disabledPermissions;

        this.players = new ArrayList<>();
        this._players = new ArrayList<>();
    }

    public static Rank load(DBObject object) {
        Rank rank = new Rank();

        rank.deserialize(object);

        return rank;
    }

    public void enablePermissions(String... permissions) {
        for (String permission : permissions) {
            this.permissions.add(permission);
            this.disabledPermissions.remove(permission);
        }

        this.applyGroupPermissions();
    }

    public void disablePermissions(String... permissions) {
        for (String permission : permissions) {
            this.permissions.remove(permission);
            this.disabledPermissions.add(permission);
        }

        this.applyGroupPermissions();
    }

    public void addPlayer(Player player) {
        this.players.add(player.getUniqueId());
        this._players.add(player);

        this.push();
        this.syncGroupUsers();
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
        this._players.remove(player);

        this.push();

        LuckPerms api = LuckPermsHook.getLuckPerms();

        User user = api.getUserManager().getUser(player.getUniqueId());

        if (user == null) return;

        if (user.data().contains(Node.builder("group." + this.getGroupName()).build(), NodeEqualityPredicate.EXACT).asBoolean())
            if (!user.data().remove(Node.builder("group." + this.getGroupName()).build()).wasSuccessful()) return;

        api.getUserManager().saveUser(user);
    }

    public void push() {
        FlowDatabaseAPI api = FlowVelocity.database;
        MongoClient mongo = api.getClient();

        if (mongo == null) return;

        DB db = mongo.getDB(api.getDatabase());
        DBCollection ranks = db.getCollection("ranks");

        DBObject potential = ranks.findOne(new BasicDBObject("name", this.name));

        if (potential != null) {
            String id = (String) potential.get("id");

            ranks.findAndRemove(new BasicDBObject("id", id));
            ranks.insert(((BasicDBObject) this.serialize()).append("id", id));
        } else {
            ranks.insert(this.serialize());
        }
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public DBObject serialize() {
        return new BasicDBObject("name", name)
                .append("prefix", prefix)
                .append("color", color)
                .append("permissions", permissions.toArray())
                .append("disabledPermissions", disabledPermissions.toArray());
    }

    @Override
    public Entity deserialize(DBObject object) {
        String name = (String) object.get("name");
        String prefix = (String) object.get("prefix");
        String color = (String) object.get("color");
        String[] permissions = (String[]) object.get("permissions");
        String[] disabledPermissions = (String[]) object.get("disabledPermissions");

        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.permissions = Arrays.stream(permissions).toList();
        this.disabledPermissions = Arrays.stream(disabledPermissions).toList();

        this.createGroup();

        return this;
    }

    public String getGroupName() {
        return "__managed.flow.groups@" + this.name;
    }

    public void createGroup() {
        LuckPerms api = LuckPermsHook.getLuckPerms();

        if (api.getGroupManager().getGroup(this.getGroupName()) == null) {
            Group group = api.getGroupManager().createAndLoadGroup(this.getGroupName()).join();

            api.getGroupManager().saveGroup(group);
        }

        this.applyGroupPermissions();
        this.syncGroupUsers();
    }

    public void syncGroupUsers() {
        LuckPerms api = LuckPermsHook.getLuckPerms();

        for (UUID uuid : this.players) {
            User user = api.getUserManager().getUser(uuid);

            if (user == null) continue;

            if (user.data().contains(Node.builder("group." + this.getGroupName()).build(), NodeEqualityPredicate.EXACT).asBoolean())
                continue;

            if (!user.data().add(Node.builder("group." + this.getGroupName()).value(true).build()).wasSuccessful())
                return;

            api.getUserManager().saveUser(user);
        }
    }

    public void applyGroupPermissions() {
        LuckPerms api = LuckPermsHook.getLuckPerms();
        Group group = api.getGroupManager().getGroup(this.getGroupName());

        if (group == null) return;

        NodeMap nodes = group.data();

        nodes.clear();

        for (String permission : this.permissions) {
            if (!nodes.add(Node.builder(permission).value(true).build()).wasSuccessful()) return;
        }

        for (String permission : this.disabledPermissions) {
            if (!nodes.add(Node.builder(permission).value(false).build()).wasSuccessful()) return;
        }

        api.getGroupManager().saveGroup(group);
    }
}
