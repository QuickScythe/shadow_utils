package me.quickscythe.shadowcore.utils.chat;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Logger {

    private final java.util.logging.@NotNull Logger LOG;
    private final Plugin plugin;

    public Logger(Plugin plugin){
        this.plugin = plugin;
        LOG = plugin.getLogger();
    }

    public void log(String msg){
        log(LogLevel.INFO, msg, null);
    }


    public void log(LogLevel level, String msg){
        log(level,msg, null);
    }

    public void log(LogLevel level, Exception ex){
        StringBuilder trace = new StringBuilder();
        for(StackTraceElement el : ex.getStackTrace())
            trace.append(el).append("\n");
        log(level, ex.getMessage() + ": " + trace.toString(), null);
    }

    public void log(LogLevel level, String msg, CommandSender feedback){
        level = level == null ? LogLevel.INFO : level;
        msg = level.getTag() + msg;
        LOG.info(msg);
//        switch(level){
//            case WARN -> LOG.info(Level.WARNING, msg);
//            case DEBUG -> LOG.log(Level.FINE, msg);
//            case ERROR -> LOG.log(Level.SEVERE, msg);
//            case TRACE -> LOG.log(Level.CONFIG, msg);
//            default -> LOG.info(msg);
//        }
        if(feedback!=null) feedback.sendMessage(msg);
    }


    public enum LogLevel {
        INFO(Component.text("[INFO]").color(TextColor.fromCSSHexString("#438df2"))), WARN("&c[WARN]"), ERROR("&e[ERROR]"), TRACE("&7[TRACE]"), DEBUG("&7[DEBUG]");

        TextComponent tag;

        LogLevel(TextComponent tag){
            this.tag = tag;
        }

        LogLevel(String tag){
            this.tag = LegacyComponentSerializer.legacyAmpersand().deserialize(tag);
        }

        public TextComponent getTag() {
            return tag;
        }
    }

}
