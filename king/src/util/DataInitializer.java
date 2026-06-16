package util;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DataInitializer — Creates all initial test data for the system.
 *
 * Purpose:
 *   This class is responsible for building the starting dataset that the
 *   entire application runs on. It creates 3 Teams, 10 Players, 15 Heroes,
 *   20 Equipment items, and 10 MatchRecords, then wires up all relationships
 *   (bidirectional associations, aggregations, etc.).
 *
 * Usage:
 *   In your Main class or GameDataManager, simply call:
 *       DataInitializer init = new DataInitializer();
 *       init.run();
 *   Then access the data via:
 *       init.getTeams()        → ArrayList<Team>
 *       init.getPlayers()      → ArrayList<Player>
 *       init.getHeroes()        → ArrayList<Hero>
 *       init.getEquipment()     → ArrayList<EquipmentItem>
 *       init.getMatchRecords()  → ArrayList<MatchRecord>
 *       init.getAdmins()        → ArrayList<Admin>
 */
public class DataInitializer {

    private ArrayList<Team> teams;
    private ArrayList<Player> players;
    private ArrayList<Hero> heroes;
    private ArrayList<EquipmentItem> equipment;
    private ArrayList<MatchRecord> matchRecords;
    private ArrayList<Admin> admins;

    public DataInitializer() {
        teams = new ArrayList<>();
        players = new ArrayList<>();
        heroes = new ArrayList<>();
        equipment = new ArrayList<>();
        matchRecords = new ArrayList<>();
        admins = new ArrayList<>();
    }

    // --- Getters ---

    public ArrayList<Team> getTeams() { return teams; }
    public ArrayList<Player> getPlayers() { return players; }
    public ArrayList<Hero> getHeroes() { return heroes; }
    public ArrayList<EquipmentItem> getEquipment() { return equipment; }
    public ArrayList<MatchRecord> getMatchRecords() { return matchRecords; }
    public ArrayList<Admin> getAdmins() { return admins; }

    // --- Main entry point ---

    public void run() {
        createEquipment();
        createHeroes();
        createTeams();
        createPlayers();
        createMatchRecords();
        createAdmins();
    }

    // --- Step 1: Create 20 Equipment items ---

    private void createEquipment() {
        equipment.add(new EquipmentItem("EQ01", "Infinite Blade",        EquipmentType.ATTACK,   80, 0));
        equipment.add(new EquipmentItem("EQ02", "Bloodweeper",           EquipmentType.ATTACK,   60, 0));
        equipment.add(new EquipmentItem("EQ03", "Shadow Battleaxe",      EquipmentType.ATTACK,   70, 0));
        equipment.add(new EquipmentItem("EQ04", "Blade of Eternity",     EquipmentType.ATTACK,   90, 0));
        equipment.add(new EquipmentItem("EQ05", "Fenrir's Tooth",       EquipmentType.ATTACK,   85, 0));

        equipment.add(new EquipmentItem("EQ06", "Guardian Armor",         EquipmentType.DEFENSE,  0, 80));
        equipment.add(new EquipmentItem("EQ07", "Blazing Cape",          EquipmentType.DEFENSE,  0, 60));
        equipment.add(new EquipmentItem("EQ08", "Frost Cloak",            EquipmentType.DEFENSE,  0, 70));
        equipment.add(new EquipmentItem("EQ09", "Divine Shield",          EquipmentType.DEFENSE,  0, 90));
        equipment.add(new EquipmentItem("EQ10", "Ominous Premonition",    EquipmentType.DEFENSE,  0, 75));

        equipment.add(new EquipmentItem("EQ11", "Sage Staff",            EquipmentType.MAGIC,   40, 0));
        equipment.add(new EquipmentItem("EQ12", "Divine Oracle",         EquipmentType.MAGIC,   50, 0));
        equipment.add(new EquipmentItem("EQ13", "Fiery Gloves",         EquipmentType.MAGIC,   45, 0));
        equipment.add(new EquipmentItem("EQ14", "Echo Staff",            EquipmentType.MAGIC,   55, 0));
        equipment.add(new EquipmentItem("EQ15", "Astral Wand",           EquipmentType.MAGIC,   60, 0));

        equipment.add(new EquipmentItem("EQ16", "Boots of Speed",        EquipmentType.MOVEMENT, 0, 0));
        equipment.add(new EquipmentItem("EQ17", "Swift Boots",           EquipmentType.MOVEMENT, 0, 0));
        equipment.add(new EquipmentItem("EQ18", "Resistance Boots",      EquipmentType.MOVEMENT, 0, 30));

        equipment.add(new EquipmentItem("EQ19", "Blade of the Jungle",   EquipmentType.JUNGLE,  65, 0));
        equipment.add(new EquipmentItem("EQ20", "Hunter's Mark",         EquipmentType.JUNGLE,  50, 0));
    }

