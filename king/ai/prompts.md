# Prompts Record

This document records the actual prompts used during the development of the Honor of Kings Information Management System, organized by AI agent role.

---

## Architect Agent 1: Project Structure and Class Design

**Date:** 2026-06-08

**Role:** Architect Agent

**Input:**
> Based on the course assignment requirements, design a Honor of Kings Information Management System using Java object-oriented programming. The system should manage players, heroes, equipment, teams, and match records, supporting login authentication, information query, ranking statistics, data management, and file persistence. Please help me design the project structure, core class diagram, and responsibilities of each class.

**AI Response:**
- Designed project package structure: `model/`, `service/`, `util/`, `exception/`
- Proposed core classes:
  - `Person` (abstract) → `Player`, `Admin`
  - `Hero`, `Team`, `MatchRecord`, `EquipmentItem`
  - `GameDataManager`, `AuthenticationService`, `SearchService`, `RankingService`, `FileStorageService`
- Defined `Searchable` interface for common search behavior
- Designed `Equipment` interface for equipment abstraction
- Created enum types: `HeroType`, `EquipmentType`, `MatchResult`
- Planned bidirectional associations: `Player ↔ Team`, `Player ↔ Hero`
- Suggested Java object serialization for persistence

**Adopted:** Yes. Class structure and package design were implemented as proposed.

---

## Architect Agent 2: Menu Design and Role-Based Access

**Date:** 2026-06-08

**Role:** Architect Agent

**Input:**
> Please help me design the menu structure of the system. The system has two user roles: Admin and Player. Admin can view information, manage data, and save data; Player can view information and check personal profile. I need to design the login menu, Admin menu, and Player menu, and explain the function number corresponding to each menu option.

**AI Response:**
- Designed three-level menu architecture:
  - **Login Menu:** Login (1), Register (2), Exit (0)
  - **Player Menu:** Search Player (1), Team Overview (2), Hero Details (3), Equipment Stats (4), Match History (5), Rankings (6), My Profile (7), Logout (0)
  - **Admin Menu:** Search Player (1), Team Overview (2), Hero Details (3), Equipment Stats (4), Match History (5), Rankings (6), Data Management (7), Save Data (8), Logout (0)
- Proposed role-based menu switching in `Main.java` using `AuthenticationService`
- Designed `Data Management` submenu: Add/Remove Player, Hero, Match Record

**Adopted:** Yes. Menu structure implemented in `Main.java` with minor adjustments (added Team management later).

---

## Implementation Agent 1: Core Model and Service Classes

**Date:** 2026-06-08

**Role:** Implementation Agent

**Input:**
> Please help me implement the core classes in the model package: Person (abstract class), Player, Admin, Hero, Team, MatchRecord, EquipmentItem, as well as the Searchable interface and Equipment interface. Need to include fields, constructors, getters/setters, toString, and implement bidirectional association synchronization methods.

**AI Response:**
- Implemented all model classes with:
  - `Person`: abstract class with `id`, `name`, `password`, abstract `getRole()`
  - `Player`: extends `Person`, adds `level`, `winRate`, `heroes`, `team`
  - `Admin`: extends `Person`, admin role marker
  - `Hero`: `id`, `name`, `HeroType`, stats, `compatibleEquipment`, `owners`
  - `Team`: `id`, `name`, `players`, `matchHistory`, statistics methods
  - `MatchRecord`: `id`, `teamA`, `teamB`, `result`, `date`
  - `EquipmentItem`: implements `Equipment` interface
- Implemented bidirectional sync methods:
  - `Team.addPlayer()` / `removePlayer()` syncs `Player.team`
  - `Player.addHero()` / `removeHero()` syncs `Hero.owners`

**Adopted:** Yes. All classes implemented and integrated.

---

## Implementation Agent 2: Persistence and Input Validation

**Date:** 2026-06-08 / 2026-06-10

**Role:** Implementation Agent

**Input:**
> Please implement the persistence feature as the AI implementation agent based on the solution you provided.

**AI Response:**
- Implemented `FileStorageService` with real persistence:
  - `saveAll()`: uses `ObjectOutputStream` to write `GameDataManager` to `data/game_data.dat`
  - `loadAll()`: uses `ObjectInputStream` to read from file, returns new empty manager if file not found
- Added `Serializable` to all model classes and `GameDataManager`
- Created `exception/ValidationException.java` for input validation
- Enhanced `InputHelper` with `readPositiveInt()` and `readPositiveDouble()`
- Added Team management to Admin menu in `Main.java`

**Adopted:** Yes. Full implementation kept.

---

## Testing Agent 1: Feature Testing (Admin/Player/Rankings)

**Date:** 2026-06-08

**Role:** Testing Agent

**Input:**
> Please act as a testing agent to test Admin Data Management and Player Self-Management.

**AI Response:**
- Created `DataMgmtTest.java` with 12 test cases:
  - Admin add player (duplicate rejection)
  - Admin remove player (cascade cleanup)
  - Admin add/remove hero
  - Admin remove team (cascade)
  - Player view profile, change password
  - Player view heroes/team
  - Role-based access check
  - Unauthenticated access prevention
- Result: 12/12 passed
- Recorded in `docs/test-cases.md` as Test Sessions 9-10

**Adopted:** Yes. Test design informed final implementation. Test code deleted after execution.

---

## Testing Agent 2: System and Edge Case Testing

**Date:** 2026-06-08 / 2026-06-10

**Role:** Testing Agent

**Input:**
> Please act as a testing agent to test Bidirectional Sync, Save Data, Load Data, and Edge Cases.

**AI Response:**
- Created `SystemEdgeTest.java` with 18 test cases across 4 sessions:
  - **Bidirectional Sync (6 tests):** Player-Team add/remove, Player-Hero add/remove
  - **Save Data (3 tests):** File creation, entity count verification, save consistency
  - **Load Data (3 tests):** File loading, empty file fallback, entity restoration
  - **Edge Cases (6 tests):** Null input, wrong ID, wrong password, no match search, draw match, duplicate add
- Result: 15/18 passed (3 Save/Load tests failed due to stub implementation)
- Identified `FileStorageService` as incomplete, informed later implementation

**Adopted:** Yes. Failed tests were addressed by Implementation Agent 2.

---

## Summary

| # | Agent Role | Prompt Topic | Tests/Results | Status |
|---|---|---|---|---|
| 1 | Architect Agent | Project structure and class design | N/A | Adopted |
| 2 | Architect Agent | Menu design and role-based access | N/A | Adopted |
| 3 | Implementation Agent | Core model and service classes | N/A | Adopted |
| 4 | Implementation Agent | Persistence and input validation | 6/6 passed | Adopted |
| 5 | Testing Agent | Admin/Player data management | 12/12 passed | Adopted |
| 6 | Testing Agent | Bidirectional sync, save/load, edge cases | 15/18 passed | Adopted |

---

*Note: This record reflects actual AI agent roles used during development. Each agent was assigned a specific role (Architect, Implementation, or Testing) before receiving prompts. Test code was always deleted after execution to keep the project clean.*
