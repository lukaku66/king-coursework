import model.*;
import service.*;
import util.DataInitializer;
import util.InputHelper;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Main — Entry point for the Honor of Kings Information Management System.
 *
 * Architecture:
 *   Initializes all services, then runs a menu-driven main loop.
 *   Menu changes based on login state and user role (Admin vs Player).
 */
public class Main {

    private static GameDataManager dataManager;
    private static AuthenticationService authService;
    private static SearchService searchService;
    private static RankingService rankingService;
    private static FileStorageService storageService;
    private static boolean running = true;

    public static void main(String[] args) {
        // Initialize all services
        initializeServices();

        // Main menu loop
        while (running) {
            if (authService.getCurrentUser() == null) {
                showLoginMenu();
            } else if (authService.currentUserIsAdmin()) {
                showAdminMenu();
            } else {
                showPlayerMenu();
            }
        }
    }

    // ============================================================
    // Initialization
    // ============================================================

    private static void initializeServices() {
        System.out.println("========================================");
        System.out.println("  Honor of Kings Info Management System");
        System.out.println("========================================");

        // Try to load from file first; if not found, use DataInitializer
        storageService = new FileStorageService();
        dataManager = storageService.loadAll();

        if (dataManager.getTotalEntityCount() == 0) {
            System.out.println("No saved data found. Loading test data...");
            dataManager = new GameDataManager(new DataInitializer());
        } else {
            System.out.println("Data loaded from disk.");
        }

        authService = new AuthenticationService(dataManager);
        searchService = new SearchService(dataManager);
        rankingService = new RankingService(dataManager);

        System.out.println("System ready. Total entities: " + dataManager.getTotalEntityCount());
    }

    // ============================================================
    // Login Menu (not logged in)
    // ============================================================

