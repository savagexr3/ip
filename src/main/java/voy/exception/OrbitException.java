package voy.exception;

/**
 * Represents an exception specific to the Orbit application.
 * Used to indicate invalid commands or execution errors.
 */
public class OrbitException extends Exception {

    /**
     * Constructs an OrbitException with the specified error message.
     *
     * @param message Description of the error.
     */
    public OrbitException(String message) {
        super(message);
    }
}

