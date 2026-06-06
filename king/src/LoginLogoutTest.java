import model.*;
import service.*;
import util.DataInitializer;

/**
 * LoginLogoutTest — Tests login and logout functionality only.
 * Automated test without user input.
 */
public class LoginLogoutTest {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Login / Logout Test");
        System.out.println("========================================\n");

        // Initialize
        DataInitializer init = new DataInitializer();
        GameDataManager dataManager = new GameDataManager(init);
        FileStorageService storageService = new FileStorageService();
        AuthenticationService authService = new AuthenticationService(dataManager);

        int passed = 0;
        int failed = 0;

        // ========== Test 1: Admin Login ==========
        System.out.println("--- Test 1: Admin Login (A01 / admin123) ---");
        Person admin = authService.login("A01", "admin123");
        boolean t1pass = (admin != null && authService.isAdmin(admin));
        System.out.println("  Input:      id=\"A01\", password=\"admin123\"");
        System.out.println("  Expected:   Login success, role=Admin");
        System.out.println("  Actual:     " + (admin != null ? "Login success, name=" + admin.getName() + ", role=" + admin.getRole() : "Login failed"));
        System.out.println("  Result:     " + (t1pass ? "PASS ✓" : "FAIL ✗"));
        if (t1pass) passed++; else failed++;
        System.out.println();

        // ========== Test 2: Admin Logout ==========
        System.out.println("--- Test 2: Admin Logout ---");
        authService.logout();
        Person afterAdminLogout = authService.getCurrentUser();
        boolean t2pass = (afterAdminLogout == null);
        System.out.println("  Input:      logout()");
        System.out.println("  Expected:   currentUser = null");
        System.out.println("  Actual:     currentUser = " + (afterAdminLogout == null ? "null" : afterAdminLogout.getName()));
        System.out.println("  Result:     " + (t2pass ? "PASS ✓" : "FAIL ✗"));
        if (t2pass) passed++; else failed++;
        System.out.println();

        // ========== Test 3: Player Login ==========
        System.out.println("--- Test 3: Player Login (P01 / pass123) ---");
        Person player = authService.login("P01", "pass123");
        boolean t3pass = (player != null && authService.isPlayer(player));
        System.out.println("  Input:      id=\"P01\", password=\"pass123\"");
        System.out.println("  Expected:   Login success, role=Player");
        System.out.println("  Actual:     " + (player != null ? "Login success, name=" + player.getName() + ", role=" + player.getRole() : "Login failed"));
        System.out.println("  Result:     " + (t3pass ? "PASS ✓" : "FAIL ✗"));
        if (t3pass) passed++; else failed++;
        System.out.println();

        // ========== Test 4: Player Logout ==========
        System.out.println("--- Test 4: Player Logout ---");
        authService.logout();
        Person afterPlayerLogout = authService.getCurrentUser();
        boolean t4pass = (afterPlayerLogout == null);
        System.out.println("  Input:      logout()");
        System.out.println("  Expected:   currentUser = null");
        System.out.println("  Actual:     currentUser = " + (afterPlayerLogout == null ? "null" : afterPlayerLogout.getName()));
        System.out.println("  Result:     " + (t4pass ? "PASS ✓" : "FAIL ✗"));
        if (t4pass) passed++; else failed++;
        System.out.println();

        // ========== Test 5: Wrong Password ==========
        System.out.println("--- Test 5: Wrong Password (P01 / wrong) ---");
        Person wrong = authService.login("P01", "wrong");
        boolean t5pass = (wrong == null);
        System.out.println("  Input:      id=\"P01\", password=\"wrong\"");
        System.out.println("  Expected:   Login failed (null)");
        System.out.println("  Actual:     " + (wrong == null ? "Login failed (null)" : "Login success, name=" + wrong.getName()));
        System.out.println("  Result:     " + (t5pass ? "PASS ✓" : "FAIL ✗"));
        if (t5pass) passed++; else failed++;
        System.out.println();

        // ========== Test 6: Non-existent ID ==========
        System.out.println("--- Test 6: Non-existent ID (X99 / any) ---");
        Person notFound = authService.login("X99", "any");
        boolean t6pass = (notFound == null);
        System.out.println("  Input:      id=\"X99\", password=\"any\"");
        System.out.println("  Expected:   Login failed (null)");
        System.out.println("  Actual:     " + (notFound == null ? "Login failed (null)" : "Login success, name=" + notFound.getName()));
        System.out.println("  Result:     " + (t6pass ? "PASS ✓" : "FAIL ✗"));
        if (t6pass) passed++; else failed++;
        System.out.println();

        // ========== Test 7: currentUserIsAdmin after admin login ==========
        System.out.println("--- Test 7: currentUserIsAdmin() after Admin login ---");
        authService.login("A01", "admin123");
        boolean t7pass = authService.currentUserIsAdmin() && !authService.currentUserIsPlayer();
        System.out.println("  Input:      login as A01, then currentUserIsAdmin()");
        System.out.println("  Expected:   currentUserIsAdmin()=true, currentUserIsPlayer()=false");
        System.out.println("  Actual:     currentUserIsAdmin()=" + authService.currentUserIsAdmin() + ", currentUserIsPlayer()=" + authService.currentUserIsPlayer());
        System.out.println("  Result:     " + (t7pass ? "PASS ✓" : "FAIL ✗"));
        if (t7pass) passed++; else failed++;
        authService.logout();
        System.out.println();

        // ========== Test 8: currentUserIsPlayer after player login ==========
        System.out.println("--- Test 8: currentUserIsPlayer() after Player login ---");
        authService.login("P01", "pass123");
        boolean t8pass = authService.currentUserIsPlayer() && !authService.currentUserIsAdmin();
        System.out.println("  Input:      login as P01, then currentUserIsPlayer()");
        System.out.println("  Expected:   currentUserIsPlayer()=true, currentUserIsAdmin()=false");
        System.out.println("  Actual:     currentUserIsPlayer()=" + authService.currentUserIsPlayer() + ", currentUserIsAdmin()=" + authService.currentUserIsAdmin());
        System.out.println("  Result:     " + (t8pass ? "PASS ✓" : "FAIL ✗"));
        if (t8pass) passed++; else failed++;
        authService.logout();
        System.out.println();

        // ========== Summary ==========
        System.out.println("========================================");
        System.out.println("  Test Summary: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("========================================");
    }
}
