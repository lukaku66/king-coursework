package model;

import java.util.ArrayList;

public class Team implements Searchable {
    private String id;
    private String name;
    private ArrayList<Player> players;
    private ArrayList<MatchRecord> matchHistory;

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
        this.players = new ArrayList<>();
        this.matchHistory = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ArrayList<Player> getPlayers() { return players; }
    public void setPlayers(ArrayList<Player> players) { this.players = players; }
    public ArrayList<MatchRecord> getMatchHistory() { return matchHistory; }
    public void setMatchHistory(ArrayList<MatchRecord> matchHistory) { this.matchHistory = matchHistory; }

    // Bidirectional sync: Team adds Player, Player knows its Team
    public void addPlayer(Player player) {
        if (player != null && !players.contains(player)) {
            players.add(player);
            player.setTeam(this);   // sync reverse side
        }
    }

    // Bidirectional sync: Team removes Player, Player's team becomes null
    public void removePlayer(Player player) {
        if (player != null && players.contains(player)) {
            players.remove(player);
            player.setTeam(null);  // sync reverse side
        }
    }

    public int getPlayerCount() { return players.size(); }

    public double getAverageLevel() {
        if (players.isEmpty()) return 0;
        int total = 0;
        for (Player p : players) {
            total += p.getLevel();
        }
        return (double) total / players.size();
    }

    public int getTotalMatches() { return matchHistory.size(); }

    public double getWinRate() {
        if (matchHistory.isEmpty()) return 0;
        int wins = 0;
        for (MatchRecord mr : matchHistory) {
            if (this.equals(mr.getWinner())) {
                wins++;
            }
        }
        return (double) wins / matchHistory.size();
    }

    public Player getTopPlayer() {
        if (players.isEmpty()) return null;
        Player top = players.get(0);
        for (Player p : players) {
            if (p.getWinRate() > top.getWinRate()) {
                top = p;
            }
        }
        return top;
    }

    public void addMatch(MatchRecord mr) {
        if (mr != null && !matchHistory.contains(mr)) {
            matchHistory.add(mr);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Team)) return false;
        return id.equals(((Team) obj).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return String.format("[%s] %s | Members:%d | AvgLevel:%.1f | Matches:%d | WinRate:%.0f%%",
                id, name, getPlayerCount(), getAverageLevel(), getTotalMatches(), getWinRate() * 100);
    }
}
