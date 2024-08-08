package me.quickscythe.shadowcore.utils.runnable;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.session.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Heartbeat implements Runnable {

    JavaPlugin plugin;

    public Heartbeat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.isEnabled()) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                long playtime = 0;
                JSONArray sessions = SessionManager.getSessions(player);
                for (int i = 0; i != sessions.length(); i++) {
                    JSONObject session = sessions.getJSONObject(i);
                    if (session.has("playtime")) playtime = playtime + session.getLong("playtime");
                    else {
                        playtime = playtime + (SessionManager.getSession(player).getLong("joined") - new Date().getTime());
                    }
                }
                ShadowUtils.getLogger().log(player.getName() + " has been playing for " + TimeUnit.SECONDS.convert(playtime, TimeUnit.MILLISECONDS) + " seconds.");

            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, 30);
        }
    }
}
