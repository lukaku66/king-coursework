**Test Cases Document**

Honor of Kings Information Management System

Course: Java OOP Project \| Last Updated: 2026-06-07

Test Session 1: Login / Logout

**Date:** 2026-06-07

**Test File:** LoginLogoutTest.java

**Component Under Test:** AuthenticationService (login, logout, isAdmin,
isPlayer, currentUserIsAdmin, currentUserIsPlayer)

**Result: 8 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Admin Login | id="A01", password="admin123" | Login success, role=Admin | Login success, name=SuperAdmin, role=Admin | **PASS** |
| 2 | Admin Logout | logout() | currentUser = null | currentUser = null | **PASS** |
| 3 | Player Login | id="P01", password="pass123" | Login success, role=Player | Login success, name=Zhang Wei, role=Player | **PASS** |
| 4 | Player Logout | logout() | currentUser = null | currentUser = null | **PASS** |
| 5 | Wrong Password | id="P01", password="wrong" | Login failed (null) | Login failed (null) | **PASS** |
| 6 | Non-existent ID | id="X99", password="any" | Login failed (null) | Login failed (null) | **PASS** |
| 7 | isAdmin after Admin login | login as A01, then currentUserIsAdmin() | isAdmin=true, isPlayer=false | isAdmin=true, isPlayer=false | **PASS** |
| 8 | isPlayer after Player login | login as P01, then currentUserIsPlayer() | isPlayer=true, isAdmin=false | isPlayer=true, isAdmin=false | **PASS** |

Notes

- All 8 test cases passed on first run.

- Compilation fix: Team class was missing "implements Searchable" —
  added during this session.

- Test data: 2 Admins (A01, A02), 10 Players (P01-P10), all initialized
  via DataInitializer.

Test Session 2: Role-Based Access

**Date:** 2026-06-07

**Test File:** RoleBasedAccessTest.java

**Component Under Test:** AuthenticationService (currentUserIsAdmin,
currentUserIsPlayer, getCurrentUser, logout)

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Admin can access data management | login as A01 | currentUserIsAdmin() = true | currentUserIsAdmin() = true | **PASS** |
| 2 | Player cannot access admin features | login as P01 | isAdmin=false, isPlayer=true | isAdmin=false, isPlayer=true | **PASS** |
| 3 | Unauthenticated state | no login | getCurrentUser() = null | getCurrentUser() = null | **PASS** |
| 4 | Admin logout clears session | login A01 → logout() | getCurrentUser() = null after logout | getCurrentUser() = null | **PASS** |
| 5 | Player logout clears session | login P01 → logout() | getCurrentUser() = null after logout | getCurrentUser() = null | **PASS** |
| 6 | Multiple login switches role | P01 → logout → A01 → logout → P02 | Player → Admin → Player | Player → Admin → Player | **PASS** |

Notes

- All 6 test cases passed on first run.

- Tested role switching across multiple login/logout cycles.

- Session state correctly cleared after each logout.

Test Session 3: Search Player (F1)

**Date:** 2026-06-07

**Test File:** SearchPlayerTest.java

**Component Under Test:** SearchService (searchById, searchByName,
searchPlayersByTeam, fuzzySearchByName)

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | searchById finds P01 | searchById(players, "P01") | found, id=P01 | found, id=P01 | **PASS** |
| 2 | searchById returns null for X99 | searchById(players, "X99") | null | null | **PASS** |
| 3 | searchByName finds Zhang Wei | searchByName(players, "Zhang Wei") | found, name=Zhang Wei | found, name=Zhang Wei | **PASS** |
| 4 | searchPlayersByTeam T01 | searchPlayersByTeam("T01") | 4 players | 4 players | **PASS** |
| 5 | searchPlayersByTeam T99 (non-existent) | searchPlayersByTeam("T99") | empty list | 0 players | **PASS** |
| 6 | fuzzySearchByName "Li" | fuzzySearchByName(players, "Li") | \>= 2 matches | 3 matches | **PASS** |

Notes

- All 6 test cases passed on first run.

- Fuzzy search found 3 matches for "Li" (Li Na, Li Bai, Li Na in
  players).

- Team-based search correctly returned 4 players for Dragon Riders
  (T01).

Test Session 4: Search Team (F2)

**Date:** 2026-06-07

**Test File:** SearchTeamTest.java

