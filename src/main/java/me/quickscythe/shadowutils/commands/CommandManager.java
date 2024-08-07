package me.quickscythe.shadowutils.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.quickscythe.shadowutils.commands.executors.UpdateCommand;
import me.quickscythe.shadowutils.utils.Utils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    public static void init(){
        new CommandBuilder("update", new UpdateCommand()).setDescription("Test desc").setAliases("getnew").register();
    }

    static class CommandBuilder {
        String label;
        BasicCommand executor;
        String desc = "";
        String[] aliases = new String[]{};


        public CommandBuilder(String label, BasicCommand executor){
            this.label = label;
            this.executor = executor;
        }

        public CommandBuilder setDescription(String desc){
            this.desc = desc;
            return this;
        }

        public CommandBuilder setAliases(String... aliases){
            this.aliases = aliases;
            return this;
        }

        public void register(){
            @NotNull LifecycleEventManager<Plugin> manager = Utils.getPlugin().getLifecycleManager();
            manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
                final Commands commands = event.registrar();
                commands.register(label, desc, executor);
            });
        }
    }


}
