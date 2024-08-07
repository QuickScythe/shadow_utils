package me.quickscythe.shadowutils;

import me.quickscythe.shadowutils.commands.CommandManager;
import me.quickscythe.shadowutils.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Utils.init(this);
        CommandManager.init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
