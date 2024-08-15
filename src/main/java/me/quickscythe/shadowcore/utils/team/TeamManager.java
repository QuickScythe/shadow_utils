package me.quickscythe.shadowcore.utils.team;


import me.quickscythe.shadowcore.utils.config.ConfigClass;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamManager extends ConfigClass {

    private final Map<String, Team> TEAMS = new HashMap<>();

    public TeamManager(JavaPlugin plugin) {
        super(plugin, "teams");
    }

    public void init(){

    }

    public Team getTeam(OfflinePlayer player){
        for(Team team : getTeams()){
            if(team.hasPlayer(player))
                return team;
        }
        return null;
    }

    public Team registerTeam(String name){
        Team team = new Team(name);
        TEAMS.put(name, team);
        return team;
    }

    public Team getTeam(String name){
        return TEAMS.getOrDefault(name, null);
    }

    public Collection<Team> getTeams(){
        return TEAMS.values();
    }


}
