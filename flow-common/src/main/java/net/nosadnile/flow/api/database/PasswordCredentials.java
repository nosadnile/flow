package net.nosadnile.flow.api.database;

public class PasswordCredentials implements DatabaseCredentials {
    private String username;
    private String password;
    private String database;
    private String host;
    private int port;

    public PasswordCredentials(String username, String password, String database, String host, int port) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = port;
    }

    public PasswordCredentials(String username, String password, String database, String host) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = 27017;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getDatabase() {
        return database;
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
        return true;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }
}
