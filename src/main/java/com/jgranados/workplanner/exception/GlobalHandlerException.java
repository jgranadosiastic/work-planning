package com.jgranados.workplanner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.persistence.EntityNotFoundException;
import java.util.AbstractMap;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(ResourceNotFoundException exception) {
        log.error("ExceptionHandler-ResourceNotFoundException: ", exception);
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(ResourceAlreadyExistsException exception) {
        log.error("ExceptionHandler-ResourceAlreadyExistsException: ", exception);
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(InvalidShiftException exception) {
        log.error("ExceptionHandler-InvalidShiftException: ", exception);
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(MethodArgumentNotValidException exception) {
        log.error("ExceptionHandler-MethodArgumentNotValidException: ", exception);
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message",
                        String.format(
                                "There is an error with the parameter %s. Full message: %s.",
                                exception.getParameter().getParameterName(),
                                exception.getMessage()
                        )
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
