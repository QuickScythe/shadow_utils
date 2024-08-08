package me.quickscythe.shadowcore.listeners;

import io.papermc.paper.event.block.BlockBreakBlockEvent;
import me.quickscythe.shadowcore.utils.ShadowUtils;
import me.quickscythe.shadowcore.utils.session.SessionManager;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json2.JSONObject;

public class SessionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        SessionManager.startSession(e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        SessionManager.finishSession(e.getPlayer());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        e.getPlayer();
        JSONObject session = SessionManager.getSession(e.getPlayer());
        String key = "blocks_mined";
        session.put(key, session.has(key) ? session.getInt(key) + 1 : 1);
    }
}
