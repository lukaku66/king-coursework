package service;

import model.Person;
import model.Player;
import model.Admin;

/**
 * AuthenticationService — Handles user login and role identification.
 *
 * Single Responsibility:
 *   Validates credentials and returns the authenticated Person.
 *   Does not manage sessions or UI logic.
 */
public class AuthenticationService {

    private GameDataManager dataManager;
    private Person currentUser;

    /** Constructor injection — receives the central data hub. */
    public AuthenticationService(GameDataManager dataManager) {
        this.dataManager = dataManager;
        this.currentUser = null;
    }

    /**
     * Authenticates a user by ID and password.
     *
     * @param id       the user ID
     * @param password the plain-text password
     * @return the authenticated Person (Player or Admin), or null if failed
     */
    public Person login(String id, String password) {
        for (Person person : dataManager.getAllPersons()) {
            if (person.getId().equals(id) && person.login(password)) {
                currentUser = person;
                return person;
            }
        }
        return null;
    }

    /** Logs out the current user. */
    public void logout() {
        currentUser = null;
    }

    /** Returns the currently logged-in user, or null if none. */
    public Person getCurrentUser() {
        return currentUser;
    }

    /** Checks whether the given person is an Admin. */
    public boolean isAdmin(Person person) {
        return person instanceof Admin;
    }

    /** Checks whether the given person is a Player. */
    public boolean isPlayer(Person person) {
        return person instanceof Player;
    }

    /** Convenience: checks if the currently logged-in user is an Admin. */
    public boolean currentUserIsAdmin() {
        return currentUser != null && isAdmin(currentUser);
    }

    /** Convenience: checks if the currently logged-in user is a Player. */
    public boolean currentUserIsPlayer() {
        return currentUser != null && isPlayer(currentUser);
    }
}
