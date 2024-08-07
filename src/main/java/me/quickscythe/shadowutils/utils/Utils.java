package me.quickscythe.shadowutils.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class Utils {

    public static JavaPlugin plugin;

    public static void init(JavaPlugin plugin){
        Utils.plugin = plugin;
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    //Paper server with a points tally with a way you can earn points
    //Pvp and traps are accepted and enabled. Kills are worth points
    //There will be 4 sessions over the course of a month.
    // Each session is a week. There may be a couple days where the server goes offline


    // Calc points at end of session

    // Earn points with minigames (different kinds of minigames)
    // Teams
    // Lots of minigames

    //Max of 24 hours per sessions, countdown as they get closer (kick them)

}
