package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.json2.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
                ShadowUtils.getLogger().log(line);
                data.append("\n");
            }
            defaults = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
            ShadowUtils.getLogger().log("Defaults on launch: " + defaults.toString());
        } catch (IOException e) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
        }
        return getFile(filename, defaults);
    }

    public static void saveFiles(){
        for(Map.Entry<String, ConfigFile> en : FILE_MAP.entrySet()){
            en.getValue().save();
        }
    }

    public static Set<String> getFiles() {
        return FILE_MAP.keySet();
    }
}
