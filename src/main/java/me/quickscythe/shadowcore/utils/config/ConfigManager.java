package me.quickscythe.shadowcore.utils.config;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends ConfigClass {


    public ConfigManager(JavaPlugin plugin, String configFile, String resource) {
        super(plugin, configFile, resource);
        if(CONFIG.getData().has("jenkins_password") && CONFIG.getData().get("jenkins_password").equals("CHANGE THIS")){
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "Please change the jenkins_password in the config file!");
        }
    }

    public Object getVariable(String key){
        return CONFIG.getData().get(key);
    }

    public void setVariable(String key, Object val){
        CONFIG.getData().put(key, val);
    }


}
