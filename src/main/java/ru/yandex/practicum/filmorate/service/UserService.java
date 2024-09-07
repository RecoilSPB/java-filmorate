package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User add(User user) {
        log.debug("add User: {}", user);
        return userStorage.add(user);
    }

    public User update(User updatedUser) {
        if (updatedUser == null || updatedUser.getId() == null) {
            String msg = "Invalid user data for update.";
            throw new IllegalArgumentException(msg);
        }
        return userStorage.update(updatedUser);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(long id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с ид %s не найден", id));
        }
        return user;
    }
}
