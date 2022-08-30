package net.nosadnile.flow.api.database;

public class PasswordlessCredentials implements DatabaseCredentials {
    private String username;
    private String host;
    private int port;

    public PasswordlessCredentials(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    public PasswordlessCredentials(String username, String host) {
        this.username = username;
        this.host = host;
        this.port = 27017;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public boolean hasPassword() {
        return false;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
