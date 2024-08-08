package me.quickscythe.shadowcore.utils.chat;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.ChatColor;

import java.awt.*;
import java.text.DecimalFormat;

public class MessageUtils {
    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("messages", ShadowUtils.getPlugin().getResource("messages.json"));
    }

    public static String colorize(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Component literal(String text){
        return Component.text(text);
    }


    public static String fade(String fromHex, String toHex, String string) {
        int[] start = getRGB(fromHex);
        int[] last = getRGB(toHex);

        StringBuilder sb = new StringBuilder();

        Integer dR = numberFade(start[0], last[0], string.length());
        Integer dG = numberFade(start[1], last[1], string.length());
        Integer dB = numberFade(start[2], last[2], string.length());

        for (int i = 0; i < string.length(); i++) {
            Color c = new Color(start[0] + dR * i, start[1] + dG * i, start[2] + dB * i);

            sb.append(net.md_5.bungee.api.ChatColor.of(c) + "" + string.charAt(i));
        }
        return sb.toString();
    }

    public static int[] getRGB(String rgb) {
        int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = hexToInt(rgb.charAt(i * 2), rgb.charAt(i * 2 + 1));
        }
        return ret;
    }

    public static int hexToInt(char a, char b) {
        int x = a < 65 ? a - 48 : a - 55;
        int y = b < 65 ? b - 48 : b - 55;
        return x * 16 + y;
    }

    private static Integer numberFade(int i, int f, int n) {
        int d = (f - i) / (n - 1);
        return d;
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
        return config.getData().has(key) ? colorize(config.getData().getString(key)) : key;
    }

}
