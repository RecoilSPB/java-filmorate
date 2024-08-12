package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.add(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody(required = false) User updatedUser,
                                           BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(e ->
                            log.error("film update: field: {}, rejected value: {}", e.getField(), e.getRejectedValue()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (updatedUser == null) {
            log.error("user update: user is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (updatedUser.getId() == null) {
            log.error("user update: user id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.update(updatedUser);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}