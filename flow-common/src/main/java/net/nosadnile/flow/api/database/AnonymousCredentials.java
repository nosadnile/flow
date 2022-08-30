package net.nosadnile.flow.api.database;

public class AnonymousCredentials implements DatabaseCredentials {
    private String host;
    private int port;

    public AnonymousCredentials(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public AnonymousCredentials(String host) {
        this.host = host;
        this.port = 27017;
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
        return true;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
