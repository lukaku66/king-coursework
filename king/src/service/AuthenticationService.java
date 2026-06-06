package service;

// TODO: Framework for AuthenticationService
// Purpose: Handle user login and role identification. Validates credentials
//          and returns the authenticated Person (Player or Admin).
//
// Fields to add:
//   - GameDataManager dataManager (reference to access all users)
//
// Constructor to add:
//   - AuthenticationService(GameDataManager dataManager)
//
// Methods to add (framework signatures only):
//   - login(String id, String password): Person
//       → Search all Players and Admins by ID, check password via login()
//       → Return the matching Person, or null if authentication fails
//   - isAdmin(Person person): boolean
//       → Check if person instanceof Admin
//   - isPlayer(Person person): boolean
//       → Check if person instanceof Player
//   - getCurrentUser(): Person (optional, for session tracking)
//
// Dependencies: model.Person, model.Player, model.Admin, service.GameDataManager
