package ru.serdyuk.diff.advices;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.serdyuk.diff.entities.DiffResult;
import ru.serdyuk.diff.entities.DiffResultStatus;
import ru.serdyuk.diff.exceptions.DiffKeyNotFoundException;
import ru.serdyuk.diff.exceptions.DiffValueIsNotPresentException;

/**
 * Create readable response for external api (contract)
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DiffKeyNotFoundException.class})
    public ResponseEntity<DiffResult> handleDiffKeyNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest()
            .body(DiffResult.builder()
            .status(DiffResultStatus.ERROR)
            .message(Optional.of(ex.getMessage()))
            .build());
    }

    @ExceptionHandler({DiffValueIsNotPresentException.class})
    public ResponseEntity<DiffResult> handleDiffValueIsNotPresentException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(DiffResult.builder()
            .status(DiffResultStatus.ERROR)
            .message(Optional.of(ex.getMessage()))
            .build());
    }
}
