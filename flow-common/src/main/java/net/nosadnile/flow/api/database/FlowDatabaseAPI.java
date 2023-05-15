package net.nosadnile.flow.api.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import net.nosadnile.flow.api.errors.NoCredentialsException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class FlowDatabaseAPI {
    public boolean connected = false;
    private MongoClient dbClient;
    private DatabaseCredentials credentials;
    private String database;

    public MongoClient getClient() {
        return this.dbClient;
    }

    public String getDatabase() {
        return this.database;
    }

    public void login(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    public void connect() throws NoCredentialsException, UnknownHostException {
        if (this.credentials == null) {
            throw new NoCredentialsException();
        }

        System.out.println("Host: " + this.credentials.getHost() + " | Port: " + this.credentials.getPort());

        ServerAddress addr = new ServerAddress(this.credentials.getHost(), this.credentials.getPort());
        List<MongoCredential> credentials = new ArrayList<>();

        this.database = this.credentials.getDatabase();

        if (!this.credentials.isAnonymous()) {
            if (this.credentials.hasPassword()) {
                MongoCredential credential = MongoCredential.createPlainCredential(this.credentials.getUsername(), "admin", this.credentials.getPassword().toCharArray());

                credentials.add(credential);
            } else {
                MongoCredential credential = MongoCredential.createPlainCredential(this.credentials.getUsername(), "admin", null);

                credentials.add(credential);
            }
        }

        this.dbClient = new MongoClient(addr, credentials);

        this.connected = true;
    }

    public void disconnect() {
        this.dbClient.close();

        this.connected = false;
    }
}
