package me.quickscythe.shadowcore.commands;


import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.quickscythe.shadowcore.commands.executors.ConfigCommand;
import me.quickscythe.shadowcore.commands.executors.EntityCommand;
import me.quickscythe.shadowcore.commands.executors.UpdateCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandManager {

    public static void init(){

        new CommandBuilder(new UpdateCommand(ShadowUtils.getPlugin())).setDescription("Test desc").setAliases("getnew").register();
        new CommandBuilder(new ConfigCommand(ShadowUtils.getPlugin())).setDescription("Edit ShadowCore config files").register();
        new CommandBuilder(new EntityCommand(ShadowUtils.getPlugin())).setDescription("Edit ShadowCore config files").setAliases("centity","ientity", "ce", "ie").register();
    }

    public static class CommandBuilder {
        ShadowCommand cmd;
        String desc = "";
        String[] aliases = new String[]{};


        @CheckReturnValue
        public CommandBuilder(ShadowCommand executor){
            this.cmd = executor;
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

        public void register(){
            @NotNull LifecycleEventManager<Plugin> manager = cmd.getPlugin().getLifecycleManager();
            manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
                final Commands commands = event.registrar();
                commands.register(cmd.getNode(), desc, List.of(aliases));
            });
        }
    }


}
