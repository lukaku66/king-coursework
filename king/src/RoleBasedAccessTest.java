import model.*;
import service.*;
import util.DataInitializer;

/**
 * RoleBasedAccessTest — Tests menu access control and role-based permissions.
 *
 * Tests:
 *   1. Admin login → can access data management features
 *   2. Player login → cannot access data management features
 *   3. Unauthenticated → redirected to login menu
 *   4. Admin logout → back to login menu
 *   5. Player logout → back to login menu
 *   6. isAdmin / isPlayer consistency after multiple logins
 */
public class RoleBasedAccessTest {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Role-Based Access Test");
        System.out.println("========================================\n");

        // Initialize
        DataInitializer init = new DataInitializer();
        GameDataManager dataManager = new GameDataManager(init);
        AuthenticationService authService = new AuthenticationService(dataManager);

        int passed = 0;
        int failed = 0;

        // ========== Test 1: Admin can access admin features ==========
        System.out.println("--- Test 1: Admin can access data management ---");
        authService.login("A01", "admin123");
        boolean t1pass = authService.currentUserIsAdmin();
        System.out.println("  Input:      login as A01");
        System.out.println("  Expected:   currentUserIsAdmin() = true");
        System.out.println("  Actual:     currentUserIsAdmin() = " + authService.currentUserIsAdmin());
        System.out.println("  Result:     " + (t1pass ? "PASS ✓" : "FAIL ✗"));
        if (t1pass) passed++; else failed++;
        authService.logout();
        System.out.println();

        // ========== Test 2: Player cannot access admin features ==========
        System.out.println("--- Test 2: Player cannot access data management ---");
        authService.login("P01", "pass123");
        boolean t2pass = !authService.currentUserIsAdmin() && authService.currentUserIsPlayer();
        System.out.println("  Input:      login as P01");
        System.out.println("  Expected:   currentUserIsAdmin() = false, currentUserIsPlayer() = true");
        System.out.println("  Actual:     isAdmin=" + authService.currentUserIsAdmin() + ", isPlayer=" + authService.currentUserIsPlayer());
        System.out.println("  Result:     " + (t2pass ? "PASS ✓" : "FAIL ✗"));
        if (t2pass) passed++; else failed++;
        authService.logout();
        System.out.println();

        // ========== Test 3: Unauthenticated state ==========
        System.out.println("--- Test 3: Unauthenticated state ---");
        Person current = authService.getCurrentUser();
        boolean t3pass = (current == null);
        System.out.println("  Input:      no login");
        System.out.println("  Expected:   getCurrentUser() = null");
        System.out.println("  Actual:     getCurrentUser() = " + (current == null ? "null" : current.getName()));
        System.out.println("  Result:     " + (t3pass ? "PASS ✓" : "FAIL ✗"));
        if (t3pass) passed++; else failed++;
        System.out.println();

        // ========== Test 4: Admin logout returns to unauthenticated ==========
        System.out.println("--- Test 4: Admin logout clears session ---");
        authService.login("A01", "admin123");
        authService.logout();
        boolean t4pass = (authService.getCurrentUser() == null);
        System.out.println("  Input:      login A01 → logout()");
        System.out.println("  Expected:   getCurrentUser() = null after logout");
        System.out.println("  Actual:     getCurrentUser() = " + (authService.getCurrentUser() == null ? "null" : "not null"));
        System.out.println("  Result:     " + (t4pass ? "PASS ✓" : "FAIL ✗"));
        if (t4pass) passed++; else failed++;
        System.out.println();

        // ========== Test 5: Player logout returns to unauthenticated ==========
        System.out.println("--- Test 5: Player logout clears session ---");
        authService.login("P01", "pass123");
        authService.logout();
        boolean t5pass = (authService.getCurrentUser() == null);
        System.out.println("  Input:      login P01 → logout()");
        System.out.println("  Expected:   getCurrentUser() = null after logout");
        System.out.println("  Actual:     getCurrentUser() = " + (authService.getCurrentUser() == null ? "null" : "not null"));
        System.out.println("  Result:     " + (t5pass ? "PASS ✓" : "FAIL ✗"));
        if (t5pass) passed++; else failed++;
        System.out.println();

        // ========== Test 6: Multiple login switches role correctly ==========
        System.out.println("--- Test 6: Multiple login switches role ---");
        authService.login("P01", "pass123");
        boolean step1 = authService.currentUserIsPlayer();
        authService.logout();
        authService.login("A01", "admin123");
        boolean step2 = authService.currentUserIsAdmin();
        authService.logout();
        authService.login("P02", "pass123");
        boolean step3 = authService.currentUserIsPlayer();
        boolean t6pass = step1 && step2 && step3;
        System.out.println("  Input:      P01 → logout → A01 → logout → P02");
        System.out.println("  Expected:   Player → Admin → Player");
        System.out.println("  Actual:     " + (step1 ? "Player" : "?") + " → " + (step2 ? "Admin" : "?") + " → " + (step3 ? "Player" : "?"));
        System.out.println("  Result:     " + (t6pass ? "PASS ✓" : "FAIL ✗"));
        if (t6pass) passed++; else failed++;
        authService.logout();
        System.out.println();

        // ========== Summary ==========
        System.out.println("========================================");
        System.out.println("  Test Summary: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("========================================");
    }
}
