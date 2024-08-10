package me.quickscythe.shadowcore.utils.occasion;

import me.quickscythe.shadowcore.utils.config.ConfigClass;
import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

public class OccasionManager extends ConfigClass {

    public OccasionManager(JavaPlugin plugin, String configFile) {
        super(plugin, configFile);
    }

    public OccasionManager(JavaPlugin plugin, String configFile, String resource) {
        super(plugin, configFile, resource);
    }

    public OccasionManager(JavaPlugin plugin, String configFile, JSONObject defaults) {
        super(plugin, configFile, defaults);
    }
}