    // --- Step 2: Create 15 Heroes with compatible equipment ---

    private void createHeroes() {
        // TANK heroes (3)
        heroes.add(new Hero("H01", "Arthur",    HeroType.TANK,     8000, 120, 350, 300));
        heroes.add(new Hero("H02", "Zhubajie",  HeroType.TANK,     7500, 100, 380, 280));
        heroes.add(new Hero("H03", "Xiang Yu",  HeroType.TANK,     8500, 130, 320, 290));

        // WARRIOR heroes (3)
        heroes.add(new Hero("H04", "Lu Bu",     HeroType.WARRIOR,  7000, 180, 250, 320));
        heroes.add(new Hero("H05", "Hua Mulan", HeroType.WARRIOR,  6500, 170, 230, 340));
        heroes.add(new Hero("H06", "Guan Yu",   HeroType.WARRIOR,  7200, 160, 260, 310));

        // ASSASSIN heroes (3)
        heroes.add(new Hero("H07", "Li Bai",    HeroType.ASSASSIN, 5500, 200, 150, 380));
        heroes.add(new Hero("H08", "Han Xin",   HeroType.ASSASSIN, 5200, 210, 140, 400));
        heroes.add(new Hero("H09", "Lan Ling Wang", HeroType.ASSASSIN, 5800, 195, 160, 370));

        // MAGE heroes (3)
        heroes.add(new Hero("H10", "Zhuge Liang", HeroType.MAGE,   4800, 140, 120, 330));
        heroes.add(new Hero("H11", "Diao Chan",    HeroType.MAGE,   4500, 150, 110, 320));
        heroes.add(new Hero("H12", "Wang Zhaojun", HeroType.MAGE,   4600, 145, 115, 325));

        // MARKSMAN heroes (2)
        heroes.add(new Hero("H13", "Hou Yi",       HeroType.MARKSMAN, 5000, 190, 130, 310));
        heroes.add(new Hero("H14", "Sun Shangxiang", HeroType.MARKSMAN, 4800, 185, 125, 330));

        // SUPPORT heroes (1)
        heroes.add(new Hero("H15", "Cai Wenji",    HeroType.SUPPORT, 5200, 100, 200, 300));

        // Assign compatible equipment to each hero
        assignEquipment("H01", "EQ06", "EQ07", "EQ09");
        assignEquipment("H02", "EQ06", "EQ08", "EQ10");
        assignEquipment("H03", "EQ07", "EQ09", "EQ10");
        assignEquipment("H04", "EQ01", "EQ03", "EQ05");
        assignEquipment("H05", "EQ02", "EQ03", "EQ04");
        assignEquipment("H06", "EQ01", "EQ02", "EQ05");
        assignEquipment("H07", "EQ01", "EQ04", "EQ19");
        assignEquipment("H08", "EQ02", "EQ03", "EQ20");
        assignEquipment("H09", "EQ01", "EQ05", "EQ19");
        assignEquipment("H10", "EQ11", "EQ12", "EQ14");
        assignEquipment("H11", "EQ12", "EQ13", "EQ15");
        assignEquipment("H12", "EQ11", "EQ14", "EQ15");
        assignEquipment("H13", "EQ01", "EQ04", "EQ16");
        assignEquipment("H14", "EQ02", "EQ03", "EQ17");
        assignEquipment("H15", "EQ12", "EQ18", "EQ10");
    }

