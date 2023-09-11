package net.nosadnile.flow.preload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FlowPreload extends JavaPlugin {
    @Override
    public void onLoad() {
        if (Bukkit.getPluginsFolder().toPath().resolve("Citizens.jar").toFile().exists()) return;
        
		try {
			InputStream is = new URL("https://ci.citizensnpcs.co/job/Citizens2/lastSuccessfulBuild/api/json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            int cp;

            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            String jsonText = sb.toString();
            JsonObject json = new Gson().fromJson(jsonText, JsonObject.class);
            JsonArray artifacts = json.getAsJsonArray("artifacts");
            JsonObject artifact = artifacts.get(0).getAsJsonObject();
            String relativePath = artifact.get("relativePath").getAsString();
            String url = "https://ci.citizensnpcs.co/job/Citizens2/lastSuccessfulBuild/artifact/" + relativePath;
            String pluginsFolder = Bukkit.getPluginsFolder().getAbsolutePath();
            Path outFile = Paths.get(pluginsFolder, "Citizens.jar");

            InputStream in = new URL(url).openStream();
            Files.copy(in, outFile, StandardCopyOption.REPLACE_EXISTING);

            Bukkit.getPluginManager().loadPlugin(outFile.toFile());
        } catch (IOException | UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
            e.printStackTrace();
        }
    }
}
