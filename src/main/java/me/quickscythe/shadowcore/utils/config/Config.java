package me.quickscythe.shadowcore.utils.config;

import org.json2.JSONObject;

public interface Config {


    void save();

    JSONObject getData();

    void setData(JSONObject data);

    void reset();
}