    // Helper: assign equipment to a hero by IDs
    private void assignEquipment(String heroId, String... eqIds) {
        Hero hero = findHeroById(heroId);
        if (hero == null) return;
        for (String eqId : eqIds) {
            EquipmentItem eq = findEquipmentById(eqId);
            if (eq != null) {
                hero.addCompatibleEquipment(eq);
            }
        }
    }

    // --- Step 3: Create 3 Teams ---

    private void createTeams() {
        teams.add(new Team("T01", "Dragon Riders"));
        teams.add(new Team("T02", "Phoenix Squad"));
        teams.add(new Team("T03", "Thunder Wolves"));
    }

    // --- Step 4: Create 15 Players, assign to teams and heroes ---

    private void createPlayers() {
        // Team 1: Dragon Riders (5 players)
        players.add(new Player("P01", "Zhang Wei",  "pass123", 30));
        players.add(new Player("P02", "Li Na",      "pass123", 28));
        players.add(new Player("P03", "Wang Qiang",  "pass123", 25));
        players.add(new Player("P04", "Zhao Min",    "pass123", 27));
        players.add(new Player("P11", "Lin Feng",    "pass123", 26));

        // Team 2: Phoenix Squad (5 players)
        players.add(new Player("P05", "Chen Jie",    "pass123", 32));
        players.add(new Player("P06", "Liu Yang",    "pass123", 26));
        players.add(new Player("P07", "Huang Lei",   "pass123", 29));
        players.add(new Player("P12", "Ma Chao",     "pass123", 31));
        players.add(new Player("P13", "Wei Qing",    "pass123", 24));

        // Team 3: Thunder Wolves (5 players)
        players.add(new Player("P08", "Sun Li",     "pass123", 24));
        players.add(new Player("P09", "Zhou Yu",     "pass123", 31));
        players.add(new Player("P10", "Wu Song",     "pass123", 22));
        players.add(new Player("P14", "Deng Ai",     "pass123", 28));
        players.add(new Player("P15", "Guan Ping",   "pass123", 23));

        // Assign players to teams (bidirectional sync)
        assignToTeam("P01", "T01");
        assignToTeam("P02", "T01");
        assignToTeam("P03", "T01");
        assignToTeam("P04", "T01");
        assignToTeam("P11", "T01");
        assignToTeam("P05", "T02");
        assignToTeam("P06", "T02");
        assignToTeam("P07", "T02");
        assignToTeam("P12", "T02");
        assignToTeam("P13", "T02");
        assignToTeam("P08", "T03");
        assignToTeam("P09", "T03");
        assignToTeam("P10", "T03");
        assignToTeam("P14", "T03");
        assignToTeam("P15", "T03");

        // Assign heroes to players (bidirectional sync)
        assignHeroToPlayer("P01", "H01", "H06");
        assignHeroToPlayer("P02", "H10", "H15");
        assignHeroToPlayer("P03", "H04", "H07");
        assignHeroToPlayer("P04", "H11", "H15");
        assignHeroToPlayer("P11", "H02", "H08");
        assignHeroToPlayer("P05", "H05", "H13");
        assignHeroToPlayer("P06", "H08", "H09");
        assignHeroToPlayer("P07", "H02", "H14");
        assignHeroToPlayer("P12", "H01", "H03");
        assignHeroToPlayer("P13", "H10", "H12");
        assignHeroToPlayer("P08", "H12", "H13");
        assignHeroToPlayer("P09", "H03", "H06");
        assignHeroToPlayer("P10", "H09", "H07");
        assignHeroToPlayer("P14", "H04", "H05");
        assignHeroToPlayer("P15", "H11", "H12");

        // Set win rates for ranking purposes
        findPlayerById("P01").setWinRate(0.72);
        findPlayerById("P02").setWinRate(0.65);
        findPlayerById("P03").setWinRate(0.58);
        findPlayerById("P04").setWinRate(0.61);
        findPlayerById("P11").setWinRate(0.54);
        findPlayerById("P05").setWinRate(0.78);
        findPlayerById("P06").setWinRate(0.55);
        findPlayerById("P07").setWinRate(0.63);
        findPlayerById("P12").setWinRate(0.71);
        findPlayerById("P13").setWinRate(0.48);
        findPlayerById("P08").setWinRate(0.50);
        findPlayerById("P09").setWinRate(0.70);
        findPlayerById("P10").setWinRate(0.45);
        findPlayerById("P14").setWinRate(0.60);
        findPlayerById("P15").setWinRate(0.53);
    }

