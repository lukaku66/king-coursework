package service;

import model.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * RankingService — Generates ranked lists of players and teams.
 *
 * Design:
 *   Uses Java Comparator with lambda expressions for sorting.
 *   Returns new ArrayList copies to avoid modifying original data.
 */
public class RankingService {

    private GameDataManager dataManager;

    /** Constructor injection — receives the central data hub. */
    public RankingService(GameDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // ============================================================
    // Player Rankings
    // ============================================================

    /** Ranks players by win rate (highest first). */
    public ArrayList<Player> rankPlayersByWinRate() {
        ArrayList<Player> list = new ArrayList<>(dataManager.getPlayers());
        list.sort(Comparator.comparingDouble(Player::getWinRate).reversed());
        return list;
    }

    /** Ranks players by level (highest first). */
    public ArrayList<Player> rankPlayersByLevel() {
        ArrayList<Player> list = new ArrayList<>(dataManager.getPlayers());
        list.sort(Comparator.comparingInt(Player::getLevel).reversed());
        return list;
    }

    /**
     * Ranks players by match count (highest first).
     * Note: Player class does not store match count directly.
     *       We derive it from the player's team match history.
     */
    public ArrayList<Player> rankPlayersByMatchCount() {
        ArrayList<Player> list = new ArrayList<>(dataManager.getPlayers());
        list.sort(Comparator.comparingInt(this::getPlayerMatchCount).reversed());
        return list;
    }

    /** Ranks players by number of heroes owned (highest first). */
    public ArrayList<Player> rankPlayersByHeroCount() {
        ArrayList<Player> list = new ArrayList<>(dataManager.getPlayers());
        list.sort(Comparator.comparingInt(Player::getHeroCount).reversed());
        return list;
    }

    // ============================================================
    // Team Rankings
    // ============================================================

    /** Ranks teams by win rate (highest first). */
    public ArrayList<Team> rankTeamsByWinRate() {
        ArrayList<Team> list = new ArrayList<>(dataManager.getTeams());
        list.sort(Comparator.comparingDouble(Team::getWinRate).reversed());
        return list;
    }

    /** Ranks teams by average player level (highest first). */
    public ArrayList<Team> rankTeamsByAverageLevel() {
        ArrayList<Team> list = new ArrayList<>(dataManager.getTeams());
        list.sort(Comparator.comparingDouble(Team::getAverageLevel).reversed());
        return list;
    }

    /** Ranks teams by total matches played (highest first). */
    public ArrayList<Team> rankTeamsByTotalMatches() {
        ArrayList<Team> list = new ArrayList<>(dataManager.getTeams());
        list.sort(Comparator.comparingInt(Team::getTotalMatches).reversed());
        return list;
    }

    // ============================================================
    // Utility — Top N
    // ============================================================

    /**
     * Returns the top N players by the specified criteria.
     *
     * @param n        number of players to return
     * @param criteria "winRate", "level", "matchCount", or "heroCount"
     * @return top N players, or empty list if criteria is invalid
     */
    public ArrayList<Player> getTopNPlayers(int n, String criteria) {
        ArrayList<Player> ranked;
        switch (criteria.toLowerCase()) {
            case "winrate":
                ranked = rankPlayersByWinRate();
                break;
            case "level":
                ranked = rankPlayersByLevel();
                break;
            case "matchcount":
                ranked = rankPlayersByMatchCount();
                break;
            case "herocount":
                ranked = rankPlayersByHeroCount();
                break;
            default:
                return new ArrayList<>();
        }
        int limit = Math.min(n, ranked.size());
        return new ArrayList<>(ranked.subList(0, limit));
    }

    // ============================================================
    // Helper Methods
    // ============================================================

    /**
     * Derives a player's match count from their team's match history.
     * If the player has no team, returns 0.
     */
    private int getPlayerMatchCount(Player player) {
        if (player.getTeam() != null) {
            return player.getTeam().getTotalMatches();
        }
        return 0;
    }
}
