package ru.yandex.practicum.filmorate.exception;

public class DublicateException extends RuntimeException {
    public DublicateException(String message) {
        super(message);
    }
}