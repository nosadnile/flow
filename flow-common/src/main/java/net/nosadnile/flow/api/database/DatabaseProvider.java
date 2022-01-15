package net.nosadnile.flow.api.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Collections;

public class DatabaseProvider {
    private MongoClient db;

    public DatabaseProvider(String host, String port, String username, String password) {
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public DatabaseProvider(String host, int port, String username, String password, String authDatabase) {
        try {
            MongoCredential credentials = MongoCredential.createMongoCRCredential(username, authDatabase, password.toCharArray());
            db = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credentials));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public DatabaseProvider(String username, String password) {
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + "@localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DatabaseProvider(String host, String port, boolean noPassword) {
        try {
            db = new MongoClient(new MongoClientURI("mongodb://" + host + ":" + port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DatabaseProvider() {
        try {
            db = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseProvider noPassword(String host, String port) {
        return new DatabaseProvider(host, port, true);
    }

    public static DatabaseProvider noPassword() {
        return new DatabaseProvider();
    }

    public void disable() {
        db.close();
    }

    public MongoClient get() {
        return db;
    }
}
