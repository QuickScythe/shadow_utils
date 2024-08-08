package me.quickscythe.shadowcore.commands.executors;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.MessageUtils;
import me.quickscythe.shadowcore.utils.config.ConfigFileManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

import static net.kyori.adventure.text.Component.text;


public class ConfigCommand implements BasicCommand {


    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Collection<String> list = new ArrayList<>();
        if(args.length == 0){
            list.add("reset");
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reset")){
                list.addAll(ConfigFileManager.getFiles());
            }
        }
        return list;

    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if(args.length <= 1 || args[0].equalsIgnoreCase("help")){
            /**
             * todo
             *  /config
             *  /config reset <file>
             *  /config set <key> <value>
             */
            stack.getSender().sendMessage(MessageUtils.getMessage("msg.test"));
            return;
        }
        if(args[0].equalsIgnoreCase("reset")){
            ConfigFileManager.getFile(args[1]).reset();
            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content(args[0]).color(NamedTextColor.WHITE).append(text(" has been reset.", NamedTextColor.WHITE)).build());
        }


    }
}
