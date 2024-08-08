package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.ShadowUtils;

public class ConfigManager extends ConfigClass {


    public static Object getVariable(String key){
        return config.getData().get(key);
    }

    public static void setVariable(String key, Object val){
        config.getData().put(key, val);
    }

    public static void finish(){
        config.save();
    }

}
