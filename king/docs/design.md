# Design Document

This document describes the detailed design of the Honor of Kings Information Management System.

---

## 1. System Overview

The system is a console-based Java application that manages players, heroes, equipment, teams, and match records for the Honor of Kings game. It supports two user roles: Admin and Player.

**Architecture Pattern:** Layered architecture with model, service, util, and exception packages.

**Persistence:** Java object serialization to `data/game_data.dat`.

---

## 2. Package Structure

```
src/
├── Main.java                    # Program entry point
├── model/                       # Entity classes
│   ├── Searchable.java          # Interface for searchable entities
│   ├── Person.java              # Abstract base class for users
│   ├── Player.java              # Player entity
│   ├── Admin.java               # Admin entity
│   ├── Hero.java                # Hero entity
│   ├── Equipment.java           # Equipment interface
│   ├── EquipmentItem.java       # Equipment implementation
│   ├── Team.java                # Team entity
│   ├── MatchRecord.java         # Match record entity
│   ├── HeroType.java            # Hero type enum
│   ├── EquipmentType.java       # Equipment type enum
│   └── MatchResult.java         # Match result enum
├── service/                     # Business logic
│   ├── GameDataManager.java     # Central data storage
│   ├── AuthenticationService.java # Login/logout handling
│   ├── SearchService.java       # Search operations
│   ├── RankingService.java      # Ranking operations
│   └── FileStorageService.java  # File persistence
├── util/                        # Utility classes
│   ├── DataInitializer.java     # Initial sample data
│   └── InputHelper.java         # Console input handling
└── exception/                   # Custom exceptions
    └── ValidationException.java # Input validation errors
```

---

## 3. Class Details

### 3.1 Person (Abstract Class)

**Purpose:** Base class for all system users.

**Fields:**
- `id: String` — Unique identifier
- `name: String` — Display name
- `password: String` — Login password

**Methods:**
- `getRole(): String` — Abstract method, returns "Player" or "Admin"
- `login(id, password): boolean` — Validates credentials
- Standard getters/setters

**Inheritance:** Extended by `Player` and `Admin`.

---

### 3.2 Player

**Purpose:** Represents a game player.

**Fields:**
- `level: int` — Player level (0-100)
- `winRate: double` — Win percentage (0.0-100.0)
- `heroes: ArrayList<Hero>` — Owned heroes
- `team: Team` — Current team (nullable)

**Key Methods:**
- `addHero(Hero)` — Adds hero with bidirectional sync
- `removeHero(Hero)` — Removes hero with bidirectional sync
- `joinTeam(Team)` — Joins team with bidirectional sync
- `leaveTeam()` — Leaves current team

---

### 3.3 Admin

**Purpose:** Administrator with data management permissions.

**Fields:** None beyond `Person`.

**Methods:**
- `getRole(): String` — Returns "Admin"

---

### 3.4 Hero

**Purpose:** Represents a playable hero.

**Fields:**
- `id: String` — Unique identifier
- `name: String` — Hero name
- `type: HeroType` — TANK, WARRIOR, ASSASSIN, MAGE, MARKSMAN, SUPPORT
- `hp: int` — Health points
- `attack: int` — Attack power
- `defense: int` — Defense power
- `speed: int` — Speed
- `compatibleEquipment: ArrayList<Equipment>` — Compatible items
- `owners: ArrayList<Player>` — Players who own this hero

**Key Methods:**
- `addCompatibleEquipment(Equipment)` — Adds compatible item
- `addOwner(Player)` — Adds owner with bidirectional sync

---

### 3.5 Team

**Purpose:** Represents a team of players.

**Fields:**
- `id: String` — Unique identifier
- `name: String` — Team name
- `players: ArrayList<Player>` — Team members
- `matchHistory: ArrayList<MatchRecord>` — Match records

**Key Methods:**
- `addPlayer(Player)` — Adds player with bidirectional sync
- `removePlayer(Player)` — Removes player with bidirectional sync
- `addMatch(MatchRecord)` — Adds match record
- `removeMatch(MatchRecord)` — Removes match record
- `getWinRate(): double` — Calculates team win rate
- `getAverageLevel(): double` — Calculates average player level

---

### 3.6 MatchRecord

**Purpose:** Records a match between two teams.

**Fields:**
- `id: String` — Unique identifier
- `teamA: Team` — First team
- `teamB: Team` — Second team
- `result: MatchResult` — TEAM_A_WINS, TEAM_B_WINS, DRAW
- `date: LocalDate` — Match date

**Key Methods:**
- `getWinner(): Team` — Returns winning team or null for draw

---

### 3.7 EquipmentItem

