package me.quickscythe.shadowcore.utils;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;

import java.util.HashMap;
import java.util.Map;

public class RegistryUtils {

    private static final Map<String, Registry> REGISTRY_MAP = new HashMap<>();
    private static final RegistryAccess REGISTRY_ACCESS = RegistryAccess.registryAccess();


    public static void init(){
        REGISTRY_MAP.put("blockType", REGISTRY_ACCESS.getRegistry(RegistryKey.BLOCK));
    }

    public static Registry<? extends Keyed> getRegistry(String key){
        return REGISTRY_MAP.get(key);
    }


}
