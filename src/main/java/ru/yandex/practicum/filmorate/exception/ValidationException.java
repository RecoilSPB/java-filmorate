package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ValidationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ResponseException>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ResponseException> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> {
                            log.error("{} create: field: {}, rejected value: {}", e.getObjectName(), e.getField(), e.getRejectedValue());
                            return new ResponseException(e.getField(), e.getRejectedValue(), e.getDefaultMessage());
                        }
                )
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> handleUserNotFoundException(NotFoundException ex) {
        log.error("User not found exception: {}", ex.getMessage());
        ResponseException responseException = new ResponseException(null, null, ex.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ResponseException> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument exception: {}", ex.getMessage());
        ResponseException responseException = new ResponseException(null, null, ex.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseException> handleGenericException(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage());
        ResponseException responseException = new ResponseException(null, null, ex.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}