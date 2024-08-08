package me.quickscythe.shadowcore.utils;

import me.quickscythe.shadowcore.utils.chat.Logger;
import me.quickscythe.shadowcore.utils.chat.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

public class ShadowUtils {

    public static JavaPlugin plugin;
    public static Logger logger;

    public static final String JENKINS_URL = "https://ci.vanillaflux.com/";
    public static final String JENKINS_API_ENDPOINT = "api/xml";

    public static void init(JavaPlugin plugin){
        ShadowUtils.plugin = plugin;
        plugin.saveConfig();
        logger = new Logger(plugin);
        MessageUtils.init();
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public static Logger getLogger(){
        return logger;
    }

    //Paper server with a points tally with a way you can earn points
    //Pvp and traps are accepted and enabled. Kills are worth points
    //There will be 4 sessions over the course of a month.
    // Each session is a week. There may be a couple days where the server goes offline


    // Calc points at end of session

    // Earn points with minigames (different kinds of minigames)
    // Teams
    // Lots of minigames

    //Max of 24 hours per sessions, countdown as they get closer (kick them)

    public static InputStream downloadFile(String url, String... auth) {



        try {

            URL myUrl = new URI(url).toURL();
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("GET");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
//            InputStream in = ;
//            FileOutputStream out = new FileOutputStream(filename);



            return conn.getInputStream();

        } catch (Exception ex) {
            getLogger().log(Logger.LogLevel.ERROR, "There was an error downloading that file.");
            getLogger().log(Logger.LogLevel.ERROR, ex);
        }

        return InputStream.nullInputStream();
    }

    public static void saveStream(InputStream in, FileOutputStream out){
        try {
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1) out.write(b, 0, c);

            in.close();
            out.close();
        } catch (IOException ex){
            getLogger().log(Logger.LogLevel.ERROR, ex);
        }
    }

}
