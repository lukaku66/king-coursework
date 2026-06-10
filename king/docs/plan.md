# plan.md — Honor of Kings Information Management System

---

## 1. Project Goal

This project implements a **Honor of Kings Information Management System** using Java object-oriented programming. The system manages players, heroes, equipment, teams, and match records. It supports login authentication, information search, ranking statistics, data management, and file persistence.

The system has two user roles:

| User Role | Permission Description |
|---|---|
| `Admin` | Can view public information, manage system data such as players, heroes, teams, and match records, and save data |
| `Player` | Can log in, view public information, check rankings, and view personal profile information |

Programming language: Java  
Program type: Console menu application  
Main entry point: `Main.java`

---

## 2. Requirement Analysis

| Code | Function | Current Implementation | Status |
|---|---|---|---|
| F1 | Player Query | Search players by player ID, name, or team | Implemented |
| F2 | Team Overview | Display team members, average level, total matches, win rate, and top player | Implemented |
| F3 | Hero Details | Search heroes by hero ID or hero type, and display attributes, compatible equipment, and owners | Implemented |
| F4 | Equipment Statistics | Display equipment type, attack bonus, defense bonus, and other equipment information | Implemented |
| F5 | Match History | Search match records by team and display result, winner, and match details | Implemented |
| F6 | Ranking List | Rank players by win rate, level, match count, and hero count; rank teams by win rate, average level, and total matches | Implemented |
| F7 | Data Management | Admin can add and remove players, heroes, teams, and match records; Player can view personal information | Implemented |
| F8 | Data Persistence | Save and load `GameDataManager` using Java object serialization | Implemented |
| F9 | Login Authentication | Log in with ID and password, and distinguish between `Admin` and `Player` roles | Implemented |

---

## 3. Java Concepts Used

| Java Concept | How It Is Used in This Project |
|---|---|
| Inheritance | `Person` is the abstract parent class; `Player` and `Admin` extend `Person` |
| Abstract Class | `Person` defines common attributes and abstract methods `login()` and `getRole()` |
| Interface | `Searchable` provides common searchable methods `getId()` and `getName()`; `Equipment` defines common equipment behavior |
| Polymorphism | `AuthenticationService` uses `Person` to handle both `Player` and `Admin` objects |
| Encapsulation | Class fields are private and accessed through getter/setter methods or controlled business methods |
| Collection | `ArrayList` is used to store players, heroes, equipment, teams, match records, and admins |
| Enum | `HeroType`, `EquipmentType`, and `MatchResult` represent fixed categories |
| Exception Handling | `try-catch` is used for input conversion, hero type conversion, and file save/load operations; `ValidationException` is used for input validation |
| File Persistence | `FileStorageService` uses `ObjectOutputStream` and `ObjectInputStream` to save and load data |
| Bidirectional Association | `Player ↔ Hero` and `Player ↔ Team` are synchronized through add/remove methods |
| Custom Exception | `ValidationException` extends `RuntimeException` for invalid input such as negative level |

---

## 4. Class Design

### 4.1 model Package

| Class Name | Responsibility |
|---|---|
| `Searchable` | Defines common searchable methods `getId()` and `getName()` |
| `Person` | Abstract parent class that stores id, name, password, and defines login and role methods |
| `Player` | Stores player level, win rate, owned heroes, and affiliated team |
| `Admin` | Represents an administrator user with data management permission |
| `Hero` | Stores hero type, attributes, compatible equipment, and players who own the hero |
| `Equipment` | Interface that defines equipment ID, name, type, and attribute bonus methods |
| `EquipmentItem` | Concrete implementation of the `Equipment` interface |
| `Team` | Stores team ID, team name, player list, and match history |
| `MatchRecord` | Stores match ID, competing teams, result, winner, date, and selected heroes |
| `HeroType` | Enum for hero types |
| `EquipmentType` | Enum for equipment types |
| `MatchResult` | Enum for match results |

### 4.2 service Package

| Class Name | Responsibility |
|---|---|
| `GameDataManager` | Central data hub that stores all collections and provides basic CRUD operations |
| `AuthenticationService` | Handles login, logout, current user state, and role checking |
| `SearchService` | Provides search functions for players, heroes, teams, and match records |
| `RankingService` | Provides player rankings and team rankings |
| `FileStorageService` | Saves system data to a file and loads data when the program starts |

