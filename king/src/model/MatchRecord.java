package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class MatchRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Team teamA;
    private Team teamB;
    private Team winner;       // null when result is DRAW
    private MatchResult result;
    private LocalDate date;
    private ArrayList<Hero> heroesTeamA;
    private ArrayList<Hero> heroesTeamB;

    public MatchRecord(String id, Team teamA, Team teamB, MatchResult result, LocalDate date) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.result = result;
        this.date = date;
        this.heroesTeamA = new ArrayList<>();
        this.heroesTeamB = new ArrayList<>();

        // Derive winner from result enum
        switch (result) {
            case TEAM_A_WINS:
                this.winner = teamA;
                break;
            case TEAM_B_WINS:
                this.winner = teamB;
                break;
            case DRAW:
                this.winner = null;
                break;
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Team getTeamA() { return teamA; }
    public void setTeamA(Team teamA) { this.teamA = teamA; }
    public Team getTeamB() { return teamB; }
    public void setTeamB(Team teamB) { this.teamB = teamB; }
    public Team getWinner() { return winner; }
    public void setWinner(Team winner) { this.winner = winner; }
    public MatchResult getResult() { return result; }
    public void setResult(MatchResult result) { this.result = result; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public ArrayList<Hero> getHeroesTeamA() { return heroesTeamA; }
    public void setHeroesTeamA(ArrayList<Hero> heroesTeamA) { this.heroesTeamA = heroesTeamA; }
    public ArrayList<Hero> getHeroesTeamB() { return heroesTeamB; }
    public void setHeroesTeamB(ArrayList<Hero> heroesTeamB) { this.heroesTeamB = heroesTeamB; }

    public String getWinnerName() {
        if (winner != null) {
            return winner.getName();
        }
        return "Draw";
    }

    public boolean isTeamInvolved(Team team) {
        return team != null && (teamA.equals(team) || teamB.equals(team));
    }

    public ArrayList<Hero> getHeroesForTeam(Team team) {
        if (team != null && teamA.equals(team)) {
            return heroesTeamA;
        }
        if (team != null && teamB.equals(team)) {
            return heroesTeamB;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MatchRecord)) return false;
        return id.equals(((MatchRecord) obj).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s vs %s | Result: %s | Winner: %s",
                id, date, teamA.getName(), teamB.getName(), result, getWinnerName());
    }
}
