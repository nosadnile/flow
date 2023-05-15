package net.nosadnile.flow.velocity.config;

import net.nosadnile.flow.api.database.AnonymousCredentials;
import net.nosadnile.flow.api.database.DatabaseCredentials;
import net.nosadnile.flow.api.database.PasswordCredentials;
import net.nosadnile.flow.api.database.PasswordlessCredentials;
import net.nosadnile.flow.velocity.FlowVelocity;

public class CredentialConfig {
    public static DatabaseCredentials getCredentials() {
        String host = FlowVelocity.configManager.getString("database.host");
        String user = FlowVelocity.configManager.getString("database.user");
        String pass = FlowVelocity.configManager.getString("database.pass");
        String database = FlowVelocity.configManager.getString("database.name");

        int port = FlowVelocity.configManager.getInt("database.port");

        boolean usePassword = FlowVelocity.configManager.getBoolean("database.usePass");
        boolean anon = FlowVelocity.configManager.getBoolean("database.anon");

        if (anon) {
            return new AnonymousCredentials(database, host, port);
        } else if (usePassword) {
            return new PasswordCredentials(user, pass, database, host, port);
        } else {
            return new PasswordlessCredentials(user, database, host, port);
        }
    }
}
