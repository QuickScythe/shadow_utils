package me.quickscythe.shadowcore.utils.heartbeat;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.heartbeat.runnable.Heartbeat;
import org.bukkit.Bukkit;

import java.nio.channels.FileLockInterruptionException;
import java.util.*;

public class HeartbeatUtils {

    private static Heartbeat heartbeat;


    public static void init() {
        heartbeat = new Heartbeat(ShadowUtils.getPlugin());
        Bukkit.getScheduler().runTaskLater(ShadowUtils.getPlugin(), heartbeat, 30);
    }

    public static Heartbeat getHeartbeat(){
        return heartbeat;
    }




}
