package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MatchRecord {
    // TODO: Fill in fields
    // - id: unique match record identifier (String)
    // - teamA: team A (Team)
    // - teamB: team B (Team)
    // - winner: winning team (Team), null or special marker for draw
    // - result: match result (MatchResult enum: TEAM_A_WINS / TEAM_B_WINS / DRAW)
    // - date: match date (LocalDate)
    // - heroesTeamA: heroes selected by team A (ArrayList<Hero>)
    // - heroesTeamB: heroes selected by team B (ArrayList<Hero>)

    // TODO: Constructor
    // Initialize all fields, date can use LocalDate.now() or passed as parameter

    // TODO: Getters / Setters
    // Provide standard getters and setters for all fields

    // TODO: Business methods (framework signatures only)
    // - getWinnerName(): return winner team name, or "Draw"
    // - isTeamInvolved(Team team): check if a team participated in this match
    // - getHeroesForTeam(Team team): return the hero list for the specified team

    // TODO: toString()
    // Formatted output: date, TeamA vs TeamB, result, winner
}
