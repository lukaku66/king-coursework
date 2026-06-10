package exception;

/**
 * ValidationException — Custom exception for input validation errors.
 *
 * Purpose:
 *   Thrown when user input fails validation rules (e.g., negative level,
 *   empty string, invalid ID format). Provides a clear error message
 *   that can be displayed to the user.
 *
 * Usage:
 *   throw new ValidationException("Level must be a positive integer.");
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
