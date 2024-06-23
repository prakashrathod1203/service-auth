package tech.sarthee.auth.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final Integer statusCode;

    public AuthException(String message) {
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public AuthException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
