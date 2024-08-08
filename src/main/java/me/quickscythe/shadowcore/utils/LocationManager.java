package me.quickscythe.shadowcore.utils;

import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json2.JSONObject;

public class LocationManager {

    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("locations");
    }

    public static void finish() {
        config.save();
    }

    public static void addLocation(String key, Location location){
        config.getData().put(key, deserialize(location));
    }

    public static void removeLocation(String key){
        config.getData().remove(key);
    }

    public static Location getLocation(String key){
        return serialize(config.getData().getJSONObject(key));
    }

    private static JSONObject deserialize(Location location){
        return new JSONObject("{\"world\":\"" + location.getWorld().getName() + "\",\"x\":" + location.getX() + ",\"y\":" + location.getY() + ",\"z\":" + location.getZ() + ",\"yaw\":" + location.getYaw() + ",\"pitch\":" + location.getPitch() + "}");
    }

    private static Location serialize(JSONObject object){
        return new Location(Bukkit.getWorld(object.getString("world")), object.getDouble("x"), object.getDouble("y"), object.getDouble("z"), object.getFloat("yaw"), object.getFloat("pitch"));
    }

}
