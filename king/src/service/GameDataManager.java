package service;

import model.*;
import util.DataInitializer;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * GameDataManager — Central data hub for the entire application.
 *
 * Single Responsibility:
 *   Only handles data storage and basic CRUD operations.
 *   No business logic (authentication, ranking, searching) belongs here.
 *
 * Initializable:
 *   - Empty constructor for file-loaded data or manual population.
 *   - DataInitializer constructor for built-in test data.
 *
 * Persistence-Ready:
 *   All fields have public getters/setters so FileStorageService
 *   can replace the lists after loading from disk.
 */
public class GameDataManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Player> players;
    private ArrayList<Hero> heroes;
    private ArrayList<Team> teams;
    private ArrayList<EquipmentItem> equipment;
    private ArrayList<MatchRecord> matchRecords;
    private ArrayList<Admin> admins;

    // --- Constructors ---

    /** Empty constructor — creates empty lists for file loading or manual setup. */
    public GameDataManager() {
        this.players = new ArrayList<>();
        this.heroes = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.equipment = new ArrayList<>();
        this.matchRecords = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    /** Constructor with DataInitializer — populates with built-in test data. */
    public GameDataManager(DataInitializer initializer) {
        this();
        initializer.run();
        this.players = initializer.getPlayers();
        this.heroes = initializer.getHeroes();
        this.teams = initializer.getTeams();
        this.equipment = initializer.getEquipment();
        this.matchRecords = initializer.getMatchRecords();
        this.admins = initializer.getAdmins();
    }

    // --- Getters for all lists ---

    public ArrayList<Player> getPlayers() { return players; }
    public ArrayList<Hero> getHeroes() { return heroes; }
    public ArrayList<Team> getTeams() { return teams; }
    public ArrayList<EquipmentItem> getEquipment() { return equipment; }
    public ArrayList<MatchRecord> getMatchRecords() { return matchRecords; }
    public ArrayList<Admin> getAdmins() { return admins; }

    // --- Setters for persistence (FileStorageService can replace lists) ---

    public void setPlayers(ArrayList<Player> players) { this.players = players; }
    public void setHeroes(ArrayList<Hero> heroes) { this.heroes = heroes; }
    public void setTeams(ArrayList<Team> teams) { this.teams = teams; }
    public void setEquipment(ArrayList<EquipmentItem> equipment) { this.equipment = equipment; }
    public void setMatchRecords(ArrayList<MatchRecord> matchRecords) { this.matchRecords = matchRecords; }
    public void setAdmins(ArrayList<Admin> admins) { this.admins = admins; }

    // --- Find by ID ---

    public Player findPlayerById(String id) {
        for (Player p : players) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public Hero findHeroById(String id) {
        for (Hero h : heroes) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    public Team findTeamById(String id) {
        for (Team t : teams) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }

    public EquipmentItem findEquipmentById(String id) {
        for (EquipmentItem eq : equipment) {
            if (eq.getId().equals(id)) return eq;
        }
        return null;
    }

    public MatchRecord findMatchRecordById(String id) {
        for (MatchRecord mr : matchRecords) {
            if (mr.getId().equals(id)) return mr;
        }
        return null;
    }

    public Admin findAdminById(String id) {
        for (Admin a : admins) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    // --- Add with duplicate check ---

    public boolean addPlayer(Player player) {
        if (player != null && findPlayerById(player.getId()) == null) {
            players.add(player);
            return true;
        }
        return false;
    }

    public boolean addHero(Hero hero) {
        if (hero != null && findHeroById(hero.getId()) == null) {
            heroes.add(hero);
            return true;
        }
        return false;
    }

    public boolean addTeam(Team team) {
        if (team != null && findTeamById(team.getId()) == null) {
            teams.add(team);
            return true;
        }
        return false;
    }

    public boolean addEquipment(EquipmentItem item) {
        if (item != null && findEquipmentById(item.getId()) == null) {
            equipment.add(item);
            return true;
        }
        return false;
    }

    public boolean addMatchRecord(MatchRecord record) {
        if (record != null && findMatchRecordById(record.getId()) == null) {
            matchRecords.add(record);
            return true;
        }
        return false;
    }

    public boolean addAdmin(Admin admin) {
        if (admin != null && findAdminById(admin.getId()) == null) {
            admins.add(admin);
            return true;
        }
        return false;
    }

    // --- Remove with cascade cleanup ---

    public boolean removePlayer(Player player) {
        if (player != null && players.contains(player)) {
            // Remove from team (bidirectional cleanup)
            if (player.getTeam() != null) {
                player.getTeam().removePlayer(player);
            }
            // Remove hero ownerships (bidirectional cleanup)
            for (Hero hero : new ArrayList<>(player.getHeroes())) {
                player.removeHero(hero);
            }
            players.remove(player);
            return true;
        }
        return false;
    }

    public boolean removeHero(Hero hero) {
        if (hero != null && heroes.contains(hero)) {
            // Remove from all players who own it (bidirectional cleanup)
            for (Player player : new ArrayList<>(hero.getOwners())) {
                player.removeHero(hero);
            }
            heroes.remove(hero);
            return true;
        }
        return false;
    }

    public boolean removeTeam(Team team) {
        if (team != null && teams.contains(team)) {
            // Remove all players from team (bidirectional cleanup)
            for (Player player : new ArrayList<>(team.getPlayers())) {
                team.removePlayer(player);
            }
            // Remove match records from team's history
            for (MatchRecord mr : new ArrayList<>(team.getMatchHistory())) {
                team.getMatchHistory().remove(mr);
            }
            teams.remove(team);
            return true;
        }
        return false;
    }

    public boolean removeEquipment(EquipmentItem item) {
        if (item != null && equipment.contains(item)) {
            // Remove from all heroes' compatible lists
            for (Hero hero : heroes) {
                hero.getCompatibleEquipment().remove(item);
            }
            equipment.remove(item);
            return true;
        }
        return false;
    }

    public boolean removeMatchRecord(MatchRecord record) {
        if (record != null && matchRecords.contains(record)) {
            // Remove from both teams' histories
            record.getTeamA().getMatchHistory().remove(record);
            record.getTeamB().getMatchHistory().remove(record);
            matchRecords.remove(record);
            return true;
        }
        return false;
    }

    public boolean removeAdmin(Admin admin) {
        if (admin != null && admins.contains(admin)) {
            admins.remove(admin);
            return true;
        }
        return false;
    }

    // --- Utility methods ---

    /**
     * Returns all Person objects (Players + Admins) in a single list.
     * Demonstrates Polymorphism — useful for AuthenticationService.
     */
    public ArrayList<Person> getAllPersons() {
        ArrayList<Person> all = new ArrayList<>();
        all.addAll(players);
        all.addAll(admins);
        return all;
    }

    /** Clears all data lists. Useful when reloading from file. */
    public void clearAll() {
        players.clear();
        heroes.clear();
        teams.clear();
        equipment.clear();
        matchRecords.clear();
        admins.clear();
    }

    /** Returns total entity count across all lists. */
    public int getTotalEntityCount() {
        return players.size() + heroes.size() + teams.size()
             + equipment.size() + matchRecords.size() + admins.size();
    }
}
