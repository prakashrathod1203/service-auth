package in.om.exceptions;

import in.om.component.Translator;
import in.om.response.ResponseBody;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("handleHttpMediaTypeNotSupported() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(status).body(responseBody);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("handleHttpMessageNotReadable() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(status).body(responseBody);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).findFirst().orElse("");
        ResponseBody responseBody = new ResponseBody(error, null, false);
        log.error("handleMethodArgumentNotValid() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<?> constraintViolationException(Exception ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("constraintViolationException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<?> dataIntegrityViolationException(SQLException ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(String.format("%d : %s", ex.getErrorCode(), ex.getLocalizedMessage()), false, null);
        log.error("dataIntegrityViolationException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<?> runtimeException(RuntimeException ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("runtimeException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ExceptionHandler(SomethingWrongException.class)
    public final ResponseEntity<?> internalServerError(RuntimeException ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("internalServerError() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("handleBadCredentialsException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public final ResponseEntity<Object> handleSQLSyntaxErrorException(Exception ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getLocalizedMessage(), null, false);
        log.error("handleSQLSyntaxErrorException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class, SizeLimitExceededException.class})
    public final ResponseEntity<Object> handleFileSizeLimitExceededException(Exception ex, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("file.master.file.size.too.large", maxFileSize), false, null);
        log.error("handleFileSizeLimitExceededException() in GlobalExceptionHandler", responseBody);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
