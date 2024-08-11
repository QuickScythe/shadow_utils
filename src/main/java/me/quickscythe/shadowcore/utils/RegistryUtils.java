package me.quickscythe.shadowcore.utils;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.quickscythe.shadowcore.utils.entity.CustomEntityRegistry;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class RegistryUtils {

    private static final Map<String, Registry> REGISTRY_MAP = new HashMap<>();
    private static final Map<String, CustomEntityRegistry> CER_MAP = new HashMap<>();
    private static final RegistryAccess REGISTRY_ACCESS = RegistryAccess.registryAccess();


    public static void init(){
        REGISTRY_MAP.put("blockType", REGISTRY_ACCESS.getRegistry(RegistryKey.BLOCK));
        REGISTRY_MAP.put("entityType",REGISTRY_ACCESS.getRegistry(RegistryKey.ENTITY_TYPE));
    }

    public static Registry<? extends Keyed> getRegistry(String key){
        return REGISTRY_MAP.get(key);
    }


    public static CustomEntityRegistry newEntityRegistry(JavaPlugin plugin) {
        CER_MAP.put(plugin.getName(), new CustomEntityRegistry(plugin));
        return CER_MAP.get(plugin.getName());
    }
}
