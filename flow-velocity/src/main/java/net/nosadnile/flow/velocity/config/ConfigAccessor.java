package net.nosadnile.flow.velocity.config;

import net.nosadnile.flow.velocity.FlowVelocity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigAccessor {
    public static Path getConfigFilePath() {
        return FlowVelocity.dataDirectory.resolve("config.yml");
    }

    public static File getConfigFile() {
        return getConfigFilePath().toFile();
    }

    public static ConfigManager getConfig() throws IOException {
        YamlHandler handler = new YamlHandler(getConfigFile());
        return new ConfigManager(handler);
    }
}
