package net.nosadnile.flow.api.database;

public class PasswordCredentials implements DatabaseCredentials {
    private String username;
    private String password;
    private String host;
    private int port;

    public PasswordCredentials(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public PasswordCredentials(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = 27017;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
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
        return true;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
