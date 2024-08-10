package me.quickscythe.shadowcore.utils;

import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;

public class ShadowVoiceService implements VoicechatPlugin {

    VoicechatApi api;
    @Override
    public String getPluginId() {
        return ShadowUtils.getPlugin().getName();
    }


    /**
     * Called when the voice chat initializes the plugin.
     *
     * @param api the voice chat API
     */
    @Override
    public void initialize(VoicechatApi api) {
        this.api = api;
    }

    /**
     * Called once by the voice chat to register all events.
     *
     * @param registration the event registration
     */
    @Override
    public void registerEvents(EventRegistration registration) {
        // TODO register your events
    }

    public VoicechatApi getApi(){
        return api;
    }
}
