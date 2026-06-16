# AI-Assisted Honor of Kings Information Management System

A Java console application for managing Honor of Kings game data, including players, heroes, equipment, teams, and match records. Developed as a coursework project with AI assistance.

---

## 1. Project Overview

This system provides a menu-driven console interface for:
- Managing player profiles, hero collections, and team memberships
- Tracking match records and calculating statistics
- Supporting role-based access (Admin vs Player)
- Persisting data through Java object serialization

**Course:** Java Object-Oriented Programming  
**Development Period:** June 2026  
**Language:** Java (Console Application)

---

## 2. How to Run

### Prerequisites
- Java JDK 17 or higher
- Command line terminal

### Compilation
```bash
cd src
javac -cp . Main.java
```

### Execution
```bash
java -cp . Main
```

### First Run
On first run, the system will:
1. Check for saved data in `data/game_data.dat`
2. If no saved data exists, initialize with sample dataset
3. Display the login menu

---

## 3. Default Login Accounts

| Role | ID | Password |
|---|---|---|
| Admin | A01 | admin123 |
| Admin | A02 | admin123 |
| Player | P01 | pass123 |
| Player | P02 | pass123 |
| Player | P05 | pass123 |

Additional players (P03, P04, P06-P10, P11-P15) are also available with password `pass123`.

---

## 4. Implemented Features

### Core Features (F1-F9)

| Code | Feature | Status |
|---|---|---|
| F1 | Player Lookup (by ID, name, team) | Implemented |
| F2 | Team Overview (members, stats, top player) | Implemented |
| F3 | Hero Details (by ID or type, stats, equipment) | Implemented |
| F4 | Equipment Statistics (type, bonuses) | Implemented |
| F5 | Match History (by team, results) | Implemented |
| F6 | Leaderboard (player/team rankings) | Implemented |
| F7 | Data Management (Admin add/remove) | Implemented |
| F8 | Data Persistence (save/load) | Implemented |
| F9 | Login Authentication (role-based) | Implemented |

### Admin Capabilities
- Add/Remove Player, Team, Hero, Match Record
- View all system data
- Save data to file

### Player Capabilities
- View public information (players, teams, heroes, equipment, matches, rankings)
- View personal profile (info, heroes, team)

---

## 5. Java Concepts Used

| Concept | Application |
|---|---|
| **Inheritance** | `Player` and `Admin` extend abstract `Person` |
| **Abstract Class** | `Person` defines common user attributes |
| **Interface** | `Searchable` (searchable entities), `Equipment` (equipment items) |
| **Polymorphism** | `AuthenticationService` uses `Person` for both roles |
| **Encapsulation** | Private fields with controlled access methods |
| **Collections** | `ArrayList` for all entity collections |
| **Enums** | `HeroType`, `EquipmentType`, `MatchResult` |
| **Exception Handling** | `try-catch` for input, file I/O; custom `ValidationException` |
| **File I/O** | `ObjectOutputStream` / `ObjectInputStream` for persistence |
| **Bidirectional Association** | `Player ↔ Team`, `Player ↔ Hero` synchronized |

---

## 6. AI Usage Summary

This project was developed with AI assistance using multiple agent roles:

| Agent Role | Purpose |
|---|---|
| **Architect Agent** | Class design, UML structure, package organization |
| **Implementation Agent** | Code implementation for specific classes and features |
| **Testing Agent** | Test case design, bug detection, verification |
| **Documentation Agent** | Plan, design documents, and README |

AI tools used: ChatGPT / Claude / Cursor (as available)

All AI-generated code was verified through compilation and testing before integration.

---

## 7. Testing Summary

### Automated Testing
- 12 Admin/Player management tests — all passed
- 18 system and edge case tests — 15 passed, 3 failed (before persistence fix)
- 6 code quality verification tests — all passed

### Manual Testing
- Menu functions 1-6 verified through console interaction
- Persistence verified (save → restart → load → data restored)

### Test Documentation
Full test records available in:
```
docs/test-cases.md
```

---

## 8. Known Limitations

1. **Console Interface Only** — No GUI implemented (extra credit feature)
2. **No Combat Simulation** — Turn-based battle simulator not implemented (extra credit)
3. **No Recommendation Engine** — Hero/equipment recommendations not implemented (extra credit)
4. **Single Save File** — Only one save slot (`data/game_data.dat`)
5. **No Password Encryption** — Passwords stored in plaintext

---

## 9. Project Structure

```
king/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Searchable.java
│   │   ├── Person.java
│   │   ├── Player.java
│   │   ├── Admin.java
│   │   ├── Hero.java
│   │   ├── Equipment.java
│   │   ├── EquipmentItem.java
│   │   ├── Team.java
│   │   ├── MatchRecord.java
│   │   ├── HeroType.java
│   │   ├── EquipmentType.java
│   │   └── MatchResult.java
│   ├── service/
│   │   ├── GameDataManager.java
│   │   ├── AuthenticationService.java
│   │   ├── SearchService.java
│   │   ├── RankingService.java
│   │   └── FileStorageService.java
│   ├── util/
│   │   ├── DataInitializer.java
│   │   └── InputHelper.java
│   └── exception/
│       └── ValidationException.java
├── docs/
│   ├── plan.md
│   ├── design.md
│   ├── test-cases.md
│   └── uml.png
├── ai/
│   ├── prompts.md
│   ├── agent-log.md
│   └── reflection.md
├── git-history.txt
└── README.md
```

---

## 10. Academic Integrity

This project was completed with AI assistance as permitted by the course policy. All AI interactions are documented in:
- `ai/prompts.md` — Prompt records
- `ai/agent-log.md` — Multi-agent interaction log
- `ai/reflection.md` — Critical reflection on AI use

All submitted code was personally verified for correctness and understandability.

---

*Project Version: 1.0*  
*Last Updated: 2026-06-10*
