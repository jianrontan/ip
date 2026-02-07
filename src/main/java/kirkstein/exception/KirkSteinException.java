package kirkstein.exception;

/**
 * Represents errors that occur during KirkStein chatbot operations.
 * This exception is thrown when user input is invalid or when task operations fail.
 */
public class KirkSteinException extends Exception {
    /**
     * Creates a KirkSteinException with the specified error message.
     *
     * @param message The error message.
     */
    public KirkSteinException(String message) {
        super(message);
    }
}
