package me.quickscythe.shadowcore.utils.team;

import me.quickscythe.shadowcore.utils.Jsonifier;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Team extends Jsonifier {
    List<UUID> members = new ArrayList<>();
    String name;
    org.bukkit.scoreboard.Team scoreboard_team;

    public Team(String name) {
        this.name = name;
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        scoreboard_team = board.getTeam(name) == null ? board.registerNewTeam(name) : board.getTeam(name);
        setColor(NamedTextColor.WHITE);


    }

    public void addPlayer(OfflinePlayer player) {
        for (Team team : TeamManager.getTeams())
            if (team.hasPlayer(player))
                team.removePlayer(player);

        members.add(player.getUniqueId());
        scoreboard_team.addPlayer(player);
    }

    public List<UUID> getPlayers() {
        return members;
    }

    public void removePlayer(OfflinePlayer player) {
        members.remove(player.getUniqueId());
        scoreboard_team.removePlayer(player);
    }

    public void setColor(NamedTextColor color) {
        scoreboard_team.color(color);
    }

    public boolean hasPlayer(OfflinePlayer player) {
        return members.contains(player.getUniqueId());
    }

    public String getName() {
        return name;
    }
}
