package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя");
        return userService.add(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User updatedUser) {
        log.info("Получен запрос на обновление фильма c id: {}", updatedUser.getId());
        return userService.update(updatedUser);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFried(@PathVariable("id") long userId,
                         @PathVariable("friendId") long friendId) {
        // Метод добавления друга
        userService.addFried(userId, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFrieds(@PathVariable("id") long userId,
                             @PathVariable("friendId") long friendId) {
        // Метод удаления друга
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") long userId) {
        // Метод получения списка друзей
        return userService.getAllFriends(userId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") long userId,
                                             @PathVariable("otherId") long otherUserId) {
        // Метод получения списка общих друзей
        return userService.getCommonFriends(userId, otherUserId);
    }

}