    private static void showLoginMenu() {
        System.out.println("\n===== Login Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Register (New Player)");
        System.out.println("0. Exit");
        int choice = InputHelper.readInt("Enter choice: ");

        switch (choice) {
            case 1: handleLogin(); break;
            case 2: handleRegister(); break;
            case 0:
                handleExit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void handleLogin() {
        String id = InputHelper.readString("ID: ");
        String password = InputHelper.readString("Password: ");
        Person user = authService.login(id, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Login failed. Invalid ID or password.");
        }
    }

    private static void handleRegister() {
        String id = InputHelper.readString("New ID (e.g., P11): ");
        if (dataManager.findPlayerById(id) != null || dataManager.findAdminById(id) != null) {
            System.out.println("ID already exists. Please choose another.");
            return;
        }
        String name = InputHelper.readString("Name: ");
        String password = InputHelper.readString("Password: ");
        int level = InputHelper.readInt("Level: ");
        Player newPlayer = new Player(id, name, password, level);
        dataManager.addPlayer(newPlayer);
        System.out.println("Registration successful! You can now login with ID: " + id);
    }

    private static void handleExit() {
        String confirm = InputHelper.readLine("Save data before exiting? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            storageService.saveAll(dataManager);
            System.out.println("Data saved.");
        }
        System.out.println("Goodbye!");
        running = false;
    }

    // ============================================================
    // Player Menu (logged in as Player)
    // ============================================================

    private static void showPlayerMenu() {
        System.out.println("\n===== Player Menu =====");
        System.out.println("Welcome, " + authService.getCurrentUser().getName());
        System.out.println("1. Search Player");
        System.out.println("2. Team Overview");
        System.out.println("3. Hero Details");
        System.out.println("4. Equipment Stats");
        System.out.println("5. Match History");
        System.out.println("6. Rankings");
        System.out.println("7. My Profile");
        System.out.println("0. Logout");
        int choice = InputHelper.readInt("Enter choice: ");

        switch (choice) {
            case 1: handleSearchPlayer(); break;
            case 2: handleTeamOverview(); break;
            case 3: handleHeroDetails(); break;
            case 4: handleEquipmentStats(); break;
            case 5: handleMatchHistory(); break;
            case 6: handleRankings(); break;
            case 7: handleMyProfile(); break;
            case 0:
                authService.logout();
                System.out.println("Logged out.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // ============================================================
    // Admin Menu (logged in as Admin)
    // ============================================================

    private static void showAdminMenu() {
        System.out.println("\n===== Admin Menu =====");
        System.out.println("Welcome, " + authService.getCurrentUser().getName() + " [ADMIN]");
        System.out.println("1.  Search Player");
        System.out.println("2.  Team Overview");
        System.out.println("3.  Hero Details");
        System.out.println("4.  Equipment Stats");
        System.out.println("5.  Match History");
        System.out.println("6.  Rankings");
        System.out.println("7.  Data Management (Add/Remove)");
        System.out.println("8.  Save Data");
        System.out.println("0.  Logout");
        int choice = InputHelper.readInt("Enter choice: ");

        switch (choice) {
            case 1: handleSearchPlayer(); break;
            case 2: handleTeamOverview(); break;
            case 3: handleHeroDetails(); break;
            case 4: handleEquipmentStats(); break;
            case 5: handleMatchHistory(); break;
            case 6: handleRankings(); break;
            case 7: handleDataManagement(); break;
            case 8: handleSaveData(); break;
            case 0:
                authService.logout();
                System.out.println("Logged out.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // ============================================================
    // Shared Feature Handlers (F1 - F6)
    // ============================================================

    /** F1: Search Player by ID or Name */
    private static void handleSearchPlayer() {
        System.out.println("\n--- Search Player ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Team");
        int sub = InputHelper.readInt("Choose search method: ");

        switch (sub) {
            case 1: {
                String id = InputHelper.readString("Enter Player ID: ");
                Player p = dataManager.findPlayerById(id);
                if (p != null) {
                    printPlayerDetail(p);
                } else {
                    System.out.println("Player not found.");
                }
                break;
            }
            case 2: {
                String name = InputHelper.readString("Enter Player Name: ");
                Searchable result = searchService.searchByName(dataManager.getPlayers(), name);
                if (result != null) {
                    printPlayerDetail((Player) result);
                } else {
                    System.out.println("Player not found.");
                }
                break;
            }
            case 3: {
                String teamId = InputHelper.readString("Enter Team ID: ");
                ArrayList<Player> players = searchService.searchPlayersByTeam(teamId);
                if (players.isEmpty()) {
                    System.out.println("No players found for this team.");
                } else {
                    System.out.println("Players in team (" + players.size() + "):");
                    for (Player p : players) {
                        System.out.println("  " + p);
                    }
                }
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    /** F2: Team Overview */
    private static void handleTeamOverview() {
        System.out.println("\n--- Team Overview ---");
        for (Team team : dataManager.getTeams()) {
            System.out.println(team);
            System.out.println("  Members:");
            for (Player p : team.getPlayers()) {
                System.out.println("    - " + p.getName() + " (Lv." + p.getLevel() + ")");
            }
            Player top = team.getTopPlayer();
            if (top != null) {
                System.out.println("  Best Player: " + top.getName() + " (WR: " + (top.getWinRate() * 100) + "%)");
            }
            System.out.println();
        }
        InputHelper.pressEnterToContinue();
    }

    /** F3: Hero Details */
    private static void handleHeroDetails() {
        System.out.println("\n--- Hero Details ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Type");
        int sub = InputHelper.readInt("Choose search method: ");

        switch (sub) {
            case 1: {
                String id = InputHelper.readString("Enter Hero ID: ");
                Hero hero = dataManager.findHeroById(id);
                if (hero != null) {
                    printHeroDetail(hero);
                } else {
                    System.out.println("Hero not found.");
                }
                break;
            }
            case 2: {
                System.out.println("Types: TANK, WARRIOR, ASSASSIN, MAGE, MARKSMAN, SUPPORT");
                String typeStr = InputHelper.readString("Enter Hero Type: ");
                try {
                    HeroType type = HeroType.valueOf(typeStr.toUpperCase());
                    ArrayList<Hero> heroes = searchService.searchHeroesByType(type);
                    if (heroes.isEmpty()) {
                        System.out.println("No heroes of this type.");
                    } else {
                        System.out.println("Heroes (" + heroes.size() + "):");
                        for (Hero h : heroes) {
                            printHeroDetail(h);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid hero type.");
                }
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    /** F4: Equipment Stats */
    private static void handleEquipmentStats() {
        System.out.println("\n--- Equipment Stats ---");
        System.out.println("1. List All Equipment");
        System.out.println("2. Filter by Type");
        int sub = InputHelper.readInt("Choose option: ");

        switch (sub) {
            case 1: {
                System.out.println("All Equipment (" + dataManager.getEquipment().size() + "):");
                for (EquipmentItem eq : dataManager.getEquipment()) {
                    System.out.println("  " + eq);
                }
                break;
            }
            case 2: {
                System.out.println("Types: ATTACK, DEFENSE, MAGIC, MOVEMENT, JUNGLE, SUPPORT");
                String typeStr = InputHelper.readString("Enter Equipment Type: ");
                try {
                    EquipmentType type = EquipmentType.valueOf(typeStr.toUpperCase());
                    System.out.println("Equipment of type " + type + ":");
                    for (EquipmentItem eq : dataManager.getEquipment()) {
                        if (eq.getType() == type) {
                            System.out.println("  " + eq);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid equipment type.");
                }
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    /** F5: Match History */
    private static void handleMatchHistory() {
        System.out.println("\n--- Match History ---");
        String teamId = InputHelper.readString("Enter Team ID: ");
        ArrayList<MatchRecord> matches = searchService.searchMatchesByTeam(teamId);
        if (matches.isEmpty()) {
            System.out.println("No matches found for this team.");
        } else {
            System.out.println("Matches (" + matches.size() + "):");
            for (MatchRecord mr : matches) {
                System.out.println("  " + mr);
            }
        }
        InputHelper.pressEnterToContinue();
    }

    /** F6: Rankings */
    private static void handleRankings() {
        System.out.println("\n--- Rankings ---");
        System.out.println("1. Players by Win Rate");
        System.out.println("2. Players by Level");
        System.out.println("3. Players by Hero Count");
        System.out.println("4. Teams by Win Rate");
        int sub = InputHelper.readInt("Choose ranking criteria: ");

        switch (sub) {
            case 1: {
                ArrayList<Player> ranked = rankingService.rankPlayersByWinRate();
                System.out.println("Players by Win Rate:");
                for (int i = 0; i < ranked.size(); i++) {
                    System.out.printf("  %d. %s (WR: %.0f%%)%n", i + 1, ranked.get(i).getName(), ranked.get(i).getWinRate() * 100);
                }
                break;
            }
            case 2: {
                ArrayList<Player> ranked = rankingService.rankPlayersByLevel();
                System.out.println("Players by Level:");
                for (int i = 0; i < ranked.size(); i++) {
                    System.out.printf("  %d. %s (Lv.%d)%n", i + 1, ranked.get(i).getName(), ranked.get(i).getLevel());
                }
                break;
            }
            case 3: {
                ArrayList<Player> ranked = rankingService.rankPlayersByHeroCount();
                System.out.println("Players by Hero Count:");
                for (int i = 0; i < ranked.size(); i++) {
                    System.out.printf("  %d. %s (%d heroes)%n", i + 1, ranked.get(i).getName(), ranked.get(i).getHeroCount());
                }
                break;
            }
            case 4: {
                ArrayList<Team> ranked = rankingService.rankTeamsByWinRate();
                System.out.println("Teams by Win Rate:");
                for (int i = 0; i < ranked.size(); i++) {
                    System.out.printf("  %d. %s (WR: %.0f%%)%n", i + 1, ranked.get(i).getName(), ranked.get(i).getWinRate() * 100);
                }
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    // ============================================================
    // Player-Only Handler
    // ============================================================

    /** View and edit current player's profile */
    private static void handleMyProfile() {
        Person current = authService.getCurrentUser();
        if (!(current instanceof Player)) return;
        Player me = (Player) current;

        System.out.println("\n--- My Profile ---");
        System.out.println("ID:       " + me.getId());
        System.out.println("Name:     " + me.getName());
        System.out.println("Level:    " + me.getLevel());
        System.out.println("Win Rate: " + (me.getWinRate() * 100) + "%");
        System.out.println("Team:     " + me.getTeamName());
        System.out.println("Heroes:   " + me.getHeroCount());

        System.out.println("\n1. Edit Name");
        System.out.println("2. Edit Password");
        System.out.println("0. Back");
        int sub = InputHelper.readInt("Choose option: ");

        switch (sub) {
            case 1: {
                String newName = InputHelper.readString("New name: ");
                me.setName(newName);
                System.out.println("Name updated.");
                break;
            }
            case 2: {
                String newPwd = InputHelper.readString("New password: ");
                me.setPassword(newPwd);
                System.out.println("Password updated.");
                break;
            }
            case 0: break;
            default: System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    // ============================================================
    // Admin-Only Handlers
    // ============================================================

    /** F7: Data Management — Add/Remove entities */
    private static void handleDataManagement() {
        System.out.println("\n--- Data Management (Admin Only) ---");
        System.out.println("1. Add Player");
        System.out.println("2. Remove Player");
        System.out.println("3. Add Hero");
        System.out.println("4. Remove Hero");
        System.out.println("5. Add Match Record");
        System.out.println("6. Remove Match Record");
        System.out.println("0. Back");
        int sub = InputHelper.readInt("Choose option: ");

        switch (sub) {
            case 1: {
                String id = InputHelper.readString("New Player ID: ");
                if (dataManager.findPlayerById(id) != null) {
                    System.out.println("ID already exists.");
                    break;
                }
                String name = InputHelper.readString("Name: ");
                String pwd = InputHelper.readString("Password: ");
                int level = InputHelper.readInt("Level: ");
                boolean ok = dataManager.addPlayer(new Player(id, name, pwd, level));
                System.out.println(ok ? "Player added." : "Failed to add player.");
                break;
            }
            case 2: {
                String id = InputHelper.readString("Player ID to remove: ");
                Player p = dataManager.findPlayerById(id);
                if (p != null) {
                    dataManager.removePlayer(p);
                    System.out.println("Player removed.");
                } else {
                    System.out.println("Player not found.");
                }
                break;
            }
            case 3: {
                String id = InputHelper.readString("New Hero ID: ");
                if (dataManager.findHeroById(id) != null) {
                    System.out.println("ID already exists.");
                    break;
                }
                String name = InputHelper.readString("Name: ");
                System.out.println("Types: TANK, WARRIOR, ASSASSIN, MAGE, MARKSMAN, SUPPORT");
                String typeStr = InputHelper.readString("Type: ");
                try {
                    HeroType type = HeroType.valueOf(typeStr.toUpperCase());
                    int hp = InputHelper.readInt("HP: ");
                    int atk = InputHelper.readInt("Attack: ");
                    int def = InputHelper.readInt("Defense: ");
                    int spd = InputHelper.readInt("Speed: ");
                    boolean ok = dataManager.addHero(new Hero(id, name, type, hp, atk, def, spd));
                    System.out.println(ok ? "Hero added." : "Failed to add hero.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid hero type.");
                }
                break;
            }
            case 4: {
                String id = InputHelper.readString("Hero ID to remove: ");
                Hero h = dataManager.findHeroById(id);
                if (h != null) {
                    dataManager.removeHero(h);
                    System.out.println("Hero removed.");
                } else {
                    System.out.println("Hero not found.");
                }
                break;
            }
            case 5: {
                String id = InputHelper.readString("New Match ID: ");
                if (dataManager.findMatchRecordById(id) != null) {
                    System.out.println("ID already exists.");
                    break;
                }
                System.out.println("Available Teams:");
                for (Team t : dataManager.getTeams()) {
                    System.out.println("  " + t.getId() + " - " + t.getName());
                }
                String teamAId = InputHelper.readString("Team A ID: ");
                String teamBId = InputHelper.readString("Team B ID: ");
                Team teamA = dataManager.findTeamById(teamAId);
                Team teamB = dataManager.findTeamById(teamBId);
                if (teamA == null || teamB == null) {
                    System.out.println("Invalid team ID.");
                    break;
                }
                System.out.println("Results: TEAM_A_WINS, TEAM_B_WINS, DRAW");
                String resultStr = InputHelper.readString("Result: ");
                try {
                    MatchResult result = MatchResult.valueOf(resultStr.toUpperCase());
                    MatchRecord mr = new MatchRecord(id, teamA, teamB, result, LocalDate.now());
                    dataManager.addMatchRecord(mr);
                    teamA.addMatch(mr);
                    teamB.addMatch(mr);
                    System.out.println("Match record added.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid match result.");
                }
                break;
            }
            case 6: {
                String id = InputHelper.readString("Match ID to remove: ");
                MatchRecord mr = dataManager.findMatchRecordById(id);
                if (mr != null) {
                    dataManager.removeMatchRecord(mr);
                    System.out.println("Match record removed.");
                } else {
                    System.out.println("Match record not found.");
                }
                break;
            }
            case 0: break;
            default: System.out.println("Invalid choice.");
        }
        InputHelper.pressEnterToContinue();
    }

    /** Save data to disk */
    private static void handleSaveData() {
        storageService.saveAll(dataManager);
        System.out.println("All data saved to disk.");
        InputHelper.pressEnterToContinue();
    }

    // ============================================================
    // Print Helpers
    // ============================================================

    private static void printPlayerDetail(Player p) {
        System.out.println("------ Player Detail ------");
        System.out.println("ID:       " + p.getId());
        System.out.println("Name:     " + p.getName());
        System.out.println("Level:    " + p.getLevel());
        System.out.println("Win Rate: " + (p.getWinRate() * 100) + "%");
        System.out.println("Team:     " + p.getTeamName());
        System.out.println("Heroes (" + p.getHeroCount() + "):");
        for (Hero h : p.getHeroes()) {
            System.out.println("  - " + h.getName() + " (" + h.getType() + ")");
        }
    }

    private static void printHeroDetail(Hero h) {
        System.out.println("------ Hero Detail ------");
        System.out.println("ID:       " + h.getId());
        System.out.println("Name:     " + h.getName());
        System.out.println("Type:     " + h.getType());
        System.out.println("HP:       " + h.getHp());
        System.out.println("Attack:   " + h.getAttack());
        System.out.println("Defense:  " + h.getDefense());
        System.out.println("Speed:    " + h.getSpeed());
        System.out.println("Compatible Equipment:");
        for (Equipment eq : h.getCompatibleEquipment()) {
            System.out.println("  - " + eq.getName() + " (" + eq.getType() + ")");
        }
        System.out.println("Owners:");
        for (Player p : h.getOwners()) {
            System.out.println("  - " + p.getName() + " (" + p.getId() + ")");
        }
    }
}
