package me.quickscythe.shadowcore.utils;

import org.json2.JSONObject;

public class Jsonifier {

    public JSONObject toJson(){
        return new JSONObject(this);
    }
}