    // Helper: assign a player to a team by IDs
    private void assignToTeam(String playerId, String teamId) {
        Player player = findPlayerById(playerId);
        Team team = findTeamById(teamId);
        if (player != null && team != null) {
            team.addPlayer(player); // bidirectional sync happens inside
        }
    }

    // Helper: assign heroes to a player by IDs
    private void assignHeroToPlayer(String playerId, String... heroIds) {
        Player player = findPlayerById(playerId);
        if (player == null) return;
        for (String heroId : heroIds) {
            Hero hero = findHeroById(heroId);
            if (hero != null) {
                player.addHero(hero); // bidirectional sync happens inside
            }
        }
    }

    // --- Step 5: Create 10 MatchRecords ---

    private void createMatchRecords() {
        matchRecords.add(new MatchRecord("M01", findTeamById("T01"), findTeamById("T02"),
                MatchResult.TEAM_A_WINS, LocalDate.of(2026, 5, 20)));
        matchRecords.add(new MatchRecord("M02", findTeamById("T01"), findTeamById("T03"),
                MatchResult.TEAM_B_WINS, LocalDate.of(2026, 5, 21)));
        matchRecords.add(new MatchRecord("M03", findTeamById("T02"), findTeamById("T03"),
                MatchResult.TEAM_A_WINS, LocalDate.of(2026, 5, 22)));
        matchRecords.add(new MatchRecord("M04", findTeamById("T01"), findTeamById("T02"),
                MatchResult.DRAW,       LocalDate.of(2026, 5, 23)));
        matchRecords.add(new MatchRecord("M05", findTeamById("T02"), findTeamById("T03"),
                MatchResult.TEAM_B_WINS, LocalDate.of(2026, 5, 24)));
        matchRecords.add(new MatchRecord("M06", findTeamById("T01"), findTeamById("T03"),
                MatchResult.TEAM_A_WINS, LocalDate.of(2026, 5, 25)));
        matchRecords.add(new MatchRecord("M07", findTeamById("T01"), findTeamById("T02"),
                MatchResult.TEAM_B_WINS, LocalDate.of(2026, 5, 26)));
        matchRecords.add(new MatchRecord("M08", findTeamById("T02"), findTeamById("T03"),
                MatchResult.TEAM_A_WINS, LocalDate.of(2026, 5, 27)));
        matchRecords.add(new MatchRecord("M09", findTeamById("T01"), findTeamById("T03"),
                MatchResult.TEAM_A_WINS, LocalDate.of(2026, 5, 28)));
        matchRecords.add(new MatchRecord("M10", findTeamById("T02"), findTeamById("T03"),
                MatchResult.DRAW,       LocalDate.of(2026, 5, 29)));

        // Add match records to each team's history
        for (MatchRecord mr : matchRecords) {
            mr.getTeamA().addMatch(mr);
            mr.getTeamB().addMatch(mr);
        }
    }

    // --- Step 6: Create Admin accounts ---

    private void createAdmins() {
        admins.add(new Admin("A01", "SuperAdmin", "admin123"));
        admins.add(new Admin("A02", "GameMaster", "admin123"));
    }

    // --- Lookup helpers ---

    private Hero findHeroById(String id) {
        for (Hero h : heroes) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    private EquipmentItem findEquipmentById(String id) {
        for (EquipmentItem eq : equipment) {
            if (eq.getId().equals(id)) return eq;
        }
        return null;
    }

    private Player findPlayerById(String id) {
        for (Player p : players) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    private Team findTeamById(String id) {
        for (Team t : teams) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }
}
