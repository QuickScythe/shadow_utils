package me.quickscythe.shadowcore;

import me.quickscythe.shadowcore.commands.CommandManager;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ShadowUtils.init(this);
        CommandManager.init();

    }

    @Override
    public void onDisable() {

        ShadowUtils.disable();
        // Plugin shutdown logic
    }
}
