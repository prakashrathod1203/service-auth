package in.om.exceptions;

/**
 * Throwing custom exception `RecordNotFoundException` with custom message, It will be thrown when record not found in to the table.
 * And also these exceptions will handle in
 * @see GlobalExceptionHandler
 */
public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String message) {
        super(message);
    }
}
