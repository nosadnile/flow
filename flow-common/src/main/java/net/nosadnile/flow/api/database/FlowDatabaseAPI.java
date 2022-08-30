package net.nosadnile.flow.api.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.nosadnile.flow.api.errors.NoCredentialsException;

import java.net.UnknownHostException;

public class FlowDatabaseAPI {
    private MongoClient dbClient;
    private DatabaseCredentials credentials;
    public boolean connected = false;

    public MongoClient getClient() {
        return this.dbClient;
    }

    public void login(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    public void connect() throws NoCredentialsException, UnknownHostException {
        if (this.credentials == null) {
            throw new NoCredentialsException();
        }

        String connectionString = "mongodb://";

        if (this.credentials.isAnonymous()) {
            connectionString += credentials.getHost() + ":" + credentials.getPort();
        } else if (this.credentials.hasPassword()) {
            connectionString += (
                    this.credentials.getUsername() + ":"
                            + this.credentials.getPassword()
                            + "@" + this.credentials.getHost()
                            + ":" + this.credentials.getPort()
            );
        } else {
            connectionString += this.credentials.getUsername() + "@" + this.credentials.getHost() + ":" + this.credentials.getPort();
        }

        MongoClientURI connectionURI = new MongoClientURI(connectionString);

        this.dbClient = new MongoClient(connectionURI);

        this.connected = true;
    }

    public void disconnect() {
        this.dbClient.close();

        this.connected = false;
    }
}
