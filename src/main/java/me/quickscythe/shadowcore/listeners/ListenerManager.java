package me.quickscythe.shadowcore.listeners;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {

    public static void init(){
        register(new SessionListener(), ShadowUtils.getPlugin());
    }

    public static void register(Listener listener, JavaPlugin plugin){
        ShadowUtils.getPlugin().getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
