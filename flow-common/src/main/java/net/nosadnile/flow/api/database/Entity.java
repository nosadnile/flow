package net.nosadnile.flow.api.database;

import com.mongodb.DBObject;

public abstract class Entity {
    public abstract DBObject serialize();

    public abstract Entity deserialize(DBObject object);
}
