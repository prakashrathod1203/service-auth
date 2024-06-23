package tech.sarthee.auth.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<RestApiResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        logException(exception, ConstraintViolationException.class);
        RestApiResponse response = new RestApiResponse(exception.getLocalizedMessage(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<?> dataIntegrityViolationException(SQLException exception) {
        logException(exception, SQLException.class);
        RestApiResponse response = new RestApiResponse(exception.getLocalizedMessage(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({IOException.class, MalformedURLException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<?> handleFileException(Exception exception) {
        logException(exception, Exception.class);
        RestApiResponse response = new RestApiResponse(exception.getLocalizedMessage(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        logException(exception, ResourceNotFoundException.class);
        RestApiResponse response = new RestApiResponse(exception.getLocalizedMessage(), null, false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public final ResponseEntity<Object> handleSQLSyntaxErrorException(SQLSyntaxErrorException exception) {
        logException(exception, SQLSyntaxErrorException.class);
        RestApiResponse response = new RestApiResponse(exception.getLocalizedMessage(), null, false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class, SizeLimitExceededException.class})
    public final ResponseEntity<Object> handleFileSizeLimitExceededException(Exception exception) {
        logException(exception, Exception.class);
        RestApiResponse response = new RestApiResponse("file.master.file.size.too.large", null, false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private <T extends Exception> void logException(Exception ex, Class<T> clazz) {
        log.warn("Exception {} handled :: {}", clazz.getSimpleName(), ex.getMessage(), ex);
        if (ex.getCause() != null) {
            log.info("Cause handled :: {0}", ex.getCause());
        }
    }
}