### 4.3 util Package

| Class Name | Responsibility |
|---|---|
| `DataInitializer` | Creates initial data, including teams, players, heroes, equipment, match records, and admins |
| `InputHelper` | Handles console input, integer reading, string reading, and pause operations |

### 4.4 exception Package

| Class Name | Responsibility |
|---|---|
| `ValidationException` | Custom runtime exception for input validation errors (e.g., negative level) |

### 4.5 Main Program

| Class Name | Responsibility |
|---|---|
| `Main` | Controls program startup, login menu, player menu, admin menu, and feature entry points |

---

## 5. UML Draft

```text
                       <<interface>>
                       Searchable
                    + getId()
                    + getName()
                            ▲
                            │
             ┌──────────────┴──────────────┐
             │                             │
       ┌────────────┐                ┌──────────┐
       │  Person    │                │   Hero   │
       │ (abstract) │                │──────────│
       │────────────│                │ - id     │
       │ - id       │                │ - name   │
       │ - name     │                │ - type   │
       │ - password │                │ - stats  │
       └─────┬──────┘                └────┬─────┘
             │                            │
      ┌──────┴──────┐                     │ aggregation
      │             │                     ▼
 ┌─────────┐   ┌─────────┐          <<interface>>
 │ Player  │   │  Admin  │           Equipment
 │─────────│   │─────────│              ▲
 │ - level │   │ role    │              │
 │ - heroes│   └─────────┘       ┌───────────────┐
 │ - team  │                     │ EquipmentItem │
 └────┬────┘                     └───────────────┘
      │
      │ belongs to
      ▼
 ┌─────────┐       has history       ┌─────────────┐
 │  Team   │────────────────────────▶│ MatchRecord │
 │─────────│                         │─────────────│
 │ - name  │                         │ - teamA     │
 │ - players                         │ - teamB     │
 │ - matches                         │ - result    │
 └─────────┘                         │ - winner    │
                                     │ - date      │
                                     └─────────────┘
```

---

## 6. Data Design

### 6.1 Initial Data

| Data Type | Quantity | Created In |
|---|---:|---|
| Team | 3 | `DataInitializer` |
| Player | 10 | `DataInitializer` |
| Hero | 15 | `DataInitializer` |
| Equipment | 20 | `DataInitializer` |
| MatchRecord | 10 | `DataInitializer` |
| Admin | 2 | `DataInitializer` |

### 6.2 Data Relationships

| Relationship | Description |
|---|---|
| `Player → Team` | One player belongs to at most one team |
| `Team → Player` | One team contains multiple players |
| `Player → Hero` | One player can own multiple heroes |
| `Hero → Player` | One hero can be owned by multiple players |
| `Hero → Equipment` | One hero can have multiple compatible equipment items |
| `Team → MatchRecord` | One team can have multiple match records |
| `MatchRecord → Team` | One match record is related to two teams |

### 6.3 Data Storage Method

When the program starts, it calls `FileStorageService.loadAll()` to try to load local saved data. If no saved file exists, the system uses `DataInitializer` to create the initial dataset.

When saving data, the system calls `FileStorageService.saveAll(dataManager)` and serializes the complete `GameDataManager` object to:

```text
data/game_data.dat
```

Java object serialization is used because the project contains multiple object relationships, such as `Player ↔ Team` and `Player ↔ Hero`. Saving the whole `GameDataManager` keeps these relationships without manually rebuilding them.

---

## 7. Function Menu Design

### 7.1 Login Menu

| Option | Function |
|---|---|
| 1 | Login |
| 2 | Register (New Player) |
| 0 | Exit |

### 7.2 Player Menu

| Option | Function |
|---|---|
| 1 | Search Player |
| 2 | Team Overview |
| 3 | Hero Details |
| 4 | Equipment Stats |
| 5 | Match History |
| 6 | Rankings |
| 7 | My Profile |
| 0 | Logout |

### 7.3 Admin Menu

| Option | Function |
|---|---|
| 1 | Search Player |
| 2 | Team Overview |
| 3 | Hero Details |
| 4 | Equipment Stats |
| 5 | Match History |
| 6 | Rankings |
| 7 | Data Management (Add/Remove) |
| 8 | Save Data |
| 0 | Logout |

#### 7.3.1 Data Management Submenu (Admin)

