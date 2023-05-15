package net.nosadnile.flow.api.database;

public interface DatabaseCredentials {
    String getUsername();

    String getPassword();

    String getDatabase();

    String getHost();

    int getPort();

    boolean hasPassword();

    boolean isAnonymous();
}
