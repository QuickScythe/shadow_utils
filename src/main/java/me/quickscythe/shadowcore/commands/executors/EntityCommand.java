package me.quickscythe.shadowcore.commands.executors;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.commands.ShadowCommand;
import me.quickscythe.shadowcore.utils.entity.RegistryUtils;
import me.quickscythe.shadowcore.utils.entity.CustomEntityRegistry;
import net.minecraft.world.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;


public class EntityCommand extends ShadowCommand {
    public EntityCommand(JavaPlugin plugin) {
        super(plugin, "customentity");
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> getNode() {
        return literal(getName())
                .executes(context -> {
                    context.getSource().getSender().sendMessage("Usage: /customentity <entity>");
                    return Command.SINGLE_SUCCESS;
                })
                .then(argument("entity", StringArgumentType.greedyString())
                        .suggests((context, builder) -> {
                            for(Map.Entry<String, CustomEntityRegistry> regs : RegistryUtils.getEntityRegistries().entrySet()){
                                for(Map.Entry<String, Class<? extends Entity>> reg : regs.getValue().getRegistry().entrySet()){
                                    builder.suggest(regs.getKey() + ":" + regs.getValue());
                                }
                            }
                            return builder.buildFuture();
                        })).build();
    }
}
