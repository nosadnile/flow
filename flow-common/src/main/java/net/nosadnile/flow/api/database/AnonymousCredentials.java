package net.nosadnile.flow.api.database;

public class AnonymousCredentials implements DatabaseCredentials {
    private String database;
    private String host;
    private int port;

    public AnonymousCredentials(String database, String host, int port) {
        this.host = host;
        this.port = port;
        this.database = database;
    }

    public AnonymousCredentials(String database, String host) {
        this.host = host;
        this.port = 27017;
        this.database = database;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean hasPassword() {
        return false;
    }

    @Override
    public boolean isAnonymous() {
        return true;
    }
}
