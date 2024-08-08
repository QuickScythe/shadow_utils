package me.quickscythe.shadowcore.utils.chat;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.text.DecimalFormat;

public class MessageUtils {
    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("messages", ShadowUtils.getPlugin().getResource("messages.json"));
    }

    public static String formatDate(long ms, String tcolor, String ncolor) {

        int l = (int) (ms / 1000);
        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        if (weeks > 0) {
            return ncolor + weeks + tcolor + " weeks" + (days > 0 ? ", " + ncolor + days + tcolor + " days" : "") + (hours > 0 ? ", " + ncolor + hours + tcolor + " hours" : "") + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (days > 0) {
            return ncolor + days + tcolor + " days" + (hours > 0 ? ", " + ncolor + hours + tcolor + " hours" : "") + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (hours > 0) {
            return ncolor + hours + tcolor + " hours" + (min > 0 ? ", " + ncolor + min + tcolor + " minutes" : "") + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (min > 0) {
            return ncolor + min + tcolor + " minutes" + (sec > 0 ? ", and " + ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds") : "");
        }
        if (sec > 0) {
            return ncolor + sec + tcolor + " " + (sec == 1 ? "second" : "seconds");
        }

        return ncolor + "less than a second" + tcolor + "";
    }

    public static String formatDateRaw(long ms) {
        return formatDate(ms, "", "");
    }

    public static String formatTime(long ms, String ncolor, String tcolor) {
        int l = (int) (ms / 1000);

        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        DecimalFormat format = new DecimalFormat("00");

        if (weeks > 0) {
            return ncolor + format.format(weeks) + tcolor + ":" + ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;

        }
        if (days > 0) {
            return ncolor + format.format(days) + tcolor + ":" + ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (hours > 0) {
            return ncolor + format.format(hours) + tcolor + ":" + ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (min > 0) {
            return ncolor + format.format(min) + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }
        if (sec > 0) {
            return ncolor + "00" + tcolor + ":" + ncolor + format.format(sec) + tcolor;
        }

        return ncolor + "00" + tcolor + ":" + ncolor + "00" + tcolor;
    }

    public static String formatTimeRaw(long ms) {
        return formatTime(ms, "", "");
    }


    public static Component getMessage(String key, Object... replacements) {
        Component text;
        String a = getMessage(key);
        for (int i = 0; i != replacements.length; i++)
            a = a.replaceFirst("\\[" + i + "]", replacements[i].toString());
        if (a.startsWith("{")) {
            text = GsonComponentSerializer.gson().deserialize(a);
        } else {

            text = Component.text(a);
        }

        return text;
    }

    private static String getMessage(String key) {
        return config.getData().has(key) ? (config.getData().getString(key)) : key;
    }

}
