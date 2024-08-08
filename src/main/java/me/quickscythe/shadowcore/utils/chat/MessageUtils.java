package me.quickscythe.shadowcore.utils.chat;

import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.config.ConfigFile;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.text.DecimalFormat;

import static net.kyori.adventure.text.Component.text;

public class MessageUtils {
    private static ConfigFile config;

    public static void init() {
        config = ConfigFileManager.getFile("messages", ShadowUtils.getPlugin().getResource("messages.json"));
    }

    public static String formatDate(long ms) {

        int l = (int) (ms / 1000);
        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        String sect = sec > 0 ? ", and " + sec + " " + (sec == 1 ? "second" : "seconds") : "";
        String mint = (min > 0 ? ", " + min + " minutes" : "");
        String hourt =(hours > 0 ? ", " + hours + " hours" : "");
        String dayt = (days > 0 ? ", " + days + " days" : "");

        if (weeks > 0) {
            return weeks + " weeks" + dayt + hourt + mint + sect;
        }
        if (days > 0) {
            return days + " days" + hourt + mint + sect;
        }
        if (hours > 0) {
            return hours + " hours" + mint + sect;
        }
        if (min > 0) {
            return min + " minutes" + sect;
        }
        if (sec > 0) {
            return sec + " " + (sec == 1 ? "second" : "seconds");
        }

        return "less than a second";
    }


    public static TextComponent formatTime(long ms) {
        int l = (int) (ms / 1000);

        int sec = l % 60;
        int min = (l / 60) % 60;
        int hours = ((l / 60) / 60) % 24;
        int days = (((l / 60) / 60) / 24) % 7;
        int weeks = (((l / 60) / 60) / 24) / 7;

        DecimalFormat format = new DecimalFormat("00");

        String sect = format.format(sec);
        String mint = format.format(min);
        String hourt = format.format(hours);
        String dayt = format.format(days);
        String weekt = format.format(weeks);

        TextComponent col = text(":", NamedTextColor.WHITE);

        if (weeks > 0)
            return text("").append(colorNum(weekt)).append(col).append(colorNum(dayt)).append(col).append(colorNum(hourt)).append(col).append(colorNum(hourt)).append(col).append(colorNum(mint)).append(col).append(colorNum(sect));
        if (days > 0)
            return text("").append(colorNum(dayt)).append(col).append(colorNum(hourt)).append(col).append(colorNum(mint)).append(col).append(colorNum(sect));
        if (hours > 0)
            return text("").append(colorNum(hourt)).append(col).append(colorNum(mint)).append(col).append(colorNum(sect));
        if (min > 0)
            return text("").append(colorNum(mint)).append(col).append(colorNum(sect));
        if (sec > 0)
            return text("").append(colorNum("00")).append(col).append(colorNum(sect));
        return text("").append(colorNum("00")).append(col).append(colorNum("00"));
    }

    private static ComponentLike colorNum(String s) {
        return text(s, NamedTextColor.YELLOW);
    }


    public static Component getMessage(String key, Object... replacements) {
        Component text;
        String a = getMessage(key);
        for (int i = 0; i != replacements.length; i++)
            a = a.replaceFirst("\\[" + i + "]", replacements[i].toString());
        if (a.startsWith("{")) {
            text = GsonComponentSerializer.gson().deserialize(a);
        } else {

            text = text(a);
        }

        return text;
    }

    private static String getMessage(String key) {
        return config.getData().has(key) ? (config.getData().getString(key)) : key;
    }

    public static void disable() {
        config.save();
    }
}