**Component Under Test:** SearchService (searchTeamByName) + Team
(getPlayerCount, getAverageLevel, getTopPlayer)

**Result: 5 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | searchTeamByName "Dragon Riders" | searchTeamByName("Dragon Riders") | found, id=T01 | found, id=T01 | **PASS** |
| 2 | searchTeamByName case insensitive | searchTeamByName("dragon riders") | found (case insensitive) | found | **PASS** |
| 3 | searchTeamByName non-existent | searchTeamByName("Unknown Team") | null | null | **PASS** |
| 4 | Team stats (T01) | Team T01 stats | 4 players, avgLevel \> 0 | 4 players, avgLevel=27.5 | **PASS** |
| 5 | getTopPlayer T01 | getTopPlayer() | not null | Zhang Wei | **PASS** |

Notes

- All 5 test cases passed on first run.

- Case-insensitive search works correctly.

- Team stats calculation verified: 4 players, avg level 27.5.

Test Session 5: Search Hero (F3)

**Date:** 2026-06-07

**Test File:** SearchHeroTest.java

**Component Under Test:** SearchService (searchById, searchHeroesByType,
searchHeroesByOwner) + Hero (getCompatibleEquipment, getOwners)

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | searchById finds H01 | searchById(heroes, "H01") | found, id=H01 | found, id=H01 | **PASS** |
| 2 | searchHeroesByType TANK | searchHeroesByType(TANK) | 3 heroes | 3 heroes | **PASS** |
| 3 | searchHeroesByType MAGE | searchHeroesByType(MAGE) | 3 heroes | 3 heroes | **PASS** |
| 4 | searchHeroesByOwner P01 | searchHeroesByOwner("P01") | 2 heroes | 2 heroes | **PASS** |
| 5 | Hero H01 compatible equipment | H01.getCompatibleEquipment() | 3 items | 3 items | **PASS** |
| 6 | Hero H01 owners | H01.getOwners() | \>= 1 owner | 1 owners | **PASS** |

Notes

- All 6 test cases passed on first run.

- Hero type distribution verified: TANK×3, WARRIOR×3, ASSASSIN×3,
  MAGE×3, MARKSMAN×2, SUPPORT×1.

- Bidirectional association verified: Hero H01 has 1 owner (P01) and 3
  compatible equipment items.

Test Session 6: Search Equipment (F4)

**Date:** 2026-06-07

**Test File:** SearchEquipmentTest.java

**Component Under Test:** GameDataManager (getEquipment,
findEquipmentById) + EquipmentItem (getType, getAttackBonus,
getDefenseBonus)

**Result: 5 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Total equipment count | getEquipment().size() | 20 | 20 | **PASS** |
| 2 | findEquipmentById EQ01 | findEquipmentById("EQ01") | Infinite Blade | Infinite Blade | **PASS** |
| 3 | Count ATTACK equipment | count ATTACK type | 5 | 5 | **PASS** |
| 4 | Count DEFENSE equipment | count DEFENSE type | 5 | 5 | **PASS** |
| 5 | Equipment EQ01 stats | EQ01 stats | ATK+80, DEF+0 | ATK+80, DEF+0 | **PASS** |

Notes

- All 5 test cases passed on first run.

- Equipment distribution: ATTACK×5, DEFENSE×5, MAGIC×5, MOVEMENT×3,
  JUNGLE×2.

- Equipment stats verified: Infinite Blade (EQ01) has ATK+80, DEF+0.

Test Session 7: Search Match (F5)

**Date:** 2026-06-07

**Test File:** SearchMatchTest.java

**Component Under Test:** SearchService (searchMatchesByTeam,
searchMatchesByDateRange) + MatchRecord (getWinner, isTeamInvolved)

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | searchMatchesByTeam T01 | searchMatchesByTeam("T01") | \>= 6 matches | 6 matches | **PASS** |
| 2 | searchMatchesByTeam T02 | searchMatchesByTeam("T02") | \>= 6 matches | 7 matches | **PASS** |
| 3 | searchMatchesByDateRange | 2026-05-20 to 2026-05-25 | 6 matches | 6 matches | **PASS** |
| 4 | MatchRecord winner M01 | M01 winner | T01 | T01 | **PASS** |
| 5 | Draw match M04 winner is null | M04 winner | null (draw) | null | **PASS** |
| 6 | isTeamInvolved | M01.isTeamInvolved(T01, T02) | both true | T01=true, T02=true | **PASS** |

