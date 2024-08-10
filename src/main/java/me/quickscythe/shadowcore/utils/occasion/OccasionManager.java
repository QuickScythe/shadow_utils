package me.quickscythe.shadowcore.utils.occasion;

import java.util.*;

public class OccasionManager {

    final static Map<String, Occasion> OCCASIONS = new HashMap<>();
    final static List<String> OCCASIONS_REMOVE = new ArrayList<>();
    final static Map<String, Occasion> OCCASIONS_ADD = new HashMap<>();

    public static void registerOccasion(String key, Occasion occasion) {
        OCCASIONS_ADD.put(key, occasion);
    }

    public static Collection<Occasion> getOccasions() {
        return OCCASIONS.values();
    }

    public static void flutter() {
        OCCASIONS.putAll(OCCASIONS_ADD);
        OCCASIONS_ADD.clear();
        for (String key : OCCASIONS_REMOVE)
            OCCASIONS.remove(key);
        for (Map.Entry<String, Occasion> entry : OCCASIONS.entrySet()) {
            if (entry.getValue().started() && entry.getValue().check()) {
                entry.getValue().end();
                OCCASIONS_REMOVE.add(entry.getKey());
            }
        }
    }
}
