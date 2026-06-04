# plan.md —— Honor of Kings Information Management System, developed with Java object-oriented programming

***

## 1. Project Goal

Make a **Honor of Kings Information Management System, developed with Java object-oriented programming** 

**System Functions**：Manage data of players, heroes, equipment, teams and match records; support functions including information search, ranking statistics, data maintenance and user login.

**users**：
- **Admin**：can manage all players' data.
- **Player**：only can use the public function to find the public information and add player's own information.

**programing way**: java.

***

## 2. Requirement Analysis

| Code | Function | How to achieve                                                                      |
|------|----------|-------------------------------------------------------------------------------------|
| F1   | Player Query     | Search players by ID or name, display their team, level, owned heroes and equipment |
| F2   | Team Overview     | Check team members, average level, total matches, win rate and top player           |
| F3   | Hero Details    | View hero type, attributes, usable equipment and players who own this hero          |
| F4   | Equipment Statistics     | Rank equipment by usage count, rating and other indicators                          |
| F5   | Match History     | Check the latest N match records of a specific player or team                       |
| F6   | Ranking List      | Rank players by win rate, level, match count and other criteria                     |
| F7   | Data Management     | Admin: add/delete data; Player: view and edit personal information                  |
| F8   | Login Authentication     | Use ID and password to log in 2 roles--Player or Admin                              |



***

## 3. Java Concepts Used

| Java Concepts    | where                                                                                                     |
|------------------|-----------------------------------------------------------------------------------------------------------|
| **Inheritance**  | Define the `Person` ` Player` and `Admin` classes using inheritance.                                      |
| **Interface**    | Apply `encapsulation` to hide all class fields, and only expose `getter` and `setter` methods for access. |
| **Polymorphism** | use `Person`  List to manage `Player` and `Admin`      .                                                  |
| **Encapsulation** | Use `ArrayList` to store all sequential data, and `HashMap` for fast key-based lookups.                   |
| **Collection**   | use `ArrayList` to persist，use `HashMap` to find rapidly.                                                 |
| **Enum**         | `HeroType`、`MatchResult`、`EquipmentType`.                                                                 |
| **Exception Handling**         | use `try-catch` to deal with bug.                                                                         |


---

## 4. Class Design

### 4.1 Entity Class

| class name    | responsibility                                                  |
|---------------|-----------------------------------------------------------------|
| `Person`      | Store common attributes: id, name, password                     |
| `Player`      | owns hero list, level, win rate, affiliated team                |
| `Admin`       | has data management access permissions                          |
| `Hero`        | 	Contains name, type, attribute values, wearable equipment list |
| `Equipment`   | Contains name, type, attribute bonus                            |
| `Team`        | Contains name, member list, competition records                 |
| `MatchRecord` | Stores competing teams, match result, date, selected heroes     |

### 4.2 service class

| class name              | 
|-------------------------|
| `GameDataManager`       |
| `AuthenticationService` | 
| `SearchService`         | 
| `RankingService`        | 
| `FileStorageService`    | 

### 4.3 tool class

| class name        | 
|-------------------|
| `InputHelper`     | 
| `DataInitializer` | 

---

## 5. UML Draft

```
                        <<interface>>
                        Searchable
                        + searchById(id)
                        + searchByName(name)
                             ▲
                             │
              ┌──────────────┴──────────────┐
              │                             │
         ┌─────────┐                  ┌──────────┐
         │  Person │ (abstract)       │   Hero   │
         │─────────│                  │──────────│
         │ - id    │                  │ - name   │
         │ - name  │                  │ - type   │
         │ - pwd   │                  │ - stats  │
         └────┬────┘                  └──────────┘
              │                              │
     ┌────────┴────────┐              uses Equipment
     │                 │                     │
┌────┴────┐      ┌─────┴────┐         ┌──────┴──────┐
│ Player  │      │  Admin   │         │  Equipment  │
│─────────│      │──────────│         │─────────────│
│ - level │      │(管理权限)│          │  - name      │ 
│ - winRt │      └──────────┘         │ - type      │
│ - heroes│                           │ - bonus     │
└────┬────┘                           └─────────────┘
     │
     │ belongs to
     ▼
┌─────────┐         ┌──────────────┐
│  Team   │───────▶ │ MatchRecord  │
│─────────│         │──────────────│
│ - name   │        │ - teamA      │
│ - players│        │ - teamB      │
│ - matches│        │ - result     │
└───────── ┘        │ - date       │
                    └──────────────┘
```

---

## 6. Data Design

### 6.1 initial data

| Data Type     | numbers | 
|---------------|----|
| Team          | 3  | 
| Player        | 10 | 
| Hero          | 15 | 
| Equipment     | 20 | 
| Match recorrd | 10 | 

### 6.2 Data Storage Method

program in`DataInitializer` .


---

## 7. AI Usage Plan



| AI character               | what to do      | can not do |
|----------------------------|-----------------|------------|
| **Architect Agent**        | Give suggestions on class design and feedback for UML diagrams  | 	I make all final design decisions |
| **Implementation Agent**   | Generate code snippets for single methods     | Generate the whole project at once |
| **Testing/Reviewer Agent** | Inspect code for bugs and propose test cases | I verify all test results   |

**AI using roles**：
- When AI respond, I need to understand what does AI do to improve the code.
- keep the history in `ai/prompts.md` and `ai/agent-log.md` 
- Need to understand the programming.

---

## 8. Prompt Strategy

**good way to ask**：
1. **Need to connect with the code that you have written before**：Tell AI the parts that you have written.
2. **Limit**：use AI to achieve the way, don't use it to write am whole class.
3. **Explanation**：Ask AI to explain the behavior.
4. **Erove**：run it by yourself.

**Check**：
- If it can run.
- If it is right.
- Use test example to run it.

---

## 9. Development Timeline

|---| what to do                      |
|---|---------------------------------|
| **Stage 1** | Requirement analysis, create repository, write plan.md (initial)              | 
| **Stage 2** | Ask AI for architecture suggestions & manually adjust project structure              | 
| **Stage 3** | Implement model classes + initialize test data | 
| **Stage 4** | Develop menu system and search functions                    | 
| **Stage 5** | Implement login authentication & permission management                     | 
| **Stage 6** | Develop ranking board and data persistence function                   | 
| **Stage 7** | Review codes with AI assistance, find & fix existing bugs             | 
| **Stage 8** | Finish project documentation, review summary, commit Git records & final testing             | 
---

## 10. Testing Plan

### 10.1 Test example



### 10.2 Test way

- use IDE to run it and use Test AI to test it.
- keep history in `docs/test-cases.md` 

---

## 11. Risk Analysis

- AI may can't understand the meaning.
- AI may make some bugs.
---

## 12. Final Reflection Placeholder



---

*version：1.0*  
*Time：2026-06-04*