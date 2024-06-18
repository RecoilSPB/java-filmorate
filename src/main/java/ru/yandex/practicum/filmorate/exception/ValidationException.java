package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Validation error");
        this.errors = errors;
    }

}
