package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.ShadowUtils;

public class ConfigManager {

    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("config", ShadowUtils.getPlugin().getResource("config.json"));
    }

    public static Object getVariable(String key){
        return config.getData().get(key);
    }

    public static void setVariable(String key, Object val){
        config.getData().put(key, val);
    }

}
