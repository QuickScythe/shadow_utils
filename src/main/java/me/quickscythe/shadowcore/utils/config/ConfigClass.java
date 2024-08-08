package me.quickscythe.shadowcore.utils.config;

import org.json2.JSONObject;

import java.io.InputStream;

public class ConfigClass {
    protected static ConfigFile config;

    public static void init(String configFile) {
        config = ConfigFileManager.getFile(configFile);
    }

    public static void init(String configFile, InputStream defaults) {
        config = ConfigFileManager.getFile(configFile, defaults);
    }

    public static void init(String configFile, JSONObject defaults) {
        config = ConfigFileManager.getFile(configFile, defaults);
    }

    public static void finish(){
        config.save();
    }
}
