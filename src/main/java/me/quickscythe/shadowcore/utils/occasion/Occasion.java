package me.quickscythe.shadowcore.utils.occasion;

import org.json2.JSONObject;

public interface Occasion {

    boolean started();

    boolean start();

    boolean check();

    boolean end();

    JSONObject toJson();
}