Notes

- All 6 test cases passed on first run.

- Date range search returned exactly 6 matches for 2026-05-20 to
  2026-05-25.

- Draw match (M04) correctly has null winner.

- Winner derivation from MatchResult enum verified: TEAM_A_WINS →
  winner=teamA.

Test Session 8: Rankings (F6)

**Date:** 2026-06-08

**Test File:** RankingTest.java

**Component Under Test:** RankingService (rankPlayersByWinRate,
rankPlayersByLevel, rankPlayersByMatchCount, rankPlayersByHeroCount,
rankTeamsByWinRate, rankTeamsByAverageLevel, rankTeamsByTotalMatches,
getTopNPlayers)

**Result: 10 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | rankPlayersByWinRate | rs.rankPlayersByWinRate() | top=Chen Jie, winRate=0.78 | top=Chen Jie(0.78) | **PASS** |
| 2 | rankPlayersByLevel | rs.rankPlayersByLevel() | top level=32 (Chen Jie) | top level=32 (Chen Jie) | **PASS** |
| 3 | rankPlayersByMatchCount | rs.rankPlayersByMatchCount() | descending order | descending order verified | **PASS** |
| 4 | rankPlayersByHeroCount | rs.rankPlayersByHeroCount() | descending order | descending order verified | **PASS** |
| 5 | getTopNPlayers winRate | getTopNPlayers(3, "winRate") | 3 players, sorted | 3 players, sorted | **PASS** |
| 6 | getTopNPlayers invalid criteria | getTopNPlayers(5, "invalid") | empty list | empty list | **PASS** |
| 7 | rankTeamsByWinRate | rs.rankTeamsByWinRate() | descending order | descending order verified | **PASS** |
| 8 | rankTeamsByAverageLevel | rs.rankTeamsByAverageLevel() | descending order | descending order verified | **PASS** |
| 9 | rankTeamsByTotalMatches | rs.rankTeamsByTotalMatches() | descending order | descending order verified | **PASS** |
| 10 | getTopNPlayers level | getTopNPlayers(2, "level") | 2 players, sorted by level | 2 players, sorted by level | **PASS** |

Notes

- All 10 test cases passed on first run.

- Player ranking: Chen Jie has highest winRate (0.78) and highest level
  (32).

- Team rankings verified: all sorted in descending order using Java
  Comparator.

- getTopNPlayers correctly handles invalid criteria by returning empty
  list.

Test Session 9: Admin Data Management (F7a)

**Date:** 2026-06-08

**Test File:** DataMgmtTest.java

**Component Under Test:** GameDataManager (addPlayer, removePlayer,
addHero, removeHero, addTeam, removeTeam) + AuthenticationService

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Admin adds new player | addPlayer(P99) | player added, findable | P99 added and found | **PASS** |
| 2 | Duplicate player rejected | addPlayer(P01 again) | false (duplicate) | false | **PASS** |
| 3 | Admin removes player with cascade | removePlayer(P99) | removed, not found | removed, P99=null | **PASS** |
| 4 | Admin adds new hero | addHero(H99) | hero added, findable | H99 added and found | **PASS** |
| 5 | Admin removes hero | removeHero(H99) | removed, not found | removed, H99=null | **PASS** |
| 6 | Admin removes team with cascade | add+remove T99 | removed, not found | removed, T99=null | **PASS** |

Notes

- All 6 test cases passed on first run.

- Admin CRUD verified: add/remove player, hero, team all work correctly.

- Duplicate check works: adding P01 again returns false.

- Cascade cleanup verified: removing player cleans up team and hero
  associations.

Test Session 10: Player Self-Management (F7b)

**Date:** 2026-06-08

**Test File:** DataMgmtTest.java

**Component Under Test:** AuthenticationService (login, logout,
getCurrentUser, currentUserIsAdmin) + Player (getHeroes, getTeam,
setPassword)

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Player views own info | login P01, getCurrentUser | id=P01, role=Player | id=P01, role=Player | **PASS** |
| 2 | Player changes password | setPassword + re-login | re-login success | re-login success | **PASS** |
| 3 | Player role check | currentUserIsAdmin() | false | false | **PASS** |
| 4 | Player views own heroes | getHeroes() | 2 heroes | 2 heroes | **PASS** |
| 5 | Player views team info | getTeam() | Dragon Riders | Dragon Riders | **PASS** |
| 6 | Unauthenticated no access | logout, getCurrentUser | null | null | **PASS** |

