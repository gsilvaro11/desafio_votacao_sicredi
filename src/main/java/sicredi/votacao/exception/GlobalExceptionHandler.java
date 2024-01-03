package sicredi.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleGlobalException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.format("%s: %s", e.getBindingResult().getFieldError().getField(),
                        e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ValidationsGlobalExceptions.class)
    public ResponseEntity<String> handleResourceNotFoundException(ValidationsGlobalExceptions e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
