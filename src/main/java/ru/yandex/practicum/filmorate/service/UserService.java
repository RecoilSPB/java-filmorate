package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Objects;

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
        User oldUser = userStorage.getById(updatedUser.getId());
        if (oldUser == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", updatedUser.getId()));
        }
//        User existUser = userStorage.getByEmail(updatedUser.getEmail());
//        if (existUser != null && !Objects.equals(updatedUser.getId(), existUser.getId())) {
//            throw new DublicateException(String.format("Пользователь с email %s уже существует", updatedUser.getEmail()));
//        }
        return userStorage.update(updatedUser);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(long id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", id));
        }
        return user;
    }

    public void addFried(long userId, long friendId) {
        log.info("UserService: addFriend user id = {}, friend id = {}", userId, friendId);
        if (Objects.equals(userId, friendId)) {
            throw new IllegalArgumentException("Ид друга совпадает с ид пользователя");
        }
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", friendId));
        }
        if (user.getFriends().contains(friend.getId())) {
            throw new IllegalArgumentException("Пользователь уже добавлен в список друзей");
        }
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(long userId, long friendId) {
        log.info("UserService: deleteFriend user id = {}, friend id = {}", userId, friendId);
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", friendId));
        }
        userStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getAllFriends(Long userId) {
        User user = userStorage.getById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        return user.getFriends()
                .stream()
                .map(userStorage::getById)
                .toList();
    }

    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = userStorage.getById(userId);
        User otherUser = userStorage.getById(otherUserId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", userId));
        }
        if (otherUser == null) {
            throw new NotFoundException(String.format("Пользователь с ид %s не найден", otherUserId));
        }
        return user.getFriends()
                .stream()
                .filter(id -> otherUser.getFriends().contains(id))
                .map(userStorage::getById)
                .toList();
    }
}
