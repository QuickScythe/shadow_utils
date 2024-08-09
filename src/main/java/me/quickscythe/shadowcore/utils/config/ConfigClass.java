package me.quickscythe.shadowcore.utils.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

public class ConfigClass {
    protected static ConfigFile config;

    public static void init(JavaPlugin plugin, String configFile) {
        config = ConfigFileManager.getFile(plugin, configFile);
    }

    public static void init(JavaPlugin plugin, String configFile, String resource) {
        config = ConfigFileManager.getFile(plugin, configFile, resource);
    }

    public static void init(JavaPlugin plugin, String configFile, JSONObject defaults) {
        config = ConfigFileManager.getFile(plugin, configFile, defaults);
    }

    public static void finish() {
        config.save();
    }
}