| Option | Function |
|---|---|
| 1 | Add Player |
| 2 | Remove Player |
| 3 | Add Team |
| 4 | Remove Team |
| 5 | Add Hero |
| 6 | Remove Hero |
| 7 | Add Match Record |
| 8 | Remove Match Record |
| 0 | Back |

---

## 8. AI Usage Plan

| AI Role | Usage Scope | Limitation |
|---|---|---|
| Architect Agent | Provides suggestions on class design, service structure, and UML design | Final design decisions are made by the student |
| Implementation Agent | Helps implement a single class, method, or local feature | Does not generate the whole project at once |
| Testing/Reviewer Agent | Helps design test cases, check code bugs, and verify feature results | Test conclusions need to be reviewed by the student |

Personal AI usage record:

> 

---

## 9. Prompt Strategy

The main prompt strategies used in this project are:

1. Describe the completed code and current file structure before asking for help.
2. Clearly assign an AI role, such as architect agent, implementation agent, or testing agent.
3. Limit each AI request to one clear task, such as implementing one class or testing one feature.
4. Ask AI to explain the code behavior so that the student can understand and modify it.
5. Compile and test AI-assisted code before deciding whether to keep it.

Personal prompt examples and records:

> To be completed by the student.

---

## 10. Development Timeline

| Stage | Content | Status |
|---|---|---|
| Stage 1 | Analyze requirements, create project structure, and write initial `plan.md` | Completed |
| Stage 2 | Design model package classes and UML draft | Completed |
| Stage 3 | Implement entity classes, enums, interfaces, and bidirectional associations | Completed |
| Stage 4 | Implement `DataInitializer` initial data | Completed |
| Stage 5 | Implement service package, including authentication, search, ranking, and data management | Completed |
| Stage 6 | Implement console menu, admin menu, and player menu | Completed |
| Stage 7 | Implement file persistence save/load functionality | Completed |
| Stage 8 | Verify main features with testing agent and manual tests | Completed |
| Stage 9 | Organize test document, project documentation, and final reflection | In Progress |

---

## 11. Testing Plan

### 11.1 Test Scope

| Test Scope | Test Content | Record Location |
|---|---|---|
| Login / Logout | Admin and player login, logout, wrong password, and wrong ID | `docs/test-cases.md` |
| Role-Based Access | Admin and player permission checks | `docs/test-cases.md` |
| Search Functions | Player, team, hero, equipment, and match search | `docs/test-cases.md` |
| Rankings | Player rankings and team rankings | `docs/test-cases.md` |
| Data Management | Admin add/remove data and player profile view | `docs/test-cases.md` |
| Bidirectional Sync | Player-Team and Player-Hero bidirectional synchronization | `docs/test-cases.md` |
| Persistence | Save and load `GameDataManager` | `docs/test-cases.md` |
| Edge Cases | Null input, wrong input, missing ID, draw match, and other edge cases | `docs/test-cases.md` |
| Manual Menu Test | Manual test for menu functions 1 to 6 | `docs/test-cases.md` |

### 11.2 Test Method

The project uses two testing methods:

1. Temporary Java test files: used to quickly verify service and model logic. These files are deleted after testing and are not kept in `src`.
2. Manual menu testing: run `Main.java` and verify menu features through console input.

Test records are saved in:

```text
docs/test-cases.md
```

Test code is not kept as part of the final submission.

Manual testing details:

> To be completed by the student.

---

## 12. Risk Analysis

| Risk | Impact | Handling Method |
|---|---|---|
| Bidirectional association is not synchronized | Player, hero, and team data may become inconsistent | Maintain both sides of the relationship in add/remove methods |
| File saving fails | Data may be lost after program restart | Use `try-catch` and verify save/load behavior through tests |
| Invalid input | The program may crash or behave incorrectly | Use `InputHelper` to handle input consistently |
| Role permission confusion | A player may access admin functions | Separate Admin and Player menus |
| AI-assisted code may be inaccurate | Compilation errors or logic bugs may appear | Compile and test after each code change |
| Missing custom exception handling | Invalid input may cause program crash | Added `ValidationException` and `readPositiveInt()` in `InputHelper` |
| MatchRecord cascade incomplete | Removing match does not clean team history | Verified `removeMatchRecord()` removes from both team histories |

---

## 13. Final Reflection Placeholder

> To be completed by the student.

---

*Version: 2.2*  
*Time: 2026-06-10*
