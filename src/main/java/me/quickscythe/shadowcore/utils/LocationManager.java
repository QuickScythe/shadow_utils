package me.quickscythe.shadowcore.utils;

import me.quickscythe.shadowcore.utils.config.ConfigClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONObject;

public class LocationManager extends ConfigClass {

    public LocationManager(JavaPlugin plugin, String configFile) {
        super(plugin, configFile);
    }

    public void addLocation(String key, Location location) {
        CONFIG.getData().put(key, deserialize(location));
    }

    public void removeLocation(String key) {
        CONFIG.getData().remove(key);
    }

    public Location getLocation(String key) {
        return serialize(CONFIG.getData().getJSONObject(key));
    }

    private JSONObject deserialize(Location location) {
        return new JSONObject("{\"world\":\"" + location.getWorld().getName() + "\",\"x\":" + location.getX() + ",\"y\":" + location.getY() + ",\"z\":" + location.getZ() + ",\"yaw\":" + location.getYaw() + ",\"pitch\":" + location.getPitch() + "}");
    }

    private Location serialize(JSONObject object) {
        try {
            return new Location(Bukkit.getWorld(object.getString("world")), object.getDouble("x"), object.getDouble("y"), object.getDouble("z"), object.getFloat("yaw"), object.getFloat("pitch"));
        } catch (NullPointerException ex) {
            return null;
        }

    }
}
