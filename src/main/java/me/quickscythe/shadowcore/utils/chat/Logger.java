package me.quickscythe.shadowcore.utils.chat;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class Logger {

    private final java.util.logging.@NotNull Logger LOG;
    private final Plugin plugin;

    public Logger(Plugin plugin) {
        this.plugin = plugin;
        LOG = plugin.getLogger();
    }

    public void log(String msg) {
        log(LogLevel.INFO, msg);
    }

    public void log(LogLevel level, String msg) {
        log(level, msg, null);
    }

    public void log(LogLevel level, TextComponent msg) {
        log(level, msg, null);
    }

    public void log(LogLevel level, Exception ex) {
        StringBuilder trace = new StringBuilder();
        for (StackTraceElement el : ex.getStackTrace())
            trace.append(el).append("\n");
        log(level, ex.getMessage() + ": " + trace.toString(), null);
    }

    public void log(LogLevel level, String msg, CommandSender feedback) {
        log(level, Component.text(msg), feedback);
    }
    public void log(LogLevel level, TextComponent msg, CommandSender feedback) {
        level = level == null ? LogLevel.INFO : level;
        LOG.info(level.getTagString() + " " + msg.toString());
//        switch(level){
//            case WARN -> LOG.info(Level.WARNING, msg);
//            case DEBUG -> LOG.log(Level.FINE, msg);
//            case ERROR -> LOG.log(Level.SEVERE, msg);
//            case TRACE -> LOG.log(Level.CONFIG, msg);
//            default -> LOG.info(msg);
//        }
        if (feedback != null) feedback.sendMessage(level.getTag().append(text(" ")).append(msg));
    }


    public enum LogLevel {
        INFO("[INFO]", "#438df2"), WARN("[WARN]", NamedTextColor.YELLOW), ERROR("[ERROR]", NamedTextColor.RED), TRACE("[TRACE]"), DEBUG("[DEBUG]");

        String tag;
        TextColor color;

        LogLevel(String tag, String color) {
            this.tag = tag;
            this.color = TextColor.fromCSSHexString(color);
        }

        LogLevel(String tag, NamedTextColor color) {
            this.tag = tag;
            this.color = color;
        }

        LogLevel(String tag) {
            this.tag = tag;
            this.color = NamedTextColor.GRAY;
        }

        public TextComponent getTag() {
            return text().content("").color(NamedTextColor.WHITE).append(text(tag, color)).build();
        }

        public String getTagString() {
            return tag;
        }
    }

}
