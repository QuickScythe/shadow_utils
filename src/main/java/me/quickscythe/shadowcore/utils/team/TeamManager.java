package me.quickscythe.shadowcore.utils.team;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamManager {

    private static final Map<String, Team> TEAMS = new HashMap<>();

    public static void init(){

    }

    public static Team registerTeam(String name){
        Team team = new Team(name);
        TEAMS.put(name, team);
        return team;
    }

    public static Team getTeam(String name){
        return TEAMS.getOrDefault(name, null);
    }

    public static Collection<Team> getTeams(){
        return TEAMS.values();
    }


}
