package me.quickscythe.shadowcore.utils.session;

import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    private static final Map<UUID, JSONObject> CURRENT_SESSIONS = new HashMap<>();
    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("sessions");
    }

    public static ConfigFile getConfig() {
        return config;
    }

    public static void finish() {
        for(Player player : Bukkit.getOnlinePlayers()){
            finishSession(player);
        }


        config.save();
    }

    public static void startSession(Player player) {
        //{"this-is-a-uuid":[{"joined":238}]}
        JSONObject session = new JSONObject();
        session.put("joined", new Date().getTime());
        CURRENT_SESSIONS.put(player.getUniqueId(), session);
    }

    public static JSONObject getSession(Player player) {
        return CURRENT_SESSIONS.getOrDefault(player.getUniqueId(), null);
    }

    public static void finishSession(Player player) {
        JSONObject json = config.getData();
        if (!json.has(player.getUniqueId().toString())) json.put(player.getUniqueId().toString(), new JSONArray());
        JSONObject session = getSession(player);
        session.put("left", new Date().getTime());
        session.put("playtime", session.getLong("left") - session.getLong("joined"));
        json.getJSONArray(player.getUniqueId().toString()).put(session);
        CURRENT_SESSIONS.remove(player.getUniqueId());
    }
}
