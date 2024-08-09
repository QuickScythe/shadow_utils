package me.quickscythe.shadowcore.utils.config;

import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

/**
 * Easier way to specify a class as a ConfigFile manager.
 */
public class ConfigClass {
    protected static ConfigFile config;

    /**
     * @param plugin Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     */
    public static void init(JavaPlugin plugin, String configFile) {
        config = ConfigFileManager.getFile(plugin, configFile);
    }
    /**
     * @param plugin Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     * @param resource String path to plugin resource. Include file extension.
     */
    public static void init(JavaPlugin plugin, String configFile, String resource) {
        config = ConfigFileManager.getFile(plugin, configFile, resource);
    }
    /**
     * @param plugin Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     * @param defaults JSONObject of default values to populate ConfigFile.
     */
    public static void init(JavaPlugin plugin, String configFile, JSONObject defaults) {
        config = ConfigFileManager.getFile(plugin, configFile, defaults);
    }

    /**
     * Save ConfigFile
     */
    public static void finish() {
        config.save();
    }
}
