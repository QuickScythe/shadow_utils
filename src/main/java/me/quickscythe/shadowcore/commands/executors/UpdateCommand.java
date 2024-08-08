package me.quickscythe.shadowcore.commands.executors;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.commands.ShadowCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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


public class UpdateCommand implements ShadowCommand {


    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        Collection<String> list = new ArrayList<>();
        if (args.length == 0) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document xml = db.parse(ShadowUtils.downloadFile(ShadowUtils.JENKINS_URL + ShadowUtils.JENKINS_API_ENDPOINT));
                xml.getDocumentElement().normalize();
                NodeList jobs = xml.getElementsByTagName("job");
                for (int temp = 0; temp < jobs.getLength(); temp++) {
                    Node job = jobs.item(temp);
                    String context = ((Element) job).getElementsByTagName("name").item(0).getTextContent();
                    list.add(context);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }

        }
        if (args.length == 1) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document xml = db.parse(ShadowUtils.downloadFile(ShadowUtils.JENKINS_URL + "job/" + args[0] + "/lastStableBuild/" + ShadowUtils.JENKINS_API_ENDPOINT));
                xml.getDocumentElement().normalize();
                NodeList versions = xml.getElementsByTagName("artifact");
                for (int temp = 0; temp < versions.getLength(); temp++) {
                    Node job = versions.item(temp);
                    String context = ((Element) job).getElementsByTagName("fileName").item(0).getTextContent();
                    if (context.endsWith("-javadoc.jar") || context.endsWith("-sources.jar")) continue;
                    context = context.replaceAll(args[0] + "-", "");
                    context = context.replaceAll(".jar", "");
                    list.add(context);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }

        }
        return list;

    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (args.length == 2) {
            String plugin = args[0];
            String version = args[1];
            String filename = plugin + "-" + version + ".jar";
            String url = "https://ci.vanillaflux.com/job/" + plugin + "/lastSuccessfulBuild/artifact/build/libs/" + filename;
            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text("Downloading ", NamedTextColor.YELLOW).append(getStylizedName(plugin, version)).append(text("...", NamedTextColor.YELLOW)), stack.getSender());
            InputStream in = ShadowUtils.downloadFile(url);
            if (in != null) {
                try {

                    for (File file : ShadowUtils.getPlugin().getDataFolder().getParentFile().listFiles()) {
                        String name = file.getName();
                        if (name.startsWith(plugin)) {
                            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text("Found existing file.").color(NamedTextColor.YELLOW), stack.getSender());
                            String old_version = name.replaceAll(plugin + "-", "").replaceAll(".jar", "");
                            Files.deleteIfExists(file.toPath());
                            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content("").append(getStylizedName(plugin, old_version)).append(text(" has been deleted", NamedTextColor.YELLOW)).build(), stack.getSender());
                        }
                    }
                    ShadowUtils.saveStream(in, new FileOutputStream("plugins/" + filename));
                    ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content("Finished downloading ").color(NamedTextColor.YELLOW).append(getStylizedName(plugin, version)).append(text(".", NamedTextColor.WHITE)).build(), stack.getSender());
                } catch (FileNotFoundException e) {
                    ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
                } catch (IOException e) {
                    ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "ERROR");
                    throw new RuntimeException(e);
                }
            } else {
                ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, text("There was an error downloading", NamedTextColor.YELLOW).append(text(plugin)), stack.getSender());
            }


        } else {
            stack.getSender().sendMessage("Usage: /update <plugin> <version>");
        }


    }

    private Component getStylizedName(String plugin, String version) {
        TextColor AQUA = NamedTextColor.AQUA;
        TextColor GREEN = NamedTextColor.GREEN;
        return text().content("").color(NamedTextColor.WHITE).append(text(plugin, AQUA)).append(text(" v" + version, GREEN)).build();
    }
}
