package model;

import java.util.ArrayList;

public class Team {
    // TODO: Fill in fields
    // - id: unique team identifier (String)
    // - name: team name (String)
    // - players: list of team members (ArrayList<Player>)
    // - matchHistory: match records this team participated in (ArrayList<MatchRecord>)

    // TODO: Constructor
    // Initialize id, name, and create empty players and matchHistory lists

    // TODO: Getters / Setters
    // Provide standard getters and setters for all fields

    // TODO: Business methods (framework signatures only)
    // - addPlayer(Player p): add a player to the team, also set the player's team field (bidirectional sync)
    // - removePlayer(Player p): remove a player from the team, also clear the player's team field
    // - getPlayerCount(): return current member count
    // - getAverageLevel(): iterate players to calculate average level
    // - getTotalMatches(): return matchHistory size
    // - getWinRate(): iterate matchHistory to calculate win rate
    // - getTopPlayer(): find the best player by win rate or level
    // - addMatch(MatchRecord mr): add a match record

    // TODO: toString()
    // Formatted output: team name, member count, average level, total matches, win rate
}
