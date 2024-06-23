package tech.sarthee.auth.library.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
