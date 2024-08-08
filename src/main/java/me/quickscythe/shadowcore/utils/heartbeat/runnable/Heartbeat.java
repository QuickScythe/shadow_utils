package me.quickscythe.shadowcore.utils.heartbeat.runnable;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.heartbeat.Flutter;
import me.quickscythe.shadowcore.utils.heartbeat.HeartbeatUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heartbeat implements Runnable {

    JavaPlugin plugin;

    private final static Map<Integer, FlutterTask> FLUTTERS = new HashMap<>();
    private final static List<Integer> FLUTTERS_REMOVE = new ArrayList<>();
    final static List<FlutterTask> FLUTTERS_ADD = new ArrayList<>();
    private static int last_id = 0;

    public Heartbeat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.isEnabled()) {

            for(FlutterTask task : FLUTTERS_ADD)
                FLUTTERS.put(task.getId(), task);
            FLUTTERS_ADD.clear();
            for(int id : FLUTTERS_REMOVE)
                FLUTTERS.remove(id);
            FLUTTERS_REMOVE.clear();

            for(Map.Entry<Integer, FlutterTask> entry : FLUTTERS.entrySet()){
                ShadowUtils.getLogger().log(Logger.LogLevel.DEBUG, "Running Flutter #" + entry.getKey() + ".");
                entry.getValue().getFlutter().run();
            }


            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, 5);
        }
    }

    public static FlutterTask addFlutter(Flutter flutter){
        last_id = last_id+1;
        FlutterTask task = new FlutterTask(last_id, flutter);
        FLUTTERS_ADD.add(task);
        return task;
    }

    public static void removeFlutter(FlutterTask task){
        removeFlutter(task.getId());
    }

    public static void removeFlutter(int id){
        FLUTTERS_REMOVE.add(id);
    }

    public static Iterable<? extends FlutterTask> getAddQueue() {
        return FLUTTERS_ADD;
    }

    public static class FlutterTask {

        private final int id;
        private final Flutter flutter;

        private FlutterTask(int id, Flutter flutter) {
            this.id = id;
            this.flutter = flutter;
        }

        public int getId(){
            return id;
        }

        public Flutter getFlutter(){
            return flutter;
        }
    }
}