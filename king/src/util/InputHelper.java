package util;

import exception.ValidationException;
import java.util.Scanner;

/**
 * InputHelper — Utility class for reading user input from the console.
 *
 * Purpose:
 *   Encapsulates Scanner to provide safe input methods with validation.
 *   Prevents duplicate Scanner creation and handles invalid input gracefully.
 *
 * Usage:
 *   String name = InputHelper.readString("Enter name: ");
 *   int choice = InputHelper.readInt("Enter choice: ");
 *   int level = InputHelper.readPositiveInt("Enter level: ");
 */
public class InputHelper {

    private static Scanner scanner = new Scanner(System.in);

    /** Reads a non-empty string from the user. */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /** Reads an integer from the user with validation. */
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Reads a positive integer (>= 0) from the user.
     * Throws ValidationException if negative.
     */
    public static int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < 0) {
                    throw new ValidationException("Value cannot be negative. Please enter a non-negative number.");
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Reads a positive double (>= 0) from the user.
     * Throws ValidationException if negative.
     */
    public static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    throw new ValidationException("Value cannot be negative. Please enter a non-negative number.");
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Reads a double from the user with validation. */
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /** Reads any line (allows empty input). */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Press Enter to continue (pause screen). */
    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
