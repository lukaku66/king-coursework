package service;

// TODO: Framework for FileStorageService
// Purpose: Persist application data to disk and load it back.
//          Enables data survival across program restarts.
//
// Fields to add:
//   - String dataDirectory (default: "data/")
//   - String playersFile, heroesFile, teamsFile, equipmentFile, matchesFile, adminsFile
//
// Constructor to add:
//   - FileStorageService() — creates data directory if not exists
//
// Methods to add (framework signatures only):
//   // Save operations
//   - saveAll(GameDataManager manager): void
//       → Delegate to individual save methods
//   - savePlayers(ArrayList<Player> players): void
//   - saveHeroes(ArrayList<Hero> heroes): void
//   - saveTeams(ArrayList<Team> teams): void
//   - saveEquipment(ArrayList<EquipmentItem> equipment): void
//   - saveMatchRecords(ArrayList<MatchRecord> records): void
//   - saveAdmins(ArrayList<Admin> admins): void
//
//   // Load operations
//   - loadAll(): GameDataManager
//       → Create new GameDataManager, populate from files, return it
//   - loadPlayers(): ArrayList<Player>
//   - loadHeroes(): ArrayList<Hero>
//   - loadTeams(): ArrayList<Team>
//   - loadEquipment(): ArrayList<EquipmentItem>
//   - loadMatchRecords(): ArrayList<MatchRecord>
//   - loadAdmins(): ArrayList<Admin>
//
//   // Utility
//   - ensureDirectoryExists(): void
//   - fileExists(String filename): boolean
//
// Implementation notes (for future):
//   - Option A: Java Serialization (simple, binary format)
//   - Option B: CSV format (human-readable, easy to edit)
//   - Option C: JSON (structured, modern approach — requires external library)
//   - For this project, Java Serialization or CSV is recommended
//
// Dependencies: model.*, java.io.*, java.nio.file.*
