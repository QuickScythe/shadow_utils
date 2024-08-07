package me.quickscythe.shadowutils.utils;


import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Logger {

    private final org.slf4j.Logger LOG;

    public Logger(){
        LOG = Utils.getPlugin().getSLF4JLogger();
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
        switch(level){
            case WARN -> LOG.warn(msg);
            case DEBUG -> LOG.debug(msg);
            case ERROR -> LOG.error(msg);
            case TRACE -> LOG.trace(msg);
            default -> LOG.info(msg);
        }
        if(feedback!=null) feedback.sendMessage("[" + level.name() + "] " + msg);
    }


    public enum LogLevel {
        INFO, WARN, ERROR, TRACE, DEBUG
    }

}
