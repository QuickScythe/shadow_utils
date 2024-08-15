package me.quickscythe.shadowcore.commands.executors;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.commands.ShadowCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import static net.kyori.adventure.text.Component.text;


public class ConfigCommand extends ShadowCommand {


    public ConfigCommand(JavaPlugin plugin, String cmd) {
        super(plugin, cmd);
    }

//    @Override
//    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String[] args) {
//        Collection<String> list = new ArrayList<>();
//        if (args.length == 0) {
//            list.add("reset");
//            list.add("save");
//        }
//        if (args.length == 1) {
//            if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("save")) {
//                list.addAll(ConfigFileManager.getFiles());
//            }
//        }
//        return list;
//
//    }
//
//    @Override
//    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
//        if (args.length <= 1 || args[0].equalsIgnoreCase("help")) {
//            /**
//             * todo
//             *  /config
//             *  /config reset <file>
//             *  /config set <file> <key> <value>
//             */
//            stack.getSender().sendMessage(ShadowUtils.getMessageUtils().getMessage("msg.test2"));
//            return;
//        }
//        if (args[0].equalsIgnoreCase("save")) {
//            ConfigFileManager.getFile(args[1]).save();
//            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content(args[1]).color(NamedTextColor.GOLD).append(text(" as been saved.").color(NamedTextColor.WHITE)).build());
//        }
//        if (args[0].equalsIgnoreCase("reset")) {
//            ConfigFileManager.getFile(args[1]).reset();
//            //<args[1]> has been reset.
//            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content(args[1]).color(NamedTextColor.GOLD).append(text(" has been reset.", NamedTextColor.WHITE)).build(), stack.getSender());
//        }
//    }
}
