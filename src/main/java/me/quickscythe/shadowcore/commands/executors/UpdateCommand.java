package me.quickscythe.shadowcore.commands.executors;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.shadowcore.commands.ShadowCommand;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
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
import java.util.concurrent.CompletableFuture;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static net.kyori.adventure.text.Component.text;

public class UpdateCommand extends ShadowCommand {


    public UpdateCommand(JavaPlugin plugin) {
        super(plugin, "update");
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> getNode() {
        return literal(getName())
                .executes(context -> logError(context.getSource().getSender(), "Usage: /update <plugin> <version|latest>"))
                .then(argument("plugin", StringArgumentType.string())
                        .suggests(this::getPluginList)
                        .executes(context -> logError(context.getSource().getSender(), "Usage: /update <plugin> <version|latest>"))
                        .then(argument("version", StringArgumentType.string())
                                .suggests(this::getVersionList)
                                .executes(context -> {
                                    String plugin = StringArgumentType.getString(context, "plugin");
                                    String version = StringArgumentType.getString(context, "version");
                                    String filename = plugin + "-" + version + ".jar";
                                    String url = "https://ci.vanillaflux.com/job/" + plugin + "/lastSuccessfulBuild/artifact/build/libs/" + filename;
                                    //Downloading <plugin> <version>...
                                    ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text("Downloading ", NamedTextColor.YELLOW).append(getStylizedName(plugin, version)).append(text("...", NamedTextColor.YELLOW)), context.getSource().getSender());
                                    InputStream in = ShadowUtils.downloadFile(url);
                                    if (in != null) {
                                        try {

                                            //TODO Make getMessage(<key>) work with placeholders
                                            for (File file : ShadowUtils.getPlugin().getDataFolder().getParentFile().listFiles()) {
                                                String name = file.getName();
                                                if (name.startsWith(plugin) && file.isFile()) {
                                                    //Found existing file.
                                                    ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text("Found existing file.").color(NamedTextColor.YELLOW), context.getSource().getSender());
                                                    String old_version = name.replaceAll(plugin + "-", "").replaceAll(".jar", "");
                                                    Files.deleteIfExists(file.toPath());
                                                    //<plugin> <version> has veen deleted.
                                                    ShadowUtils.getLogger().log(Logger.LogLevel.WARN, ShadowUtils.getMessageUtils().getMessage("msg.test4"));
                                                    ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content("").append(getStylizedName(plugin, old_version)).append(text(" has been deleted.", NamedTextColor.YELLOW)).build(), context.getSource().getSender());
                                                }
                                            }
                                            ShadowUtils.saveStream(in, new FileOutputStream("plugins/" + filename));
                                            //Finished downloading <plugin> <name>.
                                            ShadowUtils.getLogger().log(Logger.LogLevel.INFO, text().content("Finished downloading ").color(NamedTextColor.YELLOW).append(getStylizedName(plugin, version)).append(text(".", NamedTextColor.WHITE)).build(), context.getSource().getSender());
                                        } catch (FileNotFoundException e) {
                                            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
                                        } catch (IOException e) {
                                            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "ERROR");
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))).build();
    }

    private CompletableFuture<Suggestions> getVersionList(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        try {
            String plugin = context.getInput().split(" ")[1];
            System.out.println(plugin + " <------------ PLUGIN");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document xml = db.parse(ShadowUtils.downloadFile(ShadowUtils.JENKINS_URL + "job/" + plugin + "/lastSuccessfulBuild/" + ShadowUtils.JENKINS_API_ENDPOINT));
            xml.getDocumentElement().normalize();
            NodeList versions = xml.getElementsByTagName("artifact");
            for (int temp = 0; temp < versions.getLength(); temp++) {
                Node job = versions.item(temp);
                String s = ((Element) job).getElementsByTagName("fileName").item(0).getTextContent();
                if (s.endsWith("-javadoc.jar") || s.endsWith("-sources.jar") || s.endsWith("-reobf.jar")) continue;
                s = s.replaceAll(plugin + "-", "");
                s = s.replaceAll(".jar", "");
                builder.suggest(s);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> getPluginList(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xml = db.parse(ShadowUtils.downloadFile(ShadowUtils.JENKINS_URL + ShadowUtils.JENKINS_API_ENDPOINT));
            xml.getDocumentElement().normalize();
            NodeList jobs = xml.getElementsByTagName("job");
            for (int temp = 0; temp < jobs.getLength(); temp++) {
                Node job = jobs.item(temp);
                if (((Element) job).getAttribute("_class").equalsIgnoreCase("org.jenkinsci.plugins.workflow.job.WorkflowJob")) {
                    String s = ((Element) job).getElementsByTagName("name").item(0).getTextContent();
                    builder.suggest(s);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return builder.buildFuture();
    }
    private Component getStylizedName(String plugin, String version) {
        TextColor AQUA = NamedTextColor.AQUA;
        TextColor GREEN = NamedTextColor.GREEN;
        return text().content("").color(NamedTextColor.WHITE).append(text(plugin, AQUA)).append(text(" v" + version, GREEN)).build();
    }
}
