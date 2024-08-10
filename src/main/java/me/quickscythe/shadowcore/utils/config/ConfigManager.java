package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends ConfigClass {


    public ConfigManager(JavaPlugin plugin, String configFile, String resource) {
        super(plugin, configFile, resource);
    }

    public Object getVariable(String key){
        return config.getData().get(key);
    }

    public void setVariable(String key, Object val){
        config.getData().put(key, val);
    }


}
