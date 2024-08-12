package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.add(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User updatedUser) {
        if (updatedUser == null) {
            String msg = "user update: user is null";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, null, msg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (updatedUser.getId() == null) {
            String msg = "user update: user id is null";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, updatedUser, msg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        User user = userService.update(updatedUser);
        if (user == null) {
            String msg = "user update: user not found";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, updatedUser, msg);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            String msg = "users not found";
            log.error(msg);
            ErrorResponse error = new ErrorResponse(null, null, msg);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}