package me.quickscythe.shadowutils.utils;


import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class Logger {

    private final java.util.logging.@NotNull Logger LOG;

    public Logger(){
        LOG = Utils.getPlugin().getLogger();
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
        log(level, trace.toString(), null);
    }

    public void log(LogLevel level, String msg, CommandSender feedback){
        level = level == null ? LogLevel.INFO : level;
        msg = "[" + level.name() + "] " + msg;
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
        INFO, WARN, ERROR, TRACE, DEBUG
    }

}