**Purpose:** Concrete implementation of equipment.

**Fields:**
- `id: String` — Unique identifier
- `name: String` — Item name
- `type: EquipmentType` — ATTACK, DEFENSE, MAGIC, MOVEMENT, JUNGLE
- `attackBonus: int` — Attack bonus
- `defenseBonus: int` — Defense bonus

**Implements:** `Equipment` interface.

---

### 3.8 GameDataManager

**Purpose:** Central data hub for all entities.

**Fields:**
- `players: ArrayList<Player>`
- `heroes: ArrayList<Hero>`
- `equipment: ArrayList<Equipment>`
- `teams: ArrayList<Team>`
- `matchRecords: ArrayList<MatchRecord>`
- `admins: ArrayList<Admin>`

**Key Methods:**
- `addPlayer(Player): boolean` — Adds with duplicate check
- `removePlayer(Player)` — Removes with cascade cleanup
- `findPlayerById(String): Player` — Lookup by ID
- `addTeam(Team): boolean` — Adds with duplicate check
- `removeTeam(Team)` — Removes with cascade cleanup
- `addMatchRecord(MatchRecord): boolean` — Adds with duplicate check
- `removeMatchRecord(MatchRecord)` — Removes with cascade cleanup

---

### 3.9 AuthenticationService

**Purpose:** Handles user authentication.

**Fields:**
- `currentUser: Person` — Currently logged-in user

**Key Methods:**
- `login(id, password, dataManager): boolean` — Validates and sets current user
- `logout()` — Clears current user
- `getCurrentUser(): Person` — Returns current user
- `currentUserIsAdmin(): boolean` — Checks if current user is Admin

---

### 3.10 FileStorageService

**Purpose:** Saves and loads system data.

**File Location:** `data/game_data.dat`

**Key Methods:**
- `saveAll(GameDataManager)` — Serializes to file using `ObjectOutputStream`
- `loadAll(): GameDataManager` — Deserializes from file using `ObjectInputStream`

**Fallback:** If file does not exist, returns new empty `GameDataManager`.

---

### 3.11 ValidationException

**Purpose:** Custom exception for input validation errors.

**Extends:** `RuntimeException`

**Usage:** Thrown when user input fails validation (e.g., negative level).

---

## 4. Key Design Decisions

### 4.1 Why Object Serialization?

The project contains complex bidirectional relationships (`Player ↔ Team`, `Player ↔ Hero`). Java object serialization preserves these relationships automatically without manual reconstruction.

**Alternative considered:** CSV files — rejected because reconstructing bidirectional associations would require complex manual linking.

### 4.2 Why ArrayList?

`ArrayList` provides O(1) random access and dynamic sizing. For the expected data scale (tens of entities), `ArrayList` is sufficient. `HashMap` could be used for O(1) ID lookup but was not necessary given the small dataset.

### 4.3 Bidirectional Synchronization

All bidirectional associations are maintained through add/remove methods:
- `Team.addPlayer()` sets `Player.team`
- `Player.joinTeam()` adds to `Team.players`
- `Hero.addOwner()` adds to `Player.heroes`

This ensures data consistency without external synchronization logic.

### 4.4 Role-Based Menu Separation

Admin and Player menus are completely separate methods in `Main.java`. This prevents accidental access to admin functions and simplifies permission checking.

---

## 5. Data Flow

### 5.1 Program Startup

```
Main.main()
  → initializeServices()
    → new FileStorageService()
    → storageService.loadAll()
      → If file exists: deserialize GameDataManager
      → If no file: return empty manager
    → If empty: new DataInitializer().run()
  → showLoginMenu()
```

### 5.2 Login Flow

```
showLoginMenu()
  → Input ID and password
  → authService.login(id, password, dataManager)
    → Find user by ID
    → Validate password
    → Set currentUser
  → If Admin: showAdminMenu()
  → If Player: showPlayerMenu()
```

### 5.3 Save Data Flow

```
Admin selects Save Data (Option 8)
  → storageService.saveAll(dataManager)
    → Create data/ directory if needed
    → Open ObjectOutputStream
    → writeObject(dataManager)
    → Close stream
  → Display success message
```

---

## 6. Exception Handling Strategy

| Exception Type | Handling Approach |
|---|---|
| `NumberFormatException` | `InputHelper` catches and prompts for re-entry |
| `IllegalArgumentException` | Caught when invalid enum value entered |
| `ValidationException` | Thrown for negative numbers, caught in `InputHelper` |
| `IOException` | Caught in `FileStorageService`, prints error message |
| `NullPointerException` | Prevented through null checks before operations |

---

*Document Version: 1.0*  
*Last Updated: 2026-06-10*
