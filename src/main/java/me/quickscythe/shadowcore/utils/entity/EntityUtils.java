package me.quickscythe.shadowcore.utils.entity;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class EntityUtils {

    public static Mob spawn(Class<? extends Mob> mob, Location loc) {

        try {
            Level world = ((CraftWorld) loc.getWorld()).getHandle();

            Mob instance = mob.getConstructor(Level.class).newInstance(world);
            instance.setPos(loc.getX(), loc.getY(), loc.getZ());
            world.addFreshEntity(instance);
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ex) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "There was an error spawning a custom entity");
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, ex);
        }
        return null;
    }
}
