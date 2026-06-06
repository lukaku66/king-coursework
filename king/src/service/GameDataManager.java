package service;

// TODO: Framework for GameDataManager
// Purpose: Central hub for all data access. Holds references to all entity lists
//          and provides CRUD operations. Acts as the single source of truth.
//
// Fields to add:
//   - ArrayList<Player> players
//   - ArrayList<Hero> heroes
//   - ArrayList<Team> teams
//   - ArrayList<EquipmentItem> equipment
//   - ArrayList<MatchRecord> matchRecords
//   - ArrayList<Admin> admins
//
// Constructor to add:
//   - Accept DataInitializer or initialize empty lists
//   - Option to call DataInitializer.run() to populate with test data
//
// Methods to add (framework signatures only):
//   - getAllPlayers() / getAllHeroes() / getAllTeams() / getAllEquipment() / getAllMatchRecords() / getAllAdmins()
//   - findPlayerById(String id) / findHeroById(String id) / findTeamById(String id)
//   - addPlayer(Player p) / removePlayer(Player p)
//   - addHero(Hero h) / removeHero(Hero h)
//   - addTeam(Team t) / removeTeam(Team t)
//   - addMatchRecord(MatchRecord mr)
//
// Dependencies: model.*, util.DataInitializer
