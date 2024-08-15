package me.quickscythe.shadowcore.commands.executors;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.commands.ShadowCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static net.kyori.adventure.text.Component.text;


public class ConfigCommand extends ShadowCommand {


    public ConfigCommand(JavaPlugin plugin) {
        super(plugin, "config");
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> getNode() {
        return literal(getName())
                .executes(context -> {
                    context.getSource().getSender().sendMessage(text().content("Here's a list of config commands:"));
                    context.getSource().getSender().sendMessage(text().content("/config set <file> <path> <value> ").append(text("- Sets data for current config. Does not save data.", NamedTextColor.GRAY)));
                    context.getSource().getSender().sendMessage(text().content("/config reset <file>              ").append(text("- Resets file to stored defaults.", NamedTextColor.GRAY)));
                    context.getSource().getSender().sendMessage(text().content("/config save <file>               ").append(text("- Saves current data to file.", NamedTextColor.GRAY)));
                    return Command.SINGLE_SUCCESS;
                })
                .then(argument("action", StringArgumentType.string())
                        .suggests((context, builder) -> {
                            builder.suggest("set");
                            builder.suggest("reset");
                            builder.suggest("save");
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            String action = StringArgumentType.getString(context, "action");
                            if (action.equalsIgnoreCase("save"))
                                return logError(context.getSource().getSender(), "Usage: /config save <file>");
                            if (action.equalsIgnoreCase("set"))
                                return logError(context.getSource().getSender(), "Usage: /config set <file> <path> <value>");
                            if (action.equalsIgnoreCase("reset"))
                                return logError(context.getSource().getSender(), "Usage: /config reset <file>");
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(argument("file", StringArgumentType.greedyString())
                                .suggests((context, builder) -> {
                                    for (String s : ConfigFileManager.getFiles())
                                        builder.suggest(s.replaceAll("/",":"));
                                    return builder.buildFuture();
                                }).executes(context -> {
                                    String action = StringArgumentType.getString(context, "action");
                                    String file = StringArgumentType.getString(context, "file").replaceAll(":","/");
                                    if (action.equalsIgnoreCase("save")) {
                                        ConfigFileManager.getFile(file).save();
                                        ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content(file).color(NamedTextColor.GOLD).append(text(" as been saved.").color(NamedTextColor.WHITE)).build());
                                    }
                                    if (action.equalsIgnoreCase("reset")) {
                                        ConfigFileManager.getFile(file).reset();
                                        ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content(file).color(NamedTextColor.GOLD).append(text(" has been reset.", NamedTextColor.WHITE)).build(), context.getSource().getSender());
                                    }
                                    if(action.equalsIgnoreCase("set")){
                                        context.getSource().getSender().sendMessage("Error.");
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                .build();
    }
}
