package me.quickscythe.shadowcore;

import me.quickscythe.shadowcore.commands.CommandManager;
import me.quickscythe.shadowcore.utils.Utils;
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
