package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.Logger;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.Bukkit;
import org.json2.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigFileManager {

    private static final Map<String, ConfigFile> FILE_MAP = new HashMap<>();

    public static ConfigFile getFile(String filename) {
        return getFile(filename, new JSONObject());
    }

    public static ConfigFile getFile(String filename, JSONObject defaults) {
        if (!FILE_MAP.containsKey(filename)) {

            File file = new File(ShadowUtils.getPlugin().getDataFolder() + "/" + filename + ".json");
            if (!file.exists()) {
                try {
                    if (!file.createNewFile())
                        throw new IOException("Couldn't create file (" + filename + ".json)");
                } catch (IOException e) {
                    ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
                }
            }
            ConfigFile config = new ConfigFile(file, defaults);
            FILE_MAP.put(filename, config);
        }
        return FILE_MAP.get(filename);
    }

    public static ConfigFile getFile(String filename, InputStream resource) {

        JSONObject defaults = new JSONObject();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
            defaults = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        } catch (IOException e) {
            throw new RuntimeException("There was an error generating the loot tables.");
        }
        return getFile(filename, defaults);
    }
}
