package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigFileManager {

    private static final Map<String, ConfigFile> FILE_MAP = new HashMap<>();


    /**
     * Gets existing ConfigFile. Does NOT generate new one.
     * @param filename String key file is stored under, does not include file extension.
     * @return ConfigFile or null if not existing
     */
    public static ConfigFile getFile(String filename){
        return FILE_MAP.getOrDefault(filename, null);
    }

    /**
     * Get or generate ConfigFile.
     * @param plugin Plugin to generate file under, and to check for resources.
     * @param filename String key file will be stored as. Do not include file extension.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(JavaPlugin plugin, String filename) {
        return getFile(plugin, filename, new JSONObject());
    }


    /**
     * Get or generate ConfigFile.
     * @param plugin Plugin to generate file under, and to check for resources.
     * @param filename String key file will be stored as. Do not include file extension.
     * @param defaults JSONObject to load into file if empty.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(JavaPlugin plugin, String filename, JSONObject defaults) {
        if (!FILE_MAP.containsKey(filename)) {
            ShadowUtils.getLogger().log(defaults.toString());
            File file = new File(plugin.getDataFolder() + "/" + filename + ".json");
            if (!file.exists()) {
                try {
                    if (!file.createNewFile())
                        throw new IOException("Couldn't create file (" + filename + ".json)");
                } catch (IOException e) {
                    ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
                }
            }
            ConfigFile config = new ConfigFile(plugin, file, defaults);
            FILE_MAP.put(filename, config);
        }
        return FILE_MAP.get(filename);
    }


    /**
     * Get or generate ConfigFile.
     * @param plugin Plugin to generate file under, and to check for resources.
     * @param filename String key file will be stored as. Do not include file extension.
     * @param resource String path to plugin resource. File extension must be included.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(JavaPlugin plugin, String filename, String resource) {

        JSONObject defaults = new JSONObject();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(resource)))) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
            defaults = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        } catch (IOException e) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
        }
        return getFile(plugin, filename, defaults);
    }

    /**
     * Get all file names.
     * @return {@code Set<String>} of filenames.
     */
    public static Set<String> getFiles() {
        return FILE_MAP.keySet();
    }
}
