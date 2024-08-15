package me.quickscythe.shadowcore.utils.entity;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

 public class CustomEntityRegistry {

    private final Map<String, Class<? extends Entity>> REGISTRY = new HashMap<>();
    private final JavaPlugin plugin;

    public CustomEntityRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(String key, Class<? extends Entity> clazz){
        REGISTRY.put(key, clazz);
    }

    public Map<String, Class<? extends Entity>> getRegistry(){
        return REGISTRY;
    }

    public Entity spawn(String key, Location loc){
        try {
            Object world = loc.getWorld().getClass().getMethod("getHandle").invoke(loc.getWorld());
            Entity instance = getClass(key).getConstructor(Level.class).newInstance(world);
            instance.getClass().getMethod("setPos", double.class, double.class, double.class).invoke(instance, loc.getX(), loc.getY(), loc.getZ());
            world.getClass().getMethod("addFreshEntity", Entity.class).invoke(world, instance);
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ex) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "There was an error spawning a custom entity");
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, ex);
        }
        return null;
    }

    public Class<? extends Entity> getClass(String key){
        return REGISTRY.get(key);
    }


}
