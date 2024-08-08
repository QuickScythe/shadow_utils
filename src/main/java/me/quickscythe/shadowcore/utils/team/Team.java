package me.quickscythe.shadowcore.utils.team;

import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    List<UUID> members = new ArrayList<>();

    String name;
    public Team(String name){
        this.name = name;
    }

    public void addPlayer(OfflinePlayer player){
        members.add(player.getUniqueId());
    }

    public List<UUID> getPlayers(){
        return members;
    }

    public void removePlayer(OfflinePlayer player){
        members.remove(player.getUniqueId());
    }
}