Notes

- All 6 test cases passed on first run.

- Player can view own info, heroes (2), and team (Dragon Riders).

- Player can change password and re-login successfully.

- Role-based access verified: Player is not admin, unauthenticated user
  has no access.

Test Session 11: Bidirectional Sync

**Date:** 2026-06-09

**Test File:** SystemEdgeTest.java

**Component Under Test:** Team (addPlayer, removePlayer), Player
(addHero, removeHero), GameDataManager cascade cleanup

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | Team.addPlayer sync | team.addPlayer(player) | player.team = team | player.team synced | **PASS** |
| 2 | Team.removePlayer sync | team.removePlayer(player) | player.team = null | player.team cleared | **PASS** |
| 3 | Player.addHero sync | player.addHero(hero) | hero.owners contains player | owner link synced | **PASS** |
| 4 | Player.removeHero sync | player.removeHero(hero) | hero.owners excludes player | owner link removed | **PASS** |
| 5 | removePlayer cascade | removePlayer(P01) | team and hero links cleaned | links cleaned | **PASS** |
| 6 | removeHero cascade | removeHero(H10) | player hero list cleaned | hero removed from player | **PASS** |

Notes

- All 6 test cases passed.

- Team-Player and Player-Hero bidirectional links were synchronized
  correctly.

- Cascade cleanup works when removing players and heroes through
  GameDataManager.

Test Session 12: Save Data (F8a)

**Date:** 2026-06-09

**Test File:** SystemEdgeTest.java

**Component Under Test:** FileStorageService (saveAll) + data directory
behavior

**Result: 2 PASSED, 1 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | saveAll no exception | storage.saveAll(manager) | no exception | no exception | **PASS** |
| 2 | data directory exists | new FileStorageService() | data/ exists | data/ exists | **PASS** |
| 3 | save creates files | saveAll after full data init | saved data files exist | 0 files; save is stub | **FAIL** |

Notes

- saveAll can be called without exception and creates/uses the data
  directory.

- Functional persistence failed: FileStorageService.saveAll is currently
  a stub and does not create saved data files.

- Recommended fix: implement Serializable-based or text/CSV-based save
  logic for all core lists.

Test Session 13: Load Data (F8b)

**Date:** 2026-06-09

**Test File:** SystemEdgeTest.java

**Component Under Test:** FileStorageService (loadAll) + GameDataManager
loaded data validation

**Result: 1 PASSED, 2 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | loadAll returns manager | storage.loadAll() | not null | not null | **PASS** |
| 2 | loaded count matches saved | load after save | 60 entities | 0 entities; load returns empty manager | **FAIL** |
| 3 | loaded data contains P01 | findPlayerById("P01") after load | P01 found | null; data not persisted | **FAIL** |

Notes

- loadAll returns a non-null GameDataManager object.

- Functional loading failed: loadAll returns an empty manager instead of
  restoring saved data.

- This confirms FileStorageService must be implemented before final
  persistence testing can pass.

Test Session 14: Edge Cases

**Date:** 2026-06-09

**Test File:** SystemEdgeTest.java

**Component Under Test:** GameDataManager, SearchService,
AuthenticationService, MatchRecord edge behavior

**Result: 6 PASSED, 0 FAILED**

Test Cases

|  |  |  |  |  |  |
|:--:|:--:|:--:|:--:|:--:|:--:|
| **\#** | **Test Case** | **Input** | **Expected Output** | **Actual Output** | **Result** |
| 1 | missing player ID | findPlayerById("NO_SUCH_PLAYER") | null | null | **PASS** |
| 2 | add null player | addPlayer(null) | false | false | **PASS** |
| 3 | remove null player | removePlayer(null) | false | false | **PASS** |
| 4 | fuzzy search no match | fuzzySearchByName("NO_MATCH") | empty list | empty list | **PASS** |
| 5 | wrong password login | login("P01", "wrong-password") | null | null | **PASS** |
| 6 | draw match winner | M04.getWinner() | null, winnerName=Draw | null, winnerName=Draw | **PASS** |

Notes

- All 6 edge case tests passed.

- Null inputs, missing IDs, wrong password, no-match search, and draw
  winner handling behave safely.
