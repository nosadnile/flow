package net.nosadnile.flow.velocity.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class YamlHandler {
    private final Yaml yaml;
    private String fileContents;
    private Map<String, Object> config;
    private Path filePath;
    private File file;

    public YamlHandler(File file) throws IOException {
        this.file = file;
        this.yaml = new Yaml();

        this.loadFile();
    }

    private void loadFile() throws IOException {
        this.filePath = Path.of(this.file.getPath());

        InputStream inputStream = new FileInputStream(this.file);
        byte[] bytes = inputStream.readAllBytes();
        this.fileContents = new String(bytes);
        inputStream.close();

        this.config = yaml.load(this.fileContents);
        if (this.config == null)
            this.config = new HashMap<>();
    }

    public Path getFilePath() {
        return this.filePath;
    }

    public File getFile() {
        return this.file;
    }

    public Map<String, Object> getConfig() {
        return this.config;
    }

    public String getFileContents() {
        return this.fileContents;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public void saveConfig() throws IOException {
        OutputStream outputStream = new FileOutputStream(this.file);
        StringWriter writer = new StringWriter();
        yaml.dump(this.config, writer);

        outputStream.write(writer.toString().getBytes());
        outputStream.close();
    }

    public void reloadFile() throws IOException {
        this.loadFile();
    }
}
