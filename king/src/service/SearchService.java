package service;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * SearchService — Provides search capabilities across all entity types.
 *
 * Design:
 *   Layer 1: Generic search using the Searchable interface (Polymorphism + Generics).
 *   Layer 2: Domain-specific search for Players, Heroes, Teams, and MatchRecords.
 */
public class SearchService {

    private GameDataManager dataManager;

    /** Constructor injection — receives the central data hub. */
    public SearchService(GameDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // ============================================================
    // Layer 1 — Generic Search (Polymorphism via Searchable)
    // ============================================================

    /**
     * Searches a list of Searchable items by exact ID match.
     *
     * @param list the list to search (e.g., players, heroes)
     * @param id   the target ID
     * @return the matching item, or null if not found
     */
    public Searchable searchById(ArrayList<? extends Searchable> list, String id) {
        for (Searchable item : list) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Searches a list of Searchable items by exact name match (case-insensitive).
     *
     * @param list the list to search
     * @param name the target name
     * @return the first matching item, or null if not found
     */
    public Searchable searchByName(ArrayList<? extends Searchable> list, String name) {
        for (Searchable item : list) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    // ============================================================
    // Layer 2 — Domain-Specific Search
    // ============================================================

    // --- Player searches ---

    /** Returns all players belonging to the specified team. */
    public ArrayList<Player> searchPlayersByTeam(String teamId) {
        ArrayList<Player> result = new ArrayList<>();
        for (Player player : dataManager.getPlayers()) {
            if (player.getTeam() != null && player.getTeam().getId().equals(teamId)) {
                result.add(player);
            }
        }
        return result;
    }

    /** Returns all players who own the specified hero. */
    public ArrayList<Player> searchPlayersByHero(String heroId) {
        ArrayList<Player> result = new ArrayList<>();
        Hero target = dataManager.findHeroById(heroId);
        if (target == null) return result;
        for (Player player : dataManager.getPlayers()) {
            if (player.getHeroes().contains(target)) {
                result.add(player);
            }
        }
        return result;
    }

    /** Returns all players with level greater than or equal to minLevel. */
    public ArrayList<Player> searchPlayersByMinLevel(int minLevel) {
        ArrayList<Player> result = new ArrayList<>();
        for (Player player : dataManager.getPlayers()) {
            if (player.getLevel() >= minLevel) {
                result.add(player);
            }
        }
        return result;
    }

    // --- Hero searches ---

    /** Returns all heroes of the specified type. */
    public ArrayList<Hero> searchHeroesByType(HeroType type) {
        ArrayList<Hero> result = new ArrayList<>();
        for (Hero hero : dataManager.getHeroes()) {
            if (hero.getType() == type) {
                result.add(hero);
            }
        }
        return result;
    }

    /** Returns all heroes owned by the specified player. */
    public ArrayList<Hero> searchHeroesByOwner(String playerId) {
        Player target = dataManager.findPlayerById(playerId);
        if (target != null) {
            return new ArrayList<>(target.getHeroes());
        }
        return new ArrayList<>();
    }

    // --- Team search ---

    /** Finds a team by exact name match (case-insensitive). */
    public Team searchTeamByName(String name) {
        for (Team team : dataManager.getTeams()) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    // --- MatchRecord searches ---

    /** Returns all matches involving the specified team. */
    public ArrayList<MatchRecord> searchMatchesByTeam(String teamId) {
        ArrayList<MatchRecord> result = new ArrayList<>();
        Team target = dataManager.findTeamById(teamId);
        if (target == null) return result;
        for (MatchRecord mr : dataManager.getMatchRecords()) {
            if (mr.isTeamInvolved(target)) {
                result.add(mr);
            }
        }
        return result;
    }

    /** Returns all matches within the specified date range (inclusive). */
    public ArrayList<MatchRecord> searchMatchesByDateRange(LocalDate start, LocalDate end) {
        ArrayList<MatchRecord> result = new ArrayList<>();
        for (MatchRecord mr : dataManager.getMatchRecords()) {
            LocalDate date = mr.getDate();
            if (!date.isBefore(start) && !date.isAfter(end)) {
                result.add(mr);
            }
        }
        return result;
    }

    // ============================================================
    // Fuzzy Search — Name contains keyword
    // ============================================================

    /**
     * Fuzzy search: returns all items whose name contains the keyword (case-insensitive).
     *
     * @param list    the list to search
     * @param keyword the keyword to look for
     * @return list of matching items
     */
    public ArrayList<Searchable> fuzzySearchByName(ArrayList<? extends Searchable> list, String keyword) {
        ArrayList<Searchable> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Searchable item : list) {
            if (item.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Fuzzy search across all entities: returns all Players, Heroes, and Teams
     * whose name contains the keyword.
     *
     * @param keyword the keyword to look for
     * @return combined list of matching Searchable items
     */
    public ArrayList<Searchable> globalFuzzySearch(String keyword) {
        ArrayList<Searchable> result = new ArrayList<>();
        result.addAll(fuzzySearchByName(dataManager.getPlayers(), keyword));
        result.addAll(fuzzySearchByName(dataManager.getHeroes(), keyword));
        result.addAll(fuzzySearchByName(dataManager.getTeams(), keyword));
        return result;
    }
}
