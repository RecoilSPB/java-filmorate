package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        users.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setEmail(updatedUser.getEmail());
                user.setLogin(updatedUser.getLogin());
                user.setName(updatedUser.getName().isEmpty() ? updatedUser.getLogin() : updatedUser.getName());
                user.setBirthday(updatedUser.getBirthday());
                return user;
            }
        }
        return null;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }
}