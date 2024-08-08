package me.quickscythe.shadowcore.utils.config;


import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.chat.Logger;
import org.json2.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigFile {

    final JSONObject defaults;
    JSONObject data;
    File file;

    public ConfigFile(File file) {
        this(file, new JSONObject());
    }

    public ConfigFile(File file, JSONObject defaults) {
        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
        }
        this.data = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        this.defaults = new JSONObject(defaults.toString());
        this.file = file;
    }

    public void save() {
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(data.toString(2));
            f2.close();
        } catch (IOException e) {
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, "There was an error saving " + file.getName());
            ShadowUtils.getLogger().log(Logger.LogLevel.ERROR, e);
        }
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void reset() {
        this.data = new JSONObject(defaults.toString());
        save();
    }
}
