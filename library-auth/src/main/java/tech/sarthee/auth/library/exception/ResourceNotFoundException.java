package tech.sarthee.auth.library.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5272447637711039999L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
