package me.quickscythe.shadowcore.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.quickscythe.shadowcore.commands.executors.ConfigCommand;
import me.quickscythe.shadowcore.commands.executors.UpdateCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandManager {

    public static void init(){
        new CommandBuilder("update", new UpdateCommand()).setDescription("Test desc").setAliases("getnew").register(ShadowUtils.getPlugin());
        new CommandBuilder("config", new ConfigCommand()).setDescription("Edit ShadowCore config files").register(ShadowUtils.getPlugin());
    }

    public static class CommandBuilder {
        String label;
        ShadowCommand executor;
        String desc = "";
        String[] aliases = new String[]{};


        @CheckReturnValue
        public CommandBuilder(String label, ShadowCommand executor){
            this.label = label;
            this.executor = executor;
        }
        @CheckReturnValue
        public CommandBuilder setDescription(String desc){
            this.desc = desc;
            return this;
        }
        @CheckReturnValue
        public CommandBuilder setAliases(String... aliases){
            this.aliases = aliases;
            return this;
        }

        public void register(JavaPlugin plugin){
            @NotNull LifecycleEventManager<Plugin> manager = plugin.getLifecycleManager();
            manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
                final Commands commands = event.registrar();
                commands.register(label, desc, List.of(aliases), executor);
            });
        }
    }


